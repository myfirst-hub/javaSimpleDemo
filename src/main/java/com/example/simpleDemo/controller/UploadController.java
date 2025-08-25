package com.example.simpleDemo.controller;

import com.example.simpleDemo.utils.ApiResponse;
import com.example.simpleDemo.service.TransferService;
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
import java.util.UUID;

// 添加Jackson的ObjectMapper导入
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private TransferService transferService;

    // 注入ObjectMapper用于JSON解析
    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestParam("files[]") MultipartFile file,
            @RequestParam("subjectId") Long subjectId) {
        logger.info("File upload endpoint accessed with file: {} and subjectId: {}", file.getOriginalFilename(),
                subjectId);

        try {
            // // 获取项目根目录
            // String projectRoot = System.getProperty("user.dir");
            // String uploadDir = projectRoot + File.separator + "uploads";

            // // 创建上传目录（如果不存在）
            // File directory = new File(uploadDir);
            // if (!directory.exists()) {
            // directory.mkdirs();
            // }

            // // 生成唯一文件名
            // String originalFilename = file.getOriginalFilename();
            // String fileExtension = "";
            // if (originalFilename != null && originalFilename.contains(".")) {
            // fileExtension =
            // originalFilename.substring(originalFilename.lastIndexOf("."));
            // }
            // String uniqueFilename = UUID.randomUUID().toString() + fileExtension;

            // // 保存文件
            // Path filePath = Paths.get(uploadDir, uniqueFilename);
            // Files.write(filePath, file.getBytes());

            // logger.info("File uploaded successfully: {}", uniqueFilename);
            // ApiResponse<String> response = ApiResponse.success("File uploaded
            // successfully: " + uniqueFilename);
            // 记录subjectId的值

            logger.info("Received subjectId: {}", subjectId);

            // 直接将MultipartFile传递给TransferService
            String result = transferService.uploadFile(file);

            // 解析JSON结果并提取original_filename
            try {
                JsonNode jsonNode = objectMapper.readTree(result);
                JsonNode resultsArray = jsonNode.get("results");
                if (resultsArray != null && resultsArray.isArray() && resultsArray.size() > 0) {
                    JsonNode firstResult = resultsArray.get(0);
                    JsonNode originalFilenameNode = firstResult.get("original_filename");
                    if (originalFilenameNode != null) {
                        String originalFilename = originalFilenameNode.asText();
                        logger.info("Extracted original filename: {}", originalFilename);
                    }
                }
            } catch (Exception e) {
                logger.error("Failed to parse JSON result", e);
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
}