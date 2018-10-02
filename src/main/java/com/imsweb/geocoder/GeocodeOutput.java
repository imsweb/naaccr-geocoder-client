/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import okhttp3.ResponseBody;
import retrofit2.Call;

import com.imsweb.geocoder.exception.BadRequestException;

import static com.imsweb.geocoder.GeocodeInput.CURRENT_CENSUS_YEAR;

public class GeocodeOutput {

    private String _url;
    private String _transactionId;
    private String _apiVersion;
    private Integer _statusCode;
    private Double _latitude;
    private Double _longitude;
    private String _naaccrGisCoordinateQualityCode;
    private String _naaccrGisCoordinateQualityName;
    private Double _matchScore;
    private String _matchType;
    private String _matchingGeographyType;
    private Double _regionSize;
    private String _regionSizeUnit;
    private String _interpolationType;
    private String _interpolationSubType;
    private String _matchedLocationType;
    private String _featureMatchType;
    private Integer _featureMatchCount;
    private String _featureMatchTypeNotes;
    private String _tieHandlingStrategyType;
    private String _featureMatchTypeTieBreakingNotes;
    private String _featureMatchingSelectionMethod;
    private String _featureMatchingSelectionMethodNotes;
    private Double _timeTaken;
    private Address _matchAddress;
    private Address _parsedAddress;
    private Address _featureAddress;
    private Double _fArea;
    private String _fAreaType;
    private String _fGeometrySrid;
    private String _fGeometry;
    private String _fSource;
    private String _fVintage;
    private String _fPrimaryIdField;
    private String _fPrimaryIdValue;
    private String _fSecondaryIdField;
    private String _fSecondaryIdValue;
    private String _naaccrCensusTractCertaintyCode;
    private String _naaccrCensusTractCertaintyName;
    private Map<Integer, Census> _censusResults = new HashMap<>();
    private String _microMatchStatus;
    private String _penaltyCode;
    private String _penaltyCodeSummary;

    public String getUrl() {
        return _url;
    }

    public void setUrl(String url) {
        _url = url;
    }

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

    public Address getMatchAddress() {
        return _matchAddress;
    }

    public void setMatchAddress(Address matchAddress) {
        _matchAddress = matchAddress;
    }

    public Address getParsedAddress() {
        return _parsedAddress;
    }

    public void setParsedAddress(Address parsedAddress) {
        _parsedAddress = parsedAddress;
    }

    public Address getFeatureAddress() {
        return _featureAddress;
    }

    public void setFeatureAddress(Address featureAddress) {
        _featureAddress = featureAddress;
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

    public String getInterpolationType() {
        return _interpolationType;
    }

    public void setInterpolationType(String interpolationType) {
        _interpolationType = interpolationType;
    }

    public String getInterpolationSubType() {
        return _interpolationSubType;
    }

    public void setInterpolationSubType(String interpolationSubType) {
        _interpolationSubType = interpolationSubType;
    }

    public String getFeatureMatchTypeNotes() {
        return _featureMatchTypeNotes;
    }

    public void setFeatureMatchTypeNotes(String featureMatchTypeNotes) {
        _featureMatchTypeNotes = featureMatchTypeNotes;
    }

    public String getTieHandlingStrategyType() {
        return _tieHandlingStrategyType;
    }

    public void setTieHandlingStrategyType(String tieHandlingStrategyType) {
        _tieHandlingStrategyType = tieHandlingStrategyType;
    }

    public String getFeatureMatchTypeTieBreakingNotes() {
        return _featureMatchTypeTieBreakingNotes;
    }

    public void setFeatureMatchTypeTieBreakingNotes(String featureMatchTypeTieBreakingNotes) {
        _featureMatchTypeTieBreakingNotes = featureMatchTypeTieBreakingNotes;
    }

    public String getFeatureMatchingSelectionMethod() {
        return _featureMatchingSelectionMethod;
    }

    public void setFeatureMatchingSelectionMethod(String featureMatchingSelectionMethod) {
        _featureMatchingSelectionMethod = featureMatchingSelectionMethod;
    }

    public String getFeatureMatchingSelectionMethodNotes() {
        return _featureMatchingSelectionMethodNotes;
    }

    public void setFeatureMatchingSelectionMethodNotes(String featureMatchingSelectionMethodNotes) {
        _featureMatchingSelectionMethodNotes = featureMatchingSelectionMethodNotes;
    }

    public Double getfArea() {
        return _fArea;
    }

    public void setfArea(Double fArea) {
        _fArea = fArea;
    }

    public String getfAreaType() {
        return _fAreaType;
    }

    public void setfAreaType(String fAreaType) {
        _fAreaType = fAreaType;
    }

    public String getfGeometrySrid() {
        return _fGeometrySrid;
    }

    public void setfGeometrySrid(String fGeometrySrid) {
        _fGeometrySrid = fGeometrySrid;
    }

    public String getfGeometry() {
        return _fGeometry;
    }

    public void setfGeometry(String fGeometry) {
        _fGeometry = fGeometry;
    }

    public String getfSource() {
        return _fSource;
    }

    public void setfSource(String fSource) {
        _fSource = fSource;
    }

    public String getfVintage() {
        return _fVintage;
    }

    public void setfVintage(String fVintage) {
        _fVintage = fVintage;
    }

    public String getfPrimaryIdField() {
        return _fPrimaryIdField;
    }

    public void setfPrimaryIdField(String fPrimaryIdField) {
        _fPrimaryIdField = fPrimaryIdField;
    }

    public String getfPrimaryIdValue() {
        return _fPrimaryIdValue;
    }

    public void setfPrimaryIdValue(String fPrimaryIdValue) {
        _fPrimaryIdValue = fPrimaryIdValue;
    }

    public String getfSecondaryIdField() {
        return _fSecondaryIdField;
    }

    public void setfSecondaryIdField(String fSecondaryIdField) {
        _fSecondaryIdField = fSecondaryIdField;
    }

    public String getfSecondaryIdValue() {
        return _fSecondaryIdValue;
    }

    public void setfSecondaryIdValue(String fSecondaryIdValue) {
        _fSecondaryIdValue = fSecondaryIdValue;
    }

    public Map<Integer, Census> getCensusResults() {
        return _censusResults;
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

    static List<GeocodeOutput> toResults(Call<ResponseBody> call) throws IOException {
        String url = call.request().url().toString();
        ResponseBody body = call.execute().body();
        String resultString = body.string().trim();

        if (resultString.isEmpty())
            return Collections.emptyList();
        if (resultString.startsWith("invalid request - "))
            throw new BadRequestException("API indicated invalid request; could indicate API key issue");

        try (BufferedReader reader = new BufferedReader(new StringReader(resultString))) {
            return reader.lines()
                    .map((String line) -> {
                        GeocodeOutput result = new GeocodeOutput();

                        String[] parts = line.split("\t");

                        if (parts.length < 116)
                            throw new IllegalStateException("Unknown format returned from API");

                        result.setUrl(url);
                        result.setTransactionId(value(parts[0]));
                        result.setApiVersion(value(parts[1]));
                        result.setStatusCode(intValue(parts[2]));

                        result.setLatitude(doubleValue(parts[3]));
                        result.setLongitude(doubleValue(parts[4]));
                        result.setNaaccrGisCoordinateQualityCode(value(parts[5]));
                        result.setNaaccrGisCoordinateQualityName(value(parts[6]));
                        result.setMatchScore(doubleValue(value(parts[7])));
                        result.setMatchType(value(parts[8]));
                        result.setMatchingGeographyType(value(parts[9]));
                        result.setRegionSize(doubleValue(parts[10]));
                        result.setRegionSizeUnit(value(parts[11]));
                        result.setInterpolationType(value(parts[12]));
                        result.setInterpolationSubType(value(parts[13]));
                        result.setMatchedLocationType(value(parts[14]));
                        result.setFeatureMatchType(value(parts[15]));
                        result.setFeatureMatchCount(intValue(parts[16]));
                        result.setFeatureMatchTypeNotes(value(parts[17]));
                        result.setTieHandlingStrategyType(value(parts[18]));
                        result.setFeatureMatchTypeTieBreakingNotes(value(parts[19]));       //These two seem to be reversed...
                        result.setFeatureMatchingSelectionMethod(value(parts[20]));       //These two seem to be reversed...
                        result.setFeatureMatchingSelectionMethodNotes(value(parts[21]));
                        result.setTimeTaken(doubleValue(parts[22]));

                        result.setMatchAddress(createAddress(parts, 23));
                        result.setParsedAddress(createAddress(parts, 50));
                        result.setFeatureAddress(createAddress(parts, 77));

                        result.setfArea(doubleValue(parts[104]));
                        result.setfAreaType(value(parts[105]));
                        result.setfGeometrySrid(value(parts[106]));
                        result.setfGeometry(value(parts[107]));
                        result.setfSource(value(parts[108]));
                        result.setfVintage(value(parts[109]));
                        result.setfPrimaryIdField(value(parts[110]));
                        result.setfPrimaryIdValue(value(parts[111]));
                        result.setfSecondaryIdField(value(parts[112]));
                        result.setfSecondaryIdValue(value(parts[113]));
                        result.setNaaccrCensusTractCertaintyCode(value(parts[114]));
                        result.setNaaccrCensusTractCertaintyName(value(parts[115]));

                        // test if there are any census tracts returned
                        int nextPosition;
                        if (parts.length > 148) {
                            addCensus(result, parts, 1990, 116);
                            addCensus(result, parts, 2000, 127);
                            addCensus(result, parts, 2010, 138);
                            nextPosition = 149;
                        }
                        else if (parts.length > 126) {
                            addCensus(result, parts, CURRENT_CENSUS_YEAR, 116);
                            nextPosition = 127;
                        }
                        else
                            nextPosition = 116;

                        if (parts.length > nextPosition + 2) {
                            result.setMicroMatchStatus(value(parts[nextPosition]));
                            result.setPenaltyCode(value(parts[nextPosition + 1]));
                            result.setPenaltyCodeSummary(value(parts[nextPosition + 2]));
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
     * Helper method to add an address mapping to the result
     */
    private static Address createAddress(String[] parts, int position) {

        Address address = new Address();
        address.setNumber(value(parts[position]));
        address.setNumberFractional(value(parts[position + 1]));
        address.setPreDirectional(value(parts[position + 2]));
        address.setPreQualifier(value(parts[position + 3]));
        address.setPreType(value(parts[position + 4]));
        address.setPreArticle(value(parts[position + 5]));
        address.setName(value(parts[position + 6]));
        address.setPostArticle(value(parts[position + 7]));
        address.setPostQualifier(value(parts[position + 8]));
        address.setSuffix(value(parts[position + 9]));
        address.setPostDirectional(value(parts[position + 10]));
        address.setSuiteType(value(parts[position + 11]));
        address.setSuiteNumber(value(parts[position + 12]));
        address.setPoBoxType(value(parts[position + 13]));
        address.setPoBoxNumber(value(parts[position + 14]));
        address.setCity(value(parts[position + 15]));
        address.setConsolidatedCity(value(parts[position + 16]));
        address.setMinorCivilDivision(value(parts[position + 17]));
        address.setCountySubregion(value(parts[position + 18]));
        address.setCounty(value(parts[position + 19]));
        address.setState(value(parts[position + 20]));
        address.setZip(value(parts[position + 21]));
        address.setZipPlus1(value(parts[position + 22]));
        address.setZipPlus2(value(parts[position + 23]));
        address.setZipPlus3(value(parts[position + 24]));
        address.setZipPlus4(value(parts[position + 25]));
        address.setZipPlus5(value(parts[position + 26]));

        return address;
    }

    /**
     * Helper method to safely convert empty strings to null
     */
    static String value(String value) {
        if (value == null || value.isEmpty())
            return null;
        return value;
    }

    /**
     * Helper method to safely convert String to Integer
     */
    static Integer intValue(String value) {
        if (value == null || value.isEmpty())
            return null;
        try {
            return Integer.valueOf(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Helper method to safely convert String to Double
     */
    static Double doubleValue(String value) {
        if (value == null || value.isEmpty())
            return null;
        try {
            return Double.valueOf(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }
}