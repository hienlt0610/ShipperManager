package edu.hutech.shippermanager.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by jerem on 04/11/2016.
 */

public class FragmentUtils {
    public static void replaceFragment(int content, FragmentManager frgmanager, Fragment fragment){
        FragmentTransaction transaction = frgmanager.beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        transaction.replace(content, fragment);
        // Commit the transaction
        transaction.commit();
    }
}
