/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    @JsonIgnore
    private String _error;
    @JsonIgnore
    private String _message;

    @JsonProperty("results")
    private List<GeocoderResult> _results = new ArrayList<>();

    @JsonProperty("parsedAddress")
    private Address _parsedAddress;

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

    public String getError() {
        return _error;
    }

    public void setError(String error) {
        _error = error;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String message) {
        _message = message;
    }

    public Double getTimeTaken() {
        return _timeTaken;
    }

    void setTimeTaken(Double timeTaken) {
        _timeTaken = timeTaken;
    }

    public List<GeocoderResult> getResults() {
        return _results;
    }

    public void setResults(List<GeocoderResult> results) {
        _results = results;
    }

    public Address getParsedAddress() {
        return _parsedAddress;
    }

    public void setParsedAddress(Address parsedAddress) {
        _parsedAddress = parsedAddress;
    }

    static GeocodeOutput toResults(Call<ResponseBody> call) throws IOException {
        String url = call.request().url().toString();
        ResponseBody body = call.execute().body();
        String resultString = body.string().replaceAll("\"\"", "null").trim();      // this is cheesy but it's the easiest way to handle blank values

        if (resultString.isEmpty())
            return null;
        if (resultString.startsWith("invalid request - "))
            throw new BadRequestException("API indicated invalid request; could indicate API key issue");

        try {
            JsonNode node = _OBJECT_MAPPER.readTree(resultString);

            JsonNode data = node.get("data");
            JsonNode versionNode = data.get("version");
            String apiVersion = versionNode.get("major").asText() + "." + versionNode.get("minor").asText() + "." + versionNode.get("build").asText();

            GeocodeOutput output = _OBJECT_MAPPER.convertValue(node.get("data"), GeocodeOutput.class);
            output.setApiVersion(apiVersion);
            output.setUrl(url);
            output.setStatusCode(node.get("statusCode").asInt());
            if (!node.get("error").isNull())
                output.setError(node.get("error").asText());
            output.setTransactionId(data.get("transactionId").asText());
            output.setTimeTaken(data.get("transactionId").asDouble());

            return output;
        }
        catch (JsonProcessingException e) {
            throw new BadRequestException(resultString);
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
            census.setGeoLocationId(GeocoderUtils.value(parts[position + 11]));

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