package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.SubjectWithKnowledgesDTO;
import com.example.simpleDemo.service.SubjectService;
import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.utils.PageInfoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class SubjectController {

    private static final Logger logger = LoggerFactory.getLogger(SubjectController.class);

    @Autowired
    private SubjectService subjectService;

    /**
     * 分页查询科目列表，包含知识点信息
     * 
     * @param pageNum  页码，默认为1
     * @param pageSize 每页大小，默认为10
     * @param name     科目名称（可选）
     * @param semester 学期（可选）
     * @return 分页结果，包含知识点信息
     */
    @GetMapping("/subjects")
    public ResponseEntity<ApiResponse<PageInfoResult<SubjectService.SubjectWithKnowledges>>> findSubjectsWithKnowledges(
            @RequestParam(required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(required = true, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String semester) {

        logger.info(
                "Get subjects with knowledges endpoint accessed with params: pageNum={}, pageSize={}, name={}, semester={}",
                pageNum, pageSize, name, semester);

        try {
            PageInfoResult<SubjectService.SubjectWithKnowledges> result = subjectService
                    .findSubjectsWithKnowledges(pageNum, pageSize, name, semester);

            ApiResponse<PageInfoResult<SubjectService.SubjectWithKnowledges>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching subjects with knowledges", e);
            ApiResponse<PageInfoResult<SubjectService.SubjectWithKnowledges>> response = ApiResponse
                    .error("Failed to fetch subjects with knowledges");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 新增科目，同时处理知识点和科目知识点映射关系
     * 
     * @param subjectWithKnowledgesDTO 包含科目和知识点信息的DTO对象
     * @return 是否添加成功
     */
    @PostMapping("/subject/create")
    public ResponseEntity<ApiResponse<Boolean>> addSubjectWithKnowledges(
            @RequestBody SubjectWithKnowledgesDTO subjectWithKnowledgesDTO) {

        logger.info("Add subject with knowledges endpoint accessed with params: subjectWithKnowledgesDTO={}",
                subjectWithKnowledgesDTO);

        try {
            boolean result = subjectService.addSubjectWithKnowledges(subjectWithKnowledgesDTO);
            ApiResponse<Boolean> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while adding subject with knowledges", e);
            ApiResponse<Boolean> response = ApiResponse.error("Failed to add subject with knowledges");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}