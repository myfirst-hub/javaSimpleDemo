package com.example.simpleDemo.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Student {
    private Long id;
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

    // birthday的字符串格式，用于接口返回
    private String birthdayStr;

    // Constructors
    public Student() {
    }

    public Student(Long id, String name, String sex, String className, BigDecimal height, BigDecimal weight,
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
        // 同时更新birthdayStr字段
        if (birthday != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.birthdayStr = sdf.format(birthday);
        }
    }

    public String getBirthdayStr() {
        // 如果birthdayStr为空但birthday不为空，则生成格式化字符串
        if (birthdayStr == null && birthday != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            birthdayStr = sdf.format(birthday);
        }
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
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
                ", birthdayStr='" + birthdayStr + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", nationality='" + nationality + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}