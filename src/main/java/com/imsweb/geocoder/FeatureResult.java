/*
 * Copyright (C) 2020 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FeatureResult {

    @JsonProperty("area")
    private Double _area;
    @JsonProperty("areaType")
    private String _areaType;
    @JsonProperty("geometrySRID")
    private String _geometrySrid;
    @JsonProperty("geometry")
    private String _geometry;
    @JsonProperty("source")
    private String _source;
    @JsonProperty("vintage")
    private Integer _vintage;
    @JsonProperty("primaryIdField")
    private String _primaryIdField;
    @JsonProperty("primaryIdValue")
    private String _primaryIdValue;
    @JsonProperty("secondaryIdField")
    private String _secondaryIdField;
    @JsonProperty("secondaryIdValue")
    private String _secondaryIdValue;
    @JsonProperty("interpolationType")
    private String _interpolationType;
    @JsonProperty("interpolationSubType")
    private String _interpolationSubType;

    @JsonProperty("address")
    private Address _address;

    public Double getArea() {
        return _area;
    }

    public void setArea(Double area) {
        _area = area;
    }

    public String getAreaType() {
        return _areaType;
    }

    public void setAreaType(String areaType) {
        _areaType = areaType;
    }

    public String getGeometrySrid() {
        return _geometrySrid;
    }

    public void setGeometrySrid(String geometrySrid) {
        _geometrySrid = geometrySrid;
    }

    public String getGeometry() {
        return _geometry;
    }

    public void setGeometry(String geometry) {
        _geometry = geometry;
    }

    public String getSource() {
        return _source;
    }

    public void setSource(String source) {
        _source = source;
    }

    public Integer getVintage() {
        return _vintage;
    }

    public void setVintage(Integer vintage) {
        _vintage = vintage;
    }

    public String getPrimaryIdField() {
        return _primaryIdField;
    }

    public void setPrimaryIdField(String primaryIdField) {
        _primaryIdField = primaryIdField;
    }

    public String getPrimaryIdValue() {
        return _primaryIdValue;
    }

    public void setPrimaryIdValue(String primaryIdValue) {
        _primaryIdValue = primaryIdValue;
    }

    public String getSecondaryIdField() {
        return _secondaryIdField;
    }

    public void setSecondaryIdField(String secondaryIdField) {
        _secondaryIdField = secondaryIdField;
    }

    public String getSecondaryIdValue() {
        return _secondaryIdValue;
    }

    public void setSecondaryIdValue(String secondaryIdValue) {
        _secondaryIdValue = secondaryIdValue;
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

    public Address getAddress() {
        return _address;
    }

    public void setAddress(Address address) {
        _address = address;
    }
}
