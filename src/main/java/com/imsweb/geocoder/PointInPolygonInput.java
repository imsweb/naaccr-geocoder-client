/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.util.HashMap;
import java.util.Map;

public class PointInPolygonInput {

    private Integer _censusYear;
    private Double _lat;
    private Double _lon;
    private String _state;
    private Boolean _notStore;

    public Integer getCensusYear() {
        return _censusYear;
    }

    public void setCensusYear(Integer censusYear) {
        _censusYear = censusYear;
    }

    public Double getLat() {
        return _lat;
    }

    public void setLat(Double lat) {
        _lat = lat;
    }

    public Double getLon() {
        return _lon;
    }

    public void setLon(Double lon) {
        _lon = lon;
    }

    public String getState() {
        return _state;
    }

    public void setState(String state) {
        _state = state;
    }

    public Boolean getNotStore() {
        return _notStore;
    }

    public void setNotStore(Boolean notStore) {
        _notStore = notStore;
    }

    Map<String, String> toQueryParams() {
        Map<String, String> params = new HashMap<>();
        params.put("censusYear", getCensusYear().toString());
        params.put("lat", getLat().toString());
        params.put("lon", getLon().toString());
        if (getState() != null)
            params.put("s", getState());
        if (getNotStore() != null)
        params.put("notStore", getNotStore() ? "true" : "false");

        return params;
    }
}
