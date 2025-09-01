package com.example.simpleDemo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.simpleDemo.entity.TheoryTrainProgram;

@Mapper
public interface TheoryTrainProgramMapper {
    List<TheoryTrainProgram> selectTheoryTrainProgramList(@Param("name") String name,
            @Param("semester") String semester);

    // 新增理论培训计划
    int insertTheoryTrainProgram(TheoryTrainProgram theoryTrainProgram);

    // 编辑理论培训计划
    int updateTheoryTrainProgram(TheoryTrainProgram theoryTrainProgram);

    // 删除理论培训计划
    int deleteTheoryTrainProgram(@Param("id") Long id);
}