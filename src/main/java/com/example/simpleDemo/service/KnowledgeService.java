package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.Knowledge;
import com.example.simpleDemo.mapper.KnowledgeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeService {
    
    @Autowired
    private KnowledgeMapper knowledgeMapper;
    
    /**
     * 根据ID查询知识点
     *
     * @param id 知识点ID
     * @return 知识点列表
     */
    public List<Knowledge> findKnowledges(Integer id) {
        return knowledgeMapper.findKnowledges(id);
    }
    
    /**
     * 新增知识点
     *
     * @param knowledge 知识点对象
     * @return 是否添加成功
     */
    public boolean addKnowledge(Knowledge knowledge) {
        return knowledgeMapper.insertKnowledge(knowledge) > 0;
    }
    
    /**
     * 插入知识点
     *
     * @param knowledge 知识点对象
     * @return 影响的行数
     */
    public int insertKnowledge(Knowledge knowledge) {
        return knowledgeMapper.insertKnowledge(knowledge);
    }
    
    /**
     * 根据ID更新知识点
     *
     * @param knowledge 知识点对象
     * @return 影响的行数
     */
    public int updateKnowledgeById(Knowledge knowledge) {
        return knowledgeMapper.updateKnowledgeById(knowledge);
    }
}