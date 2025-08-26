package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.SubjectKnowledge;
import com.example.simpleDemo.entity.Knowledge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubjectKnowledgeMapper {
    // 根据科目ID查询关联的知识点
    List<Knowledge> findKnowledgesBySubjectId(@Param("subjectId") Long subjectId);

    // 查询科目和知识点的关联信息
    List<SubjectKnowledge> findSubjectKnowledges(@Param("subjectId") Long subjectId,
            @Param("knowledgeId") Long knowledgeId);

    // 插入科目知识点关联
    int insertSubjectKnowledge(SubjectKnowledge subjectKnowledge);

    // 根据ID删除关联
    int deleteSubjectKnowledgeById(@Param("id") Long id);

    // 根据科目ID删除关联
    int deleteSubjectKnowledgeBySubjectId(@Param("subjectId") Long subjectId);

    // 根据科目ID查询关联的知识点ID列表
    List<Long> findKnowledgeIdsBySubjectId(@Param("subjectId") Long subjectId);
}