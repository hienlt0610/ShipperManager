package edu.hutech.shippermanager.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by hienl on 11/4/2016.
 */

public class DisplayUtils {
    private  DisplayUtils(){}

    public static final String LDPI_DENSITY_NAME = "ldpi";
    public static final String MDPI_DENSITY_NAME = "mdpi";
    public static final String HDPI_DENSITY_NAME = "hdpi";
    public static final String TVDPI_DENSITY_NAME = "tvdpi";
    public static final String XHDPI_DENSITY_NAME = "xhdpi";
    public static final String XXHDPI_DENSITY_NAME = "xxhdpi";
    public static final String XXXHDPI_DENSITY_NAME = "xxxhdpi";

    /**
     * Gets the {@link android.view.WindowManager} from the context.
     *
     * @return {@link android.view.WindowManager} The window manager.
     */
    public static WindowManager getWindowManager(Context context) {
        return (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    }

    public static int getWindowHeight(Context context) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }

    public static int getWindowWidth(Context context) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static Integer getSmallestScreenWidthDp(Context context) {
        Configuration config = context.getResources().getConfiguration();
        return config.smallestScreenWidthDp;
    }

    public static Integer getScreenWidthDp(Context context) {
        Configuration config = context.getResources().getConfiguration();
        return config.screenWidthDp;
    }

    public static Boolean isLdpiDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi == DisplayMetrics.DENSITY_LOW;
    }

    public static Boolean isMdpiDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi == DisplayMetrics.DENSITY_MEDIUM;
    }

    public static Boolean isHdpiDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi == DisplayMetrics.DENSITY_HIGH;
    }

    public static Boolean isXhdpiDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi == DisplayMetrics.DENSITY_XHIGH;
    }

    public static Boolean isTVdpiDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi == DisplayMetrics.DENSITY_TV;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static Boolean isXXhdpiDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi == DisplayMetrics.DENSITY_XXHIGH;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static Boolean isXXXhdpiDensity(Context context) {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return metrics.densityDpi == DisplayMetrics.DENSITY_XXXHIGH;
    }

    public static String getScreenDensity(Context context) {
        String density = "";
        if (isLdpiDensity(context)) {
            density = LDPI_DENSITY_NAME;
        } else if (isMdpiDensity(context)) {
            density = MDPI_DENSITY_NAME;
        } else if (isHdpiDensity(context)) {
            density = HDPI_DENSITY_NAME;
        } else if (isXhdpiDensity(context)) {
            density = XHDPI_DENSITY_NAME;
        } else if (isTVdpiDensity(context)) {
            density = TVDPI_DENSITY_NAME;
        } else if (isXXhdpiDensity(context)) {
            density = XXHDPI_DENSITY_NAME;
        } else if (isXXXhdpiDensity(context)) {
            density = XXXHDPI_DENSITY_NAME;
        }
        return density;
    }

    public static Integer getLargestScreenWidthPx(Context context) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return Math.max(size.x, size.y);
    }

    public static Integer getScreenWidthPx(Context context) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    /**
     * Current device DPI
     *
     * @return amount of DPIs
     */
    public static int deviceDPI(Context context) {
        return context.getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * Current device resolution
     *
     * @return the resolution
     */
    public static String deviceResolution(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return String.valueOf(metrics.widthPixels) + "x" + metrics.heightPixels;
    }
}
