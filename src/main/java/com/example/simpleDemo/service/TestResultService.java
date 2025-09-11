package com.example.simpleDemo.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.simpleDemo.entity.TeacherCommentResult;
import com.example.simpleDemo.entity.TheoryTrainProgram;
import com.example.simpleDemo.mapper.TestResultMapper;
import com.example.simpleDemo.mapper.TheoryTrainProgramMapper;

@Service
public class TestResultService {

    private static final Logger logger = LoggerFactory.getLogger(TestResultService.class);

    @Autowired
    private TestResultMapper testResultMapper;

    @Autowired
    private TheoryTrainProgramMapper theoryTrainProgramMapper;

    // 新增：从Excel导入教师评语测试结果
    public int importTeacherCommentFromExcel(MultipartFile file, Long subjectId)
            throws Exception {
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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            // 从文件名中提取studentId
            String fileName = file.getOriginalFilename();
            // 移除文件扩展名
            if (fileName.contains(".")) {
                fileName = fileName.substring(0, fileName.lastIndexOf("."));
            }
            // 提取-后面的部分作为studentId
            Long studentId = null;
            if (fileName.contains("-")) {
                String studentIdStr = fileName.substring(fileName.lastIndexOf("-") + 1);
                try {
                    studentId = Long.parseLong(studentIdStr);
                } catch (NumberFormatException e) {
                    logger.warn("无法从文件名 {} 中解析出有效的studentId", file.getOriginalFilename());
                }
            }

            // 获取trainProgramId
            TheoryTrainProgram trainProgram = theoryTrainProgramMapper.selectTheoryTrainProgramBySubjectId(subjectId);
            Long trainProgramId = (trainProgram != null) ? trainProgram.getId() : null;

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 从第二行开始读取数据
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                TeacherCommentResult result = new TeacherCommentResult();

                // 读取评语时间（假设在第0列）
                Cell testTimeCell = row.getCell(0);
                if (testTimeCell != null) {
                    if (testTimeCell.getCellType() == CellType.NUMERIC) {
                        result.setTeacherCommentTime(testTimeCell.getDateCellValue());
                    } else {
                        result.setTeacherCommentTime(sdf.parse(testTimeCell.getStringCellValue()));
                    }
                }

                // 读取评语内容（假设在第1列）
                Cell testNameCell = row.getCell(1);
                if (testNameCell != null) {
                    result.setTeacherCommentContent(testNameCell.getStringCellValue());
                }

                result.setStudentId(studentId);
                result.setSubjectId(subjectId);
                result.setTrainProgramId(trainProgramId);

                // 设置创建和更新时间
                result.setCreatedAt(java.time.LocalDateTime.now());
                result.setUpdatedAt(java.time.LocalDateTime.now());

                logger.info(
                        "Importing TeacherCommentResult: studentId={}, subjectId={}, trainProgramId={}, commentTime={}, commentContent={}",
                        result.getStudentId(), result.getSubjectId(), result.getTrainProgramId(),
                        result.getTeacherCommentTime(), result.getTeacherCommentContent());

                // 插入数据库并获取ID
                testResultMapper.insertTeacherComment(result);
                count++;
            }
        } finally {
            if (workbook != null) {
                workbook.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return count;
    }
}
