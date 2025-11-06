package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.TeacherCommentResult;
import com.example.simpleDemo.dto.TeacherCommentResultDTO;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestResultMapper {
        // 教师评语结果插入数据
        int insertTeacherComment(TeacherCommentResult teacherCommentResult);

        // 通过学生id查询教师评语详情
        List<TeacherCommentResultDTO> selectTeacherCommentResultWithDetails(@Param("studentId") Long studentId);

        // 通过学生id和教师id查询理论考试次数及培训学时
        List<Map<String, Object>> selectTestCountAndTrainHoursByTeacherId(@Param("studentId") Long studentId,
                        @Param("teacherId") Long teacherId);

        // 通过学生id和教师id查询实操考试次数及培训学时
        Map<String, Object> selectPracticeTestCountAndTrainHoursByTeacherId(@Param("studentId") Long studentId,
                        @Param("teacherId") Long teacherId);
}
