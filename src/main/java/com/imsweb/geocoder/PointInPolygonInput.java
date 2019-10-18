/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.util.HashMap;
import java.util.Map;

public class PointInPolygonInput {

    private String _censusYear;
    private String _lat;
    private String _lon;
    private String _state;
    private Boolean _notStore;

    public String getCensusYear() {
        return _censusYear;
    }

    public void setCensusYear(String censusYear) {
        _censusYear = censusYear;
    }

    public String getLat() {
        return _lat;
    }

    public void setLat(String lat) {
        _lat = lat;
    }

    public String getLon() {
        return _lon;
    }

    public void setLon(String lon) {
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
        params.put("censusYear", getCensusYear());
        params.put("lat", getLat());
        params.put("lon", getLon());
        if (getState() != null)
            params.put("s", getState());
        if (getNotStore() != null)
            params.put("notStore", getNotStore() ? "true" : "false");

        return params;
    }
}
