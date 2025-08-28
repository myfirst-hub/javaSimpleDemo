package com.example.simpleDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.simpleDemo.entity.Classes;
import com.example.simpleDemo.entity.Teacher;
import com.example.simpleDemo.service.ClassStudentService;
import com.example.simpleDemo.utils.ApiResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
public class ClassStudentController {

    private static final Logger logger = LoggerFactory.getLogger(ClassStudentController.class);

    @Autowired
    private ClassStudentService classStudentService;

    // 根据班级ID查询学生ID列表
    @GetMapping("/class/studentIds")
    public ResponseEntity<ApiResponse<List<Long>>> getStudentIdsByClassId(@RequestParam Long id) {
        logger.info("Get all studentIds endpoint accessed with params: classId={}", id);
        try {
            List<Long> result = classStudentService.findStudentIdsByClassId(id);
            ApiResponse<List<Long>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching all studentIds", e);
            ApiResponse<List<Long>> response = ApiResponse.error("Failed to fetch all studentIds");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}