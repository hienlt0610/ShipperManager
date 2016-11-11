package edu.hutech.shippermanager.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

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
import edu.hutech.shippermanager.ui.activity.BaseActivityAuthorization;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseFragment{
    public static final String USER_ID_PARAM = "user_id";
    private String userID;
    private FirebaseUser user;

    @BindView(R.id.tvWelcome)
    TextView tvWelcome;

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference(FirebaseConfig.USERS_CHILD).child(user.getUid());
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        BaseActivityAuthorization activity = ((BaseActivityAuthorization) context);
        user = activity.getFireBaseAuth().getCurrentUser();

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }
}
