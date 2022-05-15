package com.example.toeic_adventure.model;

public class FullTest {

    private String fullTestName;

    // Image name (Without extension)
    private String flagName;

    public FullTest(String fullTestName, String flagName, int population) {
        this.fullTestName= fullTestName;
        this.flagName= flagName;
    }

    public String getFullTestName() {
        return fullTestName;
    }

    public void setFullTestName(String fullTestName) {
        this.fullTestName = fullTestName;
    }

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }

    @Override
    public String toString()  {
        return this.fullTestName;
    }
}
