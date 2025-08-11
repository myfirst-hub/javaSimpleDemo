package com.example.simpleDemo.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TrainProgramWithRelationsDTO extends TrainProgram {
    private List<Student> students;
    private List<Knowledge> knowledges;

    public TrainProgramWithRelationsDTO() {
        super();
    }

    public TrainProgramWithRelationsDTO(Integer id, String name, String semester, String trainDescribe,
            BigDecimal trainTime, Date createdAt, Date updatedAt) {
        super(id, name, semester, trainDescribe, trainTime, createdAt, updatedAt);
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
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
                "students=" + students +
                ", knowledges=" + knowledges +
                "} " + super.toString();
    }
}