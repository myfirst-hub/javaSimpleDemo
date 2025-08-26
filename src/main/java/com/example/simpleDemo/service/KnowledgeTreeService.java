package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.KnowledgeTree;

import java.util.List;

public interface KnowledgeTreeService {
    
    List<KnowledgeTree> findAll();
    
    KnowledgeTree findById(Long id);
    
    KnowledgeTree createKnowledgeTree(KnowledgeTree knowledgeTree);
    
    // 添加构建树形结构的方法
    List<KnowledgeTree> buildKnowledgeTree();
    
    // 添加根据ID列表构建树形结构的方法
    List<KnowledgeTree> buildKnowledgeTree(List<Long> ids);
    
    // 添加根据父ID查找子节点的方法
    List<KnowledgeTree> findByParentId(Long parentId);
}