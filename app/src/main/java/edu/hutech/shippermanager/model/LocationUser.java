package edu.hutech.shippermanager.model;

/**
 * Created by jerem on 04/11/2016.
 */

public class LocationUser {
    private double lat;
    private double lng;
    private long lastTime;
    private String userId;
    private boolean statusOrder;

    public LocationUser() {
    }

    public LocationUser(double lat, double lng, long lastTime, String userId, boolean statusOrder) {
        this.lat = lat;
        this.lng = lng;
        this.lastTime = lastTime;
        this.userId = userId;
        this.statusOrder = statusOrder;
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

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(boolean statusOrder) {
        this.statusOrder = statusOrder;
    }
}

