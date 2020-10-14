/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.ResponseBody;
import retrofit2.Call;

import com.imsweb.geocoder.exception.BadRequestException;

@JsonIgnoreProperties({"exceptionOccurred", "exceptionMessage"})
public class PointInPolygonOutput {

    private static ObjectMapper _OBJECT_MAPPER = new ObjectMapper();

    public static final String CENSUS_CODE_1990 = "NineteenNinety";
    public static final String CENSUS_CODE_2000 = "TwoThousand";
    public static final String CENSUS_CODE_2010 = "TwoThousandTen";
    public static final String CENSUS_CODE_2020 = "TwoThousandTwenty";
    public static final String CENSUS_CODE_ALL = "AllAvailable";

    @JsonIgnore
    private Integer _statusCode;
    @JsonIgnore
    private String _apiVersion;
    @JsonIgnore
    private String _transactionId;
    @JsonProperty("censusYear")
    private String _censusYear;
    @JsonProperty("geoLocationId")
    private String _geoLocationId;
    @JsonProperty("censusBlock")
    private String _censusBlock;
    @JsonProperty("censusBlockGroup")
    private String _censusBlockGroup;
    @JsonProperty("censusTract")
    private String _censusTract;
    @JsonProperty("censusPlaceFips")
    private String _censusPlaceFips;
    @JsonProperty("censusMcdFips")
    private String _censusMcdFips;
    @JsonProperty("censusMsaFips")
    private String _censusMsaFips;
    @JsonProperty("censusCbsaFips")
    private String _censusCbsaFips;
    @JsonProperty("censusCbsaMicro")
    private String _censusCbsaMicro;
    @JsonProperty("censusMetDivFips")
    private String _censusMetDivFips;
    @JsonProperty("censusCountyFips")
    private String _censusCountyFips;
    @JsonProperty("censusStateFips")
    private String _censusStateFips;
    @JsonIgnore
    private Double _timeTaken;

    public Integer getStatusCode() {
        return _statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        _statusCode = statusCode;
    }

    public String getApiVersion() {
        return _apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        _apiVersion = apiVersion;
    }

    public String getTransactionId() {
        return _transactionId;
    }

    public void setTransactionId(String transactionId) {
        _transactionId = transactionId;
    }

    public String getCensusYear() {
        return _censusYear;
    }

    public void setCensusYear(String censusYear) {
        _censusYear = censusYear;
    }

    public String getGeoLocationId() {
        return _geoLocationId;
    }

    public void setGeoLocationId(String geoLocationId) {
        _geoLocationId = geoLocationId;
    }

    public String getCensusBlock() {
        return _censusBlock;
    }

    public void setCensusBlock(String censusBlock) {
        _censusBlock = censusBlock;
    }

    public String getCensusBlockGroup() {
        return _censusBlockGroup;
    }

    public void setCensusBlockGroup(String censusBlockGroup) {
        _censusBlockGroup = censusBlockGroup;
    }

    public String getCensusTract() {
        return _censusTract;
    }

    public void setCensusTract(String censusTract) {
        _censusTract = censusTract;
    }

    public String getCensusPlaceFips() {
        return _censusPlaceFips;
    }

    public void setCensusPlaceFips(String censusPlaceFips) {
        _censusPlaceFips = censusPlaceFips;
    }

    public String getCensusMcdFips() {
        return _censusMcdFips;
    }

    public void setCensusMcdFips(String censusMcdFips) {
        _censusMcdFips = censusMcdFips;
    }

    public String getCensusMsaFips() {
        return _censusMsaFips;
    }

    public void setCensusMsaFips(String censusMsaFips) {
        _censusMsaFips = censusMsaFips;
    }

    public String getCensusCbsaFips() {
        return _censusCbsaFips;
    }

    public void setCensusCbsaFips(String censusCbsaFips) {
        _censusCbsaFips = censusCbsaFips;
    }

    public String getCensusMetDivFips() {
        return _censusMetDivFips;
    }

    public String getCensusCbsaMicro() {
        return _censusCbsaMicro;
    }

    public void setCensusCbsaMicro(String censusCbsaMicro) {
        _censusCbsaMicro = censusCbsaMicro;
    }

    public void setCensusMetDivFips(String censusMetDivFips) {
        _censusMetDivFips = censusMetDivFips;
    }

    public String getCensusCountyFips() {
        return _censusCountyFips;
    }

    public void setCensusCountyFips(String censusCountyFips) {
        _censusCountyFips = censusCountyFips;
    }

    public String getCensusStateFips() {
        return _censusStateFips;
    }

    public void setCensusStateFips(String censusStateFips) {
        _censusStateFips = censusStateFips;
    }

    public Double getTimeTaken() {
        return _timeTaken;
    }

    public void setTimeTaken(Double timeTaken) {
        _timeTaken = timeTaken;
    }

    static PointInPolygonOutput toResults(Call<ResponseBody> call) throws IOException {
        ResponseBody body = call.execute().body();
        String resultString = body.string().trim();

        if (resultString.startsWith("invalid request - "))
            throw new BadRequestException("API indicated invalid request; could indicate API key issue");

        try {
            JsonNode node = _OBJECT_MAPPER.readTree(resultString);

            JsonNode data = node.get("data");
            JsonNode versionNode = data.get("version");
            String apiVersion = versionNode.get("major").asText() + "." + versionNode.get("minor").asText() + "." + versionNode.get("build").asText();
            Integer statusCode = node.get("statusCode").asInt();
            String transaction = data.get("transactionGuid").asText();
            Double time = data.get("timeTaken").asDouble();

            List<PointInPolygonOutput> output = _OBJECT_MAPPER.convertValue(data.get("results"), new TypeReference<List<PointInPolygonOutput>>() {});

            PointInPolygonOutput result = output.get(0);
            result.setStatusCode(statusCode);
            result.setApiVersion(apiVersion);
            result.setTransactionId(transaction);
            result.setTimeTaken(time);
            return result;
        }
        catch (JsonProcessingException e) {
            throw new BadRequestException(resultString);
        }
    }

    private static String decodeCensusYear(String code) {
        switch (code) {
            case CENSUS_CODE_1990:
                return "1990";
            case CENSUS_CODE_2000:
                return "2000";
            case CENSUS_CODE_2010:
                return "2010";
            case CENSUS_CODE_2020:
                return "2020";
            default:
                return "";
        }
    }
}
