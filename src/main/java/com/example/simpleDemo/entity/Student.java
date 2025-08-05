package com.example.simpleDemo.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Student {
    private Integer id;
    private String name;
    private String sex;
    private String className;
    private BigDecimal height;
    private BigDecimal weight;
    private Date birthday;
    private String nativePlace;
    private String nationality;
    private Date createdAt;
    private Date updatedAt;

    // Constructors
    public Student() {
    }

    public Student(Integer id, String name, String sex, String className, BigDecimal height, BigDecimal weight,
            Date birthday, String nativePlace, String nationality, Date createdAt, Date updatedAt) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.className = className;
        this.height = height;
        this.weight = weight;
        this.birthday = birthday;
        this.nativePlace = nativePlace;
        this.nationality = nationality;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and Setters
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
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
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", className='" + className + '\'' +
                ", height=" + height +
                ", weight=" + weight +
                ", birthday=" + birthday +
                ", nativePlace='" + nativePlace + '\'' +
                ", nationality='" + nationality + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}