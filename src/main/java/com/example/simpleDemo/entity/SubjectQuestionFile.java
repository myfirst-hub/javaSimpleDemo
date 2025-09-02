package com.example.simpleDemo.entity;

import java.util.Date;

public class SubjectQuestionFile {
    private Long id;
    private Long subjectId;
    private Long questionId;
    private String questionName;
    private Integer uploadStatus;
    private String reservedField1;
    private String reservedField2;
    private Integer reservedField3;
    private Date createdAt;
    private Date updatedAt;

    // 构造函数
    public SubjectQuestionFile() {
    }

    public SubjectQuestionFile(Long subjectId, Long questionId, String questionName, Integer uploadStatus) {
        this.subjectId = subjectId;
        this.questionId = questionId;
        this.questionName = questionName;
        this.uploadStatus = uploadStatus;
    }

    // Getter和Setter方法
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

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public Integer getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(Integer uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public String getReservedField1() {
        return reservedField1;
    }

    public void setReservedField1(String reservedField1) {
        this.reservedField1 = reservedField1;
    }

    public String getReservedField2() {
        return reservedField2;
    }

    public void setReservedField2(String reservedField2) {
        this.reservedField2 = reservedField2;
    }

    public Integer getReservedField3() {
        return reservedField3;
    }

    public void setReservedField3(Integer reservedField3) {
        this.reservedField3 = reservedField3;
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
        return "SubjectQuestionFile{" +
                "id=" + id +
                ", subjectId=" + subjectId +
                ", questionId=" + questionId +
                ", questionName='" + questionName + '\'' +
                ", uploadStatus=" + uploadStatus +
                ", reservedField1='" + reservedField1 + '\'' +
                ", reservedField2='" + reservedField2 + '\'' +
                ", reservedField3=" + reservedField3 +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}