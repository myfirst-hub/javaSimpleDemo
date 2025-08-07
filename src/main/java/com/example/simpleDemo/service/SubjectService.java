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
     * 分页查询科目列表，支持按名称和学期查询
     * 
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @param name     科目名称（可选）
     * @param semester 学期（可选）
     * @return 分页结果
     */
    public PageInfoResult<Subject> findSubjectsWithPageHelper(int pageNum, int pageSize, String name, String semester) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询数据
        List<Subject> subjects = subjectMapper.findSubjects(name, semester);

        // 封装分页结果
        PageInfo<Subject> pageInfo = new PageInfo<>(subjects);
        return new PageInfoResult<>(pageInfo);
    }
}