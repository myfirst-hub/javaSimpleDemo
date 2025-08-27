package com.example.simpleDemo.entity;

import java.util.Date;

public class ClassStudent {
    private Long id;
    private Long classId;
    private Long studentId;
    private Date createTime;
    private Date updateTime;

    // Constructors
    public ClassStudent() {
    }

    public ClassStudent(Long id, Long classId, Long studentId, Date createTime, Date updateTime) {
        this.id = id;
        this.classId = classId;
        this.studentId = studentId;
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
        return "ClassStudent{" +
                "id=" + id +
                ", classId=" + classId +
                ", studentId=" + studentId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}