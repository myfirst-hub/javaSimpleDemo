package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.Subject;
import com.example.simpleDemo.entity.TheoryTrainProgram;
import com.example.simpleDemo.mapper.TheoryTrainProgramMapper;
import com.example.simpleDemo.mapper.UploadMapper;
import com.example.simpleDemo.mapper.SubjectMapper;
import com.example.simpleDemo.mapper.SubjectQuestionMapper;
import com.example.simpleDemo.utils.PageInfoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TheoryTrainProgramService {

    @Autowired
    private TheoryTrainProgramMapper theoryTrainProgramMapper;

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private UploadMapper uploadMapper;

    @Autowired
    private SubjectQuestionMapper subjectQuestionMapper;

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
    @Transactional(rollbackFor = Exception.class)
    public int deleteTheoryTrainProgram(Long id, Long subjectId) {
        // 删除subject_question_file表中与该科目关联的数据
        uploadMapper.deleteSubjectQuestionFileBySubjectId(subjectId);

        // 删除subject_question表中与该科目关联的数据
        subjectQuestionMapper.deleteSubjectQuestionBySubjectId(subjectId);

        return theoryTrainProgramMapper.deleteTheoryTrainProgram(id);
    }

    // 根据ID查询理论培训计划详情
    public TheoryTrainProgram selectTheoryTrainProgramById(Long id) {
        TheoryTrainProgram program = theoryTrainProgramMapper.selectTheoryTrainProgramById(id);
        if (program != null && program.getSubjectId() != null) {
            // 根据subjectId查询科目名称，这里需要根据实际的科目实体和Mapper进行调整
            Subject subject = subjectMapper.findSubjectById(program.getSubjectId());
            if (subject != null) {
                program.setSubjectName(subject.getName());
            }
        }
        return program;
    }
}