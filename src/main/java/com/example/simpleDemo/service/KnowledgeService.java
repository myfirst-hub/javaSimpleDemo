package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.Knowledge;
import com.example.simpleDemo.mapper.KnowledgeMapper;

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

import jakarta.servlet.http.HttpServletResponse; // 添加导入

@Service
public class KnowledgeService {

    private static final Logger logger = LoggerFactory.getLogger(KnowledgeService.class);

    @Autowired
    private KnowledgeMapper knowledgeMapper;

    /**
     * 根据ID查询知识点
     *
     * @param id 知识点ID
     * @return 知识点列表
     */
    public List<Knowledge> findKnowledges(Integer id) {
        return knowledgeMapper.findKnowledges(id);
    }

    /**
     * 新增知识点
     *
     * @param knowledge 知识点对象
     * @return 是否添加成功
     */
    public boolean addKnowledge(Knowledge knowledge) {
        return knowledgeMapper.insertKnowledge(knowledge) > 0;
    }

    /**
     * 插入知识点
     *
     * @param knowledge 知识点对象
     * @return 影响的行数
     */
    public int insertKnowledge(Knowledge knowledge) {
        return knowledgeMapper.insertKnowledge(knowledge);
    }

    /**
     * 根据ID更新知识点
     *
     * @param knowledge 知识点对象
     * @return 影响的行数
     */
    public int updateKnowledgeById(Knowledge knowledge) {
        return knowledgeMapper.updateKnowledgeById(knowledge);
    }

    /**
     * 从Excel文件导入知识点
     * 
     * @param file Excel文件
     * @return 本次导入的知识点列表
     * @throws Exception 导入过程中可能发生的异常
     */
    public List<Knowledge> importKnowledgesFromExcel(MultipartFile file) throws Exception {
        logger.info("Starting to import knowledges from excel file: {}", file.getOriginalFilename());

        List<Knowledge> knowledges = new ArrayList<>();

        try (InputStream inputStream = file.getInputStream();
                Workbook workbook = new XSSFWorkbook(inputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            DataFormatter formatter = new DataFormatter();

            // 跳过标题行，从第二行开始读取数据
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                Knowledge knowledge = new Knowledge();

                // 读取知识点名称（第一列）
                Cell nameCell = row.getCell(0);
                if (nameCell != null) {
                    String nameStr = formatter.formatCellValue(nameCell);
                    knowledge.setName(nameStr);
                }

                // 读取知识点所需掌握程度（第二列）
                Cell masteryLevelCell = row.getCell(1);
                if (masteryLevelCell != null) {
                    String masteryLevelStr = formatter.formatCellValue(masteryLevelCell);
                    try {
                        knowledge.setMasteryLevel(Integer.parseInt(masteryLevelStr));
                    } catch (NumberFormatException e) {
                        logger.warn("无法解析知识点掌握程度值: {}", masteryLevelStr);
                        knowledge.setMasteryLevel(0); // 设置默认值或根据业务处理
                    }
                }

                // 读取知识点描述（第三列）
                Cell descriptionCell = row.getCell(2);
                if (descriptionCell != null) {
                    String descriptionStr = formatter.formatCellValue(descriptionCell);
                    knowledge.setKnowledgeDescribe(descriptionStr);
                }

                // 只有当知识点名称不为空时才添加
                if (knowledge.getName() != null && !knowledge.getName().trim().isEmpty()) {
                    knowledges.add(knowledge);
                }
            }

            return knowledges;

        } catch (Exception e) {
            logger.error("Error occurred while parsing excel file", e);
            throw new Exception("解析Excel文件失败: " + e.getMessage(), e);
        }
    }

    /**
     * 生成知识点导入模板
     * 
     * @param response HttpServletResponse
     * @throws Exception 生成过程中可能发生的异常
     */
    public void generateKnowledgeTemplate(HttpServletResponse response) throws Exception {
        logger.info("Generating knowledge template");

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("知识点模板");

            // 创建标题行
            Row headerRow = sheet.createRow(0);

            // 创建单元格样式
            CellStyle headerCellStyle = workbook.createCellStyle();
            Font font = workbook.createFont();
            font.setBold(true);
            headerCellStyle.setFont(font);

            // 设置表头
            Cell nameHeaderCell = headerRow.createCell(0);
            nameHeaderCell.setCellValue("知识点名称");
            nameHeaderCell.setCellStyle(headerCellStyle);

            Cell masteryLevelHeaderCell = headerRow.createCell(1);
            masteryLevelHeaderCell.setCellValue("掌握程度（0-记忆,1-理解,2-应用,3-分析,4-评价,5-创造）");
            masteryLevelHeaderCell.setCellStyle(headerCellStyle);

            Cell descriptionHeaderCell = headerRow.createCell(2);
            descriptionHeaderCell.setCellValue("知识点描述");
            descriptionHeaderCell.setCellStyle(headerCellStyle);

            // 设置列宽
            sheet.setColumnWidth(0, 50 * 256); // 知识点名称
            sheet.setColumnWidth(1, 52 * 256); // 掌握程度
            sheet.setColumnWidth(2, 50 * 256); // 知识点描述

            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=knowledge_template.xlsx");

            // 将工作簿写入响应输出流
            workbook.write(response.getOutputStream());
            response.getOutputStream().flush();

            logger.info("Knowledge template generated successfully");
        } catch (Exception e) {
            logger.error("Error occurred while generating knowledge template", e);
            throw new Exception("生成模板失败: " + e.getMessage(), e);
        }
    }
}