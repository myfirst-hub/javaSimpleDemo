package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.KnowledgeTree;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KnowledgeTreeMapper {
    
    List<KnowledgeTree> findAll();
    
    KnowledgeTree findById(Long id);
    
    void insert(KnowledgeTree knowledgeTree);
    
    // 添加按父ID查询子节点的方法
    List<KnowledgeTree> findByParentId(@Param("parentId") Long parentId);
    
    // 添加查询根节点的方法（parentId为null的节点）
    List<KnowledgeTree> findRootNodes();
    
    // 添加根据ID列表查询根节点的方法
    List<KnowledgeTree> findRootNodesByIds(@Param("ids") List<Long> ids);
    
    // 补充更新知识点的方法
    void update(KnowledgeTree knowledgeTree);
}