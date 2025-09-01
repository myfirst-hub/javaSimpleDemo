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

    // 新增理论培训计划
    public int insertTheoryTrainProgram(TheoryTrainProgram theoryTrainProgram) {
        // 设置创建时间和更新时间
        theoryTrainProgram.setCreatedAt(new java.util.Date());
        theoryTrainProgram.setUpdatedAt(new java.util.Date());
        return theoryTrainProgramMapper.insertTheoryTrainProgram(theoryTrainProgram);
    }

    // 编辑理论培训计划
    public int updateTheoryTrainProgram(TheoryTrainProgram theoryTrainProgram) {
        // 设置更新时间
        theoryTrainProgram.setUpdatedAt(new java.util.Date());
        return theoryTrainProgramMapper.updateTheoryTrainProgram(theoryTrainProgram);
    }

    // 删除理论培训计划
    public int deleteTheoryTrainProgram(Long id) {
        return theoryTrainProgramMapper.deleteTheoryTrainProgram(id);
    }
}