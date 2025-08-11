package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.Student;
import com.example.simpleDemo.mapper.TrainProgramStudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainProgramStudentService {
    
    @Autowired
    private TrainProgramStudentMapper trainProgramStudentMapper;
    
    /**
     * 根据训练项目ID查询关联的学生列表
     * @param trainId 训练项目ID
     * @return 学生列表
     */
    public List<Student> findStudentsByTrainId(Integer trainId) {
        return trainProgramStudentMapper.findStudentsByTrainId(trainId);
    }
}