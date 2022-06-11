package com.example.toeic_adventure.model;

public class FullTestBody {
    public CorrectSentencesFullTest correctSentences;
    public String test;

    public FullTestBody(CorrectSentencesFullTest correctSentences, String test) {
        this.correctSentences = correctSentences;
        this.test = test;
    }
}
