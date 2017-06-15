/*
 * Copyright (C) 2017 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

import org.junit.Assert;
import org.junit.Test;

import junitx.util.PrivateAccessor;

public class GeocodeOutputTest {

    // The important behavior in the GeocodeOutput class is tested in GeocoderTest, but wanted a test for some of the helper methods
    @Test
    public void testIntValue() throws Throwable {
        GeocodeOutput go = new GeocodeOutput();
        Class[] clazz = {String.class};

        Object[] param = {"7"};
        Object result = PrivateAccessor.invoke(go, "intValue", clazz, param);
        Assert.assertTrue(result instanceof Integer);       //Probably not necessary but should definitely fail if not true
        Assert.assertEquals(7, result);

        param = new Object[] {"2222"};
        result = PrivateAccessor.invoke(go, "intValue", clazz, param);
        Assert.assertEquals(2222, result);

        param = new Object[] {"2222.7"};
        result = PrivateAccessor.invoke(go, "intValue", clazz, param);
        Assert.assertNull(result);

        param = new Object[] {""};
        result = PrivateAccessor.invoke(go, "intValue", clazz, param);
        Assert.assertNull(result);

        param = new Object[] {"not a number"};
        result = PrivateAccessor.invoke(go, "intValue", clazz, param);
        Assert.assertNull(result);

        param = new Object[] {null};
        result = PrivateAccessor.invoke(go, "intValue", clazz, param);
        Assert.assertNull(result);
    }

    @Test
    public void testdoubleValue() throws Throwable {
        GeocodeOutput go = new GeocodeOutput();
        Class[] clazz = {String.class};

        Object[] param = {"7"};
        Object result = PrivateAccessor.invoke(go, "doubleValue", clazz, param);
        Assert.assertTrue(result instanceof Double);       //Probably not necessary but should definitely fail if not true
        Assert.assertEquals(7D, result);

        param = new Object[] {"2222"};
        result = PrivateAccessor.invoke(go, "doubleValue", clazz, param);
        Assert.assertEquals(2222D, result);

        param = new Object[] {"2222.7"};
        result = PrivateAccessor.invoke(go, "doubleValue", clazz, param);
        Assert.assertEquals(2222.7, result);

        param = new Object[] {""};
        result = PrivateAccessor.invoke(go, "doubleValue", clazz, param);
        Assert.assertNull(result);

        param = new Object[] {"not a number"};
        result = PrivateAccessor.invoke(go, "doubleValue", clazz, param);
        Assert.assertNull(result);

        param = new Object[] {null};
        result = PrivateAccessor.invoke(go, "doubleValue", clazz, param);
        Assert.assertNull(result);
    }

}
