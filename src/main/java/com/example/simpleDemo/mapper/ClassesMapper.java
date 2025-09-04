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

    // 根据教师id查询科目id
    List<Long> findSubjectIdsByTeacherId(@Param("teacherId") Long teacherId);

    // 根据教师id查询班级id
    List<Long> findClassIdsByTeacherId(@Param("teacherId") Long teacherId);

    // 插入班级信息，确保在XML中配置了useGeneratedKeys和keyProperty
    int insertClass(Classes classes);

    // 更新班级信息
    int updateClass(Classes classes);

    // 根据ID查找班级
    Classes findClassById(Long id);

    // 根据ID删除班级
    int deleteClassById(Long id);
}