package com.example.simpleDemo.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.simpleDemo.entity.ComprehensiveTestResult;
import com.example.simpleDemo.mapper.ComprehensiveTestResultMapper;
import com.example.simpleDemo.mapper.TheoryTrainProgramMapper;

@Service
public class ComprehensiveTestResultService {

    private static final Logger logger = LoggerFactory.getLogger(ComprehensiveTestResultService.class);

    @Autowired
    private ComprehensiveTestResultMapper comprehensiveTestResultMapper;

    @Autowired
    private TheoryTrainProgramMapper theoryTrainProgramMapper;

    // 新增：从Excel导入综合测评结果
    public int importFromExcel(MultipartFile file, Long subjectId, Long classId) throws Exception {
        int count = 0;
        InputStream inputStream = file.getInputStream();
        Workbook workbook = null;

        try {
            if (file.getOriginalFilename().endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                workbook = new HSSFWorkbook(inputStream);
            }

            Sheet sheet = workbook.getSheetAt(0);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            List<ComprehensiveTestResult> results = new ArrayList<>();
            List<ComprehensiveTestResult> updateResults = new ArrayList<>();
            List<ComprehensiveTestResult> insertResults = new ArrayList<>();

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 从第二行开始读取数据
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                ComprehensiveTestResult result = new ComprehensiveTestResult();

                // 读取学号（第0列）
                Cell studentNameCell = row.getCell(0);
                if (studentNameCell != null) {
                    result.setStudentName(studentNameCell.getStringCellValue());
                }

                // 读取学号（第1列）
                Cell studentIdCell = row.getCell(1);
                if (studentIdCell != null) {
                    result.setStudentId((long) studentIdCell.getNumericCellValue());
                }

                // 读取测试时间（第2列）
                Cell testTimeCell = row.getCell(2);
                if (testTimeCell != null) {
                    if (testTimeCell.getCellType() == CellType.NUMERIC) {
                        result.setTestTime(testTimeCell.getDateCellValue());
                    } else {
                        result.setTestTime(sdf.parse(testTimeCell.getStringCellValue()));
                    }
                }

                // 读取团队协作（第3列）
                Cell organizationScoreCell = row.getCell(3);
                if (organizationScoreCell != null) {
                    result.setOrganizationScore(parseCellToBigDecimal(organizationScoreCell));
                }

                // 读取自驱能力（第4列）
                Cell selfDriveScoreCell = row.getCell(4);
                if (selfDriveScoreCell != null) {
                    result.setSelfDriveScore(parseCellToBigDecimal(selfDriveScoreCell));
                }

                // 读取逻辑思维能力（第5列）
                Cell logicScoreCell = row.getCell(5);
                if (logicScoreCell != null) {
                    result.setLogicScore(parseCellToBigDecimal(logicScoreCell));
                }

                // 设置其他字段
                result.setSubjectId(subjectId);
                result.setClassId(classId);
                result.setCreatedAt(new Date());
                result.setUpdatedAt(new Date());

                results.add(result);
            }

            // 区分需要插入和更新的记录
            for (ComprehensiveTestResult result : results) {
                // 根据学生ID、科目ID和班级ID检查是否已存在记录
                List<ComprehensiveTestResult> existingResults = comprehensiveTestResultMapper
                        .selectByStudentIdAndSubjectIdAndClassId(result.getStudentId(), subjectId, classId);

                if (!existingResults.isEmpty()) {
                    // 如果存在，准备更新记录
                    ComprehensiveTestResult existingResult = existingResults.get(0);
                    result.setId(existingResult.getId()); // 设置ID用于更新
                    updateResults.add(result);
                } else {
                    // 如果不存在，准备插入新记录
                    insertResults.add(result);
                }
            }

            // 执行批量更新
            if (!updateResults.isEmpty()) {
                for (ComprehensiveTestResult result : updateResults) {
                    comprehensiveTestResultMapper.updateBatch(java.util.Arrays.asList(result));
                }
                count += updateResults.size();
            }

            // 执行批量插入
            if (!insertResults.isEmpty()) {
                comprehensiveTestResultMapper.insertBatch(insertResults);
                count += insertResults.size();
            }

            logger.info("Successfully imported {} records from Excel, updated {} records, inserted {} records",
                    results.size(), updateResults.size(), insertResults.size());
            return count;
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    // 新增：安全解析单元格为 BigDecimal
    private BigDecimal parseCellToBigDecimal(Cell cell) {
        if (cell == null) {
            return null;
        }
        switch (cell.getCellType()) {
            case NUMERIC:
                return new BigDecimal(cell.getNumericCellValue());
            case STRING:
                try {
                    return new BigDecimal(cell.getStringCellValue().trim());
                } catch (NumberFormatException e) {
                    logger.warn("Invalid numeric value in cell: {}", cell.getStringCellValue());
                    return null;
                }
            default:
                logger.warn("Unsupported cell type for numeric parsing: {}", cell.getCellType());
                return null;
        }
    }

    // 根据学生ID和科目ID查询综合测评结果，支持参数为空
    public Map<String, Object> getComprehensiveTestResultsByStudentIdAndSubjectId(Long studentId,
            Long subjectId) {
        List<Map<String, Object>> theoryScores = theoryTrainProgramMapper.selectTheoryScoresByStudentIdAndSubjectId(
                studentId,
                subjectId);
        // Map<String, Object> theoryScore = theoryScores.isEmpty() ? null :
        // theoryScores.get(0);
        // 计算理论总成绩：多个total_score相加除以多个full_score相加，保留两位小数
        BigDecimal totalScoreSum = BigDecimal.ZERO;
        BigDecimal fullScoreSum = BigDecimal.ZERO;
        BigDecimal theoryFinalScore = BigDecimal.ZERO;

        for (Map<String, Object> score : theoryScores) {
            Object totalScoreObj = score.get("total_score");
            Object fullScoreObj = score.get("full_score");

            if (totalScoreObj != null && fullScoreObj != null) {
                BigDecimal totalScore = new BigDecimal(totalScoreObj.toString());
                BigDecimal fullScore = new BigDecimal(fullScoreObj.toString());

                totalScoreSum = totalScoreSum.add(totalScore);
                fullScoreSum = fullScoreSum.add(fullScore);
            }
        }

        if (fullScoreSum.compareTo(BigDecimal.ZERO) > 0) {
            theoryFinalScore = totalScoreSum.divide(fullScoreSum, 4, BigDecimal.ROUND_HALF_UP)
                    .multiply(new BigDecimal(100))
                    .setScale(2, BigDecimal.ROUND_HALF_UP);
        }
        List<ComprehensiveTestResult> results = comprehensiveTestResultMapper
                .selectByStudentIdAndSubjectId(studentId, subjectId);
        ComprehensiveTestResult result = results.isEmpty() ? null : results.get(0);
        Map<String, Object> mapResult = new java.util.HashMap<>();
        mapResult.put("theoryScore", theoryFinalScore);
        mapResult.put("comprehensiveTestResult", result);
        return mapResult;
    }

    // 根据学生ID和教师ID查询综合测评结果，支持参数为空
    public List<ComprehensiveTestResult> getComprehensiveTestResultsByStudentIdAndTeacherId(Long studentId,
            Long teacherId) {
        return comprehensiveTestResultMapper.selectByStudentIdAndTeacherId(studentId, teacherId);
    }
}