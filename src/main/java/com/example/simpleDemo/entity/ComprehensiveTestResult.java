package com.example.simpleDemo.entity;

import java.math.BigDecimal;
import java.util.Date;

public class ComprehensiveTestResult {
    private Long id;
    private Date testTime;
    private BigDecimal organizationScore;
    private BigDecimal selfDriveScore;
    private BigDecimal logicScore;
    private BigDecimal theoryScore;
    private BigDecimal practiceScore;
    private BigDecimal groupScore;
    private Long subjectId;
    private Long classId;
    private Long studentId;
    private Date createdAt;
    private Date updatedAt;
    private String studentName;

    // 新增：班级名称和科目名称字段
    private String className;
    private String subjectName;

    // 新增：className的getter和setter方法
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    // 新增：subjectName的getter和setter方法
    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
    }

    public BigDecimal getOrganizationScore() {
        return organizationScore;
    }

    public void setOrganizationScore(BigDecimal organizationScore) {
        this.organizationScore = organizationScore;
    }

    public BigDecimal getSelfDriveScore() {
        return selfDriveScore;
    }

    public void setSelfDriveScore(BigDecimal selfDriveScore) {
        this.selfDriveScore = selfDriveScore;
    }

    public BigDecimal getTheoryScore() {
        return theoryScore;
    }

    public void setTheoryScore(BigDecimal theoryScore) {
        this.theoryScore = theoryScore;
    }

    public BigDecimal getPracticeScore() {
        return practiceScore;
    }

    public void setPracticeScore(BigDecimal practiceScore) {
        this.practiceScore = practiceScore;
    }

    public BigDecimal getGroupScore() {
        return groupScore;
    }

    public void setGroupScore(BigDecimal groupScore) {
        this.groupScore = groupScore;
    }

    public BigDecimal getLogicScore() {
        return logicScore;
    }

    public void setLogicScore(BigDecimal logicScore) {
        this.logicScore = logicScore;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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
        return "ComprehensiveTestResult{" +
                "id=" + id +
                ", testTime=" + testTime +
                ", organizationScore=" + organizationScore +
                ", selfDriveScore=" + selfDriveScore +
                ", logicScore=" + logicScore +
                ", theoryScore=" + theoryScore +
                ", practiceScore=" + practiceScore +
                ", groupScore=" + groupScore +
                ", subjectId=" + subjectId +
                ", classId=" + classId +
                ", studentId=" + studentId +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", studentName='" + studentName + '\'' +
                ", className='" + className + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }

}