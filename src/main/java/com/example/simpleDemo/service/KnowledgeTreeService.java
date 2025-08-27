package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.KnowledgeTree;
import com.example.simpleDemo.mapper.KnowledgeTreeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KnowledgeTreeService {

    @Autowired
    private KnowledgeTreeMapper knowledgeTreeMapper;

    public List<KnowledgeTree> findAll() {
        return knowledgeTreeMapper.findAll();
    }

    public KnowledgeTree findById(Long id) {
        return knowledgeTreeMapper.findById(id);
    }

    public KnowledgeTree createKnowledgeTree(KnowledgeTree knowledgeTree) {
        // 设置创建时间和更新时间
        Date now = new Date();
        knowledgeTree.setCreatedAt(now);
        knowledgeTree.setUpdatedAt(now);

        // 如果parentId为null或0，设置为null表示根节点
        if (knowledgeTree.getParentId() == null || knowledgeTree.getParentId() == 0L) {
            knowledgeTree.setParentId(null);
        }

        // 插入知识点到数据库
        knowledgeTreeMapper.insert(knowledgeTree);

        // 返回保存后的知识点对象
        return knowledgeTree;
    }

    // 添加更新知识点的方法
    public KnowledgeTree updateKnowledgeTree(KnowledgeTree knowledgeTree) {
        // 设置更新时间
        knowledgeTree.setUpdatedAt(new Date());
        
        // 如果parentId为null或0，设置为null表示根节点
        if (knowledgeTree.getParentId() == null || knowledgeTree.getParentId() == 0L) {
            knowledgeTree.setParentId(null);
        }
        
        // 更新知识点到数据库
        knowledgeTreeMapper.update(knowledgeTree);
        
        // 返回更新后的知识点对象
        return knowledgeTree;
    }

    public List<KnowledgeTree> findByParentId(Long parentId) {
        return knowledgeTreeMapper.findByParentId(parentId);
    }

    public List<KnowledgeTree> buildKnowledgeTree(List<Long> ids) {
        // 根据ID列表获取指定的根节点
        List<KnowledgeTree> rootNodes = knowledgeTreeMapper.findRootNodesByIds(ids);

        // 为每个根节点递归构建子树
        for (KnowledgeTree rootNode : rootNodes) {
            buildChildren(rootNode);
        }

        return rootNodes;
    }

    /**
     * 递归构建知识点树的子节点
     * 
     * @param parent 父节点
     */
    private void buildChildren(KnowledgeTree parent) {
        List<KnowledgeTree> children = knowledgeTreeMapper.findByParentId(parent.getId());
        if (children != null && !children.isEmpty()) {
            // 设置子节点集合
            parent.setChildren(children);
            for (KnowledgeTree child : children) {
                buildChildren(child);
            }
        }
    }

    /**
     * 计算指定根节点列表中所有叶子节点的数量总和
     * 
     * @param ids 根节点ID列表
     * @return 叶子节点总数
     */
    public int countLeafNodes(List<Long> ids) {
        // 获取指定的根节点
        List<KnowledgeTree> rootNodes = knowledgeTreeMapper.findRootNodesByIds(ids);

        int totalLeafCount = 0;
        // 为每个根节点递归计算叶子节点数量
        for (KnowledgeTree rootNode : rootNodes) {
            totalLeafCount += countLeafNodesRecursive(rootNode);
        }

        return totalLeafCount;
    }

    /**
     * 递归计算单个节点下所有叶子节点的数量
     * 
     * @param node 知识点节点
     * @return 该节点下叶子节点的数量
     */
    private int countLeafNodesRecursive(KnowledgeTree node) {
        // 主动从数据库查询子节点，确保数据完整性
        List<KnowledgeTree> children = knowledgeTreeMapper.findByParentId(node.getId());

        // 如果没有子节点，说明是叶子节点
        if (children == null || children.isEmpty()) {
            return 1;
        }

        int count = 0;
        // 递归计算每个子节点的叶子节点数量
        for (KnowledgeTree child : children) {
            count += countLeafNodesRecursive(child);
        }

        return count;
    }
}