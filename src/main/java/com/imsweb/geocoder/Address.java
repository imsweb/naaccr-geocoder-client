/*
 * Copyright (C) 2017 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {

    @JsonProperty("nonParsedStreetAddress")
    private String _nonParsedStreetAddress;
    @JsonProperty("number")
    private String _number;
    @JsonProperty("numberFractional")
    private String _numberFractional;
    @JsonProperty("preDirectional")
    private String _preDirectional;
    @JsonProperty("preQualifier")
    private String _preQualifier;
    @JsonProperty("preType")
    private String _preType;
    @JsonProperty("preArticle")
    private String _preArticle;
    @JsonProperty("name")
    private String _name;
    @JsonProperty("postArticle")
    private String _postArticle;
    @JsonProperty("postQualifier")
    private String _postQualifier;
    @JsonProperty("suffix")
    private String _suffix;
    @JsonProperty("postDirectional")
    private String _postDirectional;
    @JsonProperty("suiteType")
    private String _suiteType;
    @JsonProperty("suiteNumber")
    private String _suiteNumber;
    @JsonProperty("poBoxType")
    private String _poBoxType;
    @JsonProperty("poBoxNumber")
    private String _poBoxNumber;
    @JsonProperty("city")
    private String _city;
    @JsonProperty("consolidatedCity")
    private String _consolidatedCity;
    @JsonProperty("minorCivilDivision")
    private String _minorCivilDivision;
    @JsonProperty("countySubRegion")
    private String _countySubRegion;
    @JsonProperty("county")
    private String _county;
    @JsonProperty("state")
    private String _state;
    @JsonProperty("country")
    private String _country;
    @JsonProperty("zip")
    private String _zip;
    @JsonProperty("zipPlus1")
    private String _zipPlus1;
    @JsonProperty("zipPlus2")
    private String _zipPlus2;
    @JsonProperty("zipPlus3")
    private String _zipPlus3;
    @JsonProperty("zipPlus4")
    private String _zipPlus4;
    @JsonProperty("zipPlus5")
    private String _zipPlus5;
    @JsonProperty("addressLocationType")
    private String _addressLocationType;
    @JsonProperty("addressFormatType")
    private String _addressFormatType;

    public String getNonParsedStreetAddress() {
        return _nonParsedStreetAddress;
    }

    public void setNonParsedStreetAddress(String nonParsedStreetAddress) {
        _nonParsedStreetAddress = nonParsedStreetAddress;
    }

    public String getNumber() {
        return _number;
    }

    public void setNumber(String number) {
        _number = number;
    }

    public String getNumberFractional() {
        return _numberFractional;
    }

    public void setNumberFractional(String numberFractional) {
        _numberFractional = numberFractional;
    }

    public String getPreDirectional() {
        return _preDirectional;
    }

    public void setPreDirectional(String preDirectional) {
        _preDirectional = preDirectional;
    }

    public String getPreQualifier() {
        return _preQualifier;
    }

    public void setPreQualifier(String preQualifier) {
        _preQualifier = preQualifier;
    }

    public String getPreType() {
        return _preType;
    }

    public void setPreType(String preType) {
        _preType = preType;
    }

    public String getPreArticle() {
        return _preArticle;
    }

    public void setPreArticle(String preArticle) {
        _preArticle = preArticle;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public String getPostArticle() {
        return _postArticle;
    }

    public void setPostArticle(String postArticle) {
        _postArticle = postArticle;
    }

    public String getPostQualifier() {
        return _postQualifier;
    }

    public void setPostQualifier(String postQualifier) {
        _postQualifier = postQualifier;
    }

    public String getSuffix() {
        return _suffix;
    }

    public void setSuffix(String suffix) {
        _suffix = suffix;
    }

    public String getPostDirectional() {
        return _postDirectional;
    }

    public void setPostDirectional(String postDirectional) {
        _postDirectional = postDirectional;
    }

    public String getSuiteType() {
        return _suiteType;
    }

    public void setSuiteType(String suiteType) {
        _suiteType = suiteType;
    }

    public String getSuiteNumber() {
        return _suiteNumber;
    }

    public void setSuiteNumber(String suiteNumber) {
        _suiteNumber = suiteNumber;
    }

    public String getPoBoxType() {
        return _poBoxType;
    }

    public void setPoBoxType(String poBoxType) {
        _poBoxType = poBoxType;
    }

    public String getPoBoxNumber() {
        return _poBoxNumber;
    }

    public void setPoBoxNumber(String poBoxNumber) {
        _poBoxNumber = poBoxNumber;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(String city) {
        _city = city;
    }

    public String getConsolidatedCity() {
        return _consolidatedCity;
    }

    public void setConsolidatedCity(String consolidatedCity) {
        _consolidatedCity = consolidatedCity;
    }

    public String getMinorCivilDivision() {
        return _minorCivilDivision;
    }

    public void setMinorCivilDivision(String minorCivilDivision) {
        _minorCivilDivision = minorCivilDivision;
    }

    public String getCountySubRegion() {
        return _countySubRegion;
    }

    public void setCountySubRegion(String countySubRegion) {
        _countySubRegion = countySubRegion;
    }

    public String getCounty() {
        return _county;
    }

    public void setCounty(String county) {
        _county = county;
    }

    public String getState() {
        return _state;
    }

    public void setState(String state) {
        _state = state;
    }

    public String getCountry() {
        return _country;
    }

    public void setCountry(String country) {
        _country = country;
    }

    public String getZip() {
        return _zip;
    }

    public void setZip(String zip) {
        _zip = zip;
    }

    public String getZipPlus1() {
        return _zipPlus1;
    }

    public void setZipPlus1(String zipPlus1) {
        _zipPlus1 = zipPlus1;
    }

    public String getZipPlus2() {
        return _zipPlus2;
    }

    public void setZipPlus2(String zipPlus2) {
        _zipPlus2 = zipPlus2;
    }

    public String getZipPlus3() {
        return _zipPlus3;
    }

    public void setZipPlus3(String zipPlus3) {
        _zipPlus3 = zipPlus3;
    }

    public String getZipPlus4() {
        return _zipPlus4;
    }

    public void setZipPlus4(String zipPlus4) {
        _zipPlus4 = zipPlus4;
    }

    public String getZipPlus5() {
        return _zipPlus5;
    }

    public void setZipPlus5(String zipPlus5) {
        _zipPlus5 = zipPlus5;
    }

    public String getAddressLocationType() {
        return _addressLocationType;
    }

    public void setAddressLocationType(String addressLocationType) {
        _addressLocationType = addressLocationType;
    }

    public String getAddressFormatType() {
        return _addressFormatType;
    }

    public void setAddressFormatType(String addressFormatType) {
        _addressFormatType = addressFormatType;
    }
}
