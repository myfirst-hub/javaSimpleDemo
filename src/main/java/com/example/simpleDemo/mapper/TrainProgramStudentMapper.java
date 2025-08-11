package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.Student;
import com.example.simpleDemo.entity.TrainProgramStudent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrainProgramStudentMapper {

    /**
     * 根据训练项目ID查询关联的学生列表
     * 
     * @param trainId 训练项目ID
     * @return 学生列表
     */
    List<Student> findStudentsByTrainId(@Param("trainId") Integer trainId);

    /**
     * 插入训练项目和学生的关联关系
     * 
     * @param trainProgramStudent 训练项目学生关联实体
     * @return 影响的行数
     */
    int insertTrainProgramStudent(TrainProgramStudent trainProgramStudent);

    /**
     * 根据训练项目ID删除关联关系
     * 
     * @param trainId 训练项目ID
     * @return 影响的行数
     */
    int deleteByTrainId(@Param("trainId") Integer trainId);

    /**
     * 根据训练项目ID和学生ID删除关联关系
     * 
     * @param trainId   训练项目ID
     * @param studentId 学生ID
     * @return 影响的行数
     */
    int deleteByTrainIdAndStudentId(@Param("trainId") Integer trainId, @Param("studentId") Integer studentId);
}