/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.util.HashMap;
import java.util.Map;

public class GeocodeInput {

    public static final Integer CURRENT_CENSUS_YEAR = 2010;

    public enum TieBreakingStrategy {
        FLIP_A_COIN,         // choose and return one of the ties at random
        REVERT_TO_HIERARCHY  // fail on a tie and match to the next level of the geographic hierarchy - parcel, then street, then ZIP, then city, etc.
    }

    public enum FeatureMatchingHierarchy {
        UNCERTAINTY_BASED,         // choose and return one of the ties at random
        FEATURE_MATCHING_SELECTION_METHOD  // fail on a tie and match to the next level of the geographic hierarchy - parcel, then street, then ZIP, then city, etc.
    }

    private String _streetAddress;
    private String _city;
    private String _state;
    private String _zip;
    private Boolean _allowTies;
    private TieBreakingStrategy _tieBreakingStrategy;
    private Boolean _census;
    private Boolean _currentCensusYearOnly;
    private Boolean _geometry;
    private Boolean _notStore;
    private String _confidenceLevels;
    private String _minScore;
    private Boolean _exhaustiveSearch;
    private Boolean _aliasTable;
    private Boolean _attributeRelaxation;
    private String _relaxedAttributes;
    private Boolean _substringMatching;
    private Boolean _soundex;
    private String _soundexableAttributes;
    private FeatureMatchingHierarchy _hierarchy;    // FeatureMatchingHierarchy
    private String _referenceDataSources;

    public String getStreetAddress() {
        return _streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        _streetAddress = streetAddress;
    }

    public String getCity() {
        return _city;
    }

    public void setCity(String city) {
        _city = city;
    }

    public String getState() {
        return _state;
    }

    public void setState(String state) {
        _state = state;
    }

    public String getZip() {
        return _zip;
    }

    public void setZip(String zip) {
        _zip = zip;
    }

    public Boolean getAllowTies() {
        return _allowTies;
    }

    public void setAllowTies(Boolean allowTies) {
        _allowTies = allowTies;
    }

    public TieBreakingStrategy getTieBreakingStrategy() {
        return _tieBreakingStrategy;
    }

    public void setTieBreakingStrategy(TieBreakingStrategy tieBreakingStrategy) {
        _tieBreakingStrategy = tieBreakingStrategy;
    }

    public Boolean getCensus() {
        return _census;
    }

    public void setCensus(Boolean census) {
        _census = census;
    }

    public Boolean getCurrentCensusYearOnly() {
        return _currentCensusYearOnly;
    }

    public void setCurrentCensusYearOnly(Boolean currentCensusYearOnly) {
        _currentCensusYearOnly = currentCensusYearOnly;
    }

    public Boolean getNotStore() {
        return _notStore;
    }

    public void setNotStore(Boolean notStore) {
        _notStore = notStore;
    }

    public Boolean getGeometry() {
        return _geometry;
    }

    public void setGeometry(Boolean geometry) {
        _geometry = geometry;
    }

    public String getConfidenceLevels() {
        return _confidenceLevels;
    }

    public void setConfidenceLevels(String confidenceLevels) {
        _confidenceLevels = confidenceLevels;
    }

    public String getMinScore() {
        return _minScore;
    }

    public void setMinScore(String minScore) {
        _minScore = minScore;
    }

    public Boolean getExhaustiveSearch() {
        return _exhaustiveSearch;
    }

    public void setExhaustiveSearch(Boolean exhaustiveSearch) {
        _exhaustiveSearch = exhaustiveSearch;
    }

    public Boolean getAliasTable() {
        return _aliasTable;
    }

    public void setAliasTable(Boolean aliasTable) {
        _aliasTable = aliasTable;
    }

    public Boolean getAttributeRelaxation() {
        return _attributeRelaxation;
    }

    public void setAttributeRelaxation(Boolean attributeRelaxation) {
        this._attributeRelaxation = attributeRelaxation;
    }

    public String getRelaxedAttributes() {
        return _relaxedAttributes;
    }

    public void setRelaxedAttributes(String relaxedAttributes) {
        this._relaxedAttributes = relaxedAttributes;
    }

    public Boolean getSubstringMatching() {
        return _substringMatching;
    }

    public void setSubstringMatching(Boolean substringMatching) {
        this._substringMatching = substringMatching;
    }

    public Boolean getSoundex() {
        return _soundex;
    }

    public void setSoundex(Boolean soundex) {
        this._soundex = soundex;
    }

    public String getSoundexableAttributes() {
        return _soundexableAttributes;
    }

    public void setSoundexableAttributes(String soundexableAttributes) {
        this._soundexableAttributes = soundexableAttributes;
    }

    public FeatureMatchingHierarchy getHierarchy() {
        return _hierarchy;
    }

    public void setHierarchy(FeatureMatchingHierarchy hierarchy) {
        this._hierarchy = hierarchy;
    }

    public String getReferenceDataSources() {
        return _referenceDataSources;
    }

    public void setReferenceDataSources(String referenceDataSources) {
        this._referenceDataSources = referenceDataSources;
    }

    /**
     * Convert to a map of parameters for the API call
     */
    Map<String, String> toQueryParams() {
        Map<String, String> params = new HashMap<>();

        // may want to turn this on for debugging
        //        params.put("includeHeader", "true");

        // address information
        if (getStreetAddress() != null)
            params.put("streetAddress", getStreetAddress());
        if (getCity() != null)
            params.put("city", getCity());
        if (getState() != null)
            params.put("state", getState());
        if (getZip() != null)
            params.put("zip", getZip());

        // tie settings
        if (getAllowTies() != null)
            params.put("allowTies", getAllowTies() ? "true" : "false");
        if (Boolean.TRUE.equals(getAllowTies()) && getTieBreakingStrategy() != null)
            params.put("tieBreakingStrategy", TieBreakingStrategy.FLIP_A_COIN.equals(getTieBreakingStrategy()) ? "flipACoin" : "revertToHierarchy");

        // census settings
        if (Boolean.TRUE.equals(getCensus())) {
            params.put("census", "true");
            if (Boolean.TRUE.equals(getCurrentCensusYearOnly()))
                params.put("censusYear", Integer.toString(CURRENT_CENSUS_YEAR));
            else
                params.put("censusYear", "allAvailable");   // default to allAvailable
        }

        if (getGeometry() != null)
            params.put("geom", getGeometry() ? "true" : "false");       // use the short name "geom" here because the full name "geometry" doesn't work
        if (getNotStore() != null)
            params.put("notStore", getNotStore() ? "true" : "false");

        if (getConfidenceLevels() != null)
            params.put("confidenceLevels", getConfidenceLevels());
        if (getMinScore() != null)
            params.put("minScore", getMinScore());
        if (getExhaustiveSearch() != null)
            params.put("exhaustiveSearch", getExhaustiveSearch() ? "true" : "false");
        if (getAliasTable() != null)
            params.put("aliasTables", getAliasTable() ? "true" : "false");

        if (getAttributeRelaxation() != null && !getAttributeRelaxation())
            params.put("attributeRelaxation", "false");
        else if (getRelaxedAttributes() != null) {
            params.put("attributeRelaxation", "true");
            params.put("relaxedAttributes", getRelaxedAttributes());
        }
        if (getSubstringMatching() != null)
            params.put("substringMatching", getSubstringMatching() ? "true" : "false");
        if (getSoundex() != null && !getSoundex())
            params.put("soundex", "false");
        else if (getSoundexableAttributes() != null) {
            params.put("soundex", "true");
            params.put("soundexableAttributes", getSoundexableAttributes());
        }
        if (getHierarchy() != null)
            params.put("hierarchy", FeatureMatchingHierarchy.UNCERTAINTY_BASED.equals(getHierarchy()) ? "uncertaintyBased" : "featureMatchingSelectionMethod");
        if (getReferenceDataSources() != null)
            params.put("referenceDataSources", getReferenceDataSources());
        return params;
    }
}
