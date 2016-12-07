package edu.hutech.shippermanager.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.OnClick;
import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.ui.view.DrawingView;

public class ConfirmActivity extends BaseActivityAuthorization {

    @BindView(R.id.drawing)
    DrawingView drawing;
    @BindView(R.id.btnClear)
    Button btnClear;
    @BindView(R.id.btnSave)
    Button btnSave;
    private String orderId;

    @Override
    int getContentView() {
        return R.layout.activity_confirm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent() != null){
            Intent intent = getIntent();
            orderId = intent.getStringExtra("orderId");
            if(orderId == null){
                Toast.makeText(this, "Lỗi không xác định được đơn hàng", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }else {
            Toast.makeText(this, "Lỗi không xác định được đơn hàng", Toast.LENGTH_SHORT).show();
            this.finish();
        }
    }

    @Override
    void onAuthentication(FirebaseAuth firebaseAuth) {

    }

    @Override
    void onAuthError() {
        this.finish();
    }

    @OnClick({R.id.btnClear, R.id.btnSave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnClear:
                drawing.clearDraw();
                break;
            case R.id.btnSave:
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                drawing.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, baos);
                StorageReference storage = FirebaseStorage.getInstance().getReference("signature");
                UploadTask task = storage.child(orderId+".jpg").putBytes(baos.toByteArray());
                task.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        String imageUrl = task.getResult().getDownloadUrl().toString();
                        Toast.makeText(ConfirmActivity.this, "Hoàn thành đơn hàng", Toast.LENGTH_SHORT).show();
                        FirebaseDatabase.getInstance().getReference("orders").child(orderId).child("status").setValue(true);
                        FirebaseDatabase.getInstance().getReference("orders").child(orderId).child("signature").setValue(imageUrl);
                        FirebaseDatabase.getInstance().getReference("user_location")
                        .child(getFireBaseAuth()
                        .getCurrentUser()
                        .getUid()).child("orders").removeValue();
                        ConfirmActivity.this.finish();
                    }
                });
                break;
        }
    }
}
