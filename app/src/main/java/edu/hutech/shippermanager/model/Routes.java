package edu.hutech.shippermanager.model;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

/**
 * Created by jerem on 12/11/2016.
 */

public class Routes {
    public Distances distance;
    public String endAddress;
    public LatLng endLocation;
    public String startAddress;
    public LatLng startLocation;

    public List<LatLng> points;
}
