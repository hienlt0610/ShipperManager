package edu.hutech.shippermanager.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import edu.hutech.shippermanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment{

    public static HomeFragment newInstance(String userID){
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }
}
