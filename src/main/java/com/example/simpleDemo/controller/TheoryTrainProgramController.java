package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.TheoryTrainProgram;
import com.example.simpleDemo.mapper.TheoryTrainProgramMapper;
import com.example.simpleDemo.service.TheoryTrainProgramService;
import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.utils.PageInfoResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TheoryTrainProgramController {

    private static final Logger logger = LoggerFactory.getLogger(TheoryTrainProgramController.class);

    @Autowired
    private TheoryTrainProgramService theoryTrainProgramService;

    @GetMapping("/theoryTrainProgram/list")
    public ResponseEntity<ApiResponse<PageInfoResult<TheoryTrainProgram>>> getTrainProgramsWithRelations(
            @RequestParam(required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(required = true, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String semester) {
        logger.info(
                "Get train programs with relations endpoint accessed with params: pageNum={}, pageSize={}, name={}, semester={}",
                pageNum, pageSize, name, semester);
        try {
            PageInfoResult<TheoryTrainProgram> result = theoryTrainProgramService.selectTheoryTrainProgramList(
                    pageNum,
                    pageSize,
                    name,
                    semester);
            ApiResponse<PageInfoResult<TheoryTrainProgram>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching train programs with relations", e);
            ApiResponse<PageInfoResult<TheoryTrainProgram>> response = ApiResponse
                    .error("Failed to fetch train programs with relations");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}