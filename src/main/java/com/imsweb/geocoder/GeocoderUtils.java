/*
 * Copyright (C) 2019 Information Management Services, Inc.
 */
package com.imsweb.geocoder;

public class GeocoderUtils {

    /**
     * Helper method to safely convert empty strings to null
     */
    public static String value(String value) {
        if (value == null || value.isEmpty())
            return null;
        return value;
    }

    /**
     * Helper method to safely convert String to Integer
     */
    public static Integer intValue(String value) {
        if (value == null || value.isEmpty())
            return null;
        try {
            return Integer.valueOf(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Helper method to safely convert String to Double
     */
    public static Double doubleValue(String value) {
        if (value == null || value.isEmpty())
            return null;
        try {
            return Double.valueOf(value);
        }
        catch (NumberFormatException e) {
            return null;
        }
    }
}
