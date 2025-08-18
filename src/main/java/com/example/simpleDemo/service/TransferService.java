package com.example.simpleDemo.service;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class TransferService {
    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

    private final WebClient webClient;

    public TransferService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:3000")
                .build();
    }

    /**
     * 调用外部新闻接口获取新闻数据
     * 
     * @return 新闻数据的字符串形式
     */
    public String fetchNews() {
        try {
            return webClient.get()
                    .uri("/news")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            logger.error("Failed to fetch news from external API", e);
            return "Error fetching news: " + e.getMessage();
        }
    }

    /**
     * 上传附件到外部服务
     * 
     * @param fileContent 文件内容
     * @param filename    文件名
     * @return 上传结果
     */
    public String uploadFile(byte[] fileContent, String filename) {
        try {
            return webClient.post()
                    .uri("/upload")
                    .bodyValue(fileContent)
                    .header("Content-Type", "application/octet-stream")
                    .header("X-Filename", filename)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            logger.error("Failed to upload file to external API", e);
            return "Error uploading file: " + e.getMessage();
        }
    }
}
