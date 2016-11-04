package edu.hutech.shippermanager.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by jerem on 04/11/2016.
 */

public class FragmentUtils {
    private FragmentUtils(){}
    public static void replaceFragment(int content, FragmentManager frgmanager, Fragment fragment, String tag){
        FragmentTransaction transaction = frgmanager.beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        transaction.replace(content, fragment,tag);
        // Commit the transaction
        transaction.commit();
    }

    public static void replaceFragment(int content, FragmentManager frgmanager, Fragment fragment){
        replaceFragment(content,frgmanager,fragment,null);
    }

    public static void addFragmentToLayout(final int containerId, final FragmentManager fragmentManager,
                                               final Fragment fragment, final String tag) {
        final FragmentTransaction ft = fragmentManager.beginTransaction();
        final Fragment previousFragment = fragmentManager
                .findFragmentByTag(tag);
        if (previousFragment != null) {
            ft.remove(previousFragment);
        }
        ft.add(containerId, fragment, tag);
        ft.commit();
    }

    /**
     * Simpler version of {@link android.support.v4.app.FragmentManager#findFragmentById(int)}} which infers the target type.
     */
    @SuppressWarnings("unchecked")
    public static <T> T findFragmentById(FragmentManager fragmentManager, int fragmentId) {
        return (T) fragmentManager.findFragmentById(fragmentId);
    }

    @SuppressWarnings("unchecked")
    public static <T> T findFragmentByTag(FragmentManager fragmentManager, String fragmentTag) {
        return (T) fragmentManager.findFragmentByTag(fragmentTag);
    }
}
