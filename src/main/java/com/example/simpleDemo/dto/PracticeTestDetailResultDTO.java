package com.example.simpleDemo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PracticeTestDetailResultDTO {
    private Long id;
    private LocalDateTime testTime;
    private String testName;
    private String attributes; // 或者使用具体的JSON对象类型
    private BigDecimal totalScore;

    // students 字段
    private String studentName;

    // classes 字段
    private String className;
    private String teacherName;
    private String subjectName;

    // practice_train_program 字段
    private String practiceTrainProgramName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTestTime() {
        return testTime;
    }

    public void setTestTime(LocalDateTime testTime) {
        this.testTime = testTime;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getPracticeTrainProgramName() {
        return practiceTrainProgramName;
    }

    public void setPracticeTrainProgramName(String practiceTrainProgramName) {
        this.practiceTrainProgramName = practiceTrainProgramName;
    }

    @Override
    public String toString() {
        return "PracticeTestDetailResultDTO{" +
                "id=" + id +
                ", testTime=" + testTime +
                ", testName='" + testName + '\'' +
                ", attributes='" + attributes + '\'' +
                ", totalScore=" + totalScore +
                ", studentName='" + studentName + '\'' +
                ", className='" + className + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", practiceTrainProgramName='" + practiceTrainProgramName + '\'' +
                '}';
    }
}
