package edu.hutech.shippermanager.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.adapter.OrderAdapter;
import edu.hutech.shippermanager.common.FirebaseConfig;
import edu.hutech.shippermanager.model.Order;
import edu.hutech.shippermanager.model.User;
import edu.hutech.shippermanager.ui.activity.OrderDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    @BindView(R.id.tvWelcome)
    TextView tvWelcome;
    @BindView(R.id.listViewOrders)
    ListView lvOrders;
    ArrayList<Order> orders;
    OrderAdapter adapter = null;
    FirebaseUser fUser;
    DatabaseReference root;

    public static HomeFragment newInstance(String userID) {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        final DatabaseReference root = FirebaseDatabase.getInstance().getReference(FirebaseConfig.USERS_CHILD);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        root = FirebaseDatabase.getInstance().getReference();
//        DatabaseReference demo = FirebaseDatabase.getInstance().getReference();
//        Order order = new Order("38 nguyen Xi", 10.801147, 106.710624, 187, true, "Nhat Hoang", "Hien Le", fUser.getUid().toString());
//        demo.child("orders").push().setValue(order);

        orders = new ArrayList<Order>();
        adapter = new OrderAdapter(getActivity(), R.layout.item_order, orders);
        lvOrders.setAdapter(adapter);
        if (fUser != null) {
            root.child(FirebaseConfig.USERS_CHILD).child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if (user != null) {
                        if (user.getFullName() != null) {
                            tvWelcome.setText("Xin chào: " + user.getFullName());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            root.child("orders").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    final Order order = dataSnapshot.getValue(Order.class);
                    order.setOrderID(dataSnapshot.getKey());
                    if (order == null) return;
                    if (order.getUserID().equals(fUser.getUid())) {
                        root.child("user_location").child(fUser.getUid()).child("orders").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Order runningOrder = dataSnapshot.getValue(Order.class);
                                if(runningOrder != null && runningOrder.getOrderID().equals(order.getOrderID())){
                                    order.setRunning(true);
                                }else{
                                    order.setRunning(false);
                                }
                                adapter.addOrderItem(order);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Order order = dataSnapshot.getValue(Order.class);
                    order.setOrderID(dataSnapshot.getKey());
                    adapter.updateOrder(order);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    Order order = dataSnapshot.getValue(Order.class);
                    adapter.deleteOrder(order);
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            lvOrders.setOnItemClickListener(this);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
        final Order order = orders.get(pos);

        //Creating the instance of PopupMenu
        final PopupMenu popup = new PopupMenu(getActivity(), view);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.poupup_menu, popup.getMenu());

        DatabaseReference userLocationRef = root.child("user_location");
        DatabaseReference userLocationItem = userLocationRef.child(fUser.getUid());
        userLocationItem.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("orders") || order.isStatus()) {
                    popup.getMenu().findItem(R.id.menu_act_run_order).setVisible(false);
                }else{
                    popup.getMenu().findItem(R.id.menu_act_run_order).setVisible(true);
                }
                popup.show();//showing popup menu
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.menu_act_view_info:
                        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                        intent.putExtra("orderID", order.getOrderID());
                        startActivity(intent);
                        break;
                    case R.id.menu_act_run_order:
                        DatabaseReference uLocationItem = root.child("user_location").child(fUser.getUid());
                        uLocationItem.child("orders").setValue(order);

                        break;
                }
                return true;
            }
        });
    }


}
