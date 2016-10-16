package edu.hutech.shippermanager.Common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by hienl on 10/9/2016.
 */

public class L {
    public static String TAG = "hienlt0610";
    public static void Log(String tag, String message){
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
