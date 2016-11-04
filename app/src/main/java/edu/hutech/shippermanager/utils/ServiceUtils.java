package edu.hutech.shippermanager.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Vibrator;
import android.view.inputmethod.InputMethodManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import edu.hutech.shippermanager.common.L;

/**
 * Created by hienl on 11/4/2016.
 */

public class ServiceUtils {

    private ServiceUtils(){}
    /**
     * Check if Google Play Service was installed in the device
     * @param activity
     * @return boolean isRuning
     */
    public static boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if(status != ConnectionResult.SUCCESS) {
            if(googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    /**
     * Check service is running
     * @param serviceClass
     * @param context
     * @return boolean isRunning
     */
    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(
                    Integer.MAX_VALUE)) {
                if (serviceClass.getName().equals(service.service.getClassName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Hides the already popped up keyboard from the screen.
     * @param context
     */
    public static void hideKeyboard(Context context) {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            L.Log("Sigh, cant even hide keyboard " + e.getMessage());
        }
    }

    /**
     * Make the smartphone vibrate for a giving time. You need to put the
     * vibration permission in the manifest as follows: <uses-permission
     * android:name="android.permission.VIBRATE"/>
     *
     * @param duration duration of the vibration in miliseconds
     */
    public static void vibrate(Context context,int duration) {
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(duration);
    }
}
