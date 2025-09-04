package com.example.simpleDemo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simpleDemo.controller.TeacherController;
import com.example.simpleDemo.entity.Student;
import com.example.simpleDemo.mapper.ClassStudentMapper;
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
    public PageInfoResult<Student> findStudentsByTeacherIdWithPage(Long teacherId, int pageNum, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 获取班级classIds
        List<Long> classIds = classesMapper.findClassIdsByTeacherId(teacherId);

        List<Long> studentIds = new ArrayList<>();

        for (Long classId : classIds) {
            List<Long> ids = classStudentMapper.findStudentIdsByClassId(classId);
            studentIds.addAll(ids);
        }

        logger.info("Get all studentIds endpoint accessed with params: studentIds={}", studentIds);

        List<Student> students = new ArrayList<>();
        for (Long id : studentIds) {
            students.add(studentMapper.findStudentById(id));
        }
        // 查询数据
        // List<Student> students = studentMapper.findStudentsByTeacherId(teacherId);

        // 封装分页结果
        PageInfo<Student> pageInfo = new PageInfo<>(students);
        return new PageInfoResult<>(pageInfo);
    }
}