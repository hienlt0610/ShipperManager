package edu.hutech.shippermanager.service;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

import edu.hutech.shippermanager.common.L;
import edu.hutech.shippermanager.model.LocationUser;
import edu.hutech.shippermanager.ui.fragment.HomeFragment;
import edu.hutech.shippermanager.utils.LocationUtils;

/**
 * Created by hienl on 11/4/2016.
 */

public class GeoService extends Service implements LocationListener {

    // Binder given to clients
    private final IBinder mBinder = new GeoBinder();
    private LocationManager locationManager;
    private boolean mIsListening = false;
    private DatabaseReference fireLocation;
    private long minTime = 5000;
    private float minDistance = 10;
    private String userID = null;
    private FirebaseUser fireUser;

    @Override
    public void onCreate() {
        super.onCreate();
        L.Log("Serive start");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        fireLocation = FirebaseDatabase.getInstance().getReference("location");
        fireUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (userID == null) {
            userID = intent.getStringExtra(HomeFragment.USER_ID_PARAM);
        }
        startListening();
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onLocationChanged(Location location) {
        LocationUser loca = new LocationUser();
        loca.setLat(location.getLatitude());
        loca.setLng(location.getLongitude());
        loca.setTime(new Date().getTime());
        loca.setUserId(fireUser.getEmail());
        //fireLocation.push().setValue(loca);
        fireLocation.child(fireUser.getUid()).setValue(loca);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        startListening();
    }

    @Override
    public void onProviderDisabled(String provider) {
        stopListening();
    }

    public void startListening() {
        if (!mIsListening) {
            if (LocationUtils.isGpsEnabled(this)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, this);
                mIsListening = true;
            }
        } else {
            L.Log("Location tracking đang được chạy");
        }
    }

    public void stopListening() {
        if (mIsListening) {
            L.Log("Dừng tracker location");
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.removeUpdates(this);
            mIsListening = false;
        } else {
            L.Log("Location tracker không được chạy");
        }
    }

    public boolean isListening() {
        return mIsListening;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopListening();
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class GeoBinder extends Binder {
        GeoService getService() {
            // Return this instance of GeoService so clients can call public methods
            return GeoService.this;
        }
    }
}
