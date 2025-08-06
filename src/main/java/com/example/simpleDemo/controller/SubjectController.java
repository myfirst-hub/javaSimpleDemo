package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.Subject;
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
     * 分页查询科目列表
     * 
     * @param pageNum  页码，默认为1
     * @param pageSize 每页大小，默认为10
     * @return 分页结果
     */
    @GetMapping("/subjects")
    public ResponseEntity<ApiResponse<PageInfoResult<Subject>>> findSubjects(
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name) {
        
        logger.info("Get subjects endpoint accessed with params: pageNum={}, pageSize={}, name={}", 
                pageNum, pageSize, name);
        
        try {
            PageInfoResult<Subject> result;
            if (name != null && !name.trim().isEmpty()) {
                result = subjectService.findSubjectsByName(name.trim(), pageNum, pageSize);
            } else {
                result = subjectService.findSubjects(pageNum, pageSize);
            }
            
            ApiResponse<PageInfoResult<Subject>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching subjects", e);
            ApiResponse<PageInfoResult<Subject>> response = ApiResponse.error("Failed to fetch subjects");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}