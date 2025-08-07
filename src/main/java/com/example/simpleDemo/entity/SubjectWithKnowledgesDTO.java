package com.example.simpleDemo.entity;

import java.util.List;

/**
 * 用于接收科目新增请求的DTO类，包含科目信息和关联的知识点列表
 */
public class SubjectWithKnowledgesDTO {
    private Subject subject;
    private List<Knowledge> knowledges;

    public SubjectWithKnowledgesDTO() {
    }

    public SubjectWithKnowledgesDTO(Subject subject, List<Knowledge> knowledges) {
        this.subject = subject;
        this.knowledges = knowledges;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public List<Knowledge> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(List<Knowledge> knowledges) {
        this.knowledges = knowledges;
    }

    @Override
    public String toString() {
        return "SubjectWithKnowledgesDTO{" +
                "subject=" + subject +
                ", knowledges=" + knowledges +
                '}';
    }
}