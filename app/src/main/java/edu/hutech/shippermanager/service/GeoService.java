package edu.hutech.shippermanager.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by hienl on 11/4/2016.
 */

public class GeoService extends Service {
    // Binder given to clients
    private final IBinder mBinder = new GeoBinder();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
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
