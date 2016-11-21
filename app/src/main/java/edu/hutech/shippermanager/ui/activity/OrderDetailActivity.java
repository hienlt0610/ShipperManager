package edu.hutech.shippermanager.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.OnClick;
import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.common.L;
import edu.hutech.shippermanager.model.Order;

public class OrderDetailActivity extends BaseActivityAuthorization implements ValueEventListener {

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
        tvTime.setText(order.getTime() + "");
        tvSenderAddress.setText(order.getSender().getAddress());
        tvReceiverAddress.setText(order.getReceiver().getAddress());
        tvDistance.setText("12Km");
        tvTotalPrice.setText(String.format("%,d", order.getTotalPrice()));
        tvReceiverName.setText(order.getReceiver().getFullName());
        tvReceiverPhone.setText(order.getReceiver().getPhone());
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
        if(currOrder == null){
            L.Toast("Đã có lỗi xảy ra, không thể thực hiện được cuộc gọi này");
            return;
        }
        String phone = currOrder.getReceiver().getPhone();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }
}
