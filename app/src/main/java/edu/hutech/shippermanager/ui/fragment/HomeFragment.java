package edu.hutech.shippermanager.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.OnClick;
import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.service.GeoService;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment{
    @BindView(R.id.buttonActive)
    Button btnActive;
    public static final String USER_ID_PARAM = "user_id";
    private String userID;

    @OnClick(R.id.buttonActive)
    public void activeClick(View view) {
        Intent intent = new Intent(getContext(), GeoService.class);
        intent.putExtra(USER_ID_PARAM,userID);
        getActivity().startService(intent);
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String userID){
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(USER_ID_PARAM, userID);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userID = getArguments().getString(USER_ID_PARAM);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }
}
