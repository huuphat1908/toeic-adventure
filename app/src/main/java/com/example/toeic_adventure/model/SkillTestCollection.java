package com.example.toeic_adventure.model;

public class SkillTestCollection {
    public String Name;
    public String Desc;
    public String Type;
    public Integer NumberOfTest;
    public Integer Thumbnail;
    public String Part;

    public SkillTestCollection(String name, String desc, String type, Integer numberOfTest, Integer thumbnail, String part) {
        Name = name;
        Desc = desc;
        Type = type;
        NumberOfTest = numberOfTest;
        Thumbnail = thumbnail;
        Part = part;
    }
}