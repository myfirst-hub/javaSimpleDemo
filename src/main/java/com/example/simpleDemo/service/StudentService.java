package com.example.simpleDemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simpleDemo.entity.Student;
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
}