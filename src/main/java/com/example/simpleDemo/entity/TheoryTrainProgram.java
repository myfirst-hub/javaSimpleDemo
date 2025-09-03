package com.example.simpleDemo.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TheoryTrainProgram {
    private Long id;
    private String name;
    private String semester;
    private String trainDescribe;
    private BigDecimal trainTime;
    private Long subjectId;
    private Date createdAt;
    private Date updatedAt;
    
    // 添加科目名称字段
    private String subjectName;

    // Constructors
    public TheoryTrainProgram() {
    }

    public TheoryTrainProgram(Long id, String name, String semester, String trainDescribe, BigDecimal trainTime,
            Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.semester = semester;
        this.trainDescribe = trainDescribe;
        this.trainTime = trainTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTrainDescribe() {
        return trainDescribe;
    }

    public void setTrainDescribe(String trainDescribe) {
        this.trainDescribe = trainDescribe;
    }

    public BigDecimal getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(BigDecimal trainTime) {
        this.trainTime = trainTime;
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
    
    // 科目名称的Getter和Setter
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "TrainProgram{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", semester='" + semester + '\'' +
                ", trainDescribe='" + trainDescribe + '\'' +
                ", trainTime=" + trainTime +
                ", subjectId=" + subjectId +
                ", subjectName='" + subjectName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}