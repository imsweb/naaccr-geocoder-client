/*
 * Copyright (C) 2020 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties("penaltyCodeDetails")
public class NaaccrResult {

    @JsonProperty("gisCoordinateQualityCode")
    private String _gisCoordinateQualityCode;
    @JsonProperty("gisCoordinateQualityType")
    private String _gisCoordinateQualityType;
    @JsonProperty("censusTractCertaintyCode")
    private String _censusTractCertaintyCode;
    @JsonProperty("censusTractCertaintyType")
    private String _censusTractCertaintyType;
    @JsonProperty("microMatchStatus")
    private String _microMatchStatus;
    @JsonProperty("penaltyCode")
    private String _penaltyCode;
    @JsonProperty("penaltyCodeSummary")
    private String _penaltyCodeSummary;

    public String getGisCoordinateQualityCode() {
        return _gisCoordinateQualityCode;
    }

    public void setGisCoordinateQualityCode(String gisCoordinateQualityCode) {
        _gisCoordinateQualityCode = gisCoordinateQualityCode;
    }

    public String getGisCoordinateQualityType() {
        return _gisCoordinateQualityType;
    }

    public void setGisCoordinateQualityType(String gisCoordinateQualityType) {
        _gisCoordinateQualityType = gisCoordinateQualityType;
    }

    public String getCensusTractCertaintyCode() {
        return _censusTractCertaintyCode;
    }

    public void setCensusTractCertaintyCode(String censusTractCertaintyCode) {
        _censusTractCertaintyCode = censusTractCertaintyCode;
    }

    public String getCensusTractCertaintyType() {
        return _censusTractCertaintyType;
    }

    public void setCensusTractCertaintyType(String censusTractCertaintyType) {
        _censusTractCertaintyType = censusTractCertaintyType;
    }

    public String getMicroMatchStatus() {
        return _microMatchStatus;
    }

    public void setMicroMatchStatus(String microMatchStatus) {
        _microMatchStatus = microMatchStatus;
    }

    public String getPenaltyCode() {
        return _penaltyCode;
    }

    public void setPenaltyCode(String penaltyCode) {
        _penaltyCode = penaltyCode;
    }

    public String getPenaltyCodeSummary() {
        return _penaltyCodeSummary;
    }

    public void setPenaltyCodeSummary(String penaltyCodeSummary) {
        _penaltyCodeSummary = penaltyCodeSummary;
    }
}
