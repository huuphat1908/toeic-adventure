package com.example.toeic_adventure.model;

public class SkillTestList {
    public int score;
    public String id;

    public SkillTestList(int score, String id) {
        this.score = score;
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
