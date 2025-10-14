package com.example.simpleDemo.dto;

public class TheoryTrainProgramListDTO {
    private Integer pageNum;
    private Integer pageSize;
    private String name;
    private String semester;
    private Long[] subjectIds;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

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

    public Long[] getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(Long[] subjectIds) {
        this.subjectIds = subjectIds;
    }
}
