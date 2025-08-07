package com.example.simpleDemo.service;

import com.example.simpleDemo.entity.Knowledge;
import com.example.simpleDemo.entity.Subject;
import com.example.simpleDemo.entity.SubjectKnowledge;
import com.example.simpleDemo.entity.SubjectWithKnowledgesDTO;
import com.example.simpleDemo.mapper.KnowledgeMapper;
import com.example.simpleDemo.mapper.SubjectKnowledgeMapper;
import com.example.simpleDemo.mapper.SubjectMapper;
import com.example.simpleDemo.service.SubjectService.SubjectWithKnowledges;
import com.example.simpleDemo.utils.PageInfoResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService {

    @Autowired
 private SubjectMapper subjectMapper;

    @Autowired
    private KnowledgeMapper knowledgeMapper;

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

        // 获取分页信息
        PageInfo<Subject> subjectPageInfo = new PageInfo<>(subjects);

        // 为每个科目查询关联的知识点
        List<SubjectWithKnowledges> subjectWithKnowledgesList = subjects.stream().map(subject -> {
            SubjectWithKnowledges subjectWithKnowledges = new SubjectWithKnowledges(subject);
            List<Knowledge> knowledges = subjectKnowledgeMapper.findKnowledgesBySubjectId(subject.getId());
            subjectWithKnowledges.setKnowledges(knowledges);
            return subjectWithKnowledges;
        }).toList();

        // 封装分页结果，使用原始的PageInfo对象确保分页信息正确
        PageInfo<SubjectWithKnowledges> pageInfo = new PageInfo<>(subjectWithKnowledgesList);
        pageInfo.setTotal(subjectPageInfo.getTotal());
        return new PageInfoResult<>(pageInfo);
    }

    /**
     * 新增科目，同时处理知识点和科目知识点映射关系
     * 
     * @param subjectWithKnowledgesDTO 包含科目和知识点信息的DTO对象
     * @return 是否添加成功
     */
    public boolean addSubjectWithKnowledges(SubjectWithKnowledgesDTO subjectWithKnowledgesDTO) {
        try {
            // 1. 插入科目信息
            Subject subject = subjectWithKnowledgesDTO.getSubject();
            subjectMapper.insertSubject(subject);
            Integer subjectId = subject.getId(); // 获取生成的科目ID

            // 2. 处理知识点：更新已存在的知识点，插入新的知识点
            List<Knowledge> knowledges = subjectWithKnowledgesDTO.getKnowledges();
            for (Knowledge knowledge : knowledges) {
                if (knowledge.getId() != null) {
                    // 如果知识点ID存在，则更新知识点
                    knowledgeMapper.updateKnowledgeById(knowledge);
                } else {
                    // 如果知识点ID不存在，则插入新的知识点
                    knowledgeMapper.insertKnowledge(knowledge);
                }
            }

            // 3. 处理科目和知识点的映射关系
            for (Knowledge knowledge : knowledges) {
                SubjectKnowledge subjectKnowledge = new SubjectKnowledge();
                subjectKnowledge.setSubjectId(subjectId);
                subjectKnowledge.setKnowledgeId(knowledge.getId());
                subjectKnowledge.setMasteryLevel(knowledge.getMasteryLevel());
                subjectKnowledgeMapper.insertSubjectKnowledge(subjectKnowledge);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
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