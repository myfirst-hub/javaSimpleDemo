package com.example.simpleDemo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.simpleDemo.entity.Classes;

@Mapper
public interface ClassesMapper {
    // 支持PageHelper的查询方法
    List<Classes> findClasses(@Param("name") String nameString, @Param("teacherName") String teacherName,
            @Param("subjectName") String subjectName);

    // 插入班级信息
    int insertClass(Classes classes);
}
