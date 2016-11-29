package edu.hutech.shippermanager.ui.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.common.FirebaseConfig;
import edu.hutech.shippermanager.model.User;
import edu.hutech.shippermanager.service.GeoService;
import edu.hutech.shippermanager.ui.fragment.HomeFragment;
import edu.hutech.shippermanager.ui.fragment.MapFragment;
import edu.hutech.shippermanager.ui.fragment.TrackingFragment;
import edu.hutech.shippermanager.utils.FragmentUtils;
import edu.hutech.shippermanager.utils.MiscUtils;
import edu.hutech.shippermanager.utils.ServiceUtils;
import edu.hutech.shippermanager.utils.StringUtils;
import edu.hutech.shippermanager.utils.SupportVersion;

public class MainActivity extends BaseActivityAuthorization implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseUser mUser;
    private DatabaseReference userChild;
    private int requestPermissionCode = 22;

    public static final String FRAGMENT_TRACKER  = "edu.hutech.shippermanager.FRAGMENT_TRACKER";
    public static final String FRAGMENT_HOME  = "edu.hutech.shippermanager.FRAGMENT_HOME";
    public static final String FRAGMENT_MAP  = "edu.hutech.shippermanager.FRAGMENT_MAP";

    @BindView(R.id.nav_view)
    NavigationView navigationView;
    View viewHeader;
    TextView tvFullName, tvEmail;
    ImageView imgProfile;


    @Override
    int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        viewHeader = navigationView.getHeaderView(0);
        tvFullName = (TextView) viewHeader.findViewById(R.id.tvFullName);
        tvEmail = (TextView) viewHeader.findViewById(R.id.tvEmail);
        imgProfile = ((ImageView) viewHeader.findViewById(R.id.imgProfile));
        userChild = FirebaseDatabase.getInstance().getReference(FirebaseConfig.USERS_CHILD);

        requestPermisstion();
    }

    private void requestPermisstion() {
        if(!SupportVersion.isMarshmallow()) return;
        MiscUtils.requestMissingPermissions(this,requestPermissionCode,new String[]{
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.SYSTEM_ALERT_WINDOW
        });
    }

    @Override
    void onAuthentication(FirebaseAuth firebaseAuth) {
        mUser = firebaseAuth.getCurrentUser();
        //Set default fragment
        setDefaultFragment();
        checkProfile();
    }

    private void setDefaultFragment() {
        Fragment defaultFragment = new HomeFragment();
        if(getIntent() != null){
            Intent intent = getIntent();
            String action = intent.getAction();
            if(StringUtils.isNotEmpty(action)) {
                if(action.equals(FRAGMENT_MAP)){
                    defaultFragment = new MapFragment();
                }
                else if(action.equals(FRAGMENT_TRACKER)){
                    defaultFragment = new TrackingFragment();
                }
            }
        }
        FragmentUtils.replaceFragment(R.id.flContent,getSupportFragmentManager(), defaultFragment);
    }

    private void checkProfile() {
        userChild.child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user == null || user.getFullName() == null || user.getPhone() == null){
                    MiscUtils.showAlertDialog(MainActivity.this, "Thiếu thông tin cá nhân", "Bạn vui lòng bổ sung thông tin cá nhân đầy đủ trước khi sử dụng app", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                            startActivity(intent);
                        }
                    },false);
                }else{
                    tvEmail.setText(mUser.getEmail());
                    tvFullName.setText(user.getFullName());
                    Glide.with(MainActivity.this).load(user.getProfilePicture()).into(imgProfile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    void onAuthError() {
        Intent i = new Intent(MainActivity.this,LoginActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav__menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                break;
            case R.id.action_profile:
                startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_map) {
            // Handle the camera action
            FragmentUtils.replaceFragment(R.id.flContent,getSupportFragmentManager(),new MapFragment());
        }
        else if(id == R.id.nav_logout){
            getFireBaseAuth().signOut();
            if(ServiceUtils.isServiceRunning(this, GeoService.class)){
                stopService(new Intent(this,GeoService.class));
            }
        }
        else if(id == R.id.nav_home){
            String uid = mUser.getUid();
            FragmentUtils.replaceFragment(R.id.flContent,getSupportFragmentManager(), HomeFragment.newInstance(uid));
        }
        else if(id == R.id.nav_location){
            FragmentUtils.replaceFragment(R.id.flContent,getSupportFragmentManager(), new TrackingFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
