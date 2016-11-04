package edu.hutech.shippermanager.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;

/**
 * Created by hienl on 11/4/2016.
 */

public class NetworkUtils {
    public static String TAG = "NetworkUtils";

    private NetworkUtils(){}
    /**
     * Check if internet is online
     * @return boolean
     */
    public static boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }

    /**
     * Check if network is available
     * @param context
     * @return
     */
    public static  boolean isNetworkAvailable(Context context) {
        if(context == null) { return false; }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // if no network is available networkInfo will be null, otherwise check if we are connected
        try {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    /**
     * Check if there is any connectivity to a Wifi network.
     * <p/>
     * Can be used in combination with {@link #isConnectedMobile}
     * to provide different features if the device is on a wifi network or a cell network.
     *
     * @param context The current Context or Activity that this method is called from
     * @return true if a wifi connection is available, otherwise false.
     */
    public static boolean isConnectedWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     * <p/>
     * Can be used in combination with {@link #isConnectedWifi}
     * to provide different features if the device is on a wifi network or a cell network.
     *
     * @param context The current Context or Activity that this method is called from
     * @return true if a mobile connection is available, otherwise false.
     */
    public static boolean isConnectedMobile(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

}
