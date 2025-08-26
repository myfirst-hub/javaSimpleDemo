package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.KnowledgeTree;
import com.example.simpleDemo.entity.SubjectKnowledge;
import com.example.simpleDemo.entity.SubjectOutline;
import com.example.simpleDemo.mapper.SubjectKnowledgeMapper;
import com.example.simpleDemo.mapper.SubjectOutlineMapper;
import com.example.simpleDemo.utils.UtilCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SubjectOutlineService {

  @Autowired
  private SubjectOutlineMapper subjectOutlineMapper;

  @Autowired
  private KnowledgeTreeService knowledgeTreeService;

  @Autowired
  private SubjectKnowledgeMapper subjectKnowledgeMapper;

  @Autowired
  private TransferService transferService;

  private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

  /**
   * 插入一个SubjectOutline数据到数据库
   * 
   * @param subjectOutline 要插入的数据对象
   * @return 插入成功的记录数
   */
  public int insertSubjectOutline(SubjectOutline subjectOutline) {
    // 设置创建时间和更新时间
    Date now = new Date();
    subjectOutline.setCreatedAt(now);
    subjectOutline.setUpdatedAt(now);

    // 调用Mapper插入数据
    return subjectOutlineMapper.insertSubjectOutline(subjectOutline);
  }

  /**
   * 启动轮询任务，每10秒检查一次，3分钟后停止并修改uploadStatus为1
   * 
   * @param id SubjectOutline记录的ID
   */
  public void startPollingToUpdateStatus(Long id, Long subjectId) {
    AtomicInteger counter = new AtomicInteger(0);
    // 每10秒执行一次，总共执行18次（3分钟）
    scheduler.scheduleAtFixedRate(() -> {
      int count = counter.incrementAndGet();
      // 轮询逻辑可以在这里添加实际的检查条件
      System.out.println("Polling attempt: " + count);

      // 1分钟后（18次，每次10秒）停止轮询并更新状态
      if (count >= 1) {

        // 停止轮询任务
        scheduler.shutdown();

        updateUploadStatus(id);

        String jsonStr = transferService.fetchKnowledge();

        // 在方法内部添加 try-catch 处理 JSON 解析异常
        try {
          UtilCustom uc = new UtilCustom();
          ObjectNode rootNode = uc.JsonTransformer(jsonStr, "物理");

          // // 从根节点开始遍历
          KnowledgeTree node = saveKnowledgeTree(rootNode, null, 0);

          SubjectKnowledge subjectKnowledge = new SubjectKnowledge();
          subjectKnowledge.setSubjectId(subjectId);
          subjectKnowledge.setKnowledgeId(node.getId());
          subjectKnowledgeMapper.insertSubjectKnowledge(subjectKnowledge);
        } catch (JsonProcessingException e) {
          System.err.println("JSON解析失败: " + e.getMessage());
          e.printStackTrace();
        } catch (Exception e) {
          System.err.println("处理过程中发生错误: " + e.getMessage());
          e.printStackTrace();
        }
      }
    }, 0, 10, TimeUnit.SECONDS);
  }

  /**
   * 更新指定记录的uploadStatus为1
   * 
   * @param id SubjectOutline记录的ID
   */
  private void updateUploadStatus(Long id) {
    try {
      SubjectOutline subjectOutline = new SubjectOutline();
      subjectOutline.setId(id);
      subjectOutline.setUploadStatus(1);
      subjectOutline.setUpdatedAt(new Date());

      // 这里需要在Mapper中添加更新方法
      subjectOutlineMapper.updateUploadStatusById(subjectOutline);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 递归保存知识点树到数据库
   *
   * @param node     当前节点
   * @param parentId 父节点ID
   * @param level    节点层级
   * @return 保存的KnowledgeTree节点
   */
  private KnowledgeTree saveKnowledgeTree(JsonNode node, Long parentId, int level) {
    KnowledgeTree knowledgeTree = new KnowledgeTree();
    knowledgeTree.setName(node.get("name").asText());
    if (node.has("masteryLevel")) {
      knowledgeTree.setMasteryLevel(node.get("masteryLevel").asInt());
    }
    knowledgeTree.setParentId(parentId);
    // 设置层级信息
    knowledgeTree.setLevel(level);

    knowledgeTree.setIsLeaf(false);

    if (!node.has("children")) {
      knowledgeTree.setIsLeaf(true);
    }

    // 保存当前节点
    KnowledgeTree savedNode = knowledgeTreeService.createKnowledgeTree(knowledgeTree);

    // 递归处理子节点
    if (node.has("children")) {
      JsonNode childrenNode = node.get("children");
      if (childrenNode.isArray()) {
        for (JsonNode childNode : childrenNode) {
          saveKnowledgeTree(childNode, savedNode.getId(), level + 1);
        }
      }
    }

    return savedNode;
  }

  /**
   * 根据科目ID查找所有关联的大纲ID
   * 
   * @param subjectId 科目ID
   * @return 大纲ID列表
   */
  public List<Long> findOutlineIdsBySubjectId(Long subjectId) {
    return subjectOutlineMapper.selectOutlineIdsBySubjectId(subjectId);
  }
}