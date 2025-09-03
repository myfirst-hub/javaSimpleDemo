package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.SubjectQuestion;
import com.example.simpleDemo.mapper.SubjectQuestionMapper;
import com.example.simpleDemo.utils.PageInfoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class SubjectQuestionService {

    @Autowired
    private SubjectQuestionMapper subjectQuestionMapper;

    /**
     * 根据subjectId查询SubjectQuestion数据
     * 
     * @param subjectId 主键
     * @return 查询到的数据对象
     */

    /**
     * 根据subjectId分页查询SubjectQuestion数据
     *
     * @param subjectId 主键
     * @param pageNum   页码
     * @param pageSize  每页大小
     * @return 分页查询结果
     */
    public PageInfoResult<SubjectQuestion> findBySubjectId(Long subjectId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<SubjectQuestion> subjectQuestions = subjectQuestionMapper.selectBySubjectId(subjectId);
        PageInfo<SubjectQuestion> pageInfo = new PageInfo<>(subjectQuestions);
        return new PageInfoResult<>(pageInfo);
    }
}
