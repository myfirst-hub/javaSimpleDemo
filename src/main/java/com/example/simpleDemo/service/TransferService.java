package com.example.simpleDemo.service;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import reactor.core.publisher.Flux;

@Service
public class TransferService {
    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

    private final WebClient webClient;

    public TransferService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://192.168.107.126:8965")
                .build();
    }

    /**
     * 上传附件到外部服务
     * 
     * @param file MultipartFile对象
     * @return 上传结果
     */
    public String uploadFile(MultipartFile file, String uri) {
        try {
            // 使用MultipartBodyBuilder构建multipart/form-data请求体
            MultipartBodyBuilder builder = new MultipartBodyBuilder();
            builder.part("files[]", file.getResource())
                    .header("Content-Disposition",
                            "form-data; name=\"files[]\"; filename=\"" + file.getOriginalFilename() + "\"");

            return webClient.post()
                    .uri(uri)
                    .contentType(MediaType.MULTIPART_FORM_DATA)
                    .bodyValue(builder.build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            logger.error("Failed to upload file to external API", e);
            return "Error uploading file: " + e.getMessage();
        }
    }

    /**
     * 调用外部知识点接口获取知识点数据
     * 
     * @return 知识点数据的字符串形式
     */
    public String fetchKnowledge() {
        try {
            return webClient.get()
                    .uri("/knowledge/苏科版9年级物理上册【高清教材】_knowledge.json")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            logger.error("Failed to fetch news from external API", e);
            return "Error fetching news: " + e.getMessage();
        }
    }

    /**
     * 调用外部试题接口获取试题数据
     * 
     * @return 试题数据的字符串形式
     */
    public String fetchQuestions() {
        try {
            return webClient.get()
                    .uri("/get_processed_exam_json/2011—2012学年度第一学期期末学情分析_processed.json")
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            logger.error("Failed to fetch questions from external API", e);
            return "Error fetching questions: " + e.getMessage();
        }
    }
}
