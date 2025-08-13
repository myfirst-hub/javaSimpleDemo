package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.Knowledge;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class KnowledgeService {
    
    private static final Logger logger = LoggerFactory.getLogger(KnowledgeService.class);
    
    
    /**
     * 从Excel文件导入知识点
     * 
     * @param file Excel文件
     * @return 导入结果信息
     * @throws Exception 导入过程中可能发生的异常
     */
    public String importKnowledgesFromExcel(MultipartFile file) throws Exception {
        logger.info("Starting to import knowledges from excel file: {}", file.getOriginalFilename());
        
        List<Knowledge> knowledges = new ArrayList<>();
        
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            
            // 跳过标题行，从第二行开始读取数据
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null) continue;
                
                Knowledge knowledge = new Knowledge();
                
                // 读取知识点名称（第一列）
                Cell nameCell = row.getCell(0);
                if (nameCell != null) {
                    nameCell.setCellType(CellType.STRING);
                    knowledge.setName(nameCell.getStringCellValue());
                }
                
                // 读取知识点描述（第二列）
                Cell descriptionCell = row.getCell(1);
                if (descriptionCell != null) {
                    descriptionCell.setCellType(CellType.STRING);
                    knowledge.setDescription(descriptionCell.getStringCellValue());
                }
                
                // 读取知识点分类（第三列）
                Cell categoryCell = row.getCell(2);
                if (categoryCell != null) {
                    categoryCell.setCellType(CellType.STRING);
                    knowledge.setCategory(categoryCell.getStringCellValue());
                }
                
                // 只有当知识点名称不为空时才添加
                if (knowledge.getName() != null && !knowledge.getName().trim().isEmpty()) {
                    knowledges.add(knowledge);
                }
            }
            
            // 批量保存知识点
            int successCount = 0;
            List<String> errorMessages = new ArrayList<>();
            
            for (Knowledge knowledge : knowledges) {
                try {
                    // 假设addKnowledge方法可以处理重复数据或进行验证
                    addKnowledge(knowledge);
                    successCount++;
                } catch (Exception e) {
                    errorMessages.add("知识点 '" + knowledge.getName() + "' 导入失败: " + e.getMessage());
                    logger.error("Failed to import knowledge: {}", knowledge.getName(), e);
                }
            }
            
            StringBuilder resultMessage = new StringBuilder();
            resultMessage.append("成功导入 ").append(successCount).append(" 条知识点");
            
            if (!errorMessages.isEmpty()) {
                resultMessage.append("，失败 ").append(errorMessages.size()).append(" 条");
                logger.warn("Import completed with errors: {}", String.join("; ", errorMessages));
            }
            
            logger.info("Import completed successfully: {} knowledges imported", successCount);
            return resultMessage.toString();
            
        } catch (Exception e) {
            logger.error("Error occurred while parsing excel file", e);
            throw new Exception("解析Excel文件失败: " + e.getMessage(), e);
        }
    }
}