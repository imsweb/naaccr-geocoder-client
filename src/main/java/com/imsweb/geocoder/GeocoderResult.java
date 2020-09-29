/*
 * Copyright (C) 2020 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.node.ArrayNode;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocoderResult {

    @JsonProperty("latitude")
    private String _latitude;
    @JsonProperty("longitude")
    private String _longitude;
    @JsonProperty("matchScore")
    private Double _matchScore;
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

    @JsonProperty("referenceFeature")
    private FeatureResult _feature;

    @JsonProperty("censusRecords")
    @JsonDeserialize(using = CensusMapDeserializer.class)
    private Map<Integer, Census> _censusRecords;

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

    public Double getMatchScore() {
        return _matchScore;
    }

    public void setMatchScore(Double matchScore) {
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

    public FeatureResult getFeature() {
        return _feature;
    }

    public void setFeature(FeatureResult feature) {
        _feature = feature;
    }

    public Map<Integer, Census> getCensusRecords() {
        return _censusRecords;
    }

    public void setCensusRecords(Map<Integer, Census> censusRecords) {
        _censusRecords = censusRecords;
    }
}

class CensusMapDeserializer extends JsonDeserializer<Map<Integer, Census>> {

    @Override
    public Map<Integer, Census> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        Map<Integer, Census> result = new HashMap<>();

        ObjectCodec codec = jp.getCodec();
        TreeNode node = codec.readTree(jp);

        if (node.isArray()) {
            Iterator<JsonNode> iter = ((ArrayNode)node).elements();
            while (iter.hasNext()) {
                JsonNode n = iter.next();
                Census c = codec.treeToValue(n, Census.class);
                if (c != null)
                    result.put(c.getYear(), c);

            }
        }
        return result;
    }
}
