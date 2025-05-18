package com.behrouz.server.utils;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * Created by Hapi KZM
 **/


public class CoordinateXY {

    private double RADIUS = 6378137.0; /* in meters on the equator */

    private int x = 0;
    private int y = 0;

    public CoordinateXY () {
    }

    public CoordinateXY ( int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Convert 4326 to 3857 (latlng to yx)
     *
     * @param coordinate
     * @return
     */
    public static CoordinateXY fromCoordinate(Coordinate coordinate) {
        CoordinateXY xy = new CoordinateXY();

        xy.setX((int) (Math.toRadians(coordinate.x) * xy.RADIUS));
        xy.setY((int) (Math.log(Math.tan(Math.PI / 4 + Math.toRadians(coordinate.y) / 2)) * xy.RADIUS));
        return xy;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private double yToLat(int y) {
        return Math.toDegrees(Math.atan(Math.exp(y / RADIUS)) * 2 - Math.PI / 2);
    }

    private double xToLong(int x) {
        return Math.toDegrees(x / RADIUS);
    }

    public Coordinate toCoordinate() {
        return new Coordinate(xToLong(x), yToLat(y));
    }

    public static Geometry toGeometry(double lat , double lng){
        GeometryFactory gf = new GeometryFactory();
        return gf.createPoint(new Coordinate(lat, lng));
    }

}
