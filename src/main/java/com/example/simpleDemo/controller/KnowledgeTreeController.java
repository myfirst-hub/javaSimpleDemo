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
@RequestMapping("/api/knowledge-tree")
public class KnowledgeTreeController {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeTreeController.class);

    @Autowired
    private KnowledgeTreeService knowledgeTreeService;

    // 查询所有知识点
    @GetMapping
    public ResponseEntity<ApiResponse<List<KnowledgeTree>>> getAllKnowledgeTrees() {
        logger.info("Get all knowledge trees endpoint accessed");

        try {
            List<KnowledgeTree> knowledgeTrees = knowledgeTreeService.findAll();
            ApiResponse<List<KnowledgeTree>> response = ApiResponse.success(knowledgeTrees);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching knowledge trees", e);
            ApiResponse<List<KnowledgeTree>> response = ApiResponse.error("Failed to fetch knowledge trees");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据ID查询知识点
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KnowledgeTree>> getKnowledgeTreeById(@PathVariable Long id) {
        logger.info("Get knowledge tree by id endpoint accessed with id: {}", id);

        try {
            KnowledgeTree knowledgeTree = knowledgeTreeService.findById(id);
            if (knowledgeTree != null) {
                ApiResponse<KnowledgeTree> response = ApiResponse.success(knowledgeTree);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                ApiResponse<KnowledgeTree> response = ApiResponse.error("Knowledge tree not found");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching knowledge tree by id: {}", id, e);
            ApiResponse<KnowledgeTree> response = ApiResponse.error("Failed to fetch knowledge tree");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 插入新的知识点
    @PostMapping
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
}