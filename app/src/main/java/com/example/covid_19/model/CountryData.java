package com.example.covid_19.model;

import java.io.Serializable;

public class CountryData implements Serializable {
    String totalCases, totalActiveCases, totalDeaths, totalRecovered, flagUrl, countryName, todayCases, todayDeaths, todayRecovered, Critical, totalTests;

    public CountryData() {
    }

    public CountryData(String totalCases, String totalActiveCases, String totalDeaths, String totalRecovered, String flagUrl, String countryName, String todayCases, String todayDeaths, String todayRecovered, String critical, String totalTests) {
        this.totalCases = totalCases;
        this.totalActiveCases = totalActiveCases;
        this.totalDeaths = totalDeaths;
        this.totalRecovered = totalRecovered;
        this.flagUrl = flagUrl;
        this.countryName = countryName;
        this.todayCases = todayCases;
        this.todayDeaths = todayDeaths;
        this.todayRecovered = todayRecovered;
        Critical = critical;
        this.totalTests = totalTests;
    }


    public String getTotalCases() {
        return totalCases;
    }

    public String getTotalActiveCases() {
        return totalActiveCases;
    }

    public String getTotalDeaths() {
        return totalDeaths;
    }

    public String getTotalRecovered() {
        return totalRecovered;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getTodayCases() {
        return todayCases;
    }

    public String getTodayDeaths() {
        return todayDeaths;
    }

    public String getTodayRecovered() {
        return todayRecovered;
    }

    public String getCritical() {
        return Critical;
    }

    public String getTotalTests() {
        return totalTests;
    }
}
