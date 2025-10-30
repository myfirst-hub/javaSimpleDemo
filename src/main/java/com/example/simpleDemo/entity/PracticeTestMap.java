package com.example.simpleDemo.entity;

import java.util.Date;

public class PracticeTestMap {
    private Long id;
    private Long practiceTestId;
    private Long studentId;
    private Long subjectId;
    private Long practiceTrainProgramId;
    private Date createdAt;
    private Date updatedAt;

    // Constructors
    public PracticeTestMap() {
    }

    public PracticeTestMap(Long id, Long practiceTestId, Long studentId, Long subjectId, Long practiceTrainProgramId,
            Date createdAt, Date updatedAt) {
        this.id = id;
        this.practiceTestId = practiceTestId;
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.practiceTrainProgramId = practiceTrainProgramId;
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

    public Long getPracticeTestId() {
        return practiceTestId;
    }

    public void setPracticeTestId(Long practiceTestId) {
        this.practiceTestId = practiceTestId;
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

    public Long getPracticeTrainProgramId() {
        return practiceTrainProgramId;
    }

    public void setPracticeTrainProgramId(Long practiceTrainProgramId) {
        this.practiceTrainProgramId = practiceTrainProgramId;
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
        return "PracticeTestMap{" +
                "id=" + id +
                ", practiceTestId=" + practiceTestId +
                ", studentId=" + studentId +
                ", subjectId=" + subjectId +
                ", practiceTrainProgramId=" + practiceTrainProgramId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
