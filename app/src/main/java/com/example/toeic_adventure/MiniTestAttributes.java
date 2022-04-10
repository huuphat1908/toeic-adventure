package com.example.toeic_adventure;

public class MiniTestAttributes {
    private String nameMiniTest;

    // Image name (Without extension)
    private String stateMiniTest;

    public MiniTestAttributes(String nameMiniTest, String stateMiniTest, int population) {
        this.nameMiniTest= nameMiniTest;
        this.stateMiniTest= stateMiniTest;
    }

    public String getNameMiniTest() {
        return this.nameMiniTest;
    }

    public void setNameMiniTest(String nameMiniTest) {
        this.nameMiniTest = nameMiniTest;
    }

    public String getStateMiniTest() {
        return this.stateMiniTest;
    }

    public void setStateMiniTest(String stateMiniTest) {
        this.stateMiniTest = stateMiniTest;
    }

    @Override
    public String toString()  {
        return this.nameMiniTest;
    }
}
