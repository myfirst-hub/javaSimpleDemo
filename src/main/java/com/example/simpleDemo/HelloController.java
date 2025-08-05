package com.example.simpleDemo;

import com.example.simpleDemo.entity.Student;
import com.example.simpleDemo.entity.ApiResponse;
import com.example.simpleDemo.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private StudentService studentService;

    @GetMapping("/hello")
    public String hello() {
        logger.info("Hello endpoint accessed");
        return "Hello World!";
    }

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents() {
        logger.info("Get all students endpoint accessed");
        try {
            List<Student> students = studentService.findAllStudents();
            if (students.isEmpty()) {
                ApiResponse<List<Student>> response = ApiResponse.success(students, "No students found");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            ApiResponse<List<Student>> response = ApiResponse.success(students);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching students", e);
            ApiResponse<List<Student>> response = ApiResponse.error("Failed to fetch students");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}