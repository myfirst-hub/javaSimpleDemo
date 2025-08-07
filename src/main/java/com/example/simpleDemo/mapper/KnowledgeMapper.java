package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.Knowledge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KnowledgeMapper {
    
    /**
     * 根据ID查询知识点
     * 
     * @param id 知识点ID
     * @return 知识点列表
     */
    List<Knowledge> findKnowledges(@Param("id") Integer id);
    
    /**
     * 新增知识点
     * 
     * @param knowledge 知识点对象
     * @return 影响的行数
     */
    int insertKnowledge(Knowledge knowledge);
}