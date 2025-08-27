package com.example.simpleDemo.entity;

import java.util.Date;

public class Classes {
    private Long id;
    private String name;
    private Long teacherId;
    private String teacherName;
    private Integer studentNum;
    private String subjectName;
    private Long subjectId;
    private String classDescribe;
    private Date createTime;
    private Date updateTime;

    // Constructors
    public Classes() {
    }

    public Classes(Long id, String name, Long teacherId, String teacherName, Integer studentNum, String subjectName, Long subjectId, String classDescribe, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.studentNum = studentNum;
        this.subjectName = subjectName;
        this.subjectId = subjectId;
        this.classDescribe = classDescribe;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getClassDescribe() {
        return classDescribe;
    }

    public void setClassDescribe(String classDescribe) {
        this.classDescribe = classDescribe;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Classes{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", studentNum=" + studentNum +
                ", subjectName='" + subjectName + '\'' +
                ", subjectId=" + subjectId +
                ", classDescribe='" + classDescribe + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}