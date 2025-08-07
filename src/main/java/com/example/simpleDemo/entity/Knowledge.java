package com.example.simpleDemo.entity;

import java.util.Date;

public class Knowledge {
    private Integer id;
    private String name;
    private Integer masteryLevel;
    private String knowledgeDescribe;
    private Date createdAt;
    private Date updatedAt;

    // 构造函数
    public Knowledge() {}

    public Knowledge(String name, Integer masteryLevel, String knowledgeDescribe) {
        this.name = name;
        this.masteryLevel = masteryLevel;
        this.knowledgeDescribe = knowledgeDescribe;
    }

    // Getter和Setter方法
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getMasteryLevel() {
        return masteryLevel;
    }

    public void setMasteryLevel(Integer masteryLevel) {
        this.masteryLevel = masteryLevel;
    }

    public String getKnowledgeDescribe() {
        return knowledgeDescribe;
    }

    public void setKnowledgeDescribe(String knowledgeDescribe) {
        this.knowledgeDescribe = knowledgeDescribe;
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
        return "Knowledge{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", masteryLevel=" + masteryLevel +
                ", knowledgeDescribe='" + knowledgeDescribe + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}