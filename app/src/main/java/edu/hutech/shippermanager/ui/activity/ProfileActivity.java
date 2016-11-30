package edu.hutech.shippermanager.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.common.FirebaseConfig;
import edu.hutech.shippermanager.model.User;
import edu.hutech.shippermanager.utils.BitmapUtils;

/**
 * Created by hienl on 11/12/2016.
 */

public class ProfileActivity extends BaseActivityAuthorization {

    @BindView(R.id.fab)
    FloatingActionButton fab;
    private FirebaseUser fUser;
    private DatabaseReference rootFire;
    private final int SELECT_PHOTO = 1;
    private StorageReference storage;

    //Bind view
    @BindView(R.id.edtEmail)
    EditText edtEmail;
    @BindView(R.id.edtFullName)
    EditText edtFullName;
    @BindView(R.id.edtPhone)
    EditText edtPhone;
    @BindView(R.id.imgProfile)
    ImageView imgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        rootFire = FirebaseDatabase.getInstance().getReference(FirebaseConfig.USERS_CHILD);
        storage = FirebaseStorage.getInstance().getReference("image_profile");
    }

    @Override
    void onAuthentication(FirebaseAuth firebaseAuth) {
        edtEmail.setText(fUser.getEmail());
        DatabaseReference userChild = rootFire.child(fUser.getUid());
        userChild.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                if (user == null) return;
                if (user.getFullName() != null)
                    edtFullName.setText(user.getFullName());
                if (user.getPhone() != null)
                    edtPhone.setText(user.getPhone());
                Glide.with(ProfileActivity.this).load(user.getProfilePicture()).into(imgProfile);
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
        switch (id) {
            case R.id.action_save:
                if (TextUtils.isEmpty(edtFullName.getText())) {
                    Toast.makeText(this, "Vui lòng nhập tên đầy đủ", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (TextUtils.isEmpty(edtPhone.getText())) {
                    Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    return false;
                }
//                User user = new User();
//                user.setFullName(edtFullName.getText().toString());
//                user.setPhone(edtPhone.getText().toString());
//                user.setEmail(fUser.getEmail());
//                rootFire.child(fUser.getUid()).setValue(user);
//                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                this.finish();
                FirebaseDatabase.getInstance().getReference("users").child(fUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        user.setFullName(edtFullName.getText().toString());
                        user.setPhone(edtPhone.getText().toString());
                        user.setEmail(fUser.getEmail());
                        rootFire.child(fUser.getUid()).setValue(user);
                        Toast.makeText(ProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        ProfileActivity.this.finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                break;
            case android.R.id.home:
                this.finish();
                break;
        }
        return true;
    }

    @OnClick(R.id.fab)
    public void takePictureClick(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SELECT_PHOTO){
            if(resultCode == RESULT_OK){
                try {
                    Uri imageUri = data.getData();
                    InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    Bitmap selectedImage = BitmapUtils.inputStreamToBitmap(imageStream);
                    //selectedImage = BitmapUtils.resizeImage(selectedImage,600,315);
                    selectedImage = BitmapUtils.compressImage(selectedImage,640,315);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    //imgProfile.setImageBitmap(selectedImage);
                    //storage.child("image_profile");
                    StorageMetadata metadata = new StorageMetadata.Builder()
                            .setContentType("image/jpg")
                            .build();
                    UploadTask uploadTask = storage.child(fUser.getUid()+".jpg").putBytes(baos.toByteArray(),metadata);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            String url = taskSnapshot.getDownloadUrl().toString();
                            DatabaseReference userChild = rootFire.child(fUser.getUid());
                            userChild.child("profilePicture").setValue(url);
                            //Toast.makeText(ProfileActivity.this, taskSnapshot.getDownloadUrl().toString(), Toast.LENGTH_SHORT).show();
                            Glide.with(ProfileActivity.this).load(url).into(imgProfile);
                        }
                    });
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
