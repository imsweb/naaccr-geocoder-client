/*
 * Copyright (C) 2016 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GeocodeInput {

    public enum TieBreakingStrategy {
        FLIP_A_COIN,         // choose and return one of the ties at random
        REVERT_TO_HIERARCHY  // fail on a tie and match to the next level of the geographic hierarchy - parcel, then street, then ZIP, then city, etc.
    }

    private String _streetAddress;
    private String _city;
    private String _state;
    private String _zip;
    private Boolean _allowTies;
    private TieBreakingStrategy _tieBreakingStrategy;
    private Boolean _census;
    private List<Integer> _censusYear;
    private Boolean _geom;
    private Boolean _notStore;
    private String _confidenceLevels;
    private String _minScore;
    private Boolean _shouldDoExhaustiveSearch;

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

    public List<Integer> getCensusYear() {
        return _censusYear;
    }

    public void setCensusYear(List<Integer> censusYear) {
        _censusYear = censusYear;
    }

    public Boolean getNotStore() {
        return _notStore;
    }

    public void setNotStore(Boolean notStore) {
        _notStore = notStore;
    }

    public Boolean getGeom() {
        return _geom;
    }

    public void setGeom(Boolean geom) {
        _geom = geom;
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

    public Boolean getShouldDoExhaustiveSearch() {
        return _shouldDoExhaustiveSearch;
    }

    public void setShouldDoExhaustiveSearch(Boolean shouldDoExhaustiveSearch) {
        _shouldDoExhaustiveSearch = shouldDoExhaustiveSearch;
    }

    /**
     * Convert to a map of parameters for the API call
     */
    Map<String, String> toQueryParams() {
        Map<String, String> params = new HashMap<>();

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
            if (getCensusYear() != null && getCensusYear().size() > 0)
                params.put("censusYear", getCensusYear().stream().map(Object::toString).collect(Collectors.joining("|")));
            else
                params.put("censusYear", "allAvailable");
        }

        if (getGeom() != null)
            params.put("geom", getGeom() ? "true" : "false");
        if (getNotStore() != null)
            params.put("notStore", getNotStore() ? "true" : "false");

        if (getConfidenceLevels() != null)
            params.put("ConfidenceLevels", getConfidenceLevels());
        if (getMinScore() != null)
            params.put("MinScore", getMinScore());
        if (getShouldDoExhaustiveSearch() != null)
            params.put("ShouldDoExhaustiveSearch", getShouldDoExhaustiveSearch() ? "true" : "false");

        return params;
    }
}
