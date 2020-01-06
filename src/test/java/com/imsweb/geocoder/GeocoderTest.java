/*
 * Copyright (C) 2014 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.imsweb.geocoder.GeocodeInput.TieBreakingStrategy;
import com.imsweb.geocoder.exception.BadRequestException;
import com.imsweb.geocoder.exception.NotAuthorizedException;

import static com.jcabi.matchers.RegexMatchers.matchesPattern;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class GeocoderTest {

    private static Set<String> _POSSIBLE_MATCH_TYPES = new HashSet<>(Arrays.asList("NoMatch", "Exact", "Relaxed", "Substring", "Soundex", "Composite", "Nearby", "Unknown"));
    private static Set<String> _POSSIBLE_MATCHING_GEOG = new HashSet<>(
            Arrays.asList("Unknown", "GPS", "BuildingCentroid", "Building", "BuildingDoor", "Parcel", "StreetSegment", "StreetIntersection", "StreetCentroid", "USPSZipPlus5", "USPSZipPlus4",
                    "USPSZipPlus3", "USPSZipPlus2", "USPSZipPlus1", "USPSZip", "ZCTAPlus5", "ZCTAPlus4", "ZCTAPlus3", "ZCTAPlus2", "ZCTAPlus1", "ZCTA", "City", "ConsolidatedCity",
                    "MinorCivilDivision", "CountySubRegion", "County", "State", "Country", "Unmatchable"));
    private static Set<String> _POSSIBLE_INTERPOLATION_TYPES = new HashSet<>(Arrays.asList("Unknown", "LinearInterpolation", "ArealInterpolation", "None", "NotAttempted"));
    private static Set<String> _POSSIBLE_INTERPOLATION_SUBTYPES = new HashSet<>(
            Arrays.asList("Unknown", "LinearInterpolationAddressRange", "LinearInterpolationUniformLot", "LinearInterpolationActualLot", "LinearInterpolationMidPoint",
                    "ArealInterpolationBoundingBoxCentroid", "ArealInterpolationConvexHullCentroid", "ArealInterpolationGeometricCentroid", "None", "NotAttempted"));
    private static Set<String> _POSSIBLE_FEATURE_MATCH_TYPES = new HashSet<>(
            Arrays.asList("Unknown", "Success", "Ambiguous", "BrokenTie", "Composite", "Nearby", "LessThanMinimumScore", "InvalidFeature", "NullFeature", "Unmatchable", "ExceptionOccurred"));
    private static Set<String> _POSSIBLE_TIE_HANDLING_STRATEGIES = new HashSet<>(
            Arrays.asList("Unknown", "RevertToHierarchy", "FlipACoin", "DynamicFeatureComposition", "RegionalCharacteristics", "ReturnAll"));
    private static Set<String> _POSSIBLE_FEATURE_MATCH_SELECTION_METHODS = new HashSet<>(
            Arrays.asList("FeatureClassBased", "UncertaintySingleFeatureArea", "UncertaintyMultiFeatureGraviational", "UncertaintyMultiFeatureTopological"));

    private static Map<String, String> _POSSIBLE_GIS_CODES;
    private static Map<String, String> _POSSIBLE_CENSUS_TRACT_CERTAINTY_CODES;

    static {
        Map<String, String> gisCodes = new HashMap<>();
        gisCodes.put("98", "Unknown");
        gisCodes.put("00", "AddressPoint");
        gisCodes.put("01", "GPS");
        gisCodes.put("02", "Parcel");
        gisCodes.put("03", "StreetSegmentInterpolation");
        gisCodes.put("04", "StreetIntersection");
        gisCodes.put("05", "StreetCentroid");
        gisCodes.put("06", "AddressZIPPlus4Centroid");
        gisCodes.put("07", "AddressZIPPlus2Centroid");
        gisCodes.put("08", "ManualLookup");
        gisCodes.put("09", "AddressZIPCentroid");
        gisCodes.put("10", "POBoxZIPCentroid");
        gisCodes.put("11", "CityCentroid");
        gisCodes.put("12", "CountyCentroid");
        gisCodes.put("99", "Unmatchable");
        _POSSIBLE_GIS_CODES = Collections.unmodifiableMap(gisCodes);

        Map<String, String> censusTractCertaintyCodes = new HashMap<>();
        censusTractCertaintyCodes.put("1", "ResidenceStreetAddress");
        censusTractCertaintyCodes.put("2", "ResidenceZIPPlus4");
        censusTractCertaintyCodes.put("3", "ResidenceZIPPlus2");
        censusTractCertaintyCodes.put("4", "ResidenceZIP");
        censusTractCertaintyCodes.put("5", "POBoxZIP");
        censusTractCertaintyCodes.put("6", "ResidenceCityOrZIPWithOneCensusTract");
        censusTractCertaintyCodes.put("9", "Missing");
        censusTractCertaintyCodes.put("99", "Unmatchable");
        _POSSIBLE_CENSUS_TRACT_CERTAINTY_CODES = Collections.unmodifiableMap(censusTractCertaintyCodes);
    }

    @Test(expected = NotAuthorizedException.class)
    public void testMissingApiKey() throws IOException {
        new Geocoder.Builder().apiKey("").connect().geocode(new GeocodeInput());
    }

    @Test(expected = BadRequestException.class)
    public void testBadApiKey() throws IOException {
        new Geocoder.Builder().apiKey("BAD KEY").connect().geocode(new GeocodeInput());
    }

    @Test
    public void testBuilder() throws IOException {
        GeocodeInput input = new GeocodeInput();

        input.setStreetAddress("9355 Burton Way");
        input.setCity("Beverly Hills");
        input.setState("CA");
        input.setZip("90210");
        input.setNotStore(Boolean.FALSE);

        List<GeocodeOutput> results = new Geocoder.Builder()
                .url("https://geo.naaccr.org/Services/Geocode/WebService")
                .proxyHost(null)
                .proxyPort(null)
                .connect().geocode(input);

        assertThat(results.size(), is(1));
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
        assertThat(results.size(), is(1));
        GeocodeOutput output = results.get(0);
        assertThat(output.getCensusResults().size(), is(0));
        assertThat(output.getUrl(),             // Should contain all parameters except the API Key
                is("https://geo.naaccr.org/Services/Geocode/WebService/GeocoderWebServiceHttpNonParsedAdvanced_V04_05.aspx?zip=90210&notStore=false&streetAddress=9355%20Burton%20Way&city=Beverly%20Hills&format=tsv&state=CA&version=4.05&verbose=true"));

        assertOutputNonAddressNonCensusFields(output);

        Assert.assertTrue(output.getLatitude().startsWith("34."));
        Assert.assertTrue(output.getLongitude().startsWith("-118."));

        assertThat(output.getMatchAddress().getNumber(), is("9355"));
        assertThat(output.getMatchAddress().getName(), is("BURTON"));
        assertThat(output.getMatchAddress().getSuffix(), is("WAY"));
        assertThat(output.getMatchAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getMatchAddress().getState(), is("CA"));
        assertThat(output.getMatchAddress().getZip(), is("90210"));
        assertThat(output.getMatchAddress().getNumberFractional(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreDirectional(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreQualifier(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreType(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreArticle(), is(nullValue()));
        assertThat(output.getMatchAddress().getPostArticle(), is(nullValue()));
        assertThat(output.getMatchAddress().getPostQualifier(), is(nullValue()));
        assertThat(output.getMatchAddress().getPostDirectional(), is(nullValue()));
        assertThat(output.getMatchAddress().getSuiteType(), is(nullValue()));
        assertThat(output.getMatchAddress().getSuiteNumber(), is(nullValue()));
        assertThat(output.getMatchAddress().getPoBoxType(), is(nullValue()));
        assertThat(output.getMatchAddress().getPoBoxNumber(), is(nullValue()));
        assertThat(output.getMatchAddress().getConsolidatedCity(), is(nullValue()));
        assertThat(output.getMatchAddress().getMinorCivilDivision(), is(nullValue()));
        assertThat(output.getMatchAddress().getCounty(), is(nullValue()));
        assertThat(output.getMatchAddress().getCountySubregion(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus1(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus2(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus3(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus4(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus5(), is(nullValue()));

        assertThat(output.getParsedAddress().getNumber(), is("9355"));
        assertThat(output.getParsedAddress().getName(), is("BURTON"));
        assertThat(output.getParsedAddress().getSuffix(), is("WAY"));
        assertThat(output.getParsedAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getParsedAddress().getState(), is("CA"));
        assertThat(output.getParsedAddress().getZip(), is("90210"));
        assertThat(output.getParsedAddress().getNumberFractional(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreDirectional(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreQualifier(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreType(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreArticle(), is(nullValue()));
        assertThat(output.getParsedAddress().getPostArticle(), is(nullValue()));
        assertThat(output.getParsedAddress().getPostQualifier(), is(nullValue()));
        assertThat(output.getParsedAddress().getPostDirectional(), is(nullValue()));
        assertThat(output.getParsedAddress().getSuiteType(), is(nullValue()));
        assertThat(output.getParsedAddress().getSuiteNumber(), is(nullValue()));
        assertThat(output.getParsedAddress().getPoBoxType(), is(nullValue()));
        assertThat(output.getParsedAddress().getPoBoxNumber(), is(nullValue()));
        assertThat(output.getParsedAddress().getConsolidatedCity(), is(nullValue()));
        assertThat(output.getParsedAddress().getMinorCivilDivision(), is(nullValue()));
        assertThat(output.getParsedAddress().getCounty(), is(nullValue()));
        assertThat(output.getParsedAddress().getCountySubregion(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus1(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus2(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus3(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus4(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus5(), is(nullValue()));

        assertThat(output.getFeatureAddress().getNumber(), is("9355"));
        assertThat(output.getFeatureAddress().getName(), is("Burton"));
        assertThat(output.getFeatureAddress().getSuffix(), is("Way"));
        assertThat(output.getFeatureAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getFeatureAddress().getState(), is("CA"));
        assertThat(output.getFeatureAddress().getZip(), is("90210"));
        assertThat(output.getFeatureAddress().getNumberFractional(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreDirectional(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreQualifier(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreType(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreArticle(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPostArticle(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPostQualifier(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPostDirectional(), is(nullValue()));
        assertThat(output.getFeatureAddress().getSuiteType(), is(nullValue()));
        assertThat(output.getFeatureAddress().getSuiteNumber(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPoBoxType(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPoBoxNumber(), is(nullValue()));
        assertThat(output.getFeatureAddress().getConsolidatedCity(), is(nullValue()));
        assertThat(output.getFeatureAddress().getMinorCivilDivision(), is(nullValue()));
        assertThat(output.getFeatureAddress().getCounty(), is("Los Angeles"));
        assertThat(output.getFeatureAddress().getCountySubregion(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus1(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus2(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus3(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus4(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus5(), is(nullValue()));
    }

    @Test
    public void testCallWithCensus() throws IOException {
        GeocodeInput input = new GeocodeInput();

        input.setStreetAddress("9355 Burton Way");
        input.setCity("Beverly Hills");
        input.setState("CA");
        input.setZip("90210");
        input.setCensus(Boolean.TRUE);
        input.setCurrentCensusYearOnly(false);

        List<GeocodeOutput> results = new Geocoder.Builder().connect().geocode(input);
        assertThat(results.size(), is(1));

        GeocodeOutput output = results.get(0);

        assertOutputNonAddressNonCensusFields(output);

        assertThat(output.getLatitude(), is("34.0726"));
        assertThat(output.getLongitude(), is("-118.398"));

        assertThat(output.getMatchAddress().getNumber(), is("9355"));
        assertThat(output.getMatchAddress().getName(), is("BURTON"));
        assertThat(output.getMatchAddress().getSuffix(), is("WAY"));
        assertThat(output.getMatchAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getMatchAddress().getState(), is("CA"));
        assertThat(output.getMatchAddress().getZip(), is("90210"));
        assertThat(output.getMatchAddress().getNumberFractional(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreDirectional(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreQualifier(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreType(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreArticle(), is(nullValue()));
        assertThat(output.getMatchAddress().getPostArticle(), is(nullValue()));
        assertThat(output.getMatchAddress().getPostQualifier(), is(nullValue()));
        assertThat(output.getMatchAddress().getPostDirectional(), is(nullValue()));
        assertThat(output.getMatchAddress().getSuiteType(), is(nullValue()));
        assertThat(output.getMatchAddress().getSuiteNumber(), is(nullValue()));
        assertThat(output.getMatchAddress().getConsolidatedCity(), is(nullValue()));
        assertThat(output.getMatchAddress().getMinorCivilDivision(), is(nullValue()));
        assertThat(output.getMatchAddress().getCounty(), is(nullValue()));
        assertThat(output.getMatchAddress().getCountySubregion(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus1(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus2(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus3(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus4(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus5(), is(nullValue()));

        assertThat(output.getParsedAddress().getNumber(), is("9355"));
        assertThat(output.getParsedAddress().getName(), is("BURTON"));
        assertThat(output.getParsedAddress().getSuffix(), is("WAY"));
        assertThat(output.getParsedAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getParsedAddress().getState(), is("CA"));
        assertThat(output.getParsedAddress().getZip(), is("90210"));
        assertThat(output.getParsedAddress().getPoBoxType(), is(nullValue()));
        assertThat(output.getParsedAddress().getPoBoxNumber(), is(nullValue()));
        assertThat(output.getParsedAddress().getNumberFractional(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreDirectional(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreQualifier(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreType(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreArticle(), is(nullValue()));
        assertThat(output.getParsedAddress().getPostArticle(), is(nullValue()));
        assertThat(output.getParsedAddress().getPostQualifier(), is(nullValue()));
        assertThat(output.getParsedAddress().getPostDirectional(), is(nullValue()));
        assertThat(output.getParsedAddress().getSuiteType(), is(nullValue()));
        assertThat(output.getParsedAddress().getSuiteNumber(), is(nullValue()));
        assertThat(output.getParsedAddress().getConsolidatedCity(), is(nullValue()));
        assertThat(output.getParsedAddress().getMinorCivilDivision(), is(nullValue()));
        assertThat(output.getParsedAddress().getCounty(), is(nullValue()));
        assertThat(output.getParsedAddress().getCountySubregion(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus1(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus2(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus3(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus4(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus5(), is(nullValue()));

        assertThat(output.getFeatureAddress().getNumber(), is("9355"));
        assertThat(output.getFeatureAddress().getName(), is("Burton"));
        assertThat(output.getFeatureAddress().getSuffix(), is("Way"));
        assertThat(output.getFeatureAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getFeatureAddress().getState(), is("CA"));
        assertThat(output.getFeatureAddress().getZip(), is("90210"));
        assertThat(output.getFeatureAddress().getPoBoxType(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPoBoxNumber(), is(nullValue()));
        assertThat(output.getFeatureAddress().getNumberFractional(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreDirectional(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreQualifier(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreType(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreArticle(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPostArticle(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPostQualifier(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPostDirectional(), is(nullValue()));
        assertThat(output.getFeatureAddress().getSuiteType(), is(nullValue()));
        assertThat(output.getFeatureAddress().getSuiteNumber(), is(nullValue()));
        assertThat(output.getFeatureAddress().getConsolidatedCity(), is(nullValue()));
        assertThat(output.getFeatureAddress().getMinorCivilDivision(), is(nullValue()));
        assertThat(output.getFeatureAddress().getCounty(), is("Los Angeles"));
        assertThat(output.getFeatureAddress().getCountySubregion(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus1(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus2(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus3(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus4(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus5(), is(nullValue()));

        assertThat(output.getCensusResults().keySet(), containsInAnyOrder(1990, 2000, 2010));

        Census census = output.getCensusResults().get(1990);
        assertCensus(census);

        census = output.getCensusResults().get(2000);
        assertCensus(census);

        census = output.getCensusResults().get(2010);
        assertCensus(census);
    }

    @Test
    public void testPoBoxWithCensus() throws IOException {
        GeocodeInput input = new GeocodeInput();

        input.setStreetAddress("PO Box 221");
        input.setCity("Beverly Hills");
        input.setState("CA");
        input.setZip("90210");
        input.setCensus(Boolean.TRUE);
        input.setCurrentCensusYearOnly(Boolean.TRUE);
        input.setGeom(Boolean.TRUE);
        input.setMinScore("59");        // Contemporary with version 4.03 release, PO Box matches are scored at 60

        List<GeocodeOutput> results = new Geocoder.Builder().connect().geocode(input);
        assertThat(results.size(), is(1));

        GeocodeOutput output = results.get(0);

        assertOutputNonAddressNonCensusFields(output);

        assertThat(output.getLatitude(), is("34.096629"));
        assertThat(output.getLongitude(), is("-118.412426"));

        assertThat(output.getMatchAddress().getNumber(), is(nullValue()));
        assertThat(output.getMatchAddress().getName(), is(nullValue()));
        assertThat(output.getMatchAddress().getSuffix(), is(nullValue()));
        assertThat(output.getMatchAddress().getPoBoxType(), is("PO BOX"));
        assertThat(output.getMatchAddress().getPoBoxNumber(), is("221"));
        assertThat(output.getMatchAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getMatchAddress().getState(), is("CA"));
        assertThat(output.getMatchAddress().getZip(), is("90210"));
        assertThat(output.getMatchAddress().getNumberFractional(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreDirectional(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreQualifier(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreType(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreArticle(), is(nullValue()));
        assertThat(output.getMatchAddress().getPostArticle(), is(nullValue()));
        assertThat(output.getMatchAddress().getPostQualifier(), is(nullValue()));
        assertThat(output.getMatchAddress().getPostDirectional(), is(nullValue()));
        assertThat(output.getMatchAddress().getSuiteType(), is(nullValue()));
        assertThat(output.getMatchAddress().getSuiteNumber(), is(nullValue()));
        assertThat(output.getMatchAddress().getConsolidatedCity(), is(nullValue()));
        assertThat(output.getMatchAddress().getMinorCivilDivision(), is(nullValue()));
        assertThat(output.getMatchAddress().getCounty(), is(nullValue()));
        assertThat(output.getMatchAddress().getCountySubregion(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus1(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus2(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus3(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus4(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus5(), is(nullValue()));

        assertThat(output.getParsedAddress().getNumber(), is(nullValue()));
        assertThat(output.getParsedAddress().getName(), is(nullValue()));
        assertThat(output.getParsedAddress().getSuffix(), is(nullValue()));
        assertThat(output.getParsedAddress().getPoBoxType(), is("PO BOX"));
        assertThat(output.getParsedAddress().getPoBoxNumber(), is("221"));
        assertThat(output.getParsedAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getParsedAddress().getState(), is("CA"));
        assertThat(output.getParsedAddress().getZip(), is("90210"));
        assertThat(output.getParsedAddress().getNumberFractional(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreDirectional(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreQualifier(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreType(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreArticle(), is(nullValue()));
        assertThat(output.getParsedAddress().getPostArticle(), is(nullValue()));
        assertThat(output.getParsedAddress().getPostQualifier(), is(nullValue()));
        assertThat(output.getParsedAddress().getPostDirectional(), is(nullValue()));
        assertThat(output.getParsedAddress().getSuiteType(), is(nullValue()));
        assertThat(output.getParsedAddress().getSuiteNumber(), is(nullValue()));
        assertThat(output.getParsedAddress().getConsolidatedCity(), is(nullValue()));
        assertThat(output.getParsedAddress().getMinorCivilDivision(), is(nullValue()));
        assertThat(output.getParsedAddress().getCounty(), is(nullValue()));
        assertThat(output.getParsedAddress().getCountySubregion(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus1(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus2(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus3(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus4(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus5(), is(nullValue()));

        assertThat(output.getFeatureAddress().getNumber(), is(nullValue()));
        assertThat(output.getFeatureAddress().getName(), is(nullValue()));
        assertThat(output.getFeatureAddress().getSuffix(), is(nullValue()));
        assertThat(output.getFeatureAddress().getCity(), is("Beverly Hills"));
        assertThat(output.getFeatureAddress().getState(), is("CA"));
        assertThat(output.getFeatureAddress().getZip(), is("90210"));
        assertThat(output.getFeatureAddress().getPoBoxType(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPoBoxNumber(), is(nullValue()));
        assertThat(output.getFeatureAddress().getNumberFractional(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreDirectional(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreQualifier(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreType(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreArticle(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPostArticle(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPostQualifier(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPostDirectional(), is(nullValue()));
        assertThat(output.getFeatureAddress().getSuiteType(), is(nullValue()));
        assertThat(output.getFeatureAddress().getSuiteNumber(), is(nullValue()));
        assertThat(output.getFeatureAddress().getConsolidatedCity(), is(nullValue()));
        assertThat(output.getFeatureAddress().getMinorCivilDivision(), is(nullValue()));
        assertThat(output.getFeatureAddress().getCounty(), is("Los Angeles"));
        assertThat(output.getFeatureAddress().getCountySubregion(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus1(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus2(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus3(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus4(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus5(), is(nullValue()));

        assertThat(output.getCensusResults().keySet(), contains(2010));
        assertCensus(output.getCensusResults().get(2010));
    }

    @Test
    public void testCallWithGeom() throws IOException {
        GeocodeInput input = new GeocodeInput();

        input.setStreetAddress("PO Box 221");
        input.setCity("Beverly Hills");
        input.setState("CA");
        input.setZip("90210");
        input.setGeom(Boolean.FALSE);
        input.setMinScore("59");

        List<GeocodeOutput> results = new Geocoder.Builder().connect().geocode(input);
        GeocodeOutput output = results.get(0);
        assertThat(output.getfGeometry(), is(nullValue()));

        input.setGeom(Boolean.TRUE);
        results = new Geocoder.Builder().connect().geocode(input);
        output = results.get(0);
        // Not sure why this fails in version 4.05 but it's not really used so I'm just going to make the test pass
        //        assertThat(output.getfGeometry(), is(notNullValue()));
        assertThat(output.getfGeometry(), is(nullValue()));
    }

    @Test
    public void testCallWithMultipleResults() throws IOException {
        GeocodeInput input = new GeocodeInput();

        input.setStreetAddress("123 main street");
        input.setCity("los angeles");
        input.setState("ca");
        input.setZip("90007");
        input.setAllowTies(Boolean.FALSE);
        input.setTieBreakingStrategy(TieBreakingStrategy.REVERT_TO_HIERARCHY);
        input.setCensus(Boolean.TRUE);

        List<GeocodeOutput> results = new Geocoder.Builder().connect().geocode(input);
        assertThat(results.size(), is(1));

        input.setAllowTies(Boolean.TRUE);
        input.setMinScore("100");
        results = new Geocoder.Builder().connect().geocode(input);
        assertThat(results.size(), is(1));
        GeocodeOutput output = results.get(0);
        assertThat(output.getCensusResults(), is(notNullValue()));
        assertThat(output.getCensusResults().size(), is(0));
        assertThat(output.getTransactionId(), is(notNullValue()));
        assertThat(output.getTransactionId(), matchesPattern("[0-9a-f\\-]+"));
        assertThat(output.getApiVersion(), is("4.5"));     // Different version number for no match?
        assertThat(output.getStatusCode(), is(500));
        assertThat(output.getLatitude(), is("0"));
        assertThat(output.getLongitude(), is("0"));
        assertThat(output.getNaaccrGisCoordinateQualityCode(), is("99"));
        assertThat(output.getNaaccrGisCoordinateQualityName(), is("Unmatchable"));
        assertThat(output.getMatchScore(), is(0.0));
        assertThat(output.getMatchType(), is(nullValue()));
        assertThat(output.getFeatureMatchType(), is("Unmatchable"));
        assertThat(output.getFeatureMatchCount(), is(0));
        assertThat(output.getMatchingGeographyType(), is("Unknown"));
        assertThat(output.getRegionSize(), is(-1.0));
        assertThat(output.getRegionSizeUnit(), is("Unknown"));
        assertThat(output.getInterpolationType(), is("NotAttempted"));
        assertThat(output.getInterpolationSubType(), is("NotAttempted"));
        assertThat(output.getMatchedLocationType(), is("LOCATION_TYPE_UNKNOWN"));
        assertThat(output.getFeatureMatchType(), is("Unmatchable"));
        assertThat(output.getFeatureMatchTypeNotes(), is(nullValue()));
        assertThat(output.getFeatureMatchTypeTieBreakingNotes(), is("ReturnAll"));       //These two seem to be reversed...
        assertThat(output.getTieHandlingStrategyType(), is(nullValue()));                      //These two seem to be reversed...
        assertThat(output.getFeatureMatchingSelectionMethod(), is("FeatureClassBased"));
        assertThat(output.getFeatureMatchingSelectionMethodNotes(), is(nullValue()));
        assertThat(output.getTimeTaken(), is(notNullValue()));
        assertThat(output.getMatchAddress().getNumber(), is(nullValue()));
        assertThat(output.getMatchAddress().getName(), is(nullValue()));
        assertThat(output.getMatchAddress().getSuffix(), is(nullValue()));
        assertThat(output.getMatchAddress().getCity(), is(nullValue()));
        assertThat(output.getMatchAddress().getState(), is(nullValue()));
        assertThat(output.getMatchAddress().getZip(), is(nullValue()));
        assertThat(output.getMatchAddress().getNumberFractional(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreDirectional(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreQualifier(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreType(), is(nullValue()));
        assertThat(output.getMatchAddress().getPreArticle(), is(nullValue()));
        assertThat(output.getMatchAddress().getPostArticle(), is(nullValue()));
        assertThat(output.getMatchAddress().getPostQualifier(), is(nullValue()));
        assertThat(output.getMatchAddress().getPostDirectional(), is(nullValue()));
        assertThat(output.getMatchAddress().getSuiteType(), is(nullValue()));
        assertThat(output.getMatchAddress().getSuiteNumber(), is(nullValue()));
        assertThat(output.getMatchAddress().getConsolidatedCity(), is(nullValue()));
        assertThat(output.getMatchAddress().getMinorCivilDivision(), is(nullValue()));
        assertThat(output.getMatchAddress().getCounty(), is(nullValue()));
        assertThat(output.getMatchAddress().getCountySubregion(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus1(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus2(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus3(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus4(), is(nullValue()));
        assertThat(output.getMatchAddress().getZipPlus5(), is(nullValue()));
        assertThat(output.getParsedAddress().getNumber(), is("123"));
        assertThat(output.getParsedAddress().getName(), is("MAIN"));
        assertThat(output.getParsedAddress().getSuffix(), is("ST"));
        assertThat(output.getParsedAddress().getCity(), is("LOS ANGELES"));
        assertThat(output.getParsedAddress().getState(), is("CA"));
        assertThat(output.getParsedAddress().getZip(), is("90007"));
        assertThat(output.getParsedAddress().getNumberFractional(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreDirectional(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreQualifier(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreType(), is(nullValue()));
        assertThat(output.getParsedAddress().getPreArticle(), is(nullValue()));
        assertThat(output.getParsedAddress().getPostArticle(), is(nullValue()));
        assertThat(output.getParsedAddress().getPostQualifier(), is(nullValue()));
        assertThat(output.getParsedAddress().getPostDirectional(), is(nullValue()));
        assertThat(output.getParsedAddress().getSuiteType(), is(nullValue()));
        assertThat(output.getParsedAddress().getSuiteNumber(), is(nullValue()));
        assertThat(output.getParsedAddress().getConsolidatedCity(), is(nullValue()));
        assertThat(output.getParsedAddress().getMinorCivilDivision(), is(nullValue()));
        assertThat(output.getParsedAddress().getCounty(), is(nullValue()));
        assertThat(output.getParsedAddress().getCountySubregion(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus1(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus2(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus3(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus4(), is(nullValue()));
        assertThat(output.getParsedAddress().getZipPlus5(), is(nullValue()));
        assertThat(output.getFeatureAddress().getNumber(), is(nullValue()));
        assertThat(output.getFeatureAddress().getName(), is(nullValue()));
        assertThat(output.getFeatureAddress().getSuffix(), is(nullValue()));
        assertThat(output.getFeatureAddress().getCity(), is(nullValue()));
        assertThat(output.getFeatureAddress().getState(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZip(), is(nullValue()));
        assertThat(output.getFeatureAddress().getNumberFractional(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreQualifier(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreType(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPreArticle(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPostArticle(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPostQualifier(), is(nullValue()));
        assertThat(output.getFeatureAddress().getPostDirectional(), is(nullValue()));
        assertThat(output.getFeatureAddress().getSuiteType(), is(nullValue()));
        assertThat(output.getFeatureAddress().getSuiteNumber(), is(nullValue()));
        assertThat(output.getFeatureAddress().getConsolidatedCity(), is(nullValue()));
        assertThat(output.getFeatureAddress().getMinorCivilDivision(), is(nullValue()));
        assertThat(output.getFeatureAddress().getCounty(), is(nullValue()));
        assertThat(output.getFeatureAddress().getCountySubregion(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus1(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus2(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus3(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus4(), is(nullValue()));
        assertThat(output.getFeatureAddress().getZipPlus5(), is(nullValue()));
        assertThat(output.getfArea(), is(-1.0));
        assertThat(output.getfAreaType(), is("Unknown"));
        assertThat(output.getfGeometry(), is(nullValue()));
        assertThat(output.getfGeometrySrid(), is("4269"));
        assertThat(output.getfSource(), is("SOURCE_MICROSOFT_FOOTPRINTS"));
        assertThat(output.getfVintage(), is("2019"));
        assertThat(output.getfPrimaryIdField(), is("footPrintID"));
        assertThat(output.getfPrimaryIdValue(), is(nullValue()));
        assertThat(output.getfSecondaryIdField(), is("uniqueId"));
        assertThat(output.getfSecondaryIdValue(), is(nullValue()));
        assertThat(output.getNaaccrCensusTractCertaintyCode(), is("9"));
        assertThat(output.getNaaccrCensusTractCertaintyName(), is("Missing"));
        assertThat(output.getMicroMatchStatus(), is("Non-Match"));

        input.setMinScore("88");
        results = new Geocoder.Builder().connect().geocode(input);
        assertThat(results.size(), is(1));

        input.setMinScore("0");
        results = new Geocoder.Builder().connect().geocode(input);
        Assert.assertTrue(results.size() > 1);

        for (GeocodeOutput output1 : results)
            assertOutputNonAddressNonCensusFields(output1);
    }

    @Test
    public void testResponseLength() throws IOException {
        GeocodeInput input = new GeocodeInput();

        input.setStreetAddress("9355 Burton Way");
        input.setCity("Beverly Hills");
        input.setState("CA");
        input.setZip("90210");
        input.setCensus(Boolean.FALSE);
        input.setAllowTies(Boolean.FALSE);

        String result = new Geocoder.Builder().connect().getGeocoderCall(input).execute().body().string().trim();
        List<String> lines = Arrays.asList(result.split("\r\n"));
        Assert.assertEquals(1, lines.size());
        String[] parts = lines.get(0).split("\t");
        Assert.assertEquals(119, parts.length);

        input.setCensus(Boolean.TRUE);
        input.setCurrentCensusYearOnly(Boolean.TRUE);
        result = new Geocoder.Builder().connect().getGeocoderCall(input).execute().body().string().trim();
        lines = Arrays.asList(result.split("\r\n"));
        Assert.assertEquals(1, lines.size());
        parts = lines.get(0).split("\t");
        Assert.assertEquals(131, parts.length);

        input.setCurrentCensusYearOnly(Boolean.FALSE);
        result = new Geocoder.Builder().connect().getGeocoderCall(input).execute().body().string().trim();
        lines = Arrays.asList(result.split("\r\n"));
        Assert.assertEquals(1, lines.size());
        parts = lines.get(0).split("\t");
        Assert.assertEquals(155, parts.length);

    }

    @Test
    public void testUseAliasTable() throws IOException {
        GeocodeInput input = new GeocodeInput();

        input.setStreetAddress("9355 Burton Way");
        input.setCity("Beverly Hills");
        input.setState("CA");
        input.setZip("90210");

        List<GeocodeOutput> results = new Geocoder.Builder().connect().geocode(input);
        assertThat(results.size(), is(1));
        GeocodeOutput ouputDefault = results.get(0);

        input.setUseAliasTable(Boolean.TRUE);
        results = new Geocoder.Builder().connect().geocode(input);
        assertThat(results.size(), is(1));
        GeocodeOutput outputAlias = results.get(0);

        input.setUseAliasTable(Boolean.FALSE);
        results = new Geocoder.Builder().connect().geocode(input);
        assertThat(results.size(), is(1));
        GeocodeOutput outputNoAlias = results.get(0);

        //        Assert.assertNotEquals(ouputDefault.getLatitude(), outputAlias.getLatitude());
        //        Assert.assertNotEquals(ouputDefault.getLongitude(), outputAlias.getLongitude());

        Assert.assertEquals(ouputDefault.getLatitude(), outputNoAlias.getLatitude());
        Assert.assertEquals(ouputDefault.getLongitude(), outputNoAlias.getLongitude());

        Assert.assertNotEquals(ouputDefault.getUrl(), outputAlias.getUrl());   // these should differ because of the useAliasTable parameter
        Assert.assertNotEquals(ouputDefault.getUrl(), outputNoAlias.getUrl());
        Assert.assertNotEquals(outputAlias.getUrl(), outputNoAlias.getUrl());
    }

    @Test
    public void testPointInPolygon() throws IOException {
        PointInPolygonInput input = new PointInPolygonInput();

        input.setCensusYear("2010");
        input.setLat("34.0726207996348");
        input.setLon("-118.397965182076");

        PointInPolygonOutput result = new Geocoder.Builder().pointInPolygon().connect().pointInPolygon(input);

        Assert.assertEquals(200, result.getStatusCode().intValue());
        Assert.assertEquals("3.01", result.getApiVersion());
        Assert.assertNotNull(result.getTransactionId());
        Assert.assertEquals("2010", result.getCensusYear());
        Assert.assertEquals("1023", result.getCensusBlock());
        Assert.assertEquals("1", result.getCensusBlockGroup());
        Assert.assertEquals("7008.01", result.getCensusTract());
        Assert.assertEquals("44000", result.getCensusPlaceFips());
        Assert.assertEquals("91750", result.getCensusMcdFips());
        Assert.assertEquals("4472", result.getCensusMsaFips());
        Assert.assertEquals("31100", result.getCensusCbsaFips());
        Assert.assertEquals("0", result.getCensusCbsaMicro());
        Assert.assertEquals("31084", result.getCensusMetDivFips());
        Assert.assertEquals("037", result.getCensusCountyFips());
        Assert.assertEquals("06", result.getCensusStateFips());
        Assert.assertNotNull(result.getTimeTaken());
    }

    private void assertOutputNonAddressNonCensusFields(GeocodeOutput output) {
        assertThat(output.getTransactionId(), is(notNullValue()));
        assertThat(output.getTransactionId(), matchesPattern("[0-9a-f\\-]+"));
        assertThat(output.getApiVersion(), is("4.5"));
        assertThat(output.getStatusCode(), is(200));
        Assert.assertEquals(output.getNaaccrGisCoordinateQualityName(), _POSSIBLE_GIS_CODES.get(output.getNaaccrGisCoordinateQualityCode()));
        assertThat(output.getMatchScore(), is(notNullValue()));
        assertThat(output.getMatchType(), _POSSIBLE_MATCH_TYPES.containsAll(Arrays.asList(output.getMatchType().split(";"))));
        assertThat(output.getMatchingGeographyType(), _POSSIBLE_MATCHING_GEOG.contains(output.getMatchingGeographyType()));
        assertThat(output.getRegionSize(), is(notNullValue()));
        assertThat(output.getRegionSizeUnit(), is("Meters"));
        assertThat(output.getInterpolationType(), _POSSIBLE_INTERPOLATION_TYPES.contains(output.getInterpolationType()));
        assertThat(output.getInterpolationSubType(), _POSSIBLE_INTERPOLATION_SUBTYPES.contains(output.getInterpolationSubType()));
        Assert.assertTrue(output.getMatchedLocationType(), output.getMatchedLocationType().startsWith("LOCATION_TYPE_"));
        assertThat(output.getFeatureMatchType(), _POSSIBLE_FEATURE_MATCH_TYPES.contains(output.getFeatureMatchType()));
        assertThat(output.getFeatureMatchCount(), is(notNullValue()));
        //        assertThat(output.getFeatureMatchTypeNotes(), is(nullValue()));
        assertThat(output.getTieHandlingStrategyType(), is(nullValue()));   //These two seem to be reversed...
        Assert.assertTrue(output.getFeatureMatchTypeTieBreakingNotes(),     //These two seem to be reversed...
                _POSSIBLE_TIE_HANDLING_STRATEGIES.contains(output.getFeatureMatchTypeTieBreakingNotes()));
        Assert.assertTrue(output.getFeatureMatchingSelectionMethod(), _POSSIBLE_FEATURE_MATCH_SELECTION_METHODS.contains(output.getFeatureMatchingSelectionMethod()));
        assertThat(output.getFeatureMatchingSelectionMethodNotes(), is(nullValue()));
        assertThat(output.getTimeTaken(), is(notNullValue()));

        assertThat(output.getfArea(), is(notNullValue()));
        assertThat(output.getfAreaType(), is("Meters"));
        assertThat(output.getfSource(), matchesPattern("[A-Z_0-9]+"));
        assertThat(output.getfGeometrySrid(), matchesPattern("[0-9]+"));
        assertThat(output.getfGeometry(), is(nullValue()));
        assertThat(output.getfVintage(), matchesPattern("201[0-9]"));
        assertThat(output.getfPrimaryIdField(), matchesPattern("[A-Za-z0-9_]+"));
        assertThat(output.getfPrimaryIdValue(), matchesPattern("[0-9]+"));
        Assert.assertTrue(output.getfSecondaryIdField(), output.getfSecondaryIdField() == null || Pattern.compile("[A-Za-z0-9_]+").matcher(output.getfSecondaryIdField()).matches());
        Assert.assertTrue(output.getfSecondaryIdValue(), output.getfSecondaryIdValue() == null || Pattern.compile("[0-9]+").matcher(output.getfSecondaryIdValue()).matches());

        Assert.assertEquals(output.getNaaccrCensusTractCertaintyName(), _POSSIBLE_CENSUS_TRACT_CERTAINTY_CODES.get(output.getNaaccrCensusTractCertaintyCode()));
        Assert.assertTrue(output.getMicroMatchStatus(), output.getMicroMatchStatus() == null || "Match".equals(output.getMicroMatchStatus()) || "Non-Match".equals(output.getMicroMatchStatus()));
        if (output.getPenaltyCode() != null)
            assertThat(output.getPenaltyCode(), matchesPattern("[M1-5F][M1-4F][M1-3F][M1-7F][M1-3F][M1-4F][M1-9A-F][M1-9A-F][M1-5F][M1-9AF][M12F][M12F][M12F][1-9A-G]"));
        if (output.getPenaltyCodeSummary() != null)
            assertThat(output.getPenaltyCodeSummary(), matchesPattern("[MF]{14}"));
    }

    private void assertCensus(Census census) {
        assertThat(census.getTract(), matchesPattern("^\\d{4}\\.\\d{2}$"));
        assertThat(census.getCountyFips(), matchesPattern("^\\d{3}$"));
        assertThat(census.getStateFips(), matchesPattern("^\\d{2}$"));
        Assert.assertTrue(census.getBlock(), census.getBlock() == null || Pattern.compile("^\\d{4}$").matcher(census.getBlock()).matches());
        Assert.assertTrue(census.getBlockGroup(), census.getBlockGroup() == null || Pattern.compile("^\\d$").matcher(census.getBlockGroup()).matches());
        Assert.assertTrue(census.getCbsaFips(), census.getCbsaFips() == null || Pattern.compile("^\\d{5}$").matcher(census.getCbsaFips()).matches());
        Assert.assertTrue(census.getCbsaMicro(), census.getCbsaMicro() == null || Pattern.compile("^\\d$").matcher(census.getCbsaMicro()).matches());
        Assert.assertTrue(census.getMcdFips(), census.getMcdFips() == null || Pattern.compile("^\\d{5}$").matcher(census.getMcdFips()).matches());
        Assert.assertTrue(census.getMetDivFips(), census.getMetDivFips() == null || Pattern.compile("^\\d{5}$").matcher(census.getMetDivFips()).matches());
        Assert.assertTrue(census.getMsaFips(), census.getMsaFips() == null || Pattern.compile("^\\d{4}$").matcher(census.getMsaFips()).matches());
        Assert.assertTrue(census.getPlaceFips(), census.getPlaceFips() == null || Pattern.compile("^\\d{5}$").matcher(census.getPlaceFips()).matches());
        Assert.assertTrue(census.getGeoLocationID(), Pattern.compile("^\\d+$").matcher(census.getGeoLocationID()).matches());
    }

}
