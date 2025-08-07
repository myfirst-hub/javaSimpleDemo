package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubjectMapper {
    // 支持PageHelper的查询方法
    List<Subject> findSubjects(@Param("name") String name, @Param("semester") String semester);
    
    // 新增科目
    int insertSubject(Subject subject);
}