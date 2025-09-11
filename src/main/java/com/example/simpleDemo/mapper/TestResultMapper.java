package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.TeacherCommentResult;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestResultMapper {
    // 教师评语结果插入数据
    int insertTeacherComment(TeacherCommentResult teacherCommentResult);
}
