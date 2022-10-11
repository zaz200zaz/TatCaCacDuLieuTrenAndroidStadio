package com.example.wichat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class PasswordSettei extends AppCompatActivity {
    private static final int MY_QUEST_CODE = 10;
    private Button nextButton;
    private Button backButton;//nút quay về
    private String UsernameAcount;//tên
    private String Email;//email
    private EditText edtPassword;
    private EditText edtConfirmationPassword;
    private int verificationCode;
    private String profilePicture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_settei);
        anhxa();

        User user = (User) getIntent().getExtras().get("acountEmail");

        UsernameAcount = user.getUsernameAcount();
        Email = user.getEmail();
        edtPassword.setText(user.getPassword());
        edtConfirmationPassword.setText(user.getPassword());
        verificationCode = user.getVerificationCode();
        profilePicture = user.getProfilePicture();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextActivity();
            }
        });
    }

    private void anhxa(){

        UsernameAcount = "";
        Email = "";
        edtPassword = findViewById(R.id.edtPassword);
        edtConfirmationPassword=findViewById(R.id.edtConfirmationPassword);
        verificationCode = 0;
        profilePicture = "";

        nextButton = findViewById(R.id.nextButton);
        backButton= findViewById(R.id.backButton);
    }
    private void nextActivity(){
        if (edtPassword.getText().toString().isEmpty()||
                edtConfirmationPassword.getText().toString().isEmpty()){
            Toast.makeText(PasswordSettei.this, "値がない所発見", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtPassword.getText().toString().equals(edtConfirmationPassword.getText().toString())){
            Toast.makeText(PasswordSettei.this, "パスワード入力成功", Toast.LENGTH_SHORT).show();
            String S_password = edtPassword.getText().toString().trim();
            User user = new User(UsernameAcount,Email,S_password,verificationCode,profilePicture);
            Bundle bundle = new Bundle();
            bundle.putSerializable("acountEmailPassword",  user);
            Intent intent = new Intent(PasswordSettei.this,NinSyouGamen.class);
            intent.putExtras(bundle);
            startActivityForResult(intent,MY_QUEST_CODE);
        }else{
            Toast.makeText(PasswordSettei.this, "パスワード入力エラー", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    private void backActivity() {

        Toast.makeText(PasswordSettei.this, "戻る", Toast.LENGTH_SHORT).show();
        String S_password = edtPassword.getText().toString().trim();
        User user = new User(UsernameAcount,Email,S_password,verificationCode,profilePicture);
        Bundle bundle = new Bundle();
        bundle.putSerializable("acountEmailPassword",user);
        Intent intent = new Intent(PasswordSettei.this,NinSyouGamen.class);
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (MY_QUEST_CODE==requestCode && resultCode == Activity.RESULT_OK){
//            User user = (User) data.getExtras().get("emailAcountPasswordverificationCode");
//            email = user.getEmail();
//            acount = .setText(user.getAcount());
//            password=user.getPassword();

        }
    }
}
