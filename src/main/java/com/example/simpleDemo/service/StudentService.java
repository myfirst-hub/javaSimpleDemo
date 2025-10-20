package com.example.simpleDemo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.simpleDemo.controller.TeacherController;
import com.example.simpleDemo.dto.TheoryTestDetailResultDTO;
import com.example.simpleDemo.entity.Classes;
import com.example.simpleDemo.entity.Student;
import com.example.simpleDemo.entity.StudentInfo;
import com.example.simpleDemo.entity.TheoryTrainProgram;
import com.example.simpleDemo.mapper.ClassStudentMapper;
import com.example.simpleDemo.mapper.TheoryTrainProgramMapper;
import com.example.simpleDemo.mapper.ClassesMapper;
import com.example.simpleDemo.mapper.StudentMapper;
import com.example.simpleDemo.utils.PageInfoResult;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ClassesMapper classesMapper;

    @Autowired
    private ClassStudentMapper classStudentMapper;

    @Autowired
    private TheoryTrainProgramMapper theoryTrainProgramMapper;

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    // 新增不使用分页查询所有学生的方法
    public List<Student> findAllStudents(String name, String className) {
        return studentMapper.findStudents(name, className);
    }

    public PageInfoResult<Student> findStudentsWithPageHelper(int page, int size, String name, String className) {
        // 开启分页
        PageHelper.startPage(page, size);

        // 查询数据
        List<Student> students = studentMapper.findStudents(name, className);

        // 封装分页结果
        PageInfo<Student> pageInfo = new PageInfo<>(students);
        return new PageInfoResult<>(pageInfo);
    }

    public int createStudent(Student student) {
        // 设置创建和更新时间
        student.setCreatedAt(new java.util.Date());
        student.setUpdatedAt(new java.util.Date());

        // 插入学生信息
        return studentMapper.insertStudent(student);
    }

    public Student findStudentById(Long id) {
        return studentMapper.findStudentById(id);
    }

    public int updateStudent(Student student) {
        // 设置更新时间
        student.setUpdatedAt(new java.util.Date());

        // 更新学生信息
        return studentMapper.updateStudent(student);
    }

    public int deleteStudentById(Long id) {
        return studentMapper.deleteStudentById(id);
    }

    /**
     * 根据教师ID查询学生列表（分页）
     * 通过教师ID关联查询班级ID，根据班级ID查询学生列表
     */
    public PageInfoResult<StudentInfo> findStudentsByTeacherIdWithPage(Long teacherId, String studentName,
            String className, int pageNum, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 获取班级classes，支持班级名称模糊匹配
        List<Classes> classes = classesMapper.findClassesByTeacherId(teacherId);

        List<StudentInfo> students = new ArrayList<>();

        for (Classes classObj : classes) {
            // 获取班级下的学生ID列表
            List<Long> ids = classStudentMapper.findStudentIdsByClassId(classObj.getId());
            TheoryTrainProgram ttp = theoryTrainProgramMapper
                    .selectTheoryTrainProgramBySubjectId(classObj.getSubjectId());
            for (Long id : ids) {
                Student student = studentMapper.findStudentById(id);
                // 支持学生姓名模糊匹配
                if ((studentName == null || studentName.isEmpty() || student.getName().contains(studentName)) &&
                        (className == null || className.isEmpty() || classObj.getName().contains(className))) {
                    StudentInfo info = new StudentInfo();
                    info.setId(student.getId());
                    info.setName(student.getName());
                    info.setClassName(classObj.getName());
                    info.setClassId(classObj.getId());
                    info.setSubjectId(classObj.getSubjectId());
                    info.setSubjectName(classObj.getSubjectName());
                    info.setTrainProgramId(ttp.getId());
                    info.setTrainProgramName(ttp.getName());
                    students.add(info);
                }
            }
        }

        // 封装分页结果
        PageInfo<StudentInfo> pageInfo = new PageInfo<>(students);
        return new PageInfoResult<>(pageInfo);
    }

    // 通过学生id查询理论考试详情
    public List<TheoryTestDetailResultDTO> findTheoryTestDetailByStudentId(Long studentId, String studentName,
            String className) {
        return studentMapper.findTheoryTestDetailByStudentId(studentId, studentName, className);
    }

    // 新增：从Excel导入学生信息
    public int importStudentsFromExcel(MultipartFile file) throws Exception {
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

            for (int i = 1; i <= sheet.getLastRowNum(); i++) { // 从第二行开始读取数据
                Row row = sheet.getRow(i);
                if (row == null)
                    continue;

                Student student = new Student();

                // 姓名（第0列）
                Cell nameCell = row.getCell(0);
                if (nameCell != null) {
                    student.setName(nameCell.getStringCellValue());
                }

                // 性别（第1列）
                Cell sexCell = row.getCell(1);
                if (sexCell != null) {
                    student.setSex(sexCell.getStringCellValue());
                }

                // 身高（第2列）
                Cell heightCell = row.getCell(2);
                if (heightCell != null) {
                    try {
                        if (heightCell.getCellType() == CellType.NUMERIC) {
                            student.setHeight(BigDecimal.valueOf(heightCell.getNumericCellValue()));
                        } else {
                            student.setHeight(new BigDecimal(heightCell.getStringCellValue()));
                        }
                    } catch (NumberFormatException e) {
                        logger.warn("Invalid height value: {}", heightCell.toString());
                    }
                }

                // 体重（第3列）
                Cell weightCell = row.getCell(3);
                if (weightCell != null) {
                    try {
                        if (weightCell.getCellType() == CellType.NUMERIC) {
                            student.setWeight(BigDecimal.valueOf(weightCell.getNumericCellValue()));
                        } else {
                            student.setWeight(new BigDecimal(weightCell.getStringCellValue()));
                        }
                    } catch (NumberFormatException e) {
                        logger.warn("Invalid weight value: {}", weightCell.toString());
                    }
                }

                // 出生日期（第4列）
                Cell birthdayCell = row.getCell(4);
                if (birthdayCell != null) {
                    if (birthdayCell.getCellType() == CellType.NUMERIC) {
                        student.setBirthday(birthdayCell.getDateCellValue());
                    } else {
                        student.setBirthday(sdf.parse(birthdayCell.getStringCellValue()));
                    }
                }

                // 籍贯（第5列）
                Cell nativePlaceCell = row.getCell(5);
                if (nativePlaceCell != null) {
                    student.setNativePlace(nativePlaceCell.getStringCellValue());
                }

                // 民族（第6列）
                Cell nationalityCell = row.getCell(6);
                if (nationalityCell != null) {
                    student.setNationality(nationalityCell.getStringCellValue());
                }

                // 设置创建和更新时间
                student.setCreatedAt(new java.util.Date());
                student.setUpdatedAt(new java.util.Date());

                // 插入数据库
                studentMapper.insertStudent(student);
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