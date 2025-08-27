package com.example.simpleDemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.simpleDemo.entity.Teacher;

import java.util.List;

@Mapper
public interface TeacherMapper {

    // 支持PageHelper的查询方法
    List<Teacher> findTeachers(@Param("name") String name, @Param("subject") String subject);
}