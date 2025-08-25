package com.example.simpleDemo.service.impl;

import com.example.simpleDemo.entity.KnowledgeTree;
import com.example.simpleDemo.mapper.KnowledgeTreeMapper;
import com.example.simpleDemo.service.KnowledgeTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KnowledgeTreeServiceImpl implements KnowledgeTreeService {

    @Autowired
    private KnowledgeTreeMapper knowledgeTreeMapper;

    @Override
    public List<KnowledgeTree> findAll() {
        return knowledgeTreeMapper.findAll();
    }

    @Override
    public KnowledgeTree findById(Long id) {
        return knowledgeTreeMapper.findById(id);
    }

    @Override
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
    
    @Override
    public List<KnowledgeTree> findByParentId(Long parentId) {
        return knowledgeTreeMapper.findByParentId(parentId);
    }
    
    @Override
    public List<KnowledgeTree> buildKnowledgeTree() {
        // 先获取所有根节点
        List<KnowledgeTree> rootNodes = knowledgeTreeMapper.findRootNodes();
        
        // 为每个根节点递归构建子树
        for (KnowledgeTree rootNode : rootNodes) {
            buildChildren(rootNode);
        }
        
        return rootNodes;
    }
    
    /**
     * 递归构建知识点树的子节点
     * @param parent 父节点
     */
    private void buildChildren(KnowledgeTree parent) {
        List<KnowledgeTree> children = knowledgeTreeMapper.findByParentId(parent.getId());
        if (children != null && !children.isEmpty()) {
            parent.setLeaf(false);
            // 设置子节点集合
            parent.setChildren(children);
            for (KnowledgeTree child : children) {
                buildChildren(child);
            }
        } else {
            parent.setLeaf(true);
        }
    }
}