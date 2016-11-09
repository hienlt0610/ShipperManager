package edu.hutech.shippermanager.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;

/**
 * Created by hienl on 11/4/2016.
 */

public abstract class BaseFragment extends Fragment {
    private boolean isNeedRegister;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentView(), container, false);
        return view;
    }

    protected void setNeedRegister(boolean isRegister){
        isNeedRegister = isRegister;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(isNeedRegister){
            try {
                EventBus.getDefault().register(this);
            } catch (Throwable t){
                t.printStackTrace();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(isNeedRegister){
            try {
                EventBus.getDefault().unregister(this);
            } catch (Throwable t){
                t.printStackTrace();
            }
        }
    }

    protected abstract int getContentView();
}
