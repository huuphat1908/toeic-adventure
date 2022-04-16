package com.example.toeic_adventure.model;

public class Country {

    private String countryName;

    // Image name (Without extension)
    private String flagName;

    public Country(String countryName, String flagName, int population) {
        this.countryName= countryName;
        this.flagName= flagName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getFlagName() {
        return flagName;
    }

    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }

    @Override
    public String toString()  {
        return this.countryName;
    }
}
