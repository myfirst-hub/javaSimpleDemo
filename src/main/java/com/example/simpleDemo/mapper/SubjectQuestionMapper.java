package com.example.simpleDemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.example.simpleDemo.entity.SubjectQuestion;

@Mapper
public interface SubjectQuestionMapper {
    /**
     * 插入一个SubjectQuestion数据到数据库
     * 
     * @param subjectQuestion 要插入的数据对象
     * @return 插入成功的记录数
     */
    int insertSubjectQuestion(SubjectQuestion subjectQuestion);
}
