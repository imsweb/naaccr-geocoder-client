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
        assertThat(output.getApiVersion(), is("4.2"));
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

        assertThat(output.getMatchAddress().getNumber(), is("9355"));
        assertThat(output.getMatchAddress().getName(), is("BURTON"));
        assertThat(output.getMatchAddress().getSuffix(), is("WAY"));
        assertThat(output.getMatchAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getMatchAddress().getState(), is("CA"));
        assertThat(output.getMatchAddress().getZip(), is("90210"));

        assertThat(output.getParsedAddress().getNumber(), is("9355"));
        assertThat(output.getParsedAddress().getName(), is("BURTON"));
        assertThat(output.getParsedAddress().getSuffix(), is("WAY"));
        assertThat(output.getParsedAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getParsedAddress().getState(), is("CA"));
        assertThat(output.getParsedAddress().getZip(), is("90210"));

        assertThat(output.getFeatureAddress().getNumber(), is("9355"));
        assertThat(output.getFeatureAddress().getName(), is("Burton"));
        assertThat(output.getFeatureAddress().getSuffix(), is("Way"));
        assertThat(output.getFeatureAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getFeatureAddress().getState(), is("CA"));
        assertThat(output.getFeatureAddress().getZip(), is("90210"));

        assertThat(output.getfSource(), is("SOURCE_NAVTEQ_ADDRESSPOINTS_2016"));
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
        assertThat(output.getApiVersion(), is("4.2"));
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

        assertThat(output.getMatchAddress().getNumber(), is("9355"));
        assertThat(output.getMatchAddress().getName(), is("BURTON"));
        assertThat(output.getMatchAddress().getSuffix(), is("WAY"));
        assertThat(output.getMatchAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getMatchAddress().getState(), is("CA"));
        assertThat(output.getMatchAddress().getZip(), is("90210"));

        assertThat(output.getParsedAddress().getNumber(), is("9355"));
        assertThat(output.getParsedAddress().getName(), is("BURTON"));
        assertThat(output.getParsedAddress().getSuffix(), is("WAY"));
        assertThat(output.getParsedAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getParsedAddress().getState(), is("CA"));
        assertThat(output.getParsedAddress().getZip(), is("90210"));

        assertThat(output.getFeatureAddress().getNumber(), is("9355"));
        assertThat(output.getFeatureAddress().getName(), is("Burton"));
        assertThat(output.getFeatureAddress().getSuffix(), is("Way"));
        assertThat(output.getFeatureAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getFeatureAddress().getState(), is("CA"));
        assertThat(output.getFeatureAddress().getZip(), is("90210"));

        assertThat(output.getfSource(), is("SOURCE_NAVTEQ_ADDRESSPOINTS_2016"));

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
    public void testPoBoxWithCensus() throws IOException {
        GeocodeInput input = new GeocodeInput();

        input.setStreetAddress("PO Box 221");
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
        assertThat(output.getApiVersion(), is("4.2"));
        assertThat(output.getStatusCode(), is(200));
        assertThat(output.getLatitude(), is(34.096629));
        assertThat(output.getLongitude(), is(-118.412426));
        assertThat(output.getNaaccrGisCoordinateQualityCode(), is("10"));
        assertThat(output.getNaaccrGisCoordinateQualityName(), is("POBoxZIPCentroid"));
        assertThat(output.getMatchScore(), is(100.0));
        assertThat(output.getMatchType(), is("Exact"));
        assertThat(output.getFeatureMatchType(), is("Success"));
        assertThat(output.getFeatureMatchCount(), is(1));
        assertThat(output.getMatchingGeographyType(), is("USPSZip"));
        assertThat(output.getRegionSize(), is(0.0));
        assertThat(output.getRegionSizeUnit(), is("Meters"));
        assertThat(output.getMatchedLocationType(), is("LOCATION_TYPE_POST_OFFICE_BOX"));
        assertThat(output.getTimeTaken(), is(notNullValue()));

        assertThat(output.getMatchAddress().getNumber(), is(nullValue()));
        assertThat(output.getMatchAddress().getName(), is(nullValue()));
        assertThat(output.getMatchAddress().getSuffix(), is(nullValue()));
        assertThat(output.getMatchAddress().getPoBoxType(), is("PO BOX"));
        assertThat(output.getMatchAddress().getPoBoxNumber(), is(221));
        assertThat(output.getMatchAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getMatchAddress().getState(), is("CA"));
        assertThat(output.getMatchAddress().getZip(), is("90210"));

        assertThat(output.getParsedAddress().getPoBoxType(), is("PO BOX"));
        assertThat(output.getParsedAddress().getPoBoxNumber(), is(221));
        assertThat(output.getParsedAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getParsedAddress().getState(), is("CA"));
        assertThat(output.getParsedAddress().getZip(), is("90210"));

        assertThat(output.getFeatureAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getFeatureAddress().getState(), is("CA"));
        assertThat(output.getFeatureAddress().getZip(), is("90210"));

        assertThat(output.getfSource(), is("SOURCE_NAME_ZIPCODEDOWNLOAD_ZIPS2013"));

        assertThat(output.getNaaccrCensusTractCertaintyCode(), is("5"));
        assertThat(output.getNaaccrCensusTractCertaintyName(), is("POBoxZIP"));

        assertThat(output.getCensusResults().keySet(), containsInAnyOrder(1990, 2000, 2010));

        Census census = output.getCensusResults().get(2010);

        assertThat(census.getTract(), is("2611.01"));
        assertThat(census.getCountyFips(), is("037"));
        assertThat(census.getStateFips(), is("06"));

        assertThat(census.getBlock(), is("2004"));
        assertThat(census.getBlockGroup(), is("2"));
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
        GeocodeOutput output = results.get(0);
        assertThat(output.getTransactionId(), is(notNullValue()));
        assertThat(output.getTransactionId(), matchesPattern("[0-9a-f\\-]+"));
        assertThat(output.getApiVersion(), is("4.2"));
        assertThat(output.getStatusCode(), is(200));
        assertThat(output.getLatitude(), is(34.0524962825713));
        assertThat(output.getLongitude(), is(-118.243276120669));
        assertThat(output.getNaaccrGisCoordinateQualityCode(), is("03"));
        assertThat(output.getNaaccrGisCoordinateQualityName(), is("StreetSegmentInterpolation"));
        assertThat(output.getMatchScore(), is(89.9408284023669));
        assertThat(output.getMatchType(), is("Relaxed"));
        assertThat(output.getFeatureMatchType(), is("Ambiguous"));
        assertThat(output.getFeatureMatchCount(), is(2));
        assertThat(output.getMatchingGeographyType(), is("StreetSegment"));
        assertThat(output.getRegionSize(), is(1462.70234987116));
        assertThat(output.getRegionSizeUnit(), is("Meters"));
        assertThat(output.getMatchedLocationType(), is("LOCATION_TYPE_STREET_ADDRESS"));
        assertThat(output.getTimeTaken(), is(notNullValue()));

        assertThat(output.getMatchAddress().getNumber(), is("123"));
        assertThat(output.getMatchAddress().getName(), is("MAIN"));
        assertThat(output.getMatchAddress().getSuffix(), is("ST"));
        assertThat(output.getMatchAddress().getCity(), is("LOS ANGELES"));
        assertThat(output.getMatchAddress().getState(), is("CA"));
        assertThat(output.getMatchAddress().getZip(), is("90007"));

        output = results.get(1);
        assertThat(output.getTransactionId(), is(notNullValue()));
        assertThat(output.getTransactionId(), matchesPattern("[0-9a-f\\-]+"));
        assertThat(output.getApiVersion(), is("4.2"));
        assertThat(output.getStatusCode(), is(200));
        assertThat(output.getLatitude(), is(34.0513205964793));
        assertThat(output.getLongitude(), is(-118.244366353744));
        assertThat(output.getNaaccrGisCoordinateQualityCode(), is("03"));
        assertThat(output.getNaaccrGisCoordinateQualityName(), is("StreetSegmentInterpolation"));
        assertThat(output.getMatchScore(), is(89.9408284023669));
        assertThat(output.getMatchType(), is("Relaxed"));
        assertThat(output.getFeatureMatchType(), is("Ambiguous"));
        assertThat(output.getFeatureMatchCount(), is(2));
        assertThat(output.getMatchingGeographyType(), is("StreetSegment"));
        assertThat(output.getRegionSize(), is(3789.09342876496));
        assertThat(output.getRegionSizeUnit(), is("Meters"));
        assertThat(output.getMatchedLocationType(), is("LOCATION_TYPE_STREET_ADDRESS"));
        assertThat(output.getTimeTaken(), is(notNullValue()));

        assertThat(output.getMatchAddress().getNumber(), is("123"));
        assertThat(output.getMatchAddress().getName(), is("MAIN"));
        assertThat(output.getMatchAddress().getSuffix(), is("ST"));
        assertThat(output.getMatchAddress().getCity(), is("LOS ANGELES"));
        assertThat(output.getMatchAddress().getState(), is("CA"));
        assertThat(output.getMatchAddress().getZip(), is("90007"));

        output = results.get(2);
        assertThat(output.getTransactionId(), is(notNullValue()));
        assertThat(output.getTransactionId(), matchesPattern("[0-9a-f\\-]+"));
        assertThat(output.getApiVersion(), is("4.2"));
        assertThat(output.getStatusCode(), is(200));
        assertThat(output.getLatitude(), is(34.0524982197878));
        assertThat(output.getLongitude(), is(-118.243245915375));
        assertThat(output.getNaaccrGisCoordinateQualityCode(), is("03"));
        assertThat(output.getNaaccrGisCoordinateQualityName(), is("StreetSegmentInterpolation"));
        assertThat(output.getMatchScore(), is(89.9408284023669));
        assertThat(output.getMatchType(), is("Relaxed"));
        assertThat(output.getFeatureMatchType(), is("Ambiguous"));
        assertThat(output.getFeatureMatchCount(), is(2));
        assertThat(output.getMatchingGeographyType(), is("StreetSegment"));
        assertThat(output.getRegionSize(), is(5662.29067549086));
        assertThat(output.getRegionSizeUnit(), is("Meters"));
        assertThat(output.getMatchedLocationType(), is("LOCATION_TYPE_STREET_ADDRESS"));
        assertThat(output.getTimeTaken(), is(notNullValue()));

        assertThat(output.getMatchAddress().getNumber(), is("123"));
        assertThat(output.getMatchAddress().getName(), is("MAIN"));
        assertThat(output.getMatchAddress().getSuffix(), is("ST"));
        assertThat(output.getMatchAddress().getCity(), is("LOS ANGELES"));
        assertThat(output.getMatchAddress().getState(), is("CA"));
        assertThat(output.getMatchAddress().getZip(), is("90007"));


        output = results.get(3);
        assertThat(output.getTransactionId(), is(notNullValue()));
        assertThat(output.getTransactionId(), matchesPattern("[0-9a-f\\-]+"));
        assertThat(output.getApiVersion(), is("4.2"));
        assertThat(output.getStatusCode(), is(200));
        assertThat(output.getLatitude(), is(34.0520118400004));
        assertThat(output.getLongitude(), is(-118.243701713254));
        assertThat(output.getNaaccrGisCoordinateQualityCode(), is("03"));
        assertThat(output.getNaaccrGisCoordinateQualityName(), is("StreetSegmentInterpolation"));
        assertThat(output.getMatchScore(), is(89.9408284023669));
        assertThat(output.getMatchType(), is("Relaxed"));
        assertThat(output.getFeatureMatchType(), is("Ambiguous"));
        assertThat(output.getFeatureMatchCount(), is(2));
        assertThat(output.getMatchingGeographyType(), is("StreetSegment"));
        assertThat(output.getRegionSize(), is(3756.62068773806));
        assertThat(output.getRegionSizeUnit(), is("Meters"));
        assertThat(output.getMatchedLocationType(), is("LOCATION_TYPE_STREET_ADDRESS"));
        assertThat(output.getTimeTaken(), is(notNullValue()));

        assertThat(output.getMatchAddress().getNumber(), is("123"));
        assertThat(output.getMatchAddress().getName(), is("MAIN"));
        assertThat(output.getMatchAddress().getSuffix(), is("ST"));
        assertThat(output.getMatchAddress().getCity(), is("LOS ANGELES"));
        assertThat(output.getMatchAddress().getState(), is("CA"));
        assertThat(output.getMatchAddress().getZip(), is("90007"));

        output = results.get(4);
        assertThat(output.getTransactionId(), is(notNullValue()));
        assertThat(output.getTransactionId(), matchesPattern("[0-9a-f\\-]+"));
        assertThat(output.getApiVersion(), is("4.2"));
        assertThat(output.getStatusCode(), is(200));
        assertThat(output.getLatitude(), is(34.026525));
        assertThat(output.getLongitude(), is(-118.282408));
        assertThat(output.getNaaccrGisCoordinateQualityCode(), is("09"));
        assertThat(output.getNaaccrGisCoordinateQualityName(), is("AddressZIPCentroid"));
        assertThat(output.getMatchScore(), is(100.0));
        assertThat(output.getMatchType(), is("Exact"));
        assertThat(output.getFeatureMatchType(), is("Success"));
        assertThat(output.getFeatureMatchCount(), is(1));
        assertThat(output.getMatchingGeographyType(), is("USPSZip"));
        assertThat(output.getRegionSize(), is(0.0));
        assertThat(output.getRegionSizeUnit(), is("Meters"));
        assertThat(output.getMatchedLocationType(), is("LOCATION_TYPE_STREET_ADDRESS"));
        assertThat(output.getTimeTaken(), is(notNullValue()));

        assertThat(output.getMatchAddress().getNumber(), is("123"));
        assertThat(output.getMatchAddress().getName(), is("MAIN"));
        assertThat(output.getMatchAddress().getSuffix(), is("ST"));
        assertThat(output.getMatchAddress().getCity(), is("LOS ANGELES"));
        assertThat(output.getMatchAddress().getState(), is("CA"));
        assertThat(output.getMatchAddress().getZip(), is("90007"));
    }
}
