package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.Teacher;
import com.example.simpleDemo.service.TeacherService;
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
public class TeacherController {

    private static final Logger logger = LoggerFactory.getLogger(TeacherController.class);

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/teacher/list")
    public ResponseEntity<ApiResponse<PageInfoResult<Teacher>>> getTeachers(
            @RequestParam(required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(required = true, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String subject) {
        logger.info("Get teachers endpoint accessed with params: pageNum={}, pageSize={}, name={}, subject={}",
                pageNum, pageSize, name, subject);
        try {
            PageInfoResult<Teacher> result = teacherService.findTeachersWithPageHelper(
                    pageNum,
                    pageSize,
                    name,
                    subject);
            ApiResponse<PageInfoResult<Teacher>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching teachers", e);
            ApiResponse<PageInfoResult<Teacher>> response = ApiResponse.error("Failed to fetch teachers");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 新增不使用分页查询所有教师的接口
    @GetMapping("/teacher/allList")
    public ResponseEntity<ApiResponse<List<Teacher>>> getAllTeachers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String subject) {
        logger.info("Get all teachers endpoint accessed with params: name={}, subject={}", name, subject);
        try {
            List<Teacher> result = teacherService.findAllTeachers(name, subject);
            ApiResponse<List<Teacher>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching all teachers", e);
            ApiResponse<List<Teacher>> response = ApiResponse.error("Failed to fetch all teachers");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}