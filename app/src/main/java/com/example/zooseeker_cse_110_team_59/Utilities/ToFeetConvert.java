package com.example.zooseeker_cse_110_team_59.Utilities;

public class ToFeetConvert {
    public static final double LAT_TO_FEET = 363843.57;
    public static final double LNG_TO_FEET = 307515.50;

    public static double FromLat(double lat) {
        return lat * LAT_TO_FEET;
    }

    public static double FromLng(double lng) {
        return lng * LNG_TO_FEET;
    }
}
