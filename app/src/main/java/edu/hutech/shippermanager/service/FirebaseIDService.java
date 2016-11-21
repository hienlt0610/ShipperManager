package edu.hutech.shippermanager.service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by hienl on 11/19/2016.
 */

public class FirebaseIDService extends FirebaseInstanceIdService {

    private static final String TAG = "hienlt0610";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Log.d(TAG,refreshedToken);
    }
}
