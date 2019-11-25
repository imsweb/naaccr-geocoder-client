package com.imsweb.geocoder;

import java.util.Map;

import org.junit.Test;

import com.imsweb.geocoder.GeocodeInput.FeatureMatchingHierarchy;

import static com.imsweb.geocoder.GeocodeInput.TieBreakingStrategy.FLIP_A_COIN;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
        input.setShouldDoExhaustiveSearch(Boolean.TRUE);
        input.setConfidenceLevels("5");
        input.setUseAliasTable(Boolean.TRUE);
        input.setShouldUseRelaxation(true);
        input.setRelaxedAttributes("pre,suffix");
        input.setAllowSubstringMatching(true);
        input.setAllowSoundex(true);
        input.setSoundexAttributes("bad value");
        input.setFeatureMatchingHierarchy(FeatureMatchingHierarchy.UNCERTAINTY_BASED);
        input.setReferenceDataSources("MicrosoftFootprints");

        Map<String, String> queryParams = input.toQueryParams();
        assertEquals(18, queryParams.size());
        assertEquals("3901 Calverton Blvd", queryParams.get("streetAddress"));
        assertEquals("Calverton", queryParams.get("city"));
        assertEquals("MD", queryParams.get("state"));
        assertEquals("20705", queryParams.get("zip"));
        assertEquals("true", queryParams.get("allowTies"));
        assertEquals("flipACoin", queryParams.get("tieBreakingStrategy"));
        assertEquals("false", queryParams.get("notStore"));
        assertEquals("true", queryParams.get("census"));
        assertEquals("allAvailable", queryParams.get("censusYear"));
        assertEquals("true", queryParams.get("shouldDoExhaustiveSearch"));
        assertEquals("5", queryParams.get("confidenceLevels"));
        assertEquals("true", queryParams.get("r"));
        assertEquals("pre,suffix", queryParams.get("ratts"));
        assertEquals("true", queryParams.get("sub"));
        assertEquals("true", queryParams.get("sou"));
        assertFalse(queryParams.containsKey("souatts"));
        assertEquals("uncertaintyBased", queryParams.get("h"));
        assertEquals("MicrosoftFootprints", queryParams.get("refs"));

    }

}