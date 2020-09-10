/*
 * Copyright (C) 2020 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocoderResult {

    @JsonProperty("latitude")
    private String _latitude;
    @JsonProperty("longitude")
    private String _longitude;
    @JsonProperty("matchScore")
    private String _matchScore;
    @JsonProperty("geocodeQualityType")
    private String _geocodeQualityType;
    @JsonProperty("featureMatchingGeographyType")
    private String _featureMatchingGeographyType;
    @JsonProperty("matchType")
    private String _matchType;
    @JsonProperty("matchedLocationType")
    private String _matchedLocationType;
    @JsonProperty("featureMatchingResultType")
    private String _featureMatchingResultType;
    @JsonProperty("queryStatusCodes")
    private String _queryStatusCodes;
    @JsonProperty("tieHandlingStrategyType")
    private String _tieHandlingStrategyType;
    @JsonProperty("featureMatchingSelectionMethod")
    private String _featureMatchingSelectionMethod;

    @JsonProperty("naaccr")
    private NaaccrResult _naaccr;

    @JsonProperty("matchedAddress")
    private Address _matchedAddress;

    @JsonProperty("censusRecords")
    private Set<Census> _censusRecords;

    public String getLatitude() {
        return _latitude;
    }

    public void setLatitude(String latitude) {
        _latitude = latitude;
    }

    public String getLongitude() {
        return _longitude;
    }

    public void setLongitude(String longitude) {
        _longitude = longitude;
    }

    public String getMatchScore() {
        return _matchScore;
    }

    public void setMatchScore(String matchScore) {
        _matchScore = matchScore;
    }

    public String getGeocodeQualityType() {
        return _geocodeQualityType;
    }

    public void setGeocodeQualityType(String geocodeQualityType) {
        _geocodeQualityType = geocodeQualityType;
    }

    public String getFeatureMatchingGeographyType() {
        return _featureMatchingGeographyType;
    }

    public void setFeatureMatchingGeographyType(String featureMatchingGeographyType) {
        _featureMatchingGeographyType = featureMatchingGeographyType;
    }

    public String getMatchType() {
        return _matchType;
    }

    public void setMatchType(String matchType) {
        _matchType = matchType;
    }

    public String getMatchedLocationType() {
        return _matchedLocationType;
    }

    public void setMatchedLocationType(String matchedLocationType) {
        _matchedLocationType = matchedLocationType;
    }

    public String getFeatureMatchingResultType() {
        return _featureMatchingResultType;
    }

    public void setFeatureMatchingResultType(String featureMatchingResultType) {
        _featureMatchingResultType = featureMatchingResultType;
    }

    public String getQueryStatusCodes() {
        return _queryStatusCodes;
    }

    public void setQueryStatusCodes(String queryStatusCodes) {
        _queryStatusCodes = queryStatusCodes;
    }

    public String getTieHandlingStrategyType() {
        return _tieHandlingStrategyType;
    }

    public void setTieHandlingStrategyType(String tieHandlingStrategyType) {
        _tieHandlingStrategyType = tieHandlingStrategyType;
    }

    public String getFeatureMatchingSelectionMethod() {
        return _featureMatchingSelectionMethod;
    }

    public void setFeatureMatchingSelectionMethod(String featureMatchingSelectionMethod) {
        _featureMatchingSelectionMethod = featureMatchingSelectionMethod;
    }

    public NaaccrResult getNaaccr() {
        return _naaccr;
    }

    public void setNaaccr(NaaccrResult naaccr) {
        _naaccr = naaccr;
    }

    public Address getMatchedAddress() {
        return _matchedAddress;
    }

    public void setMatchedAddress(Address matchedAddress) {
        _matchedAddress = matchedAddress;
    }

    public Set<Census> getCensusRecords() {
        return _censusRecords;
    }

    public void setCensusRecords(Set<Census> censusRecords) {
        _censusRecords = censusRecords;
    }
}
