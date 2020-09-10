/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.ResponseBody;
import retrofit2.Call;

import com.imsweb.geocoder.exception.BadRequestException;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeOutput {

    private static ObjectMapper _OBJECT_MAPPER = new ObjectMapper();

    @JsonIgnore
    private String _url;
    @JsonProperty("transactionId")
    private String _transactionId;
    @JsonProperty("timeTaken")
    private Double _timeTaken;
    @JsonIgnore
    private String _apiVersion;
    @JsonIgnore
    private Integer _statusCode;

    @JsonProperty("results")
    private Set<GeocoderResult> _results = new HashSet<>();

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

    public Double getTimeTaken() {
        return _timeTaken;
    }

    void setTimeTaken(Double timeTaken) {
        _timeTaken = timeTaken;
    }

    public Set<GeocoderResult> getResults() {
        return _results;
    }

    public void setResults(Set<GeocoderResult> results) {
        _results = results;
    }

    static List<GeocodeOutput> toResults(Call<ResponseBody> call) throws IOException {
        String url = call.request().url().toString();
        ResponseBody body = call.execute().body();
        String resultString = body.string().trim();

        if (resultString.isEmpty())
            return Collections.emptyList();
        if (resultString.startsWith("invalid request - "))
            throw new BadRequestException("API indicated invalid request; could indicate API key issue");

        JsonNode node = _OBJECT_MAPPER.readTree(resultString);

        String statusCode = node.get("statusCode").asText();
        GeocodeOutput output = _OBJECT_MAPPER.convertValue(node.get("data"), GeocodeOutput.class);

        try (BufferedReader reader = new BufferedReader(new StringReader(resultString))) {
            //            reader.lines().forEach(System.out::println);
            //            return reader.lines()
            //                    .peek(System.out::println)      // uncomment for debugging
            //                    .map((String line) -> {
            //                        GeocodeOutput result = new GeocodeOutput();
            //
            //                        String[] parts = line.split("\t");
            //
            //                        if (parts.length < 116)
            //                            throw new IllegalStateException("Unknown format returned from API");
            //
            //                        result.setUrl(url);
            //                        result.setTransactionId(GeocoderUtils.value(parts[0]));
            //                        result.setApiVersion(GeocoderUtils.value(parts[1]));
            //                        result.setStatusCode(GeocoderUtils.intValue(parts[2]));
            //
            //                        result.setLatitude(GeocoderUtils.value(parts[3]));
            //                        result.setLongitude(GeocoderUtils.value(parts[4]));
            //                        result.setNaaccrGisCoordinateQualityCode(GeocoderUtils.value(parts[5]));
            //                        result.setNaaccrGisCoordinateQualityName(GeocoderUtils.value(parts[6]));
            //                        result.setMatchScore(GeocoderUtils.doubleValue(GeocoderUtils.value(parts[7])));
            //                        result.setMatchType(GeocoderUtils.value(parts[8]));
            //                        result.setMatchingGeographyType(GeocoderUtils.value(parts[9]));
            //                        result.setRegionSize(GeocoderUtils.doubleValue(parts[10]));
            //                        result.setRegionSizeUnit(GeocoderUtils.value(parts[11]));
            //                        result.setInterpolationType(GeocoderUtils.value(parts[12]));
            //                        result.setInterpolationSubType(GeocoderUtils.value(parts[13]));
            //                        result.setMatchedLocationType(GeocoderUtils.value(parts[14]));
            //                        result.setFeatureMatchType(GeocoderUtils.value(parts[15]));
            //                        result.setFeatureMatchCount(GeocoderUtils.intValue(parts[16]));
            //                        result.setFeatureMatchTypeNotes(GeocoderUtils.value(parts[17]));
            //                        result.setTieHandlingStrategyType(GeocoderUtils.value(parts[18]));
            //                        result.setFeatureMatchTypeTieBreakingNotes(GeocoderUtils.value(parts[19]));       //These two seem to be reversed...
            //                        result.setFeatureMatchingSelectionMethod(GeocoderUtils.value(parts[20]));       //These two seem to be reversed...
            //                        result.setFeatureMatchingSelectionMethodNotes(GeocoderUtils.value(parts[21]));
            //                        result.setTimeTaken(GeocoderUtils.doubleValue(parts[22]));
            //
            //                        result.setMatchAddress(createAddress(parts, 23));
            //                        result.setParsedAddress(createAddress(parts, 50));
            //                        result.setFeatureAddress(createAddress(parts, 77));
            //
            //                        result.setfArea(GeocoderUtils.doubleValue(parts[104]));
            //                        result.setfAreaType(GeocoderUtils.value(parts[105]));
            //                        result.setfGeometrySrid(GeocoderUtils.value(parts[106]));
            //                        result.setfGeometry(GeocoderUtils.value(parts[107]));
            //                        result.setfSource(GeocoderUtils.value(parts[108]));
            //                        result.setfVintage(GeocoderUtils.value(parts[109]));
            //                        result.setfPrimaryIdField(GeocoderUtils.value(parts[110]));
            //                        result.setfPrimaryIdValue(GeocoderUtils.value(parts[111]));
            //                        result.setfSecondaryIdField(GeocoderUtils.value(parts[112]));
            //                        result.setfSecondaryIdValue(GeocoderUtils.value(parts[113]));
            //                        result.setNaaccrCensusTractCertaintyCode(GeocoderUtils.value(parts[114]));
            //                        result.setNaaccrCensusTractCertaintyName(GeocoderUtils.value(parts[115]));
            //
            //                        // test if there are any census tracts returned
            //                        int nextPosition;
            //                        if (parts.length > 148) {
            //                            addCensus(result, parts, 1990, 116);
            //                            addCensus(result, parts, 2000, 128);
            //                            addCensus(result, parts, 2010, 140);
            //                            nextPosition = 152;
            //                        }
            //                        else if (parts.length > 126) {
            //                            addCensus(result, parts, CURRENT_CENSUS_YEAR, 116);
            //                            nextPosition = 128;
            //                        }
            //                        else
            //                            nextPosition = 116;
            //
            //                        if (parts.length > nextPosition + 2) {
            //                            result.setMicroMatchStatus(GeocoderUtils.value(parts[nextPosition]));
            //                            result.setPenaltyCode(GeocoderUtils.value(parts[nextPosition + 1]));
            //                            result.setPenaltyCodeSummary(GeocoderUtils.value(parts[nextPosition + 2]));
            //                        }
            //                        return result;
            //                    })
            //                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
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
            census.setBlock(GeocoderUtils.value(parts[position]));
            census.setBlockGroup(GeocoderUtils.value(parts[position + 1]));
            census.setTract(GeocoderUtils.value(parts[position + 2]));
            census.setCountyFips(GeocoderUtils.value(parts[position + 3]));
            census.setCbsaFips(GeocoderUtils.value(parts[position + 4]));
            census.setCbsaMicro(GeocoderUtils.value(parts[position + 5]));
            census.setMcdFips(GeocoderUtils.value(parts[position + 6]));
            census.setMetDivFips(GeocoderUtils.value(parts[position + 7]));
            census.setMsaFips(GeocoderUtils.value(parts[position + 8]));
            census.setPlaceFips(GeocoderUtils.value(parts[position + 9]));
            census.setStateFips(GeocoderUtils.value(parts[position + 10]));
            census.setGeoLocationID(GeocoderUtils.value(parts[position + 11]));

            //            result.getCensusResults().put(year, census);
        }
    }

    /**
     * Helper method to add an address mapping to the result
     */
    private static Address createAddress(String[] parts, int position) {

        Address address = new Address();
        address.setNumber(GeocoderUtils.value(parts[position]));
        address.setNumberFractional(GeocoderUtils.value(parts[position + 1]));
        address.setPreDirectional(GeocoderUtils.value(parts[position + 2]));
        address.setPreQualifier(GeocoderUtils.value(parts[position + 3]));
        address.setPreType(GeocoderUtils.value(parts[position + 4]));
        address.setPreArticle(GeocoderUtils.value(parts[position + 5]));
        address.setName(GeocoderUtils.value(parts[position + 6]));
        address.setPostArticle(GeocoderUtils.value(parts[position + 7]));
        address.setPostQualifier(GeocoderUtils.value(parts[position + 8]));
        address.setSuffix(GeocoderUtils.value(parts[position + 9]));
        address.setPostDirectional(GeocoderUtils.value(parts[position + 10]));
        address.setSuiteType(GeocoderUtils.value(parts[position + 11]));
        address.setSuiteNumber(GeocoderUtils.value(parts[position + 12]));
        address.setPoBoxType(GeocoderUtils.value(parts[position + 13]));
        address.setPoBoxNumber(GeocoderUtils.value(parts[position + 14]));
        address.setCity(GeocoderUtils.value(parts[position + 15]));
        address.setConsolidatedCity(GeocoderUtils.value(parts[position + 16]));
        address.setMinorCivilDivision(GeocoderUtils.value(parts[position + 17]));
        address.setCountySubRegion(GeocoderUtils.value(parts[position + 18]));
        address.setCounty(GeocoderUtils.value(parts[position + 19]));
        address.setState(GeocoderUtils.value(parts[position + 20]));
        address.setZip(GeocoderUtils.value(parts[position + 21]));
        address.setZipPlus1(GeocoderUtils.value(parts[position + 22]));
        address.setZipPlus2(GeocoderUtils.value(parts[position + 23]));
        address.setZipPlus3(GeocoderUtils.value(parts[position + 24]));
        address.setZipPlus4(GeocoderUtils.value(parts[position + 25]));
        address.setZipPlus5(GeocoderUtils.value(parts[position + 26]));

        return address;
    }

}