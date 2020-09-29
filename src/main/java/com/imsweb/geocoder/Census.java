/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties({"exceptionOccurred", "exceptionMessage"})
public class Census {

    @JsonProperty("censusYear")
    private Integer _year;
    @JsonProperty("censusBlock")
    private String _block;
    @JsonProperty("censusBlockGroup")
    private String _blockGroup;
    @JsonProperty("censusTract")
    private String _tract;
    @JsonProperty("censusCountyFips")
    private String _countyFips;
    @JsonProperty("censusCbsaFips")
    private String _cbsaFips;
    @JsonProperty("censusCbsaMicro")
    private String _cbsaMicro;
    @JsonProperty("censusMcdFips")
    private String _mcdFips;
    @JsonProperty("censusMetDivFips")
    private String _metDivFips;
    @JsonProperty("censusMsaFips")
    private String _msaFips;
    @JsonProperty("censusPlaceFips")
    private String _placeFips;
    @JsonProperty("censusStateFips")
    private String _stateFips;
    @JsonProperty("geoLocationId")
    private String _geoLocationId;

    public Integer getYear() {
        return _year;
    }

    public void setYear(Integer year) {
        _year = year;
    }

    public String getBlock() {
        return _block;
    }

    public void setBlock(String block) {
        _block = block;
    }

    public String getBlockGroup() {
        return _blockGroup;
    }

    public void setBlockGroup(String blockGroup) {
        _blockGroup = blockGroup;
    }

    public String getTract() {
        return _tract;
    }

    public void setTract(String tract) {
        _tract = tract;
    }

    public String getCountyFips() {
        return _countyFips;
    }

    public void setCountyFips(String countyFips) {
        _countyFips = countyFips;
    }

    public String getCbsaFips() {
        return _cbsaFips;
    }

    public void setCbsaFips(String cbsaFips) {
        _cbsaFips = cbsaFips;
    }

    public String getCbsaMicro() {
        return _cbsaMicro;
    }

    public void setCbsaMicro(String cbsaMicro) {
        _cbsaMicro = cbsaMicro;
    }

    public String getMcdFips() {
        return _mcdFips;
    }

    public void setMcdFips(String mcdFips) {
        _mcdFips = mcdFips;
    }

    public String getMetDivFips() {
        return _metDivFips;
    }

    public void setMetDivFips(String metDivFips) {
        _metDivFips = metDivFips;
    }

    public String getMsaFips() {
        return _msaFips;
    }

    public void setMsaFips(String msaFips) {
        _msaFips = msaFips;
    }

    public String getPlaceFips() {
        return _placeFips;
    }

    public void setPlaceFips(String placeFips) {
        _placeFips = placeFips;
    }

    public String getStateFips() {
        return _stateFips;
    }

    public void setStateFips(String stateFips) {
        _stateFips = stateFips;
    }

    public String getGeoLocationId() {
        return _geoLocationId;
    }

    public void setGeoLocationId(String geoLocationId) {
        _geoLocationId = geoLocationId;
    }
}