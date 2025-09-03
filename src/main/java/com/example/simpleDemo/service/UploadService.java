package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.KnowledgeTree;
import com.example.simpleDemo.entity.SubjectKnowledge;
import com.example.simpleDemo.entity.SubjectOutline;
import com.example.simpleDemo.entity.SubjectQuestionFile;
import com.example.simpleDemo.entity.SubjectQuestion;
import com.example.simpleDemo.mapper.SubjectKnowledgeMapper;
import com.example.simpleDemo.mapper.UploadMapper;
import com.example.simpleDemo.mapper.SubjectQuestionMapper;
import com.example.simpleDemo.utils.UtilCustom;
import com.example.simpleDemo.enums.UploadType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class UploadService {

  @Autowired
  private UploadMapper uploadMapper;

  @Autowired
  private KnowledgeTreeService knowledgeTreeService;

  @Autowired
  private SubjectKnowledgeMapper subjectKnowledgeMapper;

  @Autowired
  private TransferService transferService;

  @Autowired
  private SubjectQuestionMapper subjectQuestionMapper;

  private ScheduledExecutorService scheduler;

  private static final Logger logger = LoggerFactory.getLogger(UploadService.class);

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
    return uploadMapper.insertSubjectOutline(subjectOutline);
  }

  /**
   * 启动轮询任务，每10秒检查一次，3分钟后停止并修改uploadStatus为1
   * 
   * @param id SubjectOutline记录的ID
   */
  public void startPollingToUpdateStatus(Long id, Long subjectId, Long typeId, UploadType type) {
    AtomicInteger counter = new AtomicInteger(0);
    // 创建新的线程池实例，避免使用已关闭的线程池
    scheduler = Executors.newScheduledThreadPool(1);
    // 每10秒执行一次，总共执行18次（3分钟）
    scheduler.scheduleAtFixedRate(() -> {
      int count = counter.incrementAndGet();
      // 轮询逻辑可以在这里添加实际的检查条件 根据typeId字段查询文件解析的状态
      System.out.println("Polling attempt: " + count + "typeId.........:" + typeId);

      // 1分钟后（18次，每次10秒）停止轮询并更新状态
      if (count >= 1) {

        // 停止轮询任务
        scheduler.shutdown();

        if (type == UploadType.SUBJECT_OUTLINE) {
          dealSubjectOutlineFileResultData(id, subjectId);
        }

        if (type == UploadType.SUBJECT_QUESTION) {
          dealSubjectQuestionFileResultData(id, subjectId);
        }
      }
    }, 0, 10, TimeUnit.SECONDS);
  }

  /**
   * 处理大纲文件解析成功后的结果数据
   * 
   * @param id        SubjectOutline记录的ID
   * @param subjectId 科目id
   */
  private void dealSubjectOutlineFileResultData(Long id, Long subjectId) {
    // 更新指定记录的uploadStatus为1
    SubjectOutline subjectOutline = new SubjectOutline();
    subjectOutline.setId(id);
    subjectOutline.setUploadStatus(1);
    subjectOutline.setUpdatedAt(new Date());
    uploadMapper.updateUploadStatusById(subjectOutline);

    // 获取所有知识点
    String jsonStr = transferService.fetchKnowledge();

    // 在方法内部添加 try-catch 处理 JSON 解析异常
    try {
      UtilCustom uc = new UtilCustom();
      ObjectNode rootNode = uc.JsonTransformer(jsonStr, "物理");

      // 从根节点开始遍历
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
    return uploadMapper.selectOutlineIdsBySubjectId(subjectId);
  }

  /**
   * 根据科目ID删除数据
   * 
   * @param subjectId 科目ID
   * @return 删除的记录数
   */
  public int deleteBySubjectId(Long subjectId) {
    if (subjectId == null) {
      throw new IllegalArgumentException("科目ID不能为空");
    }
    return uploadMapper.deleteBySubjectId(subjectId);
  }

  /**
   * 插入一个SubjectQuestionFile数据到数据库
   * 
   * @param subjectQuestionFile 要插入的数据对象
   * @return 插入成功的记录数
   */
  public int insertSubjectQuestionFile(SubjectQuestionFile subjectQuestionFile) {
    // 设置创建时间和更新时间
    Date now = new Date();
    subjectQuestionFile.setCreatedAt(now);
    subjectQuestionFile.setUpdatedAt(now);

    // 调用Mapper插入数据
    return uploadMapper.insertSubjectQuestionFile(subjectQuestionFile);
  }

  /**
   * 处理试题文件解析成功后的结果数据
   * 
   * @param id        SubjectOutline记录的ID
   * @param subjectId 科目id
   */
  private void dealSubjectQuestionFileResultData(Long id, Long subjectId) {
    // 更新指定记录的uploadStatus为1
    SubjectQuestionFile subjectQuestionFile = new SubjectQuestionFile();
    subjectQuestionFile.setId(id);
    subjectQuestionFile.setUploadStatus(1);
    subjectQuestionFile.setUpdatedAt(new Date());

    // 这里需要在Mapper中添加更新方法
    uploadMapper.updateSubjectQuestionUploadStatusById(subjectQuestionFile);

    // 获取所有知识点
    String jsonStr = transferService.fetchQuestions();
    logger.info("str....fetchQuestions............:" + jsonStr);

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonArray = objectMapper.readTree(jsonStr);

      if (jsonArray.isArray()) {
        // 添加日志记录数组大小
        logger.info("Processing {} questions", jsonArray.size());
        for (JsonNode questionNode : jsonArray) {
          SubjectQuestion subjectQuestion = new SubjectQuestion();
          subjectQuestion.setQuestionCode(questionNode.get("id").asText());
          String questionName = questionNode.get("question").asText();
          subjectQuestion.setQuestionName(questionName);
          subjectQuestion.setAnswer(questionNode.get("answer").asText());
          subjectQuestion.setSubjectId(subjectId);
          // 修复：使用正确的questionId，而不是subjectId
          subjectQuestion.setQuestionId(1l);
          int result = subjectQuestionMapper.insertSubjectQuestion(subjectQuestion);
          // 添加日志记录插入结果
          logger.info("Insert question result: {}", result);
        }
        logger.info("Finished processing {} questions", jsonArray.size());
      } else {
        logger.warn("JSON is not an array");
      }
    } catch (JsonProcessingException e) {
      logger.error("解析JSON失败: " + e.getMessage());
      e.printStackTrace();
    } catch (Exception e) {
      logger.error("处理试题数据时发生错误: " + e.getMessage());
      e.printStackTrace();
    }
  }
}