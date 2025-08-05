package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.Student;
import com.example.simpleDemo.entity.ApiResponse;
import com.example.simpleDemo.entity.PageInfoResult;
import com.example.simpleDemo.entity.StudentPageRequest;
import com.example.simpleDemo.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @PostMapping("/students")
    public ResponseEntity<ApiResponse<PageInfoResult<Student>>> getStudents(
            @RequestBody(required = false) StudentPageRequest request) {
        logger.info("Get students endpoint accessed with request: {}", request);
        try {
            // throw new Exception("Testing error handling");
            if (request == null) {
                request = new StudentPageRequest();
            }

            PageInfoResult<Student> result = studentService.findStudentsWithPageHelper(
                    request.getPageNum(),
                    request.getPageSize(),
                    request.getName(),
                    request.getClassName());
            ApiResponse<PageInfoResult<Student>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching students", e);
            ApiResponse<PageInfoResult<Student>> response = ApiResponse.error("Failed to fetch students");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/student/create")
    public ResponseEntity<ApiResponse<Student>> createStudent(@RequestBody Student student) {
        logger.info("Create student endpoint accessed with student: {}", student);
        try {
            // 调用服务创建学生
            int result = studentService.createStudent(student);

            if (result > 0) {
                // 创建成功
                ApiResponse<Student> response = ApiResponse.success(student, "Student created successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // 创建失败
                ApiResponse<Student> response = ApiResponse.error("Failed to create student");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error occurred while creating student", e);
            ApiResponse<Student> response = ApiResponse.error("Failed to create student: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/student/update")
    public ResponseEntity<ApiResponse<Student>> updateStudent(@RequestBody Student student) {
        logger.info("Update student endpoint accessed with student: {}", student);
        try {
            // 检查学生ID是否存在
            if (student.getId() == null) {
                ApiResponse<Student> response = ApiResponse.error("Student ID is required for update");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // 检查学生是否存在
            Student existingStudent = studentService.findStudentById(student.getId());
            if (existingStudent == null) {
                ApiResponse<Student> response = ApiResponse.error("Student not found with ID: " + student.getId());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // 调用服务更新学生
            int result = studentService.updateStudent(student);

            if (result > 0) {
                // 更新成功
                ApiResponse<Student> response = ApiResponse.success(student, "Student updated successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // 更新失败
                ApiResponse<Student> response = ApiResponse.error("Failed to update student");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error occurred while updating student", e);
            ApiResponse<Student> response = ApiResponse.error("Failed to update student: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}