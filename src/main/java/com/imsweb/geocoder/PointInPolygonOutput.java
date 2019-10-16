/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;

import com.imsweb.geocoder.exception.BadRequestException;

public class PointInPolygonOutput {

    public static String CENSUS_CODE_1990 = "NineteenNinety";
    public static String CENSUS_CODE_2000 = "TwoThousand";
    public static String CENSUS_CODE_2010 = "TwoThousandTen";
    public static String CENSUS_CODE_ALL = "AllAvailable";

    private Integer _statusCode;
    private String _apiVersion;
    private String _transactionId;
    private String _censusYear;
    private String _censusBlock;
    private String _censusBlockGroup;
    private String _censusTract;
    private String _censusPlaceFips;
    private String _censusMcdFips;
    private String _censusMsaFips;
    private String _censusCbsaFips;
    private String _censusCbsaMicro;
    private String _censusMetDivFips;
    private String _censusCountyFips;
    private String _censusStateFips;
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

        String[] parts = resultString.split("\t", -1);

        if (parts.length != 16)
            throw new IllegalStateException("Unknown format returned from API");

        PointInPolygonOutput result = new PointInPolygonOutput();

        result.setStatusCode(GeocoderUtils.intValue(parts[0]));
        result.setApiVersion(GeocoderUtils.value(parts[1]));
        result.setTransactionId(GeocoderUtils.value(parts[2]));
        result.setCensusYear(GeocoderUtils.value(parts[3]));
        result.setCensusBlock(GeocoderUtils.value(parts[4]));
        result.setCensusBlockGroup(GeocoderUtils.value(parts[5]));
        result.setCensusTract(GeocoderUtils.value(parts[6]));
        result.setCensusPlaceFips(GeocoderUtils.value(parts[7]));
        result.setCensusMcdFips(GeocoderUtils.value(parts[8]));
        result.setCensusMsaFips(GeocoderUtils.value(parts[9]));
        result.setCensusCbsaFips(GeocoderUtils.value(parts[10]));
        result.setCensusCbsaMicro(GeocoderUtils.value(parts[11]));
        result.setCensusMetDivFips(GeocoderUtils.value(parts[12]));
        result.setCensusCountyFips(GeocoderUtils.value(parts[13]));
        result.setCensusStateFips(GeocoderUtils.value(parts[14]));
        result.setTimeTaken(GeocoderUtils.doubleValue(parts[15]));

        return result;
    }
}
