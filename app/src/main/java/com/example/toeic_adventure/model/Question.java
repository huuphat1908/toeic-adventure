package com.example.toeic_adventure.model;

import org.json.JSONArray;

public class Question {
    public String text;
    public JSONArray image;
    public JSONArray sound;
    public JSONArray choices;

    public Question(String text, JSONArray image, JSONArray sound, JSONArray choices) {
        this.text = text;
        this.image = image;
        this.sound = sound;
        this.choices = choices;
    }
}
