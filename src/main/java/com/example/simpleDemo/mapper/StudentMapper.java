package com.example.simpleDemo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.simpleDemo.dto.TheoryTestDetailResultDTO;
import com.example.simpleDemo.dto.PracticeTestDetailResultDTO;
import com.example.simpleDemo.entity.Student;

import java.util.List;

@Mapper
public interface StudentMapper {

        // 新增支持条件查询的所有学生查询方法
        List<Student> findStudents(@Param("name") String name, @Param("className") String className);

        // 新增支持条件查询的所有学生查询方法
        List<Student> findAllStudents(@Param("name") String name, @Param("className") String className);

        // 插入学生信息
        int insertStudent(Student student);

        // 更新学生信息
        int updateStudent(Student student);

        // 根据ID查找学生
        Student findStudentById(@Param("id") Long id);

        // 根据ID删除学生
        int deleteStudentById(@Param("id") Long id);

        // 通过学生id查询理论考试详情
        List<TheoryTestDetailResultDTO> findTheoryTestDetailByStudentId(@Param("studentId") Long studentId,
                        @Param("teacherId") Long teacherId,
                        @Param("studentName") String studentName,
                        @Param("className") String className);

        // 通过学生id查询实操考试详情
        List<PracticeTestDetailResultDTO> findPracticeTestDetailByStudentId(@Param("studentId") Long studentId,
                        @Param("teacherId") Long teacherId,
                        @Param("studentName") String studentName,
                        @Param("className") String className);
}