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
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.common.FirebaseConfig;
import edu.hutech.shippermanager.common.L;
import edu.hutech.shippermanager.model.LocationUser;
import edu.hutech.shippermanager.ui.activity.MainActivity;
import edu.hutech.shippermanager.utils.LocationUtils;
import edu.hutech.shippermanager.utils.NotificationUtils;
import edu.hutech.shippermanager.utils.TimeUtils;

/**
 * Created by hienl on 11/4/2016.
 */

public class GeoService extends Service implements LocationListener, ValueEventListener {

    // Binder given to clients
    private final IBinder mBinder = new GeoBinder();
    private LocationManager locationManager;
    private boolean mIsListening = false;
    private DatabaseReference fireLocation;
    private long minTime = 1000;
    private float minDistance = 5;
    private String userID = null;
    public static int NOTIFICATION_ID = 126;
    private Handler timer;
    private long currentTime = 0 ;
    NotificationCompat.Builder notifiBuilder;
    private FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference connectedRef;
    private DatabaseReference userRef;

    public static final String START_TRACKING = "edu.hutech.shippermanager.START_TRACKING";
    public static final String STOP_TRACKING = "edu.hutech.shippermanager.STOP_TRACKING";

    @Override
    public void onCreate() {
        super.onCreate();
        L.Log("Serive start");
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        fireLocation = FirebaseDatabase.getInstance().getReference(FirebaseConfig.USER_LOCATION_CHILD);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setAction(MainActivity.FRAGMENT_TRACKER);
        notifiBuilder = NotificationUtils.create(this,intent, R.drawable.ic_map,"Đang tracking","0:00");
        startForeground(NOTIFICATION_ID, notifiBuilder.build());
        timer = new Handler();
        timer.post(schedule);
        connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        userRef = FirebaseDatabase.getInstance().getReference("presences").child(mUser.getUid());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(userID == null){
            userID = intent.getStringExtra("user_id");
        }
        if(intent != null){
            String action = intent.getAction();
            if(action.equals(START_TRACKING)){
                startListening();
            }else if(action.equals(STOP_TRACKING)){
                stopListening();
            }
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onLocationChanged(Location location) {
        if(mUser == null){
            stopListening();
            return;
        }
        LocationUser loca = new LocationUser();
        loca.setLat(location.getLatitude());
        loca.setLng(location.getLongitude());
        loca.setLastTime(new Date().getTime());
        fireLocation.child(mUser.getUid()).setValue(loca);
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
                if(mUser == null) return ;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, this);
                mIsListening = true;
                connectedRef.addValueEventListener(this);
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
            userRef.setValue(getOfflineValue());
            connectedRef.removeEventListener(this);
        }else{
            L.Log("Location tracker không được chạy");
        }
    }

    public boolean isListening(){
        return mIsListening;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopListening();
        timer.removeCallbacks(schedule);
    }
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        if ((Boolean) dataSnapshot.getValue()) {
            userRef.onDisconnect().setValue(getOfflineValue(), new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    if(databaseError == null){
                        userRef.setValue(getOnlineValue());
                    }
                }
            });
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class GeoBinder extends Binder {
        public GeoService getService() {
            // Return this instance of GeoService so clients can call public methods
            return GeoService.this;
        }
    }

    Runnable schedule = new Runnable() {
        @Override
        public void run() {
            currentTime++;
            notifiBuilder.setContentText(TimeUtils.getConvertedTime(currentTime));
            NotificationUtils.sendNotification(NOTIFICATION_ID,GeoService.this,notifiBuilder.build());
            timer.postDelayed(this,1000);
        }
    };

    private Map<String, Object> getOfflineValue() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "offline");
        map.put("lastSeen", ServerValue.TIMESTAMP);
        return map;
    }

    private Map<String, Object> getOnlineValue() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "online");
        map.put("lastSeen", ServerValue.TIMESTAMP);
        return map;
    }
}
