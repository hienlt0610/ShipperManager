package edu.hutech.shippermanager.model;

/**
 * Created by jerem on 04/11/2016.
 */

public class LocationUser {
    private double lat;
    private double lng;
    private long time;
    private String userId;

    public LocationUser() {
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public long getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocationUser(double lat, double lng, long time, String userId) {
        this.lat = lat;
        this.lng = lng;
        this.time = time;
        this.userId = userId;

    }
}

