package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.Knowledge;
import com.example.simpleDemo.service.KnowledgeService;
import com.example.simpleDemo.utils.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/knowledges")
public class KnowledgeController {
    
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeController.class);
    
    @Autowired
    private KnowledgeService knowledgeService;
    
    /**
     * 查询知识点列表
     * 
     * @param id 知识点ID（可选）
     * @return 知识点列表
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<Knowledge>>> findKnowledges(
            @RequestParam(required = false) Integer id) {
        
        logger.info("Get knowledges endpoint accessed with params: id={}", id);
        
        try {
            List<Knowledge> knowledges = knowledgeService.findKnowledges(id);
            ApiResponse<List<Knowledge>> response = ApiResponse.success(knowledges);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching knowledges", e);
            ApiResponse<List<Knowledge>> response = ApiResponse.error("Failed to fetch knowledges");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * 新增知识点
     * 
     * @param knowledge 知识点对象
     * @return 是否添加成功
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Boolean>> addKnowledge(@RequestBody Knowledge knowledge) {
        
        logger.info("Add knowledge endpoint accessed with params: knowledge={}", knowledge);
        
        try {
            boolean result = knowledgeService.addKnowledge(knowledge);
            ApiResponse<Boolean> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while adding knowledge", e);
            ApiResponse<Boolean> response = ApiResponse.error("Failed to add knowledge");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}