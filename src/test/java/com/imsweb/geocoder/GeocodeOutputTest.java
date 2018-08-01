/*
 * Copyright (C) 2017 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import org.junit.Assert;
import org.junit.Test;

public class GeocodeOutputTest {

    // The important behavior in the GeocodeOutput class is tested in GeocoderTest, but wanted a test for some of the helper methods

    @Test
    public void testIntValue() {
        Assert.assertEquals(7, GeocodeOutput.intValue("7").intValue());
        Assert.assertEquals(2222, GeocodeOutput.intValue("2222").intValue());
        Assert.assertNull(GeocodeOutput.intValue("2222.7"));
        Assert.assertNull(GeocodeOutput.intValue("not a number"));
        Assert.assertNull(GeocodeOutput.intValue(""));
        Assert.assertNull(GeocodeOutput.intValue(null));
    }

    @Test
    public void testDoubleValue() {
        Assert.assertEquals(7D, GeocodeOutput.doubleValue("7"), 0.0);
        Assert.assertEquals(2222D, GeocodeOutput.doubleValue("2222"), 0.0);
        Assert.assertEquals(2222.7D, GeocodeOutput.doubleValue("2222.7"), 0.0);
        Assert.assertNull(GeocodeOutput.doubleValue("not a number"));
        Assert.assertNull(GeocodeOutput.doubleValue(""));
        Assert.assertNull(GeocodeOutput.doubleValue(null));
    }

}
