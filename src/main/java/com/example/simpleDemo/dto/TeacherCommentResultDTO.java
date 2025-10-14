package com.example.simpleDemo.dto;

import java.time.LocalDateTime;

public class TeacherCommentResultDTO {
    private Long id;
    private String teacherCommentContent;
    private LocalDateTime teacherCommentTime;
    private String studentName;
    private String className;
    private String teacherName;
    private String subjectName;
    private String theoryTrainProgramName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherCommentContent() {
        return teacherCommentContent;
    }

    public void setTeacherCommentContent(String teacherCommentContent) {
        this.teacherCommentContent = teacherCommentContent;
    }

    public LocalDateTime getTeacherCommentTime() {
        return teacherCommentTime;
    }

    public void setTeacherCommentTime(LocalDateTime teacherCommentTime) {
        this.teacherCommentTime = teacherCommentTime;
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

    public String getTheoryTrainProgramName() {
        return theoryTrainProgramName;
    }

    public void setTheoryTrainProgramName(String theoryTrainProgramName) {
        this.theoryTrainProgramName = theoryTrainProgramName;
    }

    @Override
    public String toString() {
        return "TeacherCommentResultDTO{" +
                "id=" + id +
                ", teacherCommentContent='" + teacherCommentContent + '\'' +
                ", teacherCommentTime=" + teacherCommentTime +
                ", studentName='" + studentName + '\'' +
                ", className='" + className + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", theoryTrainProgramName='" + theoryTrainProgramName + '\'' +
                '}';
    }
}