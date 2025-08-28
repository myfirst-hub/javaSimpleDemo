package com.example.simpleDemo.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.simpleDemo.entity.ClassStudent;

import java.util.List;

@Mapper
public interface ClassStudentMapper {
    // 插入班级学生映射信息
    int insertClassStudent(ClassStudent classStudent);

    // 根据班级ID查询学生ID列表
    List<Long> findStudentIdsByClassId(Long classId);

    // 根据班级ID删除班级学生关联
    int deleteByClassId(Long classId);
}