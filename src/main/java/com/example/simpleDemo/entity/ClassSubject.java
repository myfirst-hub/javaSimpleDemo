package com.example.simpleDemo.entity;

import java.util.Date;

public class ClassSubject {
    private Long id;
    private Long classId;
    private Long subjectId;
    private Date createTime;

    // Constructors
    public ClassSubject() {
    }

    public ClassSubject(Long id, Long classId, Long subjectId, Date createTime) {
        this.id = id;
        this.classId = classId;
        this.subjectId = subjectId;
        this.createTime = createTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ClassSubject{" +
                "id=" + id +
                ", classId=" + classId +
                ", subjectId=" + subjectId +
                ", createTime=" + createTime +
                '}';
    }
}