package edu.hutech.shippermanager.event;

/**
 * Created by jerem on 01/12/2016.
 */

public class EventDirect {
    private double lat;
    private double lng;

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

    public EventDirect(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
