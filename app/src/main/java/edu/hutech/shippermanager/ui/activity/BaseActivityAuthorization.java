package edu.hutech.shippermanager.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by hienl on 11/4/2016.
 */

public abstract class BaseActivityAuthorization extends BaseActivity implements FirebaseAuth.AuthStateListener {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            onAuthentication(firebaseAuth);
        }else{
            onAuthError();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(this);
    }

    public FirebaseAuth getFireBaseAuth() {
        return mAuth;
    }

    abstract void onAuthentication(FirebaseAuth firebaseAuth);
    abstract void onAuthError();
}

