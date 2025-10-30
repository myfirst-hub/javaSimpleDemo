package com.example.simpleDemo.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.simpleDemo.entity.PracticeTestMap;
import com.example.simpleDemo.entity.PracticeTestResult;
import com.example.simpleDemo.entity.PracticeTrainProgram;

@Mapper
public interface PracticeTrainProgramMapper {
        List<PracticeTrainProgram> selectPracticeTrainProgramList(@Param("name") String name,
                        @Param("semester") String semester, @Param("subjectIds") Long[] subjectIds);

        // 新增实操培训计划
        int insertPracticeTrainProgram(PracticeTrainProgram practiceTrainProgram);

        // 编辑实操培训计划
        int updatePracticeTrainProgram(PracticeTrainProgram practiceTrainProgram);

        // 删除实操培训计划
        int deletePracticeTrainProgram(@Param("id") Long id);

        // 根据ID查询实操培训计划详情
        PracticeTrainProgram selectPracticeTrainProgramById(@Param("id") Long id);

        // // 根据科目ID查询实操培训计划
        PracticeTrainProgram selectPracticeTrainProgramBySubjectId(@Param("subjectId") Long subjectId);

        // 动态插入实操考试结果
        int insertDynamicPracticeTestResult(PracticeTestResult PracticeTestResult);

        // 动态更新实操考试结果
        int updateDynamicPracticeTestResult(PracticeTestResult PracticeTestResult);

        // 实操考试结果关联表插入数据
        int insertPracticeTestMap(PracticeTestMap PracticeTestMap);

        // // 根据学生ID、科目ID查询实操考试结果
        List<Map<String, Object>> selectPracticeScoresByStudentIdAndSubjectId(@Param("studentId") Long studentId,
                        @Param("subjectId") Long subjectId);

        // 根据学生ID、测试名称和测试时间查询是否存在同一次考试记录
        PracticeTestResult selectPracticeTestResultByStudentIdAndTestInfo(@Param("studentId") Long studentId,
                        @Param("testName") String testName, @Param("testTime") java.util.Date testTime);
}
