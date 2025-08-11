package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.TrainProgram;
import com.example.simpleDemo.service.TrainProgramService;
import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.utils.PageInfoResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TrainProgramController {

    private static final Logger logger = LoggerFactory.getLogger(TrainProgramController.class);

    @Autowired
    private TrainProgramService trainProgramService;

    @GetMapping("/trainPrograms")
    public ResponseEntity<ApiResponse<PageInfoResult<TrainProgram>>> getTrainPrograms(
            @RequestParam(required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(required = true, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String semester) {
        logger.info("Get train programs endpoint accessed with params: pageNum={}, pageSize={}, name={}, semester={}",
                pageNum, pageSize, name, semester);
        try {
            PageInfoResult<TrainProgram> result = trainProgramService.findTrainProgramsWithPageHelper(
                    pageNum,
                    pageSize,
                    name,
                    semester);
            ApiResponse<PageInfoResult<TrainProgram>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching train programs", e);
            ApiResponse<PageInfoResult<TrainProgram>> response = ApiResponse.error("Failed to fetch train programs");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}