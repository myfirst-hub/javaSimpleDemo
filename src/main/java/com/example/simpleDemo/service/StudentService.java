package com.example.simpleDemo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simpleDemo.entity.Student;
import com.example.simpleDemo.entity.PageInfoResult;
import com.example.simpleDemo.mapper.StudentMapper;

import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public List<Student> findAllStudents() {
        return studentMapper.findAll();
    }

    public List<Student> findAllStudentsWithXml() {
        return studentMapper.findAllStudents();
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
}