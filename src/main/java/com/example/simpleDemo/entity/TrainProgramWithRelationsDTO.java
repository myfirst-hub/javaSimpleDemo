package com.example.simpleDemo.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TrainProgramWithRelationsDTO extends TrainProgram {
    private List<Long> studentIds;
    private List<Knowledge> knowledges;

    public TrainProgramWithRelationsDTO() {
        super();
    }

    public TrainProgramWithRelationsDTO(Integer id, String name, String semester, String trainDescribe,
            BigDecimal trainTime, Date createdAt, Date updatedAt) {
        super(id, name, semester, trainDescribe, trainTime, createdAt, updatedAt);
    }

    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
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
        return "TrainProgramWithRelationsDTO{" +
                "studentIds=" + studentIds +
                ", knowledges=" + knowledges +
                "} " + super.toString();
    }
}