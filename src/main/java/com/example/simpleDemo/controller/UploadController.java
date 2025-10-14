package com.example.simpleDemo.controller;

import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.service.TransferService;
import com.example.simpleDemo.service.UploadService;
import com.example.simpleDemo.entity.SubjectOutline;
import com.example.simpleDemo.entity.SubjectQuestionFile;
// 添加枚举导入
import com.example.simpleDemo.enums.UploadType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

// 添加Jackson的ObjectMapper导入
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private TransferService transferService;

    @Autowired
    private UploadService uploadService;

    // 注入ObjectMapper用于JSON解析
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/upload/outline")
    public ResponseEntity<ApiResponse<String>> uploadOutlineFile(@RequestParam("files[]") MultipartFile file,
            @RequestParam("subjectId") Long subjectId) {
        logger.info("File upload endpoint accessed with file: {} and subjectId: {}", file.getOriginalFilename(),
                subjectId);

        try {
            // 记录subjectId的值
            logger.info("Received subjectId: {}", subjectId);

            // 直接将MultipartFile传递给TransferService
            String result = transferService.uploadFile(file, "/extract_knowledge");

            // 解析JSON结果并提取original_filename
            try {
                JsonNode jsonNode = objectMapper.readTree(result);
                JsonNode resultsArray = jsonNode.get("results");
                if (resultsArray != null && resultsArray.isArray() && resultsArray.size() > 0) {
                    JsonNode firstResult = resultsArray.get(0);
                    JsonNode originalFilenameNode = firstResult.get("original_filename");
                    // 使用更精确的时间戳
                    long currentTimeMillis = Instant.now().toEpochMilli();
                    if (originalFilenameNode != null) {
                        String originalFilename = originalFilenameNode.asText();
                        logger.info("Extracted original filename: {}", originalFilename);
                        // 使用构造函数创建SubjectOutline对象
                        SubjectOutline st = new SubjectOutline(subjectId, currentTimeMillis, originalFilename, 2);
                        // 插入记录并检查结果
                        int insertResult = uploadService.insertSubjectOutline(st);
                        if (insertResult > 0) {
                            logger.info("Successfully inserted SubjectOutline record with id: {}", st.getId());
                            // 插入成功后启动轮询任务，每10秒检查一次，3分钟后停止并修改uploadStatus为1
                            uploadService.startPollingToUpdateStatus(st.getId(), subjectId, st.getOutlineId(),
                                    UploadType.SUBJECT_OUTLINE);
                        } else {
                            logger.warn("Failed to insert SubjectOutline record");
                        }
                    } else {
                        logger.warn("Original filename not found in the response");
                    }
                } else {
                    logger.warn("Results array is empty or null in the response");
                }
            } catch (Exception e) {
                logger.error("Failed to parse JSON result or insert SubjectOutline record", e);
            }

            logger.info("result uploaded successfully: {}", result);
            logger.info("File uploaded successfully: {}", file.getOriginalFilename());
            ApiResponse<String> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while uploading file", e);
            ApiResponse<String> response = ApiResponse.error("Failed to upload file due to unexpected error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/upload/question")
    public ResponseEntity<ApiResponse<String>> uploadQuestionFile(@RequestParam("files[]") MultipartFile file,
            @RequestParam("subjectId") Long subjectId) {
        logger.info("File upload endpoint accessed with file: {} and subjectId: {}", file.getOriginalFilename(),
                subjectId);

        try {
            // 记录subjectId的值
            logger.info("Received subjectId: {}", subjectId);

            // 直接将MultipartFile传递给TransferService
            String result = transferService.uploadFile(file, "/process_exam_paper_knowledge");

            // 解析JSON结果并提取source_file
            try {
                logger.info("result uploadQuestionFile successfully: {}", result);
                JsonNode jsonNode = objectMapper.readTree(result);
                JsonNode resultsArray = jsonNode.get("results");
                if (resultsArray != null && resultsArray.isArray() && resultsArray.size() > 0) {
                    JsonNode firstResult = resultsArray.get(0);
                    JsonNode originalFilenameNode = firstResult.get("source_file");
                    // 使用更精确的时间戳
                    long currentTimeMillis = Instant.now().toEpochMilli();
                    if (originalFilenameNode != null) {
                        String originalFilename = originalFilenameNode.asText();
                        logger.info("Extracted original filename: {}", originalFilename);
                        // 使用构造函数创建SubjectOutline对象
                        SubjectQuestionFile sq = new SubjectQuestionFile(subjectId, currentTimeMillis,
                                originalFilename, 2);
                        // 插入记录并检查结果
                        int insertResult = uploadService.insertSubjectQuestionFile(sq);
                        if (insertResult > 0) {
                            logger.info("Successfully inserted SubjectOutline record with id: {}",
                                    sq.getId());
                            // 插入成功后启动轮询任务，每10秒检查一次，3分钟后停止并修改uploadStatus为1
                            try {
                                uploadService.startPollingToUpdateStatus(sq.getId(), subjectId, sq.getQuestionId(),
                                        UploadType.SUBJECT_QUESTION);
                            } catch (Exception e) {
                                logger.error("Failed to start polling task", e);
                            }
                        } else {
                            logger.warn("Failed to insert SubjectOutline record");
                        }
                    } else {
                        logger.warn("Original filename not found in the response");
                    }
                } else {
                    logger.warn("Results array is empty or null in the response");
                }
            } catch (Exception e) {
                logger.error("Failed to parse JSON result or insert SubjectOutline record", e);
            }

            logger.info("result uploaded successfully: {}", result);
            logger.info("File uploaded successfully: {}", file.getOriginalFilename());
            ApiResponse<String> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while uploading file", e);
            ApiResponse<String> response = ApiResponse.error("Failed to upload file due to unexpected error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 插入科目大纲关联表数据
    @PostMapping("/insert/outline/batch")
    public ResponseEntity<ApiResponse<String>> insertOutlineRecords(@RequestBody List<SubjectOutline> subjectOutlines) {
        logger.info("Batch insert outline records endpoint accessed with data size: {}", subjectOutlines.size());

        try {
            int successCount = 0;
            for (SubjectOutline subjectOutline : subjectOutlines) {
                // 插入记录并检查结果
                int insertResult = uploadService.insertSubjectOutline(subjectOutline);
                if (insertResult > 0) {
                    successCount++;
                    logger.info("Successfully inserted SubjectOutline record with id: {}", subjectOutline.getId());
                } else {
                    logger.warn("Failed to insert SubjectOutline record: {}", subjectOutline);
                }
            }

            logger.info("Batch insert completed. Success: {}/{}", successCount, subjectOutlines.size());
            ApiResponse<String> response = ApiResponse
                    .success("Batch insert completed. Success: " + successCount + "/" + subjectOutlines.size());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while batch inserting SubjectOutline records", e);
            ApiResponse<String> response = ApiResponse
                    .error("Failed to batch insert SubjectOutline records due to unexpected error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 插入科目题目文件关联表数据
    @PostMapping("/insert/question/batch")
    public ResponseEntity<ApiResponse<String>> insertQuestionRecords(
            @RequestBody List<SubjectQuestionFile> subjectQuestionFiles) {
        logger.info("Batch insert question records endpoint accessed with data size: {}", subjectQuestionFiles.size());

        try {
            int successCount = 0;
            for (SubjectQuestionFile subjectQuestionFile : subjectQuestionFiles) {
                // 插入记录并检查结果
                int insertResult = uploadService.insertSubjectQuestionFile(subjectQuestionFile);
                if (insertResult > 0) {
                    successCount++;
                    logger.info("Successfully inserted SubjectQuestionFile record with id: {}",
                            subjectQuestionFile.getId());
                } else {
                    logger.warn("Failed to insert SubjectQuestionFile record: {}", subjectQuestionFile);
                }
            }

            logger.info("Batch insert completed. Success: {}/{}", successCount, subjectQuestionFiles.size());
            ApiResponse<String> response = ApiResponse
                    .success("Batch insert completed. Success: " + successCount + "/" + subjectQuestionFiles.size());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while batch inserting SubjectQuestionFile records", e);
            ApiResponse<String> response = ApiResponse
                    .error("Failed to batch insert SubjectQuestionFile records due to unexpected error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
