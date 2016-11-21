package edu.hutech.shippermanager.model;

/**
 * Created by jerem on 21/11/2016.
 */

public class Customer {
    private String address;
    private String fullName;
    private double lat;
    private double lng;
    private String phone;

    public Customer() {
    }

    public Customer(String address, String fullName, double lat, double lng, String phone) {
        this.address = address;
        this.fullName = fullName;
        this.lat = lat;
        this.lng = lng;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{address: "+this.address);
        stringBuilder.append(" ,fullName: "+this.fullName);
        stringBuilder.append(" ,phoneNumber: "+this.phone);
        stringBuilder.append(" ,lat: "+this.lat);
        stringBuilder.append(" ,lng: "+this.lng).append("}");
        return stringBuilder.toString();
    }
}
