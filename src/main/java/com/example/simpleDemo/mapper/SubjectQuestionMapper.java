package com.example.simpleDemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.simpleDemo.entity.SubjectQuestion;

import java.util.List;

@Mapper
public interface SubjectQuestionMapper {
    /**
     * 插入一个SubjectQuestion数据到数据库
     * 
     * @param subjectQuestion 要插入的数据对象
     * @return 插入成功的记录数
     */
    int insertSubjectQuestion(SubjectQuestion subjectQuestion);

    /**
     * 根据subjectId查询SubjectQuestion数据
     * 
     * @param subjectId 主键
     * @return 查询到的数据对象
     */
    List<SubjectQuestion> selectBySubjectId(@Param("subjectId") Long subjectId);

    // 新增删除与科目关联的问题数据
    int deleteSubjectQuestionBySubjectId(@Param("subjectId") Long subjectId);
}
