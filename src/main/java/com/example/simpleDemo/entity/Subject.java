package com.example.simpleDemo.entity;

import java.util.Date;

public class Subject {
    private Long id;
    private String name;
    private String type;
    private String semester;
    private String subjectDescribe;
    private Date createdAt;
    private Date updatedAt;

    // 构造函数
    public Subject() {
    }

    public Subject(String name, String semester, String subjectDescribe) {
        this.name = name;
        this.semester = semester;
        this.subjectDescribe = subjectDescribe;
    }

    public Subject(Long id, String name, String semester, String subjectDescribe, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.semester = semester;
        this.subjectDescribe = subjectDescribe;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter和Setter方法
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void seType(String type) {
        this.type = type;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSubjectDescribe() {
        return subjectDescribe;
    }

    public void setSubjectDescribe(String subjectDescribe) {
        this.subjectDescribe = subjectDescribe;
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
        return "Subject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", semester='" + semester + '\'' +
                ", subjectDescribe='" + subjectDescribe + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}