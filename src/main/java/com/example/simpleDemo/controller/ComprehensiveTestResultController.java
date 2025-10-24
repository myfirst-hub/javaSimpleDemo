package com.example.simpleDemo.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.service.ComprehensiveTestResultService;
import com.example.simpleDemo.entity.ComprehensiveTestResult;

@RestController
public class ComprehensiveTestResultController {
    private static final Logger logger = LoggerFactory.getLogger(ComprehensiveTestResultController.class);

    @Autowired
    private ComprehensiveTestResultService comprehensiveTestResultService;

    // 新增：上传Excel并解析插入综合测评结果接口
    @PostMapping("/comprehensive/upload")
    public ResponseEntity<ApiResponse<String>> uploadComprehensiveTestResult(
            @RequestParam("file") MultipartFile file,
            @RequestParam("subjectId") Long subjectId,
            @RequestParam("classId") Long classId) {
        logger.info("Upload comprehensive test result excel endpoint accessed");
        try {
            if (file == null || file.isEmpty()) {
                ApiResponse<String> response = ApiResponse.error("上传文件为空");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            String fileName = file.getOriginalFilename();
            if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
                ApiResponse<String> response = ApiResponse.error("文件格式不正确，请上传Excel文件");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            int result = comprehensiveTestResultService.importFromExcel(file, subjectId, classId);
            ApiResponse<String> response = ApiResponse.success("成功导入" + result + "条记录");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while uploading comprehensive test result excel", e);
            ApiResponse<String> response = ApiResponse.error("导入失败：" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据学生ID和科目ID查询综合测评结果接口，支持参数为空
    @GetMapping("/comprehensive/findListByStudentIdAndTeacherId")
    public ResponseEntity<ApiResponse<List<ComprehensiveTestResult>>> getComprehensiveTestResultsByTeacherId(
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value = "teacherId", required = false) Long teacherId) {
        logger.info("Get comprehensive test results by studentId and subjectId endpoint accessed");
        try {
            List<ComprehensiveTestResult> results = comprehensiveTestResultService
                    .getComprehensiveTestResultsByStudentIdAndTeacherId(studentId, teacherId);
            ApiResponse<List<ComprehensiveTestResult>> response = ApiResponse.success(results);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while getting comprehensive test results", e);
            ApiResponse<List<ComprehensiveTestResult>> response = ApiResponse.error("查询失败：" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 根据学生ID和科目ID查询综合测评结果接口，支持参数为空
    @GetMapping("/comprehensive/findListByStudentIdAndSubjectId")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getComprehensiveTestResultsBySubjectId(
            @RequestParam(value = "studentId", required = false) Long studentId,
            @RequestParam(value = "subjectId", required = false) Long subjectId) {
        logger.info("Get comprehensive test results by studentId and subjectId endpoint accessed");
        try {
            Map<String, Object> results = comprehensiveTestResultService
                    .getComprehensiveTestResultsByStudentIdAndSubjectId(studentId, subjectId);
            ApiResponse<Map<String, Object>> response = ApiResponse.success(results);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while getting comprehensive test results", e);
            ApiResponse<Map<String, Object>> response = ApiResponse.error("查询失败：" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}