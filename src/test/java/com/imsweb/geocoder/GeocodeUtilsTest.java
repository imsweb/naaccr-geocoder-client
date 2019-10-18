/*
 * Copyright (C) 2017 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import org.junit.Assert;
import org.junit.Test;

public class GeocodeUtilsTest {

    @Test
    public void testIntValue() {
        Assert.assertEquals(7, GeocoderUtils.intValue("7").intValue());
        Assert.assertEquals(2222, GeocoderUtils.intValue("2222").intValue());
        Assert.assertNull(GeocoderUtils.intValue("2222.7"));
        Assert.assertNull(GeocoderUtils.intValue("not a number"));
        Assert.assertNull(GeocoderUtils.intValue(""));
        Assert.assertNull(GeocoderUtils.intValue(null));
    }

    @Test
    public void testDoubleValue() {
        Assert.assertEquals(7D, GeocoderUtils.doubleValue("7"), 0.0);
        Assert.assertEquals(2222D, GeocoderUtils.doubleValue("2222"), 0.0);
        Assert.assertEquals(2222.7D, GeocoderUtils.doubleValue("2222.7"), 0.0);
        Assert.assertNull(GeocoderUtils.doubleValue("not a number"));
        Assert.assertNull(GeocoderUtils.doubleValue(""));
        Assert.assertNull(GeocoderUtils.doubleValue(null));
    }

}
