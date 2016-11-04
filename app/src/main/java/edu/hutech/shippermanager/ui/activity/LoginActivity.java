package edu.hutech.shippermanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

import butterknife.BindView;
import butterknife.OnClick;
import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.common.L;
import edu.hutech.shippermanager.utils.NetworkUtils;

public class LoginActivity extends BaseActivityAuthorization {

    @BindView(R.id.editTextEmail)
    EditText edtEmail;
    @BindView(R.id.editTextPassword)
    EditText edtPass;

    @Override
    int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    void onAuthentication(FirebaseAuth firebaseAuth) {
        Intent i = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    void onAuthError() {

    }

    @OnClick(R.id.buttonLogin)
    public void Login(Button btnLogin) {
        if(!NetworkUtils.isOnline())
        {
            L.Toast("Mạng không khả dụng, vui lòng kiểm tra lại kết nối");
            return;
        }
        if (TextUtils.isEmpty(edtEmail.getText())) {
            L.Toast("Vui lòng nhập email");
            return;
        }
        if (TextUtils.isEmpty(edtPass.getText())) {
            L.Toast("Vui lòng nhập Password");
            return;
        }
        getFireBaseAuth().signInWithEmailAndPassword(edtEmail.getText().toString(), edtPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            FirebaseAuthException ex = ((FirebaseAuthException) task.getException());
                            switch (ex.getErrorCode()){
                                case "ERROR_INVALID_EMAIL":
                                    L.Toast("Email không đúng định dạng");
                                    break;
                                case "ERROR_WRONG_PASSWORD":
                                    L.Toast("Mật khẩu không chính xác");
                                    break;
                                case "ERROR_USER_NOT_FOUND":
                                    L.Toast("Tài khoản không tồn tại trong hệ thống");
                                    break;
                                default:
                                    L.Toast("Đã có lỗi xảy ra, vui lòng đăng nhập lại");
                                    break;
                            }
                        } else {
                            Intent iMainAct = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(iMainAct);
                        }

                        // ...
                    }

                });
    }

}
