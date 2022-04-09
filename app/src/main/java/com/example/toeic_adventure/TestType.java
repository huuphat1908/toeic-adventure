package com.example.toeic_adventure;

public enum TestType {
    LISTENING("Listening"),
    READING("Reading");

    private String stringValue;
    private TestType(String toString) {
        stringValue = toString;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
