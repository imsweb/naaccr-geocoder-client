/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;

import com.imsweb.geocoder.exception.BadRequestException;

public class GeocodeOutput {

    private String _transactionId;
    private String _apiVersion;
    private Integer _statusCode;
    private Double _latitude;
    private Double _longitude;
    private String _naaccrGisCoordinateQualityCode;
    private String _naaccrGisCoordinateQualityName;
    private Double _matchScore;
    private String _matchType;
    private String _featureMatchType;
    private Integer _featureMatchCount;
    private String _matchingGeographyType;
    private Double _regionSize;
    private String _regionSizeUnit;
    private String _matchedLocationType;
    private Double _timeTaken;
    private String _naaccrCensusTractCertaintyCode;
    private String _naaccrCensusTractCertaintyName;
    private Map<Integer, Census> _censusResults = new HashMap<>();

    public String getTransactionId() {
        return _transactionId;
    }

    void setTransactionId(String transactionId) {
        _transactionId = transactionId;
    }

    public String getApiVersion() {
        return _apiVersion;
    }

    void setApiVersion(String apiVersion) {
        _apiVersion = apiVersion;
    }

    public Integer getStatusCode() {
        return _statusCode;
    }

    void setStatusCode(Integer statusCode) {
        _statusCode = statusCode;
    }

    public Double getLatitude() {
        return _latitude;
    }

    void setLatitude(Double latitude) {
        _latitude = latitude;
    }

    public Double getLongitude() {
        return _longitude;
    }

    void setLongitude(Double longitude) {
        _longitude = longitude;
    }

    public String getNaaccrGisCoordinateQualityCode() {
        return _naaccrGisCoordinateQualityCode;
    }

    void setNaaccrGisCoordinateQualityCode(String naaccrGisCoordinateQualityCode) {
        _naaccrGisCoordinateQualityCode = naaccrGisCoordinateQualityCode;
    }

    public String getNaaccrGisCoordinateQualityName() {
        return _naaccrGisCoordinateQualityName;
    }

    void setNaaccrGisCoordinateQualityName(String naaccrGisCoordinateQualityName) {
        _naaccrGisCoordinateQualityName = naaccrGisCoordinateQualityName;
    }

    public Double getMatchScore() {
        return _matchScore;
    }

    void setMatchScore(Double matchScore) {
        _matchScore = matchScore;
    }

    public String getMatchType() {
        return _matchType;
    }

    void setMatchType(String matchType) {
        _matchType = matchType;
    }

    public String getFeatureMatchType() {
        return _featureMatchType;
    }

    void setFeatureMatchType(String featureMatchType) {
        _featureMatchType = featureMatchType;
    }

    public Integer getFeatureMatchCount() {
        return _featureMatchCount;
    }

    void setFeatureMatchCount(Integer featureMatchCount) {
        _featureMatchCount = featureMatchCount;
    }

    public String getMatchingGeographyType() {
        return _matchingGeographyType;
    }

    void setMatchingGeographyType(String matchingGeographyType) {
        _matchingGeographyType = matchingGeographyType;
    }

    public Double getRegionSize() {
        return _regionSize;
    }

    void setRegionSize(Double regionSize) {
        _regionSize = regionSize;
    }

    public String getRegionSizeUnit() {
        return _regionSizeUnit;
    }

    void setRegionSizeUnit(String regionSizeUnit) {
        _regionSizeUnit = regionSizeUnit;
    }

    public String getMatchedLocationType() {
        return _matchedLocationType;
    }

    void setMatchedLocationType(String matchedLocationType) {
        _matchedLocationType = matchedLocationType;
    }

    public Double getTimeTaken() {
        return _timeTaken;
    }

    void setTimeTaken(Double timeTaken) {
        _timeTaken = timeTaken;
    }

    public String getNaaccrCensusTractCertaintyCode() {
        return _naaccrCensusTractCertaintyCode;
    }

    void setNaaccrCensusTractCertaintyCode(String naaccrCensusTractCertaintyCode) {
        _naaccrCensusTractCertaintyCode = naaccrCensusTractCertaintyCode;
    }

    public String getNaaccrCensusTractCertaintyName() {
        return _naaccrCensusTractCertaintyName;
    }

    void setNaaccrCensusTractCertaintyName(String naaccrCensusTractCertaintyName) {
        _naaccrCensusTractCertaintyName = naaccrCensusTractCertaintyName;
    }

    public Map<Integer, Census> getCensusResults() {
        return _censusResults;
    }

    static List<GeocodeOutput> toResults(ResponseBody body) throws IOException {
        String resultString = body.string().trim();

        if (resultString.isEmpty())
            throw new IllegalStateException("Empty response returned from API");
        if (resultString.startsWith("invalid request - "))
            throw new BadRequestException("API indicated invalid request; could indicate API key issue");

        try (BufferedReader reader = new BufferedReader(new StringReader(resultString))) {
            return reader.lines()
                    .map(line -> {
                        GeocodeOutput result = new GeocodeOutput();

                        String[] parts = resultString.split("\t");

                        if (parts.length < 16)
                            throw new IllegalStateException("Unknown format returned from API");

                        result.setTransactionId(value(parts[0]));
                        result.setApiVersion(value(parts[1]));
                        result.setStatusCode(intValue(parts[2]));

                        result.setLatitude(doubleValue(parts[3]));
                        result.setLongitude(doubleValue(parts[4]));
                        result.setNaaccrGisCoordinateQualityCode(value(parts[5]));
                        result.setNaaccrGisCoordinateQualityName(value(parts[6]));
                        result.setMatchScore(doubleValue(value(parts[7])));
                        result.setMatchType(value(parts[8]));
                        result.setFeatureMatchType(value(parts[9]));
                        result.setFeatureMatchCount(intValue(parts[10]));
                        result.setMatchingGeographyType(value(parts[11]));
                        result.setRegionSize(doubleValue(parts[12]));
                        result.setRegionSizeUnit(value(parts[13]));
                        result.setMatchedLocationType(value(parts[14]));
                        result.setTimeTaken(doubleValue(parts[15]));

                        // test if there are any census tracts returned
                        if (parts.length > 17) {
                            result.setNaaccrCensusTractCertaintyCode(value(parts[16]));
                            result.setNaaccrCensusTractCertaintyName(value(parts[17]));

                            addCensus(result, parts, 1990, 18);
                            addCensus(result, parts, 2000, 29);
                            addCensus(result, parts, 2010, 40);
                        }

                        return result;
                    })
                    .collect(Collectors.toList());
        }

    }

    /**
     * Helper method to add a census mapping to the result
     */
    private static void addCensus(GeocodeOutput result, String[] parts, int year, int position) {
        if (parts.length < position + 1)
            return;

        // check there is a census tract value
        if (parts[position + 2].length() > 0) {
            Census census = new Census();
            census.setBlock(value(parts[position]));
            census.setBlockGroup(value(parts[position + 1]));
            census.setTract(value(parts[position + 2]));
            census.setCountyFips(value(parts[position + 3]));
            census.setCbsaFips(value(parts[position + 4]));
            census.setCbsaMicro(value(parts[position + 5]));
            census.setMcdFips(value(parts[position + 6]));
            census.setMetDivFips(value(parts[position + 7]));
            census.setMsaFips(value(parts[position + 8]));
            census.setPlaceFips(value(parts[position + 9]));
            census.setStateFips(value(parts[position + 10]));

            result.getCensusResults().put(year, census);
        }
    }

    /**
     * Helper method to safely convert empty strings to null
     */
    private static String value(String value) {
        if (value == null || value.isEmpty())
            return null;
        return value;
    }

    /**
     * Helper method to safely convert String to Integer
     */
    private static Integer intValue(String value) {
        if (value == null || value.isEmpty())
            return null;
        return Integer.valueOf(value);
    }

    /**
     * Helper method to safely convert String to Double
     */
    private static Double doubleValue(String value) {
        if (value == null || value.isEmpty())
            return null;
        return Double.valueOf(value);
    }
}