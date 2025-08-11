package com.example.simpleDemo.entity;

import java.util.Date;

public class TrainProgramKnowledge {
    private Integer id;
    private Integer trainId;
    private Integer knowledgeId;
    private Date createdAt;

    // Constructors
    public TrainProgramKnowledge() {
    }

    public TrainProgramKnowledge(Integer trainId, Integer knowledgeId, Date createdAt) {
        this.trainId = trainId;
        this.knowledgeId = knowledgeId;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public Integer getKnowledgeId() {
        return knowledgeId;
    }

    public void setKnowledgeId(Integer knowledgeId) {
        this.knowledgeId = knowledgeId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "TrainProgramKnowledge{" +
                "id=" + id +
                ", trainId=" + trainId +
                ", knowledgeId=" + knowledgeId +
                ", createdAt=" + createdAt +
                '}';
    }
}