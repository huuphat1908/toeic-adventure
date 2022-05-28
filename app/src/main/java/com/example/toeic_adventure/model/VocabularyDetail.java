package com.example.toeic_adventure.model;

public class VocabularyDetail {
    public Integer No = 0;
    public String Name = "";
    public String Desc = "";
    public String Mean = "";

    public VocabularyDetail() {

    }

    public VocabularyDetail(Integer no, String name, String desc, String mean){
        No = no;
        Name = name;
        Desc = desc;
        Mean = mean;
    }
}
