package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.KnowledgeTree;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KnowledgeTreeMapper {
    
    List<KnowledgeTree> findAll();
    
    KnowledgeTree findById(Long id);
    
    void insert(KnowledgeTree knowledgeTree);
}