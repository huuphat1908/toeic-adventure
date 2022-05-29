package com.example.toeic_adventure.model;

public class SkillTestList {
    public int score;
    public int totalSentences;
    public String id;

    public SkillTestList(int score, int totalSentences, String id) {
        this.score = score;
        this.totalSentences = totalSentences;
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
