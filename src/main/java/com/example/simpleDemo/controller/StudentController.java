package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.Student;
import com.example.simpleDemo.service.StudentService;
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
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public ResponseEntity<ApiResponse<PageInfoResult<Student>>> getStudents(
            @RequestParam(required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(required = true, defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String className) {
        logger.info("Get students endpoint accessed with params: pageNum={}, pageSize={}, name={}, className={}",
                pageNum, pageSize, name, className);
        try {
            PageInfoResult<Student> result = studentService.findStudentsWithPageHelper(
                    pageNum,
                    pageSize,
                    name,
                    className);
            ApiResponse<PageInfoResult<Student>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching students", e);
            ApiResponse<PageInfoResult<Student>> response = ApiResponse.error("Failed to fetch students");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 新增不使用分页查询所有学生的接口
    @GetMapping("/students/all")
    public ResponseEntity<ApiResponse<List<Student>>> getAllStudents(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String className) {
        logger.info("Get all students endpoint accessed with params: name={}, className={}", name, className);
        try {
            List<Student> result = studentService.findAllStudents(name, className);
            ApiResponse<List<Student>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching all students", e);
            ApiResponse<List<Student>> response = ApiResponse.error("Failed to fetch all students");
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

    @PostMapping("/student/delete")
    public ResponseEntity<ApiResponse<String>> deleteStudent(@RequestBody Student student) {
        logger.info("Delete student endpoint accessed with id: {}", student.getId());
        try {
            // 检查学生ID是否存在
            if (student.getId() == null) {
                ApiResponse<String> response = ApiResponse.error("Student ID is required for delete");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // 检查学生是否存在
            Student existingStudent = studentService.findStudentById(student.getId());
            if (existingStudent == null) {
                ApiResponse<String> response = ApiResponse.error("Student not found with ID: " + student.getId());
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

            // 调用服务删除学生
            int result = studentService.deleteStudentById(student.getId());

            if (result > 0) {
                // 删除成功
                ApiResponse<String> response = ApiResponse.success("Student deleted successfully");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // 删除失败
                ApiResponse<String> response = ApiResponse.error("Failed to delete student");
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error occurred while deleting student", e);
            ApiResponse<String> response = ApiResponse.error("Failed to delete student: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * 根据教师ID查询学生列表（分页）
     * 通过教师ID关联查询班级ID，根据班级ID查询学生列表
     */
    @GetMapping("/students/byTeacherId/list")
    public ResponseEntity<ApiResponse<PageInfoResult<Student>>> getStudentsByTeacherId(
            @RequestParam Long teacherId,
            @RequestParam(required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(required = true, defaultValue = "10") Integer pageSize) {
        logger.info("Get students by teacher id endpoint accessed with teacherId: {}, pageNum: {}, pageSize: {}",
                teacherId, pageNum, pageSize);
        try {
            PageInfoResult<Student> result = studentService.findStudentsByTeacherIdWithPage(teacherId, pageNum,
                    pageSize);
            ApiResponse<PageInfoResult<Student>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching students by teacher id", e);
            ApiResponse<PageInfoResult<Student>> response = ApiResponse
                    .error("Failed to fetch students by teacher id: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}