package edu.hutech.shippermanager.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import edu.hutech.shippermanager.R;

/**
 * Created by hienl on 11/4/2016.
 */

public abstract class BaseActivity extends AppCompatActivity {
    abstract int getContentView();
    private boolean isNeedRegister = false;

    private Toolbar toolbar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        configureToolbar();
    }

    private void configureToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
    }

    protected void setDisplayHomeAsUpEnabled(boolean isHomeAsUp){
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeAsUp);
        }
    }

    protected void setActionBarIcon(int iconRes) {
        if(toolbar == null) return;
        toolbar.setNavigationIcon(iconRes);
    }

    public Toolbar getToolbar(){
        return toolbar;
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
