package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.TrainProgram;
import com.example.simpleDemo.entity.TrainProgramCreateDTO;
import com.example.simpleDemo.entity.TrainProgramWithRelationsDTO;
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

    @GetMapping("/trainProgramsWithRelations")
    public ResponseEntity<ApiResponse<PageInfoResult<TrainProgramWithRelationsDTO>>> getTrainProgramsWithRelations(
            @RequestParam(required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(required = true, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String semester) {
        logger.info(
                "Get train programs with relations endpoint accessed with params: pageNum={}, pageSize={}, name={}, semester={}",
                pageNum, pageSize, name, semester);
        try {
            PageInfoResult<TrainProgramWithRelationsDTO> result = trainProgramService.findTrainProgramsWithRelations(
                    pageNum,
                    pageSize,
                    name,
                    semester);
            ApiResponse<PageInfoResult<TrainProgramWithRelationsDTO>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching train programs with relations", e);
            ApiResponse<PageInfoResult<TrainProgramWithRelationsDTO>> response = ApiResponse
                    .error("Failed to fetch train programs with relations");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/trainProgram/create")
    public ResponseEntity<ApiResponse<TrainProgram>> createTrainProgram(@RequestBody TrainProgramCreateDTO createDTO) {
        logger.info("Create train program endpoint accessed with data: {}", createDTO);
        try {
            TrainProgram result = trainProgramService.createTrainProgram(createDTO);
            ApiResponse<TrainProgram> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while creating train program", e);
            ApiResponse<TrainProgram> response = ApiResponse.error("Failed to create train program");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}