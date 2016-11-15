package edu.hutech.shippermanager.utils;

import android.os.Build;

/**
 * Created by hienl on 11/15/2016.
 */

public class SupportVersion {
    /**
     * @return true when the caller API version is at Android 1.5
     */
    public static boolean isCupcake() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE;
    }

    /**
     * @return true when the caller API version is at Android 1.6
     */
    public static boolean isDonut() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT;
    }

    /**
     * @return true when the caller API version is at Android 2.0
     */
    public static boolean isEclair() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR;
    }

    /**
     * @return true when the caller API version is at Android 2.2.x
     */
    public static boolean isFroyo() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    /**
     * @return true when the caller API version is at least Android 2.3 - 2.3.2
     */
    public static boolean isGingerbread() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    /**
     * @return true when the caller API version is at Android 3.0.x
     */
    public static boolean isHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    /**
     * @return true when the caller API version is at Android 3.2
     */
    public static boolean isHoneycombMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2;
    }

    /**
     * @return true when the caller API version is at Android 4.0 - 4.0.2
     */
    public static boolean isIceCreamSandwich() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    /**
     * @return true when the caller API version is at Android 4.1 - 4.1.1
     */
    public static boolean isJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    /**
     * @return true when the caller API version is at Android 4.2 - 4.2.2
     */
    public static boolean isJellyBeanMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
    }

    /**
     * @return true when the caller API version is at Android 4.3
     */
    public static boolean isJellyBeanMR2() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2;
    }

    /**
     * @return true when the caller API version is at Android 4.4
     */
    public static boolean isKitkat() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }


    /**
     * @return true when the caller API version is at Android 4.4W
     */
    public static boolean isKitkatWatch() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;
    }


    /**
     * @return true when the caller API version is at Android 5.0
     */
    public static boolean isLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    /**
     * @return true when the caller API version is Android 5.1
     */
    public static boolean isLollipopMR1() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    /**
     * @return true when the caller API version is Android 6.0
     */
    public static boolean isMarshmallow(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * @return true when the caller API version is Android 7.0
     */
    public static boolean isNougat(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }


    /**
     * @param apiLevel minimum API level version that has to support the device
     * @return true when the caller API version is at least apiLevel
     */
    public static boolean isAtLeastAPI(int apiLevel) {
        return Build.VERSION.SDK_INT >= apiLevel;
    }
}
