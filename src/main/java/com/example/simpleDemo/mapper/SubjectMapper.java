package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.Subject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SubjectMapper {
    List<Subject> findSubjects(@Param("name") String name, @Param("semester") String semester);

    Subject findSubjectById(@Param("id") Long id);

    int insertSubject(Subject subject);

    int updateSubject(Subject subject);

    int deleteSubjectById(@Param("id") Long id);
}