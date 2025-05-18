package com.behrouz.server.utils;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * Created by Hapi KZM
 **/


public class CoordinateUtils {


    private static final double RADIUS = 6378137.0; /* in meters on the equator */


    public static double yToLat(double y) {
        return Math.toDegrees(Math.atan(Math.exp(y / RADIUS)) * 2 - Math.PI / 2);
    }

    public static double xToLong(double x) {
        return Math.toDegrees(x / RADIUS);
    }


    /* These functions take their angle parameter in degrees and return a length in meters */
    public static double[] latLongToXY(Coordinate coordinate) {
        CoordinateXY xy = new CoordinateXY();
        double x = Math.toRadians(coordinate.x) * RADIUS;
        double y = Math.log(Math.tan(Math.PI / 4 + Math.toRadians(coordinate.y) / 2)) * RADIUS;

        return new double[]{x, y};
    }


}
