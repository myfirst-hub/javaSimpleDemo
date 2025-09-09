package com.example.simpleDemo.entity;

import java.util.Date;

public class TheoryTestMap {
    private Long id;
    private Long theoryTestId;
    private Long studentId;
    private Long subjectId;
    private Long trainProgramId;
    private Date createdAt;
    private Date updatedAt;

    // Constructors
    public TheoryTestMap() {
    }

    public TheoryTestMap(Long id, Long theoryTestId, Long studentId, Long subjectId, Long trainProgramId, Date createdAt, Date updatedAt) {
        this.id = id;
        this.theoryTestId = theoryTestId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.trainProgramId = trainProgramId;
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

    public Long getTheoryTestId() {
        return theoryTestId;
    }

    public void setTheoryTestId(Long theoryTestId) {
        this.theoryTestId = theoryTestId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getTrainProgramId() {
        return trainProgramId;
    }

    public void setTrainProgramId(Long trainProgramId) {
        this.trainProgramId = trainProgramId;
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
        return "TheoryTestMap{" +
                "id=" + id +
                ", theoryTestId=" + theoryTestId +
                ", studentId=" + studentId +
                ", subjectId=" + subjectId +
                ", trainProgramId=" + trainProgramId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}