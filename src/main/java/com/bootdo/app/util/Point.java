package com.bootdo.app.util;

public class Point {
    private double lat;// 纬度
    private double lon;// 经度

    public Point() {
    }

    public Point(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point bmapPoint = (Point) obj;
            return (bmapPoint.getLon() == lon && bmapPoint.getLat() == lat) ? true : false;
        } else {
            return false;
        }
    }

    public double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public double getLon() {
        return lon;
    }
    public void setLng(double lng) {
        this.lon = lng;
    }

    @Override
    public String toString() {
        return "Point [lat=" + lat + ", lng=" + lon + "]";
    }


}
