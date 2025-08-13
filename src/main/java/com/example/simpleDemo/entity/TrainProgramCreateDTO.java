package com.example.simpleDemo.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TrainProgramCreateDTO {
    private String name;
    private String semester;
    private String trainDescribe;
    private BigDecimal trainTime;
    private List<Integer> studentIds;
    private List<Knowledge> knowledges;

    // Constructors
    public TrainProgramCreateDTO() {
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getTrainDescribe() {
        return trainDescribe;
    }

    public void setTrainDescribe(String trainDescribe) {
        this.trainDescribe = trainDescribe;
    }

    public BigDecimal getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(BigDecimal trainTime) {
        this.trainTime = trainTime;
    }

    public List<Integer> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Integer> studentIds) {
        this.studentIds = studentIds;
    }

    public List<Knowledge> getKnowledges() {
        return knowledges;
    }

    public void setKnowledges(List<Knowledge> knowledges) {
        this.knowledges = knowledges;
    }

    @Override
    public String toString() {
        return "TrainProgramCreateDTO{" +
                "name='" + name + '\'' +
                ", semester='" + semester + '\'' +
                ", trainDescribe='" + trainDescribe + '\'' +
                ", trainTime=" + trainTime +
                ", studentIds=" + studentIds +
                ", knowledges=" + knowledges +
                '}';
    }
}