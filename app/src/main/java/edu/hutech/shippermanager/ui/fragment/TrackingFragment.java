package edu.hutech.shippermanager.ui.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.service.GeoService;
import edu.hutech.shippermanager.ui.activity.BaseActivityAuthorization;
import edu.hutech.shippermanager.utils.LocationUtils;
import edu.hutech.shippermanager.utils.ServiceUtils;

/**
 * Created by hienl on 11/9/2016.
 */

public class TrackingFragment extends BaseFragment {

    @BindView(R.id.rgLocation)
    RadioGroup rdgLocation;
    private boolean mBound = false;
    private GeoService mService;
    private FirebaseUser mUser;
    @Override
    protected int getContentView() {
        return R.layout.fragment_tracking;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rdgLocation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbStart){
                    if (!LocationUtils.isGpsEnabled(getActivity())) {
                        group.check(R.id.rbStop);
                        LocationUtils.askEnableProviders(getActivity(),"Bạn chưa mở định vị GPS, bạn muốn mở chứ?","Mở","Không");
                        return;
                    }
                    Intent iTrackingStart = new Intent(getActivity(),GeoService.class);
                    iTrackingStart.setAction(GeoService.START_TRACKING);
                    getActivity().startService(iTrackingStart);
                }else if (checkedId == R.id.rbStop){
                    Intent iTrackingStart = new Intent(getActivity(),GeoService.class);
                    getActivity().stopService(iTrackingStart);
                }
            }
        });
        setStateRadioButton();
    }

    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((GeoService.GeoBinder) service).getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    private void setStateRadioButton() {
        if (ServiceUtils.isServiceRunning(getActivity(),GeoService.class)) {
            rdgLocation.check(R.id.rbStart);
        }else{
            rdgLocation.check(R.id.rbStop);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!mBound)
            getActivity().bindService(new Intent(getActivity(),GeoService.class),conn,0);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBound) {
            getActivity().unbindService(conn);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mUser = ((BaseActivityAuthorization) context).getFireBaseAuth().getCurrentUser();
    }
}
