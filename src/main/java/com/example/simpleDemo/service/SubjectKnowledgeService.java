package com.example.simpleDemo.service;

import com.example.simpleDemo.mapper.SubjectKnowledgeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectKnowledgeService {

    @Autowired
    private SubjectKnowledgeMapper subjectKnowledgeMapper;

    /**
     * 根据科目ID查找关联的知识点ID列表
     * 
     * @param subjectId 科目ID
     * @return 知识点ID列表
     */
    public List<Long> findKnowledgeIdsBySubjectId(Long subjectId) {
        return subjectKnowledgeMapper.findKnowledgeIdsBySubjectId(subjectId);
    }
    
    /**
     * 根据科目ID删除关联的知识点数据
     * 
     * @param subjectId 科目ID
     * @return 删除的记录数
     */
    public int deleteBySubjectId(Long subjectId) {
        if (subjectId == null) {
            throw new IllegalArgumentException("科目ID不能为空");
        }
        return subjectKnowledgeMapper.deleteSubjectKnowledgeBySubjectId(subjectId);
    }
}