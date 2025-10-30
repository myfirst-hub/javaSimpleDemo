package com.example.simpleDemo.controller;

import com.example.simpleDemo.entity.Student;
import com.example.simpleDemo.entity.StudentInfo;
import com.example.simpleDemo.entity.Subject;
import com.example.simpleDemo.mapper.SubjectMapper;
import com.example.simpleDemo.mapper.TestResultMapper;
import com.example.simpleDemo.dto.TheoryTestDetailResultDTO;
import com.example.simpleDemo.dto.PracticeTestDetailResultDTO;
import com.example.simpleDemo.service.StudentService;
import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.utils.PageInfoResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    private StudentService studentService;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private TestResultMapper testResultMapper;

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
    public ResponseEntity<ApiResponse<PageInfoResult<StudentInfo>>> getStudentsByTeacherId(
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) String className,
            @RequestParam(required = true, defaultValue = "1") Integer pageNum,
            @RequestParam(required = true, defaultValue = "10") Integer pageSize) {
        logger.info("Get students by teacher id endpoint accessed with teacherId: {}, pageNum: {}, pageSize: {}",
                teacherId, pageNum, pageSize);
        try {
            PageInfoResult<StudentInfo> result = studentService.findStudentsByTeacherIdWithPage(teacherId, studentName,
                    className, pageNum,
                    pageSize);
            ApiResponse<PageInfoResult<StudentInfo>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching students by teacher id", e);
            ApiResponse<PageInfoResult<StudentInfo>> response = ApiResponse
                    .error("Failed to fetch students by teacher id: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 通过学生id查询理论考试详情
    @GetMapping("/students/byTeacherId/Theory/detail")
    public ResponseEntity<ApiResponse<List<TheoryTestDetailResultDTO>>> getTheoryTestDetailByStudentId(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) String className) {
        logger.info(
                "Get theory test detail by teacher id endpoint accessed with studentId: {}, studentName: {}, className: {}",
                studentId, studentName, className);
        try {
            List<TheoryTestDetailResultDTO> result = studentService
                    .findTheoryTestDetailByStudentId(studentId, teacherId, studentName, className);
            ApiResponse<List<TheoryTestDetailResultDTO>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching theory test detail by teacher id", e);
            ApiResponse<List<TheoryTestDetailResultDTO>> response = ApiResponse
                    .error("Failed to fetch theory test detail by teacher id: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 通过学生id查询实操考试详情
    @GetMapping("/students/byTeacherId/practice/detail")
    public ResponseEntity<ApiResponse<List<PracticeTestDetailResultDTO>>> getPracticeTestDetailByStudentId(
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Long teacherId,
            @RequestParam(required = false) String studentName,
            @RequestParam(required = false) String className) {
        logger.info(
                "Get theory test detail by teacher id endpoint accessed with studentId: {}, studentName: {}, className: {}",
                studentId, studentName, className);
        try {
            List<PracticeTestDetailResultDTO> result = studentService
                    .findPracticeTestDetailByStudentId(studentId, teacherId, studentName, className);
            ApiResponse<List<PracticeTestDetailResultDTO>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching theory test detail by teacher id", e);
            ApiResponse<List<PracticeTestDetailResultDTO>> response = ApiResponse
                    .error("Failed to fetch theory test detail by teacher id: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 学员导入
    @PostMapping("/students/import")
    public ResponseEntity<ApiResponse<String>> importStudents(@RequestParam("files") MultipartFile file) {
        logger.info("Import students endpoint accessed with file: {}", file.getOriginalFilename());
        try {
            int count = studentService.importStudentsFromExcel(file);
            ApiResponse<String> response = ApiResponse.success("Successfully imported " + count + " students");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while importing students", e);
            ApiResponse<String> response = ApiResponse.error("Failed to import students: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 查询学员信息总览
    @GetMapping("/students/overview")
    public ResponseEntity<ApiResponse<Object>> getStudentOverview(
            @RequestParam(required = true) Long id,
            @RequestParam(required = false) Long teacherId) {
        logger.info("Get student detail endpoint accessed with params: id={}", id);
        try {
            Student studentBaseInfo = studentService.findStudentById(id);
            List<Subject> subjects = subjectMapper.findSubjectByStudentId(id, teacherId);
            Map<String, Object> theoryTestResult = testResultMapper.selectTestCountAndTrainHoursByTeacherId(id,
                    teacherId);
            Map<String, Object> practiceTestResult = testResultMapper.selectPracticeTestCountAndTrainHoursByTeacherId(
                    id,
                    teacherId);

            // 创建包含学员信息的返回对象
            java.util.Map<String, Object> result = new java.util.HashMap<>();
            result.put("studentBaseInfo", studentBaseInfo);
            result.put("subjects", subjects);
            result.put("theoryTestResult", theoryTestResult);
            result.put("practiceTestResult", practiceTestResult);

            ApiResponse<Object> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching student detail", e);
            ApiResponse<Object> response = ApiResponse.error("Failed to fetch student detail");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}