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
            String name, String semester, Long[] subjectIds) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询科目数据
        List<TheoryTrainProgram> theoryTrainPrograms = theoryTrainProgramMapper.selectTheoryTrainProgramList(name,
                semester, subjectIds);

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
    @Transactional(rollbackFor = Exception.class)
    public int importTestResultFromExcel(MultipartFile file, Long subjectId)
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

            // 获取trainProgramId
            TheoryTrainProgram trainProgram = theoryTrainProgramMapper.selectTheoryTrainProgramBySubjectId(subjectId);
            Long trainProgramId = (trainProgram != null) ? trainProgram.getId() : null;

            // 提取总分列的标题中的 fullScore 值
            Row headerRow = sheet.getRow(0);
            Cell totalScoreHeaderCell = headerRow.getCell(headerRow.getLastCellNum() - 1); // 最后一列是总分
            Integer fullScore = extractFullScoreFromHeader(totalScoreHeaderCell);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 从第二行开始读取数据
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                TheoryTestResult result = new TheoryTestResult();

                // 读取学号（假设在第1列）并转换为studentId
                Cell studentIdCell = row.getCell(0);
                Long studentId = null;
                if (studentIdCell != null) {
                    // 检查单元格是否为公式类型
                    if (studentIdCell.getCellType() == CellType.FORMULA) {
                        switch (studentIdCell.getCachedFormulaResultType()) {
                            case NUMERIC:
                                studentId = Math.round(studentIdCell.getNumericCellValue());
                                break;
                            case STRING:
                                try {
                                    String cellValue = studentIdCell.getStringCellValue();
                                    if (cellValue != null && !cellValue.isEmpty()) {
                                        studentId = Long.parseLong(cellValue.trim());
                                    }
                                } catch (NumberFormatException e) {
                                    logger.warn("无法从学号 {} 中解析出有效的studentId", studentIdCell.getStringCellValue());
                                }
                                break;
                            default:
                                logger.warn("不支持的公式结果类型: {}", studentIdCell.getCachedFormulaResultType());
                        }
                    } else if (studentIdCell.getCellType() == CellType.NUMERIC) {
                        studentId = Math.round(studentIdCell.getNumericCellValue());
                    } else if (studentIdCell.getCellType() == CellType.STRING) {
                        try {
                            String cellValue = studentIdCell.getStringCellValue();
                            if (cellValue != null && !cellValue.isEmpty()) {
                                studentId = Long.parseLong(cellValue.trim());
                            }
                        } catch (NumberFormatException e) {
                            logger.warn("无法从学号 {} 中解析出有效的studentId");
                        }
                    } else {
                        String cellValue = getCellValueAsString(studentIdCell);
                        if (cellValue != null && !cellValue.isEmpty()) {
                            try {
                                studentId = Long.parseLong(cellValue.trim());
                            } catch (NumberFormatException e) {
                                logger.warn("无法从学号 {} 中解析出有效的studentId", cellValue);
                            }
                        }
                    }
                }

                // 读取测试时间（假设在第2列）
                Cell testTimeCell = row.getCell(3);
                java.util.Date testTime = null;
                if (testTimeCell != null) {
                    if (testTimeCell.getCellType() == CellType.NUMERIC) {
                        testTime = testTimeCell.getDateCellValue();
                    } else {
                        String cellValue = getCellValueAsString(testTimeCell);
                        if (cellValue != null && !cellValue.isEmpty()) {
                            try {
                                // 支持 yyyy/MM/dd 格式
                                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy/MM/dd");
                                testTime = sdf1.parse(cellValue);
                            } catch (Exception e1) {
                                try {
                                    // 支持 yyyy-MM-dd 格式
                                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                                    testTime = sdf2.parse(cellValue);
                                } catch (Exception e2) {
                                    logger.warn("无法解析测试时间: {}", cellValue);
                                }
                            }
                        }
                    }
                }

                // 读取测试名称（假设在第3列）
                Cell testNameCell = row.getCell(4);
                String testName = null;
                if (testNameCell != null) {
                    testName = getCellValueAsString(testNameCell);
                }

                // 判断是否已存在同一次考试记录
                TheoryTestResult existingResult = theoryTrainProgramMapper
                        .selectTheoryTestResultByStudentIdAndTestInfo(studentId, testName, testTime);
                if (existingResult != null) {
                    // 更新已有记录
                    result.setId(existingResult.getId());
                    result.setUpdatedAt(java.time.LocalDateTime.now());
                } else {
                    // 新增记录
                    result.setCreatedAt(java.time.LocalDateTime.now());
                    result.setUpdatedAt(java.time.LocalDateTime.now());
                }

                // 设置测试时间
                result.setTestTime(testTime);
                result.setTestName(testName);

                // 读取属性（第4列到倒数第二列）
                StringBuilder attributesBuilder = new StringBuilder();
                attributesBuilder.append("{");
                boolean first = true;
                for (int j = 5; j < row.getLastCellNum() - 1; j++) { // 最后一列是总分，不包含
                    Cell cell = row.getCell(j);
                    // 获取对应列的标题作为key
                    String key = "";
                    if (headerRow != null) {
                        Cell headerCell = headerRow.getCell(j);
                        if (headerCell != null) {
                            key = getCellValueAsString(headerCell);
                        }
                    }
                    if (key == null || key.isEmpty()) {
                        key = "试题编号" + (j - 3);
                    }
                    String value = "";
                    if (cell != null) {
                        value = getCellValueAsString(cell);
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
                BigDecimal totalScore = null;
                if (totalScoreCell != null) {
                    if (totalScoreCell.getCellType() == CellType.NUMERIC) {
                        totalScore = BigDecimal.valueOf(totalScoreCell.getNumericCellValue());
                    } else {
                        String cellValue = getCellValueAsString(totalScoreCell);
                        if (cellValue != null && !cellValue.isEmpty()) {
                            totalScore = new BigDecimal(cellValue);
                        }
                    }
                }
                result.setTotalScore(totalScore);

                // 设置 fullScore
                if (fullScore != null) {
                    result.setFullScore(BigDecimal.valueOf(fullScore));
                } else {
                    // 使用默认满分
                    result.setFullScore(BigDecimal.valueOf(100));
                }

                // 插入或更新数据库
                if (result.getId() == null) {
                    theoryTrainProgramMapper.insertDynamicTheoryTestResult(result);
                    Long generatedId = result.getId();
                    logger.info("Generated ID.............: {}", generatedId);
                    TheoryTestMap ttm = new TheoryTestMap();
                    ttm.setTheoryTestId(generatedId);
                    ttm.setStudentId(studentId);
                    ttm.setTrainProgramId(trainProgramId);
                    ttm.setSubjectId(subjectId);
                    ttm.setCreatedAt(new java.util.Date());
                    ttm.setUpdatedAt(new java.util.Date());
                    theoryTrainProgramMapper.insertTheoryTestMap(ttm);
                } else {
                    theoryTrainProgramMapper.updateDynamicTheoryTestResult(result);
                }
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

    // 辅助方法：从总分列标题中提取 fullScore
    private Integer extractFullScoreFromHeader(Cell cell) {
        if (cell == null)
            return null;
        String headerText = getCellValueAsString(cell);
        if (headerText == null || headerText.isEmpty())
            return null;

        // 支持多种格式：总分（40）、总分(40)、总分: 40、总分=40 等
        // 使用 [（(] 匹配中文或英文左括号，[）)] 匹配中文或英文右括号
        java.util.regex.Pattern pattern = java.util.regex.Pattern
                .compile("\\b总分\\s*[（(]?([\\d]+)[）)]?|\\s*:\\s*(\\d+)|\\s*=\\s*(\\d+)");
        java.util.regex.Matcher matcher = pattern.matcher(headerText);
        if (matcher.find()) {
            // 获取第一个数字组
            for (int i = 1; i <= matcher.groupCount(); i++) {
                String group = matcher.group(i);
                if (group != null && !group.isEmpty()) {
                    return Integer.parseInt(group);
                }
            }
        }
        // 如果未匹配到，返回默认值 100
        return 100; // 默认满分
    }

    // 辅助方法：安全地获取单元格的字符串值
    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    // 避免科学计数法显示
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case NUMERIC:
                        return String.valueOf((long) cell.getNumericCellValue());
                    case STRING:
                        return cell.getStringCellValue();
                    default:
                        return "";
                }
            default:
                return "";
        }
    }
}