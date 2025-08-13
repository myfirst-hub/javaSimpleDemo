package com.example.simpleDemo.mapper;

import com.example.simpleDemo.entity.Knowledge;
import com.example.simpleDemo.entity.TrainProgramKnowledge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrainProgramKnowledgeMapper {

    /**
     * 根据训练项目ID查询关联的知识点
     * 
     * @param trainId 训练项目ID
     * @return 知识点列表
     */
    List<Knowledge> findKnowledgesByTrainId(@Param("trainId") Integer trainId);
    
    /**
     * 插入训练项目和知识点的关联关系
     * 
     * @param trainProgramKnowledge 训练项目知识点关联实体
     * @return 影响的行数
     */
    int insertTrainProgramKnowledge(TrainProgramKnowledge trainProgramKnowledge);

}