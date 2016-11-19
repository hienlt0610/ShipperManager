package edu.hutech.shippermanager.model;

/**
 * Created by jerem on 19/11/2016.
 */

public class Order {
    private String address;
    private double lat;
    private double lng;
    private int time;
    private boolean status;
    private String nameSender;
    private String nameReceiver;
    private String userID;

    public Order() {
    }

    public Order(String address, double lat, double lng, int time, boolean status, String nameSender, String nameReceiver, String userID) {
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.time = time;
        this.status = status;
        this.nameSender = nameSender;
        this.nameReceiver = nameReceiver;
        this.userID = userID;
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

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }

    public String getNameReceiver() {
        return nameReceiver;
    }

    public void setNameReceiver(String nameReceiver) {
        this.nameReceiver = nameReceiver;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
