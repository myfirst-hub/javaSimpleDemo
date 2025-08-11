package com.example.simpleDemo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simpleDemo.entity.Knowledge;
import com.example.simpleDemo.entity.Student;
import com.example.simpleDemo.entity.TrainProgram;
import com.example.simpleDemo.entity.TrainProgramWithRelationsDTO;
import com.example.simpleDemo.mapper.TrainProgramMapper;
import com.example.simpleDemo.mapper.TrainProgramStudentMapper;
import com.example.simpleDemo.mapper.TrainProgramKnowledgeMapper;
import com.example.simpleDemo.utils.PageInfoResult;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainProgramService {

    @Autowired
    private TrainProgramMapper trainProgramMapper;

    @Autowired
    private TrainProgramStudentMapper trainProgramStudentMapper;

    @Autowired
    private TrainProgramKnowledgeMapper trainProgramKnowledgeMapper;

    public List<TrainProgram> findAllTrainPrograms() {
        return trainProgramMapper.findAll();
    }

    public PageInfoResult<TrainProgramWithRelationsDTO> findTrainProgramsWithRelations(int page, int size, String name,
            String semester) {
        // 开启分页
        PageHelper.startPage(page, size);

        // 查询数据
        List<TrainProgram> trainPrograms = trainProgramMapper.findTrainPrograms(name, semester);

        // 转换为带关联信息的DTO
        List<TrainProgramWithRelationsDTO> trainProgramsWithRelations = trainPrograms.stream().map(trainProgram -> {
            TrainProgramWithRelationsDTO dto = new TrainProgramWithRelationsDTO(
                    trainProgram.getId(),
                    trainProgram.getName(),
                    trainProgram.getSemester(),
                    trainProgram.getTrainDescribe(),
                    trainProgram.getTrainTime(),
                    trainProgram.getCreatedAt(),
                    trainProgram.getUpdatedAt());

            // 查询关联的学生
            List<Student> students = trainProgramStudentMapper.findStudentsByTrainId(trainProgram.getId());
            dto.setStudents(students);

            // 查询关联的知识点
            List<Knowledge> knowledges = trainProgramKnowledgeMapper.findKnowledgesByTrainId(trainProgram.getId());
            dto.setKnowledges(knowledges);

            return dto;
        }).collect(Collectors.toList());

        // 封装分页结果
        PageInfo<TrainProgramWithRelationsDTO> pageInfo = new PageInfo<>(trainProgramsWithRelations);
        return new PageInfoResult<>(pageInfo);
    }
}