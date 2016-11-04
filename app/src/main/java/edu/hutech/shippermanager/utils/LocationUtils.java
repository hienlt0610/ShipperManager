package edu.hutech.shippermanager.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.support.v7.app.AlertDialog;

/**
 * Created by hienl on 11/5/2016.
 */

public class LocationUtils {

    /**
     * Check if GPS is running
     * @param context   UI context
     * @return boolean
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * Build a dialog to ask the user to change his location settings
     * @param context           UI context
     * @param message           The message to show to the user in a dialog
     * @param positiveLabel     The positive button text
     * @param negativeLabel     The negative button text
     */
    public void askEnableProviders(final Context context, String message, String positiveLabel, String negativeLabel){
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(negativeLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
