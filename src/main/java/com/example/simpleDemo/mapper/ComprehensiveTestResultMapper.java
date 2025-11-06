package com.example.simpleDemo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.simpleDemo.entity.ComprehensiveTestResult;

@Mapper
public interface ComprehensiveTestResultMapper {
        int insertBatch(@Param("list") List<ComprehensiveTestResult> list);

        // 根据学生ID和科目ID查询综合测评结果，支持参数为空
        List<ComprehensiveTestResult> selectByStudentIdAndSubjectId(@Param("studentId") Long studentId,
                        @Param("subjectId") Long subjectId);

        // 修改：根据学生ID和教师ID查询综合测评结果，支持参数为空
        List<ComprehensiveTestResult> selectByStudentIdAndTeacherId(@Param("studentId") Long studentId,
                        @Param("teacherId") Long teacherId);

        // 新增：根据学生ID、科目ID和班级ID查询综合测评结果
        List<ComprehensiveTestResult> selectByStudentIdAndSubjectIdAndClassId(@Param("studentId") Long studentId,
                        @Param("subjectId") Long subjectId);

        // 新增：批量更新方法
        int updateBatch(@Param("list") List<ComprehensiveTestResult> list);
}