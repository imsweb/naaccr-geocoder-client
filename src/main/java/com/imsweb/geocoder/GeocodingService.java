/*
 * Copyright (C) 2015 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface GeocodingService {

    /**
     * Perform a geocoding call; had a lot of issues with parsing the XML version so this method returns the raw
     * response body and we parse the TSV format
     *
     * @param searchParams A Map of query parameters.
     * @return a GeocodingResult
     */
    @GET("GeocoderWebServiceHttpNonParsedAdvanced_V04_05.aspx")
    Call<ResponseBody> geocode(@QueryMap Map<String, String> searchParams);

    @GET("CensusIntersectionWebServiceHttp_V03_01.aspx")
    Call<ResponseBody> pointInPolygon(@QueryMap Map<String, String> searchParams);

}
