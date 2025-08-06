package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.Subject;
import com.example.simpleDemo.mapper.SubjectMapper;
import com.example.simpleDemo.utils.PageInfoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {
    
    @Autowired
    private SubjectMapper subjectMapper;
    
    /**
     * 分页查询科目列表
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    public PageInfoResult<Subject> findSubjects(int pageNum, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 查询所有科目
        List<Subject> subjects = subjectMapper.findAllSubjects();
        // 封装分页结果
        PageInfo<Subject> pageInfo = new PageInfo<>(subjects);
        return new PageInfoResult<>(pageInfo);
    }
    
    /**
     * 根据名称分页查询科目列表
     * @param name 科目名称
     * @param pageNum 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    public PageInfoResult<Subject> findSubjectsByName(String name, int pageNum, int pageSize) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 根据名称查询科目
        List<Subject> subjects = subjectMapper.findSubjectsByName(name);
        // 封装分页结果
        PageInfo<Subject> pageInfo = new PageInfo<>(subjects);
        return new PageInfoResult<>(pageInfo);
    }
}