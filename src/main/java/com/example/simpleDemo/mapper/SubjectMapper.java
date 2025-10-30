package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.Subject;
import com.example.simpleDemo.entity.Student;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SubjectMapper {
    List<Subject> findSubjects(@Param("name") String name, @Param("semester") String semester);

    List<Subject> findSubjectByStudentId(@Param("studentId") Long name, @Param("teacherId") Long teacherId);

    Subject findSubjectById(@Param("id") Long id);

    int insertSubject(Subject subject);

    int updateSubject(Subject subject);

    int deleteSubjectById(@Param("id") Long id);

    // 添加根据科目ID查找班级和学生信息的方法
    List<Student> findClassesAndStudentsBySubjectId(@Param("subjectId") Long subjectId);

    // 添加根据科目ID查找训练信息的方法
    Map<String, Object> findTrainInfoBySubjectId(@Param("subjectId") Long subjectId);

    // 添加根据科目ID查找训练信息的方法
    Map<String, Object> findPracticeTrainInfoBySubjectId(@Param("subjectId") Long subjectId);
}