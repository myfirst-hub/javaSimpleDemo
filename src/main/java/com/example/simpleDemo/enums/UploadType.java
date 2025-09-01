package com.example.simpleDemo.enums;

public enum UploadType {
    SUBJECT_OUTLINE("subjectOutline"),
    SUBJECT_QUESTION("subjectQuestion");

    private final String value;

    UploadType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}