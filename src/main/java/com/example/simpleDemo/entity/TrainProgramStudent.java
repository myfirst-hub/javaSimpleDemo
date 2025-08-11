package com.example.simpleDemo.entity;

import java.util.Date;

public class TrainProgramStudent {
    private Integer id;
    private Integer trainId;
    private Integer studentId;
    private Date createdAt;

    // Constructors
    public TrainProgramStudent() {
    }

    public TrainProgramStudent(Integer id, Integer trainId, Integer studentId, Date createdAt) {
        this.id = id;
        this.trainId = trainId;
        this.studentId = studentId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "TrainProgramStudent{" +
                "id=" + id +
                ", trainId=" + trainId +
                ", studentId=" + studentId +
                ", createdAt=" + createdAt +
                '}';
    }
}