package com.example.toeic_adventure.model;

public class VocabularyDetail {
    public Integer No;
    public String Name;
    public String Desc;
    public String Mean;
    public String Audio;

    public VocabularyDetail(Integer no, String name, String desc, String mean, String audio){
        No = no;
        Name = name;
        Desc = desc;
        Mean = mean;
        Audio = audio;
    }
}
