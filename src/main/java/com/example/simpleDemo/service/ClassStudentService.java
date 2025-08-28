package com.example.simpleDemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simpleDemo.entity.ClassStudent;
import com.example.simpleDemo.mapper.ClassStudentMapper;

import java.util.List;

@Service
public class ClassStudentService {
    @Autowired
    private ClassStudentMapper classStudentMapper;

    public int createClassStudent(ClassStudent classStudent) {
        // 设置创建和更新时间
        classStudent.setCreateTime(new java.util.Date());
        classStudent.setUpdateTime(new java.util.Date());

        // 插入学生信息
        return classStudentMapper.insertClassStudent(classStudent);
    }

    // 根据班级ID查询学生ID列表
    public List<Long> findStudentIdsByClassId(Long classId) {
        return classStudentMapper.findStudentIdsByClassId(classId);
    }

    // 根据班级ID删除班级学生关联
    public int deleteClassStudentByClassId(Long classId) {
        return classStudentMapper.deleteByClassId(classId);
    }
}