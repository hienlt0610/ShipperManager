package edu.hutech.shippermanager.model;

/**
 * Created by jerem on 19/11/2016.
 */

public class Order {
    private String address;
    private double lat;
    private double lng;
    private boolean status;
    private String userID;

    public Order(String address, double lat, double lng, boolean status, String userID) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.status = status;
        this.userID = userID;
    }

    public Order() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
