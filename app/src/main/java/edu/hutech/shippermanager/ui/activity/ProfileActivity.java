package edu.hutech.shippermanager.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

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
 * Created by hienl on 11/12/2016.
 */

public class ProfileActivity extends BaseActivityAuthorization {

    private FirebaseUser fUser;
    private DatabaseReference rootFire;

    //Bind view
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtFullName)
    EditText edtFullName;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        rootFire = FirebaseDatabase.getInstance().getReference(FirebaseConfig.USERS_CHILD);
    }

    @Override
    void onAuthentication(FirebaseAuth firebaseAuth) {
        edtEmail.setText(fUser.getEmail());
        DatabaseReference userChild = rootFire.child(fUser.getUid());
        userChild.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if(user == null) return;
                if(user.getFullName()!=null)
                    edtFullName.setText(user.getFullName());
                if(user.getPhone() != null)
                    edtPhone.setText(user.getPhone());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    void onAuthError() {
        this.finish();
    }

    @Override
    int getContentView() {
        return R.layout.activity_profile;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_save:
                if(TextUtils.isEmpty(edtFullName.getText())){
                    Toast.makeText(this, "Vui lòng nhập tên đầy đủ", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if(TextUtils.isEmpty(edtPhone.getText())){
                    Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return false;
                }
                User user = new User();
                user.setFullName(edtFullName.getText().toString());
                user.setPhone(edtPhone.getText().toString());
                user.setProfilePicture(null);
                rootFire.child(fUser.getUid()).setValue(user);
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                this.finish();
                break;
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }
}
