package edu.hutech.shippermanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.model.Order;

public class OrderDetailActivity extends BaseActivityAuthorization implements ValueEventListener {

    @BindView(R.id.tvReceive)
    TextView tvReceive;
    private String orderID;
    private DatabaseReference itemOrderRef;

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
        tvReceive.setText(order.getReceiver().toString());
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
