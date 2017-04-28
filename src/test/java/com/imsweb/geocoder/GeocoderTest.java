/*
 * Copyright (C) 2014 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.imsweb.geocoder.exception.BadRequestException;
import com.imsweb.geocoder.exception.NotAuthorizedException;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertEquals;

public class GeocoderTest {

    @Test(expected = NotAuthorizedException.class)
    public void testMissingApiKey() throws IOException {
        new Geocoder.Builder().apiKey("").connect().geocode(new GeocodeInput());
    }

    @Test(expected = BadRequestException.class)
    public void testBadApiKey() throws IOException {
        new Geocoder.Builder().apiKey("BAD KEY").connect().geocode(new GeocodeInput());
    }

    @Test
    public void testCallWithoutCensus() throws IOException {
        GeocodeInput input = new GeocodeInput();

        input.setStreetAddress("9355 Burton Way");
        input.setCity("Beverly Hills");
        input.setState("CA");
        input.setZip("90210");
        input.setNotStore(Boolean.FALSE);

        List<GeocodeOutput> results = new Geocoder.Builder().connect().geocode(input);
        assertEquals(1, results.size());
        assertEquals(0, results.get(0).getCensusResults().size());

        GeocodeOutput output = results.get(0);
        assertThat(output.getTransactionId(), is(notNullValue()));
        assertThat(output.getTransactionId(), matchesPattern("[0-9a-f\\-]+"));
        assertThat(output.getApiVersion(), is("4.1"));
        assertThat(output.getStatusCode(), is(200));
        assertThat(output.getLatitude(), is(34.0726));
        assertThat(output.getLongitude(), is(-118.398));
        assertThat(output.getNaaccrGisCoordinateQualityCode(), is("00"));
        assertThat(output.getNaaccrGisCoordinateQualityName(), is("AddressPoint"));
        assertThat(output.getMatchScore(), is(100.0));
        assertThat(output.getMatchType(), is("Exact"));
        assertThat(output.getFeatureMatchType(), is("Success"));
        assertThat(output.getFeatureMatchCount(), is(1));
        assertThat(output.getMatchingGeographyType(), is("BuildingCentroid"));
        assertThat(output.getRegionSize(), is(0.0));
        assertThat(output.getRegionSizeUnit(), is("Meters"));
        assertThat(output.getMatchedLocationType(), is("LOCATION_TYPE_STREET_ADDRESS"));
        assertThat(output.getTimeTaken(), is(notNullValue()));
        assertThat(output.getNaaccrCensusTractCertaintyCode(), is(nullValue()));
        assertThat(output.getNaaccrCensusTractCertaintyName(), is(nullValue()));
    }

    @Test
    public void testCallWithCensus() throws IOException {
        GeocodeInput input = new GeocodeInput();

        input.setStreetAddress("9355 Burton Way");
        input.setCity("Beverly Hills");
        input.setState("CA");
        input.setZip("90210");
        input.setCensus(Boolean.TRUE);
        input.setCensusYear(Arrays.asList(1990, 2000, 2010));

        List<GeocodeOutput> results = new Geocoder.Builder().connect().geocode(input);
        assertEquals(1, results.size());

        GeocodeOutput output = results.get(0);
        assertThat(output.getTransactionId(), is(notNullValue()));
        assertThat(output.getTransactionId(), matchesPattern("[0-9a-f\\-]+"));
        assertThat(output.getApiVersion(), is("4.1"));
        assertThat(output.getStatusCode(), is(200));
        assertThat(output.getLatitude(), is(34.0726));
        assertThat(output.getLongitude(), is(-118.398));
        assertThat(output.getNaaccrGisCoordinateQualityCode(), is("00"));
        assertThat(output.getNaaccrGisCoordinateQualityName(), is("AddressPoint"));
        assertThat(output.getMatchScore(), is(100.0));
        assertThat(output.getMatchType(), is("Exact"));
        assertThat(output.getFeatureMatchType(), is("Success"));
        assertThat(output.getFeatureMatchCount(), is(1));
        assertThat(output.getMatchingGeographyType(), is("BuildingCentroid"));
        assertThat(output.getRegionSize(), is(0.0));
        assertThat(output.getRegionSizeUnit(), is("Meters"));
        assertThat(output.getMatchedLocationType(), is("LOCATION_TYPE_STREET_ADDRESS"));
        assertThat(output.getTimeTaken(), is(notNullValue()));

        assertThat(output.getNaaccrCensusTractCertaintyCode(), is("1"));
        assertThat(output.getNaaccrCensusTractCertaintyName(), is("ResidenceStreetAddress"));

        assertThat(output.getCensusResults().keySet(), containsInAnyOrder(1990, 2000, 2010));

        Census census = output.getCensusResults().get(2010);

        assertThat(census.getTract(), is("7008.01"));
        assertThat(census.getCountyFips(), is("037"));
        assertThat(census.getStateFips(), is("06"));

        assertThat(census.getBlock(), is("1023"));
        assertThat(census.getBlockGroup(), is("1"));
        assertThat(census.getCbsaFips(), is("31100"));
        assertThat(census.getCbsaMicro(), is("0"));
        assertThat(census.getMcdFips(), is("91750"));
        assertThat(census.getMetDivFips(), is("31084"));
        assertThat(census.getMsaFips(), is("4472"));
        assertThat(census.getPlaceFips(), is("44000"));
    }

    @Test
    public void testCallWithMultipleResults() throws IOException {
        GeocodeInput input = new GeocodeInput();

        input.setStreetAddress("123 main street");
        input.setCity("los angeles");
        input.setState("ca");
        input.setZip("90007");
        input.setAllowTies(Boolean.TRUE);
        input.setTieBreakingStrategy(GeocodeInput.TieBreakingStrategy.REVERT_TO_HIERARCHY);

        List<GeocodeOutput> results = new Geocoder.Builder().connect().geocode(input);
        assertEquals(5, results.size());

        // TODO test all the values
    }
}
