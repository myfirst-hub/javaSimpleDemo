package com.example.simpleDemo.entity;

import java.util.Date;

public class SubjectKnowledge {
    private Integer id;
    private Integer subjectId;
    private Integer knowledgeId;
    private Integer masteryLevel;
    private Date createdAt;
    private Date updatedAt;

    // 构造函数
    public SubjectKnowledge() {}

    public SubjectKnowledge(Integer subjectId, Integer knowledgeId, Integer masteryLevel) {
        this.subjectId = subjectId;
        this.knowledgeId = knowledgeId;
        this.masteryLevel = masteryLevel;
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
    }

    public Integer getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(Integer knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public Integer getMasteryLevel() {
        return masteryLevel;
    }

    public void setMasteryLevel(Integer masteryLevel) {
        this.masteryLevel = masteryLevel;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "SubjectKnowledge{" +
                "id=" + id +
                ", subjectId=" + subjectId +
                ", knowledgeId=" + knowledgeId +
                ", masteryLevel=" + masteryLevel +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}