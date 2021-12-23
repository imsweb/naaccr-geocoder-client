package com.imsweb.geocoder;

import java.util.Map;

import org.junit.Test;

import com.imsweb.geocoder.GeocodeInput.FeatureMatchingHierarchy;

import static com.imsweb.geocoder.GeocodeInput.TieBreakingStrategy.FLIP_A_COIN;
import static org.junit.Assert.assertEquals;

public class GeocodingInputTest {

    @Test
    public void testBasicInput() {
        GeocodeInput input = new GeocodeInput();

        assertEquals(0, input.toQueryParams().size());

        input.setStreetAddress("3901 Calverton Blvd");
        input.setCity("Calverton");
        input.setState("MD");
        input.setZip("20705");
        input.setNotStore(Boolean.FALSE);
        input.setAllowTies(Boolean.TRUE);
        input.setTieBreakingStrategy(FLIP_A_COIN);
        input.setCensus(Boolean.TRUE);
        input.setExhaustiveSearch(Boolean.TRUE);
        input.setConfidenceLevels("5");
        input.setAliasTable(Boolean.TRUE);
        input.setAttributeRelaxation(true);
        input.setRelaxedAttributes("pre,suffix");
        input.setSubstringMatching(true);
        input.setSoundex(true);
        input.setSoundexableAttributes("bad value");
        input.setHierarchy(FeatureMatchingHierarchy.UNCERTAINTY_BASED);
        input.setReferenceDataSources("MicrosoftFootprints");

        Map<String, String> queryParams = input.toQueryParams();
        assertEquals(19, queryParams.size());
        assertEquals("3901 Calverton Blvd", queryParams.get("streetAddress"));
        assertEquals("Calverton", queryParams.get("city"));
        assertEquals("MD", queryParams.get("state"));
        assertEquals("20705", queryParams.get("zip"));
        assertEquals("true", queryParams.get("allowTies"));
        assertEquals("flipACoin", queryParams.get("tieBreakingStrategy"));
        assertEquals("false", queryParams.get("notStore"));
        assertEquals("true", queryParams.get("census"));
        assertEquals("allAvailable", queryParams.get("censusYear"));
        assertEquals("true", queryParams.get("exhaustiveSearch"));
        assertEquals("5", queryParams.get("confidenceLevels"));
        assertEquals("true", queryParams.get("attributeRelaxation"));
        assertEquals("pre,suffix", queryParams.get("relaxedAttributes"));
        assertEquals("true", queryParams.get("substringMatching"));
        assertEquals("true", queryParams.get("soundex"));
        assertEquals("bad value", queryParams.get("soundexableAttributes"));      // bad values for Soundex Attributes are ignored
        assertEquals("uncertaintyBased", queryParams.get("hierarchy"));
        assertEquals("MicrosoftFootprints", queryParams.get("referenceDataSources"));
    }
}