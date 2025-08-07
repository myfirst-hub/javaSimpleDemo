package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.Subject;
import com.example.simpleDemo.entity.Knowledge;
import com.example.simpleDemo.mapper.SubjectMapper;
import com.example.simpleDemo.mapper.SubjectKnowledgeMapper;
import com.example.simpleDemo.utils.PageInfoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    @Autowired
    private SubjectMapper subjectMapper;

    @Autowired
    private SubjectKnowledgeMapper subjectKnowledgeMapper;

    /**
     * 分页查询科目列表，包含知识点信息
     * 
     * @param pageNum  页码
     * @param pageSize 每页大小
     * @param name     科目名称（可选）
     * @param semester 学期（可选）
     * @return 分页结果，包含知识点信息
     */
    public PageInfoResult<SubjectWithKnowledges> findSubjectsWithKnowledges(int pageNum, int pageSize, String name,
            String semester) {
        // 开启分页
        PageHelper.startPage(pageNum, pageSize);

        // 查询科目数据
        List<Subject> subjects = subjectMapper.findSubjects(name, semester);

        // 为每个科目查询关联的知识点
        List<SubjectWithKnowledges> subjectWithKnowledgesList = subjects.stream().map(subject -> {
            SubjectWithKnowledges subjectWithKnowledges = new SubjectWithKnowledges(subject);
            List<Knowledge> knowledges = subjectKnowledgeMapper.findKnowledgesBySubjectId(subject.getId());
            subjectWithKnowledges.setKnowledges(knowledges);
            return subjectWithKnowledges;
        }).toList();

        // 封装分页结果
        PageInfo<SubjectWithKnowledges> pageInfo = new PageInfo<>(subjectWithKnowledgesList);
        return new PageInfoResult<>(pageInfo);
    }

    // 内部类，用于封装科目和知识点信息
    public static class SubjectWithKnowledges extends Subject {
        private List<Knowledge> knowledges;

        public SubjectWithKnowledges() {
            super();
        }

        public SubjectWithKnowledges(Subject subject) {
            super();
            this.setId(subject.getId());
            this.setName(subject.getName());
            this.setSemester(subject.getSemester());
            this.setSubjectDescribe(subject.getSubjectDescribe());
            this.setCreatedAt(subject.getCreatedAt());
            this.setUpdatedAt(subject.getUpdatedAt());
        }

        public List<Knowledge> getKnowledges() {
            return knowledges;
        }

        public void setKnowledges(List<Knowledge> knowledges) {
            this.knowledges = knowledges;
        }
    }
}