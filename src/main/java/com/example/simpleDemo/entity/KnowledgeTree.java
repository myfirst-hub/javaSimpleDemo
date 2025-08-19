package com.example.simpleDemo.entity;

import java.util.Date;

public class KnowledgeTree {
    private Long id;
    private String name;
    private Long parentId;
    private Integer masteryLevel;
    private String knowledgeDescribe;
    private Boolean leaf;
    private Date createdAt;
    private Date updatedAt;

    // Constructors
    public KnowledgeTree() {
    }

    public KnowledgeTree(Long id, String name, Long parentId, Integer masteryLevel,
            String knowledgeDescribe, Boolean leaf, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.masteryLevel = masteryLevel;
        this.knowledgeDescribe = knowledgeDescribe;
        this.leaf = leaf;
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
        return leaf;
    }

    public void setLeaf(Boolean leaf) {
        this.leaf = leaf;
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
        return "KnowledgeTree{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", masteryLevel=" + masteryLevel +
                ", knowledgeDescribe='" + knowledgeDescribe + '\'' +
                ", leaf=" + leaf +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}