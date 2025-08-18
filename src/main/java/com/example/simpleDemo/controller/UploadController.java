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

@RestController
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private TransferService transferService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<String>> uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("File upload endpoint accessed with file: {}", file.getOriginalFilename());

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
            byte[] fileContent = file.getBytes();
            String originalFilename = file.getOriginalFilename();

            // 调用TransferService的uploadFile方法
            String result = transferService.uploadFile(fileContent, originalFilename);

            logger.info("File uploaded successfully: {}", originalFilename);
            ApiResponse<String> response = ApiResponse.success(result);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            logger.error("Error occurred while uploading file", e);
            ApiResponse<String> response = ApiResponse.error("Failed to upload file: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Unexpected error occurred while uploading file", e);
            ApiResponse<String> response = ApiResponse.error("Failed to upload file due to unexpected error");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}