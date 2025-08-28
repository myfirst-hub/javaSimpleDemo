package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.TheoryTrainProgram;
import com.example.simpleDemo.mapper.TheoryTrainProgramMapper;
import com.example.simpleDemo.utils.PageInfoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TheoryTrainProgramService {

    @Autowired
    private TheoryTrainProgramMapper theoryTrainProgramMapper;

    public PageInfoResult<TheoryTrainProgram> selectTheoryTrainProgramList(Integer pageNum, Integer pageSize,
            String name, String semester) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询科目数据
        List<TheoryTrainProgram> theoryTrainPrograms = theoryTrainProgramMapper.selectTheoryTrainProgramList(name,
                semester);

        // 获取分页信息
        PageInfo<TheoryTrainProgram> theoryTrainProgramPageInfo = new PageInfo<>(theoryTrainPrograms);
        return new PageInfoResult<>(theoryTrainProgramPageInfo);
    }
}