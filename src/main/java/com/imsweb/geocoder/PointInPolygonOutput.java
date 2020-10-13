/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.ResponseBody;
import retrofit2.Call;

import com.imsweb.geocoder.exception.BadRequestException;

public class PointInPolygonOutput {

    private static ObjectMapper _OBJECT_MAPPER = new ObjectMapper();

    public static final String CENSUS_CODE_1990 = "NineteenNinety";
    public static final String CENSUS_CODE_2000 = "TwoThousand";
    public static final String CENSUS_CODE_2010 = "TwoThousandTen";
    public static final String CENSUS_CODE_2020 = "TwoThousandTwenty";
    public static final String CENSUS_CODE_ALL = "AllAvailable";

    private Integer _statusCode;
    @JsonProperty("Version")
    private String _apiVersion;
    @JsonProperty("TransactionId")
    private String _transactionId;
    @JsonProperty("CensusYear")
    private String _censusYear;
    @JsonProperty("CensusBlock")
    private String _censusBlock;
    @JsonProperty("CensusBlockGroup")
    private String _censusBlockGroup;
    @JsonProperty("CensusTract")
    private String _censusTract;
    @JsonProperty("CensusPlaceFips")
    private String _censusPlaceFips;
    @JsonProperty("CensusMcdFips")
    private String _censusMcdFips;
    @JsonProperty("CensusMsaFips")
    private String _censusMsaFips;
    @JsonProperty("CensusCbsaFips")
    private String _censusCbsaFips;
    @JsonProperty("CensusCbsaMicro")
    private String _censusCbsaMicro;
    @JsonProperty("CensusMetDivFips")
    private String _censusMetDivFips;
    @JsonProperty("CensusCountyFips")
    private String _censusCountyFips;
    @JsonProperty("CensusStateFips")
    private String _censusStateFips;
    @JsonProperty("TransactionId")
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

            List<PointInPolygonOutput> output = _OBJECT_MAPPER.convertValue(node.get("CensusResults"), List.class);

            return output.get(0);
        }
        catch (JsonProcessingException e) {
            throw new BadRequestException(resultString);
        }
        //        String[] parts = resultString.split("\t", -1);
        //
        //        if (parts.length != 16)
        //            throw new IllegalStateException("Unknown format returned from API");
        //
        //        PointInPolygonOutput result = new PointInPolygonOutput();
        //
        //        result.setStatusCode(GeocoderUtils.intValue(parts[0]));
        //        result.setApiVersion(GeocoderUtils.value(parts[1]));
        //        result.setTransactionId(GeocoderUtils.value(parts[2]));
        //        result.setCensusYear(decodeCensusYear(GeocoderUtils.value(parts[3])));
        //        result.setCensusBlock(GeocoderUtils.value(parts[4]));
        //        result.setCensusBlockGroup(GeocoderUtils.value(parts[5]));
        //        result.setCensusTract(GeocoderUtils.value(parts[6]));
        //        result.setCensusPlaceFips(GeocoderUtils.value(parts[7]));
        //        result.setCensusMcdFips(GeocoderUtils.value(parts[8]));
        //        result.setCensusMsaFips(GeocoderUtils.value(parts[9]));
        //        result.setCensusCbsaFips(GeocoderUtils.value(parts[10]));
        //        result.setCensusCbsaMicro(GeocoderUtils.value(parts[11]));
        //        result.setCensusMetDivFips(GeocoderUtils.value(parts[12]));
        //        result.setCensusCountyFips(GeocoderUtils.value(parts[13]));
        //        result.setCensusStateFips(GeocoderUtils.value(parts[14]));
        //        result.setTimeTaken(GeocoderUtils.doubleValue(parts[15]));
        //
        //        return result;
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
