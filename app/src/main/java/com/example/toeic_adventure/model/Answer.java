package com.example.toeic_adventure.model;

public class Answer {
    public String text;
    public String explanation;
    public String userAnswer;

    public Answer(String text, String explanation) {
        this.text = text;
        this.explanation = explanation;
        this.userAnswer = "";
    }

    public Answer(String text, String explanation, String userAnswer) {
        this.text = text;
        this.explanation = explanation;
        this.userAnswer = userAnswer;
    }
}
