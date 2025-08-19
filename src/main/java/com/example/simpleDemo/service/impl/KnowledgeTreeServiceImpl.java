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
}