package edu.hutech.shippermanager.common;

import android.app.Application;
import android.content.Context;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by hienl on 11/4/2016.
 */

public class App extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static Context getContext(){
        return mContext;
    }
}