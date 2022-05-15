package com.example.toeic_adventure.model;

public class SkillTest {
    public String Name;
    public String Desc;
    public String Type;
    public Integer NumberOfTest;
    public Integer Thumbnail;

    public SkillTest(String name, String desc, String type, Integer numberOfTest, Integer thumbnail) {
        Name = name;
        Desc = desc;
        Type = type;
        NumberOfTest = numberOfTest;
        Thumbnail = thumbnail;
    }
}