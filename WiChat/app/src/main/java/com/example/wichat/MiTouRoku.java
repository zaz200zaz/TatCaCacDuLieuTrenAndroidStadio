package com.example.wichat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MiTouRoku extends AppCompatActivity {
    private EditText edtEmail;
    private EditText edtPassword;
    private Button loginButton;
    private Button forgotPasswordButton;
    private Button createAcountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_tou_ro_ku);
        hansa();
        //khi có tài khoản trong sever thì ko quay lại màn hình đăng nhập nữa
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(MiTouRoku.this,ChatGamen.class));
            finish();
        }
        //khi nhấn nút đăng kí thì sẽ đi vào Activity đăng kí
        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEmail.getText().toString().isEmpty()||edtPassword.getText().toString().isEmpty()){
//                    if (edtUsename.getText().toString().isEmpty()){
                        Toast.makeText(MiTouRoku.this, "invalid input", Toast.LENGTH_SHORT).show();
                        return;
//                    }
                }
//
//                    handleSignUp();
//
                    handleLogin();
            }
        });
        findViewById(R.id.forgotPasswordButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MiTouRoku.this,AcountNinSyou.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.createAcountButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MiTouRoku.this,AcountSakuSeiGamen.class);
                startActivity(intent);
            }
        });
    }
    private void hansa(){
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtTextPassword);
        loginButton = findViewById(R.id.loginButton);
        forgotPasswordButton = findViewById(R.id.forgotPasswordButton);
        createAcountButton = findViewById(R.id.createAcountButton);
    }

    //dang nhap
    private void handleLogin(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MiTouRoku.this,ChatGamen.class));
                    Toast.makeText(MiTouRoku.this,"Logged in Successfully ",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MiTouRoku.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
