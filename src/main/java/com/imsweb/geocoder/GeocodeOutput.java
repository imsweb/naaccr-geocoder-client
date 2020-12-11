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
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import okhttp3.ResponseBody;
import retrofit2.Call;

import com.imsweb.geocoder.exception.BadRequestException;

@JsonIgnoreProperties({"version",
        "transactionGuid",
        "apiHost",
        "clientHost",
        "queryStatusCode",
        "inputParameterSet",
        "apiKey",
        "dontStoreTransactionDetails",
        "allowTies",
        "tieHandlingStrategyType",
        "relaxableAttributes",
        "relaxation",
        "substring",
        "soundex",
        "soundexAttributes",
        "referenceSources",
        "featureMatchingSelectionMethod",
        "attributeWeightingScheme",
        "minimumMatchScore",
        "confidenceLevels",
        "exhaustiveSearch",
        "aliasTables",
        "multiThreading",
        "censusYears",
        "includeHeader",
        "verbose",
        "outputCensusVariables",
        "outputReferenceFeatureGeometry",
        "outputFormat"})
public class GeocodeOutput {

    private static final ObjectMapper _OBJECT_MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    static {
        // AGGIE API occasionally returns empty string; we want to set the corresponding java properties to null instead
        SimpleModule sm = new SimpleModule();
        sm.addDeserializer(String.class, new TrimToNullDeserializer());
        _OBJECT_MAPPER.registerModule(sm);
    }

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
        if (body == null)
            return null;

        String resultString = body.string().trim();
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
            output.setTransactionId(data.get("transactionGuid").asText());
            output.setTimeTaken(data.get("timeTaken").asDouble());

            return output;
        }
        catch (JsonProcessingException e) {
            throw new BadRequestException(resultString);
        }
    }

    static void ignoreUnknown(boolean shouldIgnore) {
        _OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, !shouldIgnore);
    }

    static class TrimToNullDeserializer extends JsonDeserializer<String> {

        @Override
        public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            String text = p.getText().trim();
            if (text.length() == 0)
                return null;
            return text;
        }
    }
}