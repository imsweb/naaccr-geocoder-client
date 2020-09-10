/*
 * Copyright (C) 2020 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NaaccrResult {

    @JsonProperty("gisCoordinateQualityCode")
    private String gisCoordinateQualityCode;
    @JsonProperty("gisCoordinateQualityType")
    private String gisCoordinateQualityType;
    @JsonProperty("censusTractCertaintyCode")
    private String censusTractCertaintyCode;
    @JsonProperty("censusTractCertaintyType")
    private String censusTractCertaintyType;
    @JsonProperty("microMatchStatus")
    private String microMatchStatus;
    @JsonProperty("penaltyCode")
    private String penaltyCode;
    @JsonProperty("penaltyCodeSummary")
    private String penaltyCodeSummary;

    public String getGisCoordinateQualityCode() {
        return gisCoordinateQualityCode;
    }

    public void setGisCoordinateQualityCode(String gisCoordinateQualityCode) {
        this.gisCoordinateQualityCode = gisCoordinateQualityCode;
    }

    public String getGisCoordinateQualityType() {
        return gisCoordinateQualityType;
    }

    public void setGisCoordinateQualityType(String gisCoordinateQualityType) {
        this.gisCoordinateQualityType = gisCoordinateQualityType;
    }

    public String getCensusTractCertaintyCode() {
        return censusTractCertaintyCode;
    }

    public void setCensusTractCertaintyCode(String censusTractCertaintyCode) {
        this.censusTractCertaintyCode = censusTractCertaintyCode;
    }

    public String getCensusTractCertaintyType() {
        return censusTractCertaintyType;
    }

    public void setCensusTractCertaintyType(String censusTractCertaintyType) {
        this.censusTractCertaintyType = censusTractCertaintyType;
    }

    public String getMicroMatchStatus() {
        return microMatchStatus;
    }

    public void setMicroMatchStatus(String microMatchStatus) {
        this.microMatchStatus = microMatchStatus;
    }

    public String getPenaltyCode() {
        return penaltyCode;
    }

    public void setPenaltyCode(String penaltyCode) {
        this.penaltyCode = penaltyCode;
    }

    public String getPenaltyCodeSummary() {
        return penaltyCodeSummary;
    }

    public void setPenaltyCodeSummary(String penaltyCodeSummary) {
        this.penaltyCodeSummary = penaltyCodeSummary;
    }
}
