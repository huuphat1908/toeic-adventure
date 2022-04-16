package com.example.toeic_adventure.model;

import com.example.toeic_adventure.TestType;

public class SkillTest {
    public String Name;
    public String Desc;
    public TestType Type;
    public Integer NumberOfTest;
    public Integer Thumbnail;

    public SkillTest(String name, String desc, TestType type, Integer numberOfTest, Integer thumbnail) {
        Name = name;
        Desc = desc;
        Type = type;
        NumberOfTest = numberOfTest;
        Thumbnail = thumbnail;
    }
}