package com.example.simpleDemo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<TheoryTestDetailResultDTO> findTheoryTestDetailByStudentId(Long studentId, String studentName, String className) {
        return studentMapper.findTheoryTestDetailByStudentId(studentId, studentName, className);
    }
}