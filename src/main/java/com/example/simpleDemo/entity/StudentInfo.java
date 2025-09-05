package com.example.simpleDemo.entity;

public class StudentInfo extends Student {
    private String trainProgramName; // 训练项目名称
    private Long trainProgramId; // 训练项目id
    private String className; // 班级名称
    private Long classId; // 班级id
    private Integer trainTime; // 训练时长
    private Integer trainNum; // 训练次数
    private String subjectName; // 科目名称
    private Long subjectId; // 科目id

    // getters and setters
    public String getTrainProgramName() {
        return trainProgramName;
    }

    public void setTrainProgramName(String trainProgramName) {
        this.trainProgramName = trainProgramName;
    }

    public Long getTrainProgramId() {
        return trainProgramId;
    }

    public void setTrainProgramId(Long trainProgramId) {
        this.trainProgramId = trainProgramId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getTrainTime() {
        return trainTime;
    }

    public void setTrainTime(Integer trainTime) {
        this.trainTime = trainTime;
    }

    public Integer getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(Integer trainNum) {
        this.trainNum = trainNum;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "StudentInfo{" +
                "trainProgramName='" + trainProgramName + '\'' +
                ", trainProgramId=" + trainProgramId +
                ", className='" + className + '\'' +
                ", trainTime=" + trainTime +
                ", trainNum=" + trainNum +
                ", subjectName='" + subjectName + '\'' +
                ", subjectId=" + subjectId +
                '}';
    }
}