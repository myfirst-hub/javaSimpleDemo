package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.*;
import com.example.simpleDemo.mapper.TrainProgramMapper;
import com.example.simpleDemo.mapper.TrainProgramStudentMapper;
import com.example.simpleDemo.mapper.TrainProgramKnowledgeMapper;
import com.example.simpleDemo.utils.PageInfoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
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
    
    @Autowired
    private KnowledgeService knowledgeService;

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

            // 查询关联的学生ID列表
            List<Student> students = trainProgramStudentMapper.findStudentsByTrainId(trainProgram.getId());
            List<Integer> studentIds = students.stream().map(Student::getId).collect(Collectors.toList());
            dto.setStudentIds(studentIds);

            // 查询关联的知识点
            List<Knowledge> knowledges = trainProgramKnowledgeMapper.findKnowledgesByTrainId(trainProgram.getId());
            dto.setKnowledges(knowledges);

            return dto;
        }).collect(Collectors.toList());

        // 封装分页结果
        PageInfo<TrainProgramWithRelationsDTO> pageInfo = new PageInfo<>(trainProgramsWithRelations);
        return new PageInfoResult<>(pageInfo);
    }
    
    public TrainProgram createTrainProgram(TrainProgramCreateDTO createDTO) {
        // 创建训练项目
        TrainProgram trainProgram = new TrainProgram();
        trainProgram.setName(createDTO.getName());
        trainProgram.setSemester(createDTO.getSemester());
        trainProgram.setTrainDescribe(createDTO.getTrainDescribe());
        trainProgram.setTrainTime(createDTO.getTrainTime());
        trainProgram.setCreatedAt(new Date());
        trainProgram.setUpdatedAt(new Date());
        
        // 插入训练项目
        trainProgramMapper.insertTrainProgram(trainProgram);
        
        // 创建学生关联
        if (createDTO.getStudentIds() != null && !createDTO.getStudentIds().isEmpty()) {
            createDTO.getStudentIds().forEach(studentId -> {
                TrainProgramStudent trainProgramStudent = new TrainProgramStudent();
                trainProgramStudent.setTrainId(trainProgram.getId());
                trainProgramStudent.setStudentId(studentId);
                trainProgramStudent.setCreatedAt(new Date());
                trainProgramStudentMapper.insertTrainProgramStudent(trainProgramStudent);
            });
        }
        
        // 创建知识点关联
        if (createDTO.getKnowledges() != null && !createDTO.getKnowledges().isEmpty()) {
            createDTO.getKnowledges().forEach(knowledge -> {
                // 如果知识点ID为空，则为新增知识点
                if (knowledge.getId() == null) {
                    // 插入新知识点
                    knowledge.setCreatedAt(new Date());
                    knowledge.setUpdatedAt(new Date());
                    knowledgeService.insertKnowledge(knowledge);
                } else {
                    // 更新现有知识点
                    knowledge.setUpdatedAt(new Date());
                    knowledgeService.updateKnowledgeById(knowledge);
                }
                
                // 创建训练项目与知识点的关联
                TrainProgramKnowledge trainProgramKnowledge = new TrainProgramKnowledge();
                trainProgramKnowledge.setTrainId(trainProgram.getId());
                trainProgramKnowledge.setKnowledgeId(knowledge.getId());
                trainProgramKnowledge.setCreatedAt(new Date());
                trainProgramKnowledgeMapper.insertTrainProgramKnowledge(trainProgramKnowledge);
            });
        }
        
        return trainProgram;
    }
}