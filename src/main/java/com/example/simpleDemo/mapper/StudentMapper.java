package com.example.simpleDemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.simpleDemo.entity.Student;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM students")
    List<Student> findAll();

    // 使用XML映射的示例方法
    List<Student> findAllStudents();
}