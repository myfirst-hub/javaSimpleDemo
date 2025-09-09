package com.example.simpleDemo.service;

import com.example.simpleDemo.controller.SubjectController;
import com.example.simpleDemo.entity.Subject;
import com.example.simpleDemo.entity.TheoryTestMap;
import com.example.simpleDemo.entity.TheoryTrainProgram;
import com.example.simpleDemo.entity.TheoryTestResult;
import com.example.simpleDemo.mapper.TheoryTrainProgramMapper;
import com.example.simpleDemo.mapper.UploadMapper;
import com.example.simpleDemo.mapper.SubjectMapper;
import com.example.simpleDemo.mapper.SubjectQuestionMapper;
import com.example.simpleDemo.utils.PageInfoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Service
public class TheoryTrainProgramService {

    private static final Logger logger = LoggerFactory.getLogger(TheoryTrainProgramService.class);

    @Autowired
    private TheoryTrainProgramMapper theoryTrainProgramMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private UploadMapper uploadMapper;

    @Autowired
    private SubjectQuestionMapper subjectQuestionMapper;

    public PageInfoResult<TheoryTrainProgram> selectTheoryTrainProgramList(Integer pageNum, Integer pageSize,
            String name, String semester) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询科目数据
        List<TheoryTrainProgram> theoryTrainPrograms = theoryTrainProgramMapper.selectTheoryTrainProgramList(name,
                semester);

        // 获取分页信息
        PageInfo<TheoryTrainProgram> theoryTrainProgramPageInfo = new PageInfo<>(theoryTrainPrograms);
        return new PageInfoResult<>(theoryTrainProgramPageInfo);
    }

    // 新增理论培训计划
    public int insertTheoryTrainProgram(TheoryTrainProgram theoryTrainProgram) {
        // 设置创建时间和更新时间
        theoryTrainProgram.setCreatedAt(new java.util.Date());
        theoryTrainProgram.setUpdatedAt(new java.util.Date());
        return theoryTrainProgramMapper.insertTheoryTrainProgram(theoryTrainProgram);
    }

    // 编辑理论培训计划
    public int updateTheoryTrainProgram(TheoryTrainProgram theoryTrainProgram) {
        // 设置更新时间
        theoryTrainProgram.setUpdatedAt(new java.util.Date());
        return theoryTrainProgramMapper.updateTheoryTrainProgram(theoryTrainProgram);
    }

    // 删除理论培训计划
    @Transactional(rollbackFor = Exception.class)
    public int deleteTheoryTrainProgram(Long id, Long subjectId) {
        // 删除subject_question_file表中与该科目关联的数据
        uploadMapper.deleteSubjectQuestionFileBySubjectId(subjectId);

        // 删除subject_question表中与该科目关联的数据
        subjectQuestionMapper.deleteSubjectQuestionBySubjectId(subjectId);

        return theoryTrainProgramMapper.deleteTheoryTrainProgram(id);
    }

    // 根据ID查询理论培训计划详情
    public TheoryTrainProgram selectTheoryTrainProgramById(Long id) {
        TheoryTrainProgram program = theoryTrainProgramMapper.selectTheoryTrainProgramById(id);
        if (program != null && program.getSubjectId() != null) {
            // 根据subjectId查询科目名称，这里需要根据实际的科目实体和Mapper进行调整
            Subject subject = subjectMapper.findSubjectById(program.getSubjectId());
            if (subject != null) {
                program.setSubjectName(subject.getName());
            }
        }
        return program;
    }

    // 新增：从Excel导入测试结果
    public int importTestResultFromExcel(MultipartFile file, Long subjectId, Long trainProgramId, Long studentId)
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

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 从第二行开始读取数据
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                TheoryTestResult result = new TheoryTestResult();

                // 读取测试时间（假设在第0列）
                Cell testTimeCell = row.getCell(0);
                if (testTimeCell != null) {
                    if (testTimeCell.getCellType() == CellType.NUMERIC) {
                        result.setTestTime(testTimeCell.getDateCellValue());
                    } else {
                        result.setTestTime(sdf.parse(testTimeCell.getStringCellValue()));
                    }
                }

                // 读取测试名称（假设在第1列）
                Cell testNameCell = row.getCell(1);
                if (testNameCell != null) {
                    result.setTestName(testNameCell.getStringCellValue());
                }

                // 读取属性（第2列到倒数第二列）
                StringBuilder attributesBuilder = new StringBuilder();
                attributesBuilder.append("{");
                boolean first = true;
                for (int j = 2; j < row.getLastCellNum() - 1; j++) { // 最后一列是总分，不包含
                    Cell cell = row.getCell(j);
                    String key = "试题编号" + (j - 1); // 试题编号1, 试题编号2...
                    String value = "";
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case NUMERIC:
                                value = String.valueOf(cell.getNumericCellValue());
                                break;
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            default:
                                value = "";
                        }
                    }
                    if (!first) {
                        attributesBuilder.append(",");
                    }
                    attributesBuilder.append("\"").append(key).append("\":\"").append(value).append("\"");
                    first = false;
                }
                attributesBuilder.append("}");
                result.setAttributes(attributesBuilder.toString());

                // 读取总分（最后一列）
                Cell totalScoreCell = row.getCell(row.getLastCellNum() - 1);
                if (totalScoreCell != null) {
                    if (totalScoreCell.getCellType() == CellType.NUMERIC) {
                        result.setTotalScore(BigDecimal.valueOf(totalScoreCell.getNumericCellValue()));
                    } else {
                        result.setTotalScore(new BigDecimal(totalScoreCell.getStringCellValue()));
                    }
                }

                // 设置创建和更新时间
                result.setCreatedAt(java.time.LocalDateTime.now());
                result.setUpdatedAt(java.time.LocalDateTime.now());

                // 插入数据库并获取ID
                theoryTrainProgramMapper.insertDynamicTheoryTestResult(result);
                Long generatedId = result.getId(); // 获取生成的ID
                logger.info("Generated ID.............: {}", generatedId); // 可选：打印日志
                TheoryTestMap ttm = new TheoryTestMap();
                ttm.setTheoryTestId(generatedId);
                ttm.setStudentId(studentId);
                ttm.setTrainProgramId(trainProgramId);
                ttm.setSubjectId(subjectId);
                // 设置创建和更新时间
                ttm.setCreatedAt(new java.util.Date());
                ttm.setUpdatedAt(new java.util.Date());
                // 插入关联表
                theoryTrainProgramMapper.insertTheoryTestMap(ttm);
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