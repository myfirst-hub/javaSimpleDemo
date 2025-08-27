package com.example.simpleDemo.entity;

import java.util.Date;

public class ClassTeacher {
    private Long id;
    private Long classId;
    private Long teacherId;
    private Date createTime;
    private Date updateTime;

    // Constructors
    public ClassTeacher() {
    }

    public ClassTeacher(Long id, Long classId, Long teacherId, Date createTime, Date updateTime) {
        this.id = id;
        this.classId = classId;
        this.teacherId = teacherId;
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

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
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
        return "ClassTeacher{" +
                "id=" + id +
                ", classId=" + classId +
                ", teacherId=" + teacherId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}