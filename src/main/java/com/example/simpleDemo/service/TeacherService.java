package com.example.simpleDemo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simpleDemo.entity.Teacher;
import com.example.simpleDemo.mapper.TeacherMapper;
import com.example.simpleDemo.utils.PageInfoResult;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    public PageInfoResult<Teacher> findTeachersWithPageHelper(int page, int size, String name, String code) {
        // 开启分页
        PageHelper.startPage(page, size);

        // 查询数据
        List<Teacher> teachers = teacherMapper.findTeachers(name, code);

        // 封装分页结果
        PageInfo<Teacher> pageInfo = new PageInfo<>(teachers);
        return new PageInfoResult<>(pageInfo);
    }

    // 新增不使用分页查询所有教师的方法
    public List<Teacher> findAllTeachers(String name, String code) {
        return teacherMapper.findTeachers(name, code);
    }
}