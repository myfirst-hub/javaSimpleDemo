package com.example.simpleDemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.simpleDemo.entity.TrainProgram;

import java.util.List;

@Mapper
public interface TrainProgramMapper {

    // 支持PageHelper的查询方法
    List<TrainProgram> findTrainPrograms(@Param("name") String name, @Param("semester") String semester);

    // 查询所有训练项目
    List<TrainProgram> findAll();
}