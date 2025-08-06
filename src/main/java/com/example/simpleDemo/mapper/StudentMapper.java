package com.example.simpleDemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.example.simpleDemo.entity.Student;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM students")
    List<Student> findAll();

    // 使用XML映射的示例方法
    List<Student> findAllStudents();
    
    // 支持PageHelper的查询方法
    List<Student> findStudents(@Param("name") String name, @Param("className") String className);
    
    // 插入学生信息
    int insertStudent(Student student);
    
    // 更新学生信息
    int updateStudent(Student student);
    
    // 根据ID查找学生
    Student findStudentById(@Param("id") Integer id);
    
    // 根据ID删除学生
    int deleteStudentById(@Param("id") Integer id);
}