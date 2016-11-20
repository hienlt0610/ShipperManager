package edu.hutech.shippermanager.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment{

    @BindView(R.id.tvWelcome)
    TextView tvWelcome;
    FirebaseUser fUser;
    public static HomeFragment newInstance(String userID){
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
        DatabaseReference root = FirebaseDatabase.getInstance().getReference(FirebaseConfig.USERS_CHILD);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference demo = FirebaseDatabase.getInstance().getReference();
//        Order order = new Order("475 dien bien phu", 10.801147, 106.710624, 187, true, "Nhat Hoang", "Hien Le", fUser.getEmail());
//        demo.child("orders").push().setValue(order);
        if(fUser != null){
            root.child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    if(user != null){
                        if(user.getFullName()!=null){
                            tvWelcome.setText("Xin chào: "+user.getFullName());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }
}
