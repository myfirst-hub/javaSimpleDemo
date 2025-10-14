package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.TeacherCommentResult;
import com.example.simpleDemo.dto.TeacherCommentResultDTO;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestResultMapper {
    // 教师评语结果插入数据
    int insertTeacherComment(TeacherCommentResult teacherCommentResult);

    // 通过学生id查询教师评语详情
    List<TeacherCommentResultDTO> selectTeacherCommentResultWithDetails(@Param("studentId") Long studentId);
}
