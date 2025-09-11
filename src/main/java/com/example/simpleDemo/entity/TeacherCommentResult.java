package com.example.simpleDemo.entity;

import java.time.LocalDateTime;
import java.util.Date;

public class TeacherCommentResult {
    private Long id;
    private Long subjectId;
    private Long studentId;
    private Long trainProgramId;
    private String teacherCommentContent;
    private Date teacherCommentTime;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public TeacherCommentResult() {
    }

    public TeacherCommentResult(Long id, Long subjectId, Long studentId, Long trainProgramId,
            String teacherCommentContent, Date teacherCommentTime, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.trainProgramId = trainProgramId;
        this.teacherCommentContent = teacherCommentContent;
        this.teacherCommentTime = teacherCommentTime;
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getTrainProgramId() {
        return trainProgramId;
    }

    public void setTrainProgramId(Long trainProgramId) {
        this.trainProgramId = trainProgramId;
    }

    public String getTeacherCommentContent() {
        return teacherCommentContent;
    }

    public void setTeacherCommentContent(String teacherCommentContent) {
        this.teacherCommentContent = teacherCommentContent;
    }

    public Date getTeacherCommentTime() {
        return teacherCommentTime;
    }

    public void setTeacherCommentTime(Date teacherCommentTime) {
        this.teacherCommentTime = teacherCommentTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "TeacherCommentResult{" +
                "id=" + id +
                ", subjectId=" + subjectId +
                ", studentId=" + studentId +
                ", trainProgramId=" + trainProgramId +
                ", teacherCommentContent='" + teacherCommentContent + '\'' +
                ", teacherCommentTime=" + teacherCommentTime +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}