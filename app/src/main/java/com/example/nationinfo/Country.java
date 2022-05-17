package com.example.nationinfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Country {
    private String continent;
    private String capital;
    private String languages;
    private String geoNameId;
    private double south;
    private double north;
    private double east;
    private double west;
    private String isoAlpha3;
    private String fipsCode;
    private String population;
    private String isoNumeric;
    private String areaInSqKm;
    private String countryCode;
    private String countryName;
    private String postalCodeFormat;
    private String continentName;
    private String currencyCode;

    public Country () {
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public String getGeoNameId() {
        return geoNameId;
    }

    public void setGeoNameId(String geoNameId) {
        this.geoNameId = geoNameId;
    }

    public double getSouth() {
        return south;
    }

    public void setSouth(double south) {
        this.south = south;
    }

    public double getNorth() {
        return north;
    }

    public void setNorth(double north) {
        this.north = north;
    }

    public double getEast() {
        return east;
    }

    public void setEast(double east) {
        this.east = east;
    }

    public double getWest() {
        return west;
    }

    public void setWest(double west) {
        this.west = west;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public String getFipsCode() {
        return fipsCode;
    }

    public void setFipsCode(String fipsCode) {
        this.fipsCode = fipsCode;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getIsoNumeric() {
        return isoNumeric;
    }

    public void setIsoNumeric(String isoNumeric) {
        this.isoNumeric = isoNumeric;
    }

    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPostalCodeFormat() {
        return postalCodeFormat;
    }

    public void setPostalCodeFormat(String postalCodeFormat) {
        this.postalCodeFormat = postalCodeFormat;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public static ArrayList<Country> fromJson(JSONArray jsonArray){
        ArrayList<Country> countries = new ArrayList<Country>(jsonArray.length());
        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject countriesJson = null;
            try {
                countriesJson = jsonArray.getJSONObject(i);
            }
            catch (Exception e){
                e.printStackTrace();
                continue;
            }
            Country country = Country.fromJson(countriesJson);
            if(country != null){
                countries.add(country);
            }
        }
        return countries;
    }

    public static Country fromJson(JSONObject jsonObject) {
        Country country = new Country();
        try {

            country.setContinent(jsonObject.getString("continent"));
            country.setCapital(jsonObject.getString("capital"));
            country.setLanguages(jsonObject.getString("languages"));
            country.setGeoNameId(jsonObject.getString("geonameId"));
            country.setSouth(jsonObject.getInt("south"));
            country.setIsoAlpha3(jsonObject.getString("isoAlpha3"));
            country.setNorth(jsonObject.getDouble("north"));
            country.setFipsCode(jsonObject.getString("fipsCode"));
            country.setPopulation(jsonObject.getString("population"));
            country.setEast(jsonObject.getDouble("east"));
            country.setIsoNumeric(jsonObject.getString("isoNumeric"));
            country.setAreaInSqKm(jsonObject.getString("areaInSqKm"));
            country.setCountryCode(jsonObject.getString("countryCode"));
            country.setWest(jsonObject.getDouble("west"));
            country.setCountryName(jsonObject.getString("countryName"));
            country.setContinentName(jsonObject.getString("continentName"));
            country.setCurrencyCode(jsonObject.getString("currencyCode"));
            country.setPostalCodeFormat(jsonObject.getString("postalCodeFormat"));

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return country;
    }
}
