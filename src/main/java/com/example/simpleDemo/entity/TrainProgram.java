package com.example.simpleDemo.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TrainProgram {
    private Integer id;
    private String name;
    private String semester;
    private String trainDescribe;
    private BigDecimal trainTime;
    private Date createdAt;
    private Date updatedAt;

    // Constructors
    public TrainProgram() {
    }

    public TrainProgram(Integer id, String name, String semester, String trainDescribe, BigDecimal trainTime, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.semester = semester;
        this.trainDescribe = trainDescribe;
        this.trainTime = trainTime;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "TrainProgram{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", semester='" + semester + '\'' +
                ", trainDescribe='" + trainDescribe + '\'' +
                ", trainTime=" + trainTime +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}