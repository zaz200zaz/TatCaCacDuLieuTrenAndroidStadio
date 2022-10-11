package com.example.ankiapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText edtUsername,edtPassword,edtEmail;
    private Button btnSubmit;
    private TextView txtLoginInfo;
    private boolean isSigningUp=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmail=findViewById(R.id.edtEmail);
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        btnSubmit=findViewById(R.id.btnSubmit);
        txtLoginInfo=findViewById(R.id.txtLoginInfo);
        if (FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(MainActivity.this, com.example.ankiapp.FriendsActivity.class));
            finish();
        }

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtEmail.getText().toString().isEmpty()||edtPassword.getText().toString().isEmpty())
                    if (isSigningUp&&edtUsername.getText().toString().isEmpty()){
                        Toast.makeText(MainActivity.this,"Invalid input",Toast.LENGTH_SHORT).show();
                        return;
                    }

                if (isSigningUp){
                    handleSignUp();
                }else{
                    handleLogin();
                }

            }
        });

        txtLoginInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isSigningUp){
                    isSigningUp=false;
                    btnSubmit.setText("ログイン");
                    edtUsername.setVisibility(View.GONE);
                    txtLoginInfo.setText("don't have an account?Sign up");
                }else{
                    isSigningUp=true;
                    btnSubmit.setText("サインアップ");
                    edtUsername.setVisibility(View.VISIBLE);
                    txtLoginInfo.setText("already have account?Log in");
                }

            }
        });

    }
    private void handleSignUp(){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new com.example.ankiapp.User(edtUsername.getText().toString(),edtEmail.getText().toString(),""));
                    startActivity(new Intent(MainActivity.this, com.example.ankiapp.FriendsActivity.class));
                    Toast.makeText(MainActivity.this,"signed up successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("TAG", "onComplete: "+task.getException().getLocalizedMessage());
                    Toast.makeText(MainActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
    private void handleLogin(){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(edtEmail.getText().toString(),edtPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, com.example.ankiapp.FriendsActivity.class));
                    Toast.makeText(MainActivity.this,"logged in successfully",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}