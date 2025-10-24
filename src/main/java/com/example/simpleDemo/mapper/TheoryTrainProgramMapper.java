package com.example.simpleDemo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.simpleDemo.entity.TheoryTrainProgram;
import com.example.simpleDemo.entity.TheoryTestResult;
import com.example.simpleDemo.entity.TheoryTestMap;

@Mapper
public interface TheoryTrainProgramMapper {
        List<TheoryTrainProgram> selectTheoryTrainProgramList(@Param("name") String name,
                        @Param("semester") String semester, @Param("subjectIds") Long[] subjectIds);

        // 新增理论培训计划
        int insertTheoryTrainProgram(TheoryTrainProgram theoryTrainProgram);

        // 编辑理论培训计划
        int updateTheoryTrainProgram(TheoryTrainProgram theoryTrainProgram);

        // 删除理论培训计划
        int deleteTheoryTrainProgram(@Param("id") Long id);

        // 根据ID查询理论培训计划详情
        TheoryTrainProgram selectTheoryTrainProgramById(@Param("id") Long id);

        // 根据科目ID查询理论培训计划
        TheoryTrainProgram selectTheoryTrainProgramBySubjectId(@Param("subjectId") Long subjectId);

        // 动态插入理论考试结果
        int insertDynamicTheoryTestResult(TheoryTestResult theoryTestResult);

        // 动态更新理论考试结果
        int updateDynamicTheoryTestResult(TheoryTestResult theoryTestResult);

        // 理论考试结果关联表插入数据
        int insertTheoryTestMap(TheoryTestMap theoryTestMap);

        // 根据学生ID、科目ID查询理论考试结果
        List<Map<String, Object>> selectTheoryScoresByStudentIdAndSubjectId(@Param("studentId") Long studentId,
                        @Param("subjectId") Long subjectId);

        // 根据学生ID、测试名称和测试时间查询是否存在同一次考试记录
        TheoryTestResult selectTheoryTestResultByStudentIdAndTestInfo(@Param("studentId") Long studentId,
                        @Param("testName") String testName, @Param("testTime") java.util.Date testTime);
}