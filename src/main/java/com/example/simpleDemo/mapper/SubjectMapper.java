package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SubjectMapper {

    /**
     * 查询所有科目
     * 
     * @return 科目列表
     */
    @Select("SELECT id, name, semester, subject_describe as subjectDescribe, created_at as createdAt, updated_at as updatedAt FROM subjects")
    List<Subject> findAllSubjects();

    /**
     * 根据名称查询科目列表（模糊查询）
     * 
     * @param name 科目名称
     * @return 科目列表
     */
    List<Subject> findSubjectsByName(@Param("name") String name);
}