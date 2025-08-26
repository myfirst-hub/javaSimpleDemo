package com.example.simpleDemo.entity;

import java.util.Date;
import java.util.List;

public class KnowledgeTree {
    private Long id;
    private String name;
    private Long parentId;
    private Integer masteryLevel;
    private String knowledgeDescribe;
    private Boolean isLeaf;
    private Date createdAt;
    private Date updatedAt;
    // 添加level字段
    private Integer level;

    // 添加children字段用于存储子节点
    private List<KnowledgeTree> children;

    // Constructors
    public KnowledgeTree() {
    }

    public KnowledgeTree(Long id, String name, Long parentId, Integer masteryLevel,
            String knowledgeDescribe, Boolean isLeaf, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.masteryLevel = masteryLevel;
        this.knowledgeDescribe = knowledgeDescribe;
        this.isLeaf = isLeaf;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
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

    public Boolean getLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
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

    // 添加level的getter和setter
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    // 添加children的getter和setter
    public List<KnowledgeTree> getChildren() {
        return children;
    }

    public void setChildren(List<KnowledgeTree> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "KnowledgeTree{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", masteryLevel=" + masteryLevel +
                ", knowledgeDescribe='" + knowledgeDescribe + '\'' +
                ", isLeaf=" + isLeaf +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", level=" + level +
                ", children=" + children +
                '}';
    }
}