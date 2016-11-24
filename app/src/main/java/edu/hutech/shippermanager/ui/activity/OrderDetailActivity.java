package edu.hutech.shippermanager.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.constant.Language;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.common.Config;
import edu.hutech.shippermanager.common.L;
import edu.hutech.shippermanager.model.Order;
import edu.hutech.shippermanager.utils.TimeUtils;

public class OrderDetailActivity extends BaseActivityAuthorization implements ValueEventListener, LocationListener,DirectionCallback {

    @BindView(R.id.tvTime)
    TextView tvTime;
    @BindView(R.id.tvComment)
    TextView tvComment;
    @BindView(R.id.tvSenderAddress)
    TextView tvSenderAddress;
    @BindView(R.id.tvReceiverAddress)
    TextView tvReceiverAddress;
    @BindView(R.id.tvDistance)
    TextView tvDistance;
    @BindView(R.id.tvTotalPrice)
    TextView tvTotalPrice;
    @BindView(R.id.imgRecievePiture)
    ImageView imgRecievePiture;
    @BindView(R.id.tvReceiverName)
    TextView tvReceiverName;
    @BindView(R.id.tvReceiverPhone)
    TextView tvReceiverPhone;
    @BindView(R.id.btnDirection)
    Button btnDirection;
    @BindView(R.id.btnFinish)
    Button btnFinish;
    @BindView(R.id.cardCallPhone)
    CardView cardCallPhone;
    private String orderID;
    private DatabaseReference itemOrderRef;
    private Order currOrder;
    private LocationManager locationManager;
    private Location currentLocation;

    @Override
    int getContentView() {
        return R.layout.activity_order;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) this.finish();
        orderID = intent.getStringExtra("orderID");
        if (orderID == null) this.finish();

        setDisplayHomeAsUpEnabled(true);
        setTitle("Thông tin đơn đặt hàng");
        itemOrderRef = FirebaseDatabase.getInstance().getReference("orders").child(orderID);
        itemOrderRef.addValueEventListener(this);
    }

    private void initLocation() {
        locationManager = ((LocationManager) getSystemService(Context.LOCATION_SERVICE));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        //currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    void onAuthentication(FirebaseAuth firebaseAuth) {

    }

    @Override
    void onAuthError() {
        this.finish();
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        Order order = dataSnapshot.getValue(Order.class);
        this.currOrder = order;
        Date orderDate = TimeUtils.parseDate(order.getTime());
        try {
            tvTime.setText(TimeUtils.dateToString(orderDate, "dd/MM/yyyy hh:mm"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        tvSenderAddress.setText("Nơi nhận: " + order.getSender().getAddress());
        tvReceiverAddress.setText("Nơi giao: " + order.getReceiver().getAddress());
        tvTotalPrice.setText("Tổng tiền thu: " + String.format("%,d", order.getTotalPrice()) + "đ");
        tvReceiverName.setText(order.getReceiver().getFullName());
        tvReceiverPhone.setText(order.getReceiver().getPhone());
        initLocation();
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    @OnClick({R.id.btnDirection, R.id.btnFinish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnDirection:
                break;
            case R.id.btnFinish:
                break;
        }
    }

    @OnClick(R.id.cardCallPhone)
    public void cardCallPhone() {
        if (currOrder == null) {
            L.Toast("Đã có lỗi xảy ra, không thể thực hiện được cuộc gọi này");
            return;
        }
        String phone = currOrder.getReceiver().getPhone();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            currentLocation = location;
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            updateOrderLocationDetail(location);
            locationManager.removeUpdates(this);
        }
    }

    private void updateOrderLocationDetail(Location location) {
        GoogleDirection.withServerKey(Config.GOOGLE_MAP_API_KEY)
                .from(new LatLng(location.getLatitude(),location.getLongitude()))
                .to(new LatLng(currOrder.getReceiver().getLat(),currOrder.getReceiver().getLng()))
                .avoid(AvoidType.FERRIES)
                .avoid(AvoidType.HIGHWAYS)
                .language(Language.VIETNAMESE)
                .execute(this);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        Toast.makeText(this, "Test222", Toast.LENGTH_SHORT).show();
        if(direction.isOK()){
            Route route = direction.getRouteList().get(0);
            Leg leg = route.getLegList().get(0);
            tvDistance.setText("Khoảng cách: "+leg.getDistance().getText());
            tvComment.setText("Thời gian giao dự tính: "+leg.getDuration().getText());
        }else{
            tvDistance.setText("Khoảng cách: Không xác định");
            tvComment.setText("Thời gian giao dự tính không xác định");
        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
        tvDistance.setText("Khoảng cách: Không xác định");
        tvComment.setText("Thời gian giao dự tính không xác định");
    }
}
