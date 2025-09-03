package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.SubjectQuestion;
import com.example.simpleDemo.service.SubjectQuestionService;
import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.utils.PageInfoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class SubjectQuestionController {

    private static final Logger logger = LoggerFactory.getLogger(SubjectQuestionController.class);

    @Autowired
    private SubjectQuestionService subjectQuestionService;

    /**
     * 根据subjectId分页查询试题列表
     *
     * @param pageNum   页码，默认为1
     * @param pageSize  每页大小，默认为10
     * @param subjectId 科目ID
     * @return 分页结果
     */
    @GetMapping("/subjectQuestion/list")
    public ResponseEntity<ApiResponse<PageInfoResult<SubjectQuestion>>> findSubjectQuestions(
            @RequestParam(required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(required = true, defaultValue = "10") Integer pageSize,
            @RequestParam(required = true) Long subjectId) {

        logger.info(
                "Get subject questions endpoint accessed with params: pageNum={}, pageSize={}, subjectId={}",
                pageNum, pageSize, subjectId);

        try {
            PageInfoResult<SubjectQuestion> result = subjectQuestionService.findBySubjectId(subjectId, pageNum,
                    pageSize);
            ApiResponse<PageInfoResult<SubjectQuestion>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching subject questions", e);
            ApiResponse<PageInfoResult<SubjectQuestion>> response = ApiResponse
                    .error("Failed to fetch subject questions");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}