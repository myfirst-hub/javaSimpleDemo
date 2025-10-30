package com.example.simpleDemo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

public class PracticeTestResult {
    private Long id;
    private Date testTime;
    private String testName;
    private String attributes;
    private BigDecimal totalScore;
    private BigDecimal fullScore;
    private BigDecimal rateScore;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Getter and Setter methods
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

    public BigDecimal getRateScore() {
        return rateScore;
    }

    public void setRateScore(BigDecimal rateScore) {
        this.rateScore = rateScore;
    }

    public BigDecimal getFullScore() {
        return fullScore;
    }

    public void setFullScore(BigDecimal fullScore) {
        this.fullScore = fullScore;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "DynamicPracticeTestResult{" +
                "id=" + id +
                ", testTime=" + testTime +
                ", testName='" + testName + '\'' +
                ", attributes='" + attributes + '\'' +
                ", totalScore=" + totalScore + '\'' +
                ", fullScore=" + fullScore + '\'' +
                ", rateScore=" + rateScore +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
