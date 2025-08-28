package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.*;
import com.example.simpleDemo.mapper.ClassesMapper;
import com.example.simpleDemo.mapper.SubjectKnowledgeMapper;
import com.example.simpleDemo.mapper.SubjectMapper;
import com.example.simpleDemo.utils.PageInfoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

@Service
public class SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private SubjectKnowledgeMapper subjectKnowledgeMapper;

    @Autowired
    private SubjectOutlineService subjectOutlineService;

    @Autowired
    private KnowledgeTreeService knowledgeTreeService;

    @Autowired
    private SubjectKnowledgeService subjectKnowledgeService;

    @Autowired
    private ClassesMapper classesMapper;

    /**
     * 分页查询科目列表
     * 
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @param name     科目名称（可选）
     * @param semester 学期（可选）
     * @return 分页结果
     */
    public PageInfoResult<Subject> findSubjects(int pageNum, int pageSize, String name,
            String semester) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询科目数据
        List<Subject> subjects = subjectMapper.findSubjects(name, semester);

        // 获取分页信息
        PageInfo<Subject> subjectPageInfo = new PageInfo<>(subjects);
        return new PageInfoResult<>(subjectPageInfo);
    }

    /**
     * 通过教师id分页查询科目列表
     * 
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @param name     科目名称（可选）
     * @param semester 学期（可选）
     * @return 分页结果
     */
    @Transactional(readOnly = true)
    public PageInfoResult<Subject> findSubjectsByTeacherId(int pageNum, int pageSize, Long teacherId, String name,
            String semester) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        List<Long> subjectIds = classesMapper.findSubjectIdsByTeacherId(teacherId);

        // 查询科目数据
        List<Subject> subjects = subjectMapper.findSubjects(name, semester);

        // 过滤只保留subjectIds中存在的科目
        List<Subject> filteredSubjects = subjects.stream()
                .filter(subject -> subjectIds.contains(subject.getId()))
                .collect(Collectors.toList());

        // 获取分页信息
        PageInfo<Subject> subjectPageInfo = new PageInfo<>(filteredSubjects);
        return new PageInfoResult<>(subjectPageInfo);
    }

    /**
     * 新增科目，只处理科目信息
     * 
     * @param subject 科目对象
     * @return 是否添加成功
     */
    public boolean addSubject(Subject subject) {
        try {
            // 1. 插入科目信息
            subjectMapper.insertSubject(subject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 编辑科目，只处理科目信息
     * 
     * @param subject 包含科目和知识点信息的DTO对象
     * @return 是否编辑成功
     */
    public boolean updateSubject(Subject subject) {
        try {
            // 1. 更新科目信息
            subjectMapper.updateSubject(subject);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据ID删除科目
     * 
     * @param id 科目ID
     * @return 是否删除成功
     */
    @Transactional
    public boolean deleteSubjectById(Long id) {
        try {
            // 1. 删除关联的科目大纲信息
            subjectOutlineService.deleteBySubjectId(id);

            List<Long> ids = subjectKnowledgeMapper.findKnowledgeIdsBySubjectId(id);

            // 2. 删除关联的知识点信息
            for (Long knowledgeId : ids) {
                knowledgeTreeService.deleteKnowledgeTree(knowledgeId);
            }
            // 3. 删除关联的科目知识点信息
            subjectKnowledgeService.deleteBySubjectId(id);

            // 4. 删除科目信息
            subjectMapper.deleteSubjectById(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}