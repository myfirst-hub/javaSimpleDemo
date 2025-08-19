package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.KnowledgeTree;

import java.util.List;

public interface KnowledgeTreeService {
    
    List<KnowledgeTree> findAll();
    
    KnowledgeTree findById(Long id);
    
    KnowledgeTree createKnowledgeTree(KnowledgeTree knowledgeTree);
}