package edu.hutech.shippermanager.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by hienl on 11/4/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {
    abstract int getContentView();
    private boolean isNeedRegister = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
    }

    protected void setNeedRegister(boolean isRegister){
        isNeedRegister = isRegister;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(isNeedRegister){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(isNeedRegister){
            EventBus.getDefault().unregister(this);
        }
    }
}
