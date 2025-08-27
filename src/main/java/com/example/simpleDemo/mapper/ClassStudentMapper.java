package com.example.simpleDemo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.simpleDemo.entity.ClassStudent;

@Mapper
public interface ClassStudentMapper {
    // 插入班级学生映射信息
    int insertClassStudent(ClassStudent classStudent);
}
