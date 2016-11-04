package edu.hutech.shippermanager.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.hutech.shippermanager.R;
import edu.hutech.shippermanager.common.L;

public class LoginActivity extends BaseActivity {

    private FirebaseAuth mAuth;
    @BindView(R.id.editTextEmail)
    EditText edtEmail;
    @BindView(R.id.editTextPassword)
    EditText edtPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();

    }

    @OnClick(R.id.buttonLogin)
    public void Login(Button btnLogin) {
        if (TextUtils.isEmpty(edtEmail.getText())) {
            L.Toast("Vui lòng nhập email");
            return;
        }
        if (TextUtils.isEmpty(edtPass.getText())) {
            L.Toast("Vui lòng nhập Password");
            return;
        }
        mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(), edtPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Không đăng nhập được !!!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Intent it1 = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(it1);
                        }

                        // ...
                    }

                });
    }

}
