package com.imsweb.geocoder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GeocodingInputTest {

    @Test
    public void testBasicInput() {
        GeocodeInput input = new GeocodeInput();

        assertEquals(0, input.toQueryParams().size());

        // TODO add full tests
    }

}