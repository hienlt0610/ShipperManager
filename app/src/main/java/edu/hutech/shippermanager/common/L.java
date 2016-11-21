package edu.hutech.shippermanager.common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by hienl on 11/4/2016.
 */

public class L {
    public static String TAG = "shipper";

    public static final boolean DEBUG = true;

    public static void Log(String tag, String message){
        if(DEBUG)
            Log.d(tag,message);
    }

    public static void Log(String message){
        L.Log(TAG, message);
    }

    public static void Toast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void Toast(String message){
        L.Toast(App.getContext(), message);
    }
}