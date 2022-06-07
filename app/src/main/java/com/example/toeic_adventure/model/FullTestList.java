package com.example.toeic_adventure.model;

public class FullTestList {
    public int score;
    public String id;
    public int listeningScore;
    public int readingScore;

    public FullTestList(int score, String id,  int listeningScore, int readingScore) {
        this.score = score;
        this.id = id;
        this.listeningScore = listeningScore;
        this.readingScore = readingScore;
    }
}
