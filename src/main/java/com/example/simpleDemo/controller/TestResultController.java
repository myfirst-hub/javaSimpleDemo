package com.example.simpleDemo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.simpleDemo.dto.TeacherCommentResultDTO;
import com.example.simpleDemo.service.TestResultService;
import com.example.simpleDemo.service.TheoryTrainProgramService;
import com.example.simpleDemo.utils.ApiResponse;

@RestController
public class TestResultController {
    private static final Logger logger = LoggerFactory.getLogger(TestResultController.class);

    @Autowired
    private TestResultService testResultService;

    // 新增：上传Excel并解析插入数据接口
    @PostMapping("/teacherComment/uploadTestResult")
    public ResponseEntity<ApiResponse<String>> uploadTeacherCommentExcel(@RequestParam("files") MultipartFile[] files,
            @RequestParam("subjectId") Long subjectId) {
        logger.info("Upload test result excel endpoint accessed");
        try {
            if (files == null || files.length == 0) {
                ApiResponse<String> response = ApiResponse.error("上传文件为空");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            int totalResult = 0;
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }

                String fileName = file.getOriginalFilename();
                if (!fileName.endsWith(".xls") && !fileName.endsWith(".xlsx")) {
                    ApiResponse<String> response = ApiResponse.error("文件格式不正确，请上传Excel文件");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

                int result = testResultService.importTeacherCommentFromExcel(file, subjectId);
                totalResult += result;
            }

            ApiResponse<String> response = ApiResponse.success("成功导入" + totalResult + "条记录");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while uploading test result excel", e);
            ApiResponse<String> response = ApiResponse.error("导入失败：" + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 通过学生id查询教师评语详情
    @GetMapping("/teacherComment/detail")
    public ResponseEntity<ApiResponse<List<TeacherCommentResultDTO>>> getTheoryTestDetailByStudentId(
            @RequestParam(required = false) Long studentId) {
        logger.info(
                "Get theory test detail by teacher id endpoint accessed with studentId: {}",
                studentId);
        try {
            List<TeacherCommentResultDTO> result = testResultService.selectTeacherCommentResultWithDetails(studentId);
            ApiResponse<List<TeacherCommentResultDTO>> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred while fetching theory test detail by teacher id", e);
            ApiResponse<List<TeacherCommentResultDTO>> response = ApiResponse
                    .error("Failed to fetch theory test detail by teacher id: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
