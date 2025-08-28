package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.SubjectKnowledge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubjectKnowledgeMapper {
    // 插入科目知识点关联
    int insertSubjectKnowledge(SubjectKnowledge subjectKnowledge);

    // 根据ID删除关联
    int deleteSubjectKnowledgeById(@Param("id") Long id);

    // 根据科目ID删除关联
    int deleteSubjectKnowledgeBySubjectId(@Param("subjectId") Long subjectId);

    // 根据科目ID查询关联的知识点ID列表
    List<Long> findKnowledgeIdsBySubjectId(@Param("subjectId") Long subjectId);
}