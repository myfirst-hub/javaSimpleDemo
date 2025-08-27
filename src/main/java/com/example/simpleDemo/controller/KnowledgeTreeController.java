package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.KnowledgeTree;
import com.example.simpleDemo.service.KnowledgeTreeService;
import com.example.simpleDemo.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
public class KnowledgeTreeController {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeTreeController.class);

    @Autowired
    private KnowledgeTreeService knowledgeTreeService;

    // 插入新的知识点
    @PostMapping("/knowledgeTree/add")
    public ResponseEntity<ApiResponse<KnowledgeTree>> createKnowledgeTree(@RequestBody KnowledgeTree knowledgeTree) {
        logger.info("Create knowledge tree endpoint accessed with params: knowledgeTree={}", knowledgeTree);

        try {
            KnowledgeTree createdKnowledgeTree = knowledgeTreeService.createKnowledgeTree(knowledgeTree);
            ApiResponse<KnowledgeTree> response = ApiResponse.success(createdKnowledgeTree);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while creating knowledge tree", e);
            ApiResponse<KnowledgeTree> response = ApiResponse.error("Failed to create knowledge tree");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据ID数组获取指定的知识点树
    @GetMapping("/knowledgeTreeByIds")
    public ResponseEntity<ApiResponse<List<KnowledgeTree>>> getKnowledgeTreeByIds(@RequestParam List<Long> ids) {
        logger.info("Get knowledge tree by ids endpoint accessed with params: ids={}", ids);

        try {
            List<KnowledgeTree> treeStructure = knowledgeTreeService.buildKnowledgeTree(ids);
            ApiResponse<List<KnowledgeTree>> response = ApiResponse.success(treeStructure);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while building knowledge tree structure by ids", e);
            ApiResponse<List<KnowledgeTree>> response = ApiResponse
                    .error("Failed to build knowledge tree structure by ids");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据知识点ID编辑知识点
    @PostMapping("/knowledgeTree/edit")
    public ResponseEntity<ApiResponse<KnowledgeTree>> editKnowledgeTree(@RequestBody KnowledgeTree knowledgeTree) {
        logger.info("Edit knowledge tree endpoint accessed with params: knowledgeTree={}", knowledgeTree);

        try {
            KnowledgeTree existingKnowledgeTree = knowledgeTreeService.findById(knowledgeTree.getId());
            if (existingKnowledgeTree == null) {
                ApiResponse<KnowledgeTree> response = ApiResponse
                        .error("Knowledge tree not found with id: " + knowledgeTree.getId());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // 更新知识点信息
            existingKnowledgeTree.setName(knowledgeTree.getName());
            existingKnowledgeTree.setParentId(knowledgeTree.getParentId());
            existingKnowledgeTree.setMasteryLevel(knowledgeTree.getMasteryLevel());
            existingKnowledgeTree.setKnowledgeDescribe(knowledgeTree.getKnowledgeDescribe());
            existingKnowledgeTree.setIsLeaf(knowledgeTree.getLeaf());
            existingKnowledgeTree.setLevel(knowledgeTree.getLevel());

            // 更新时间设置为当前时间
            existingKnowledgeTree.setUpdatedAt(new java.util.Date());

            // 保存更新后的知识点
            KnowledgeTree updatedKnowledgeTree = knowledgeTreeService.updateKnowledgeTree(existingKnowledgeTree);
            ApiResponse<KnowledgeTree> response = ApiResponse.success(updatedKnowledgeTree);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while editing knowledge tree with id: " + knowledgeTree.getId(), e);
            ApiResponse<KnowledgeTree> response = ApiResponse.error("Failed to edit knowledge tree");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}