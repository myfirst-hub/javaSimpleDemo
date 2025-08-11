package com.example.simpleDemo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.simpleDemo.entity.TrainProgram;
import com.example.simpleDemo.mapper.TrainProgramMapper;
import com.example.simpleDemo.utils.PageInfoResult;

import java.util.List;

@Service
public class TrainProgramService {

    @Autowired
    private TrainProgramMapper trainProgramMapper;

    public List<TrainProgram> findAllTrainPrograms() {
        return trainProgramMapper.findAll();
    }

    public PageInfoResult<TrainProgram> findTrainProgramsWithPageHelper(int page, int size, String name,
            String semester) {
        // 开启分页
        PageHelper.startPage(page, size);

        // 查询数据
        List<TrainProgram> trainPrograms = trainProgramMapper.findTrainPrograms(name, semester);

        // 封装分页结果
        PageInfo<TrainProgram> pageInfo = new PageInfo<>(trainPrograms);
        return new PageInfoResult<>(pageInfo);
    }
}