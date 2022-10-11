package com.example.wichat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;

public class AcountSakuSeiGamen extends AppCompatActivity {
    private static final int MY_QUEST_CODE = 10;
    private Button backButton;//nút quay về
    private Button nextButton;//nút đi tiếp
    private EditText edtAcount;//tên
    private EditText edtConfirmationAcount;//xác nhận lại tên
    private EditText edtEmail;//email
    private EditText edtConfirmationEmail;//xác nhận email
    private String password;
    private int verificationCode;
    private String profilePicture;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_saku_sei_gamen);
        anhXa();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });
    }
    private void anhXa(){
        edtAcount= findViewById(R.id.edtAcount);
        edtConfirmationAcount= findViewById(R.id.edtConfirmationAcount);
        edtEmail= findViewById(R.id.edtEmail);
        edtConfirmationEmail= findViewById(R.id.edtConfirmationEmail);
        password = "";
        verificationCode = 0;
        profilePicture = "";

        backButton= findViewById(R.id.backButton);
        nextButton= findViewById(R.id.nextButton);
    }
    private void next(){
        if (edtAcount.getText().toString().isEmpty()||
                edtConfirmationAcount.getText().toString().isEmpty()||
                edtConfirmationEmail.getText().toString().isEmpty()||
                edtEmail.getText().toString().isEmpty()){
            Toast.makeText(AcountSakuSeiGamen.this, "値がない所発見", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!edtAcount.getText().toString().equals(edtConfirmationAcount.getText().toString())){
            Toast.makeText(AcountSakuSeiGamen.this, "アカウント入力エラー", Toast.LENGTH_LONG).show();
            return;
//                    startActivity(new Intent(AcountSakuSeiGamen.this,AcountSakuSeiGamen.class));
        }
        if (!edtEmail.getText().toString().equals(edtConfirmationEmail.getText().toString())){
            Toast.makeText(AcountSakuSeiGamen.this, "メール入力エラー", Toast.LENGTH_LONG).show();
            return;
//                    startActivity(new Intent(AcountSakuSeiGamen.this,AcountSakuSeiGamen.class));

        }
        if (edtAcount.getText().toString().equals(edtConfirmationAcount.getText().toString()) &&edtEmail.getText().toString().equals(edtConfirmationEmail.getText().toString())){
            String acount = edtAcount.getText().toString().trim();
            String email = edtEmail.getText().toString().trim();
            User user = new User(acount,email,password,verificationCode,profilePicture);
            Bundle bundle = new Bundle();
            bundle.putSerializable("acountEmail",  user);
            Intent intent = new Intent(AcountSakuSeiGamen.this,PasswordSettei.class);
            intent.putExtras(bundle);
            startActivityForResult(intent,MY_QUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (MY_QUEST_CODE == requestCode && resultCode == Activity.RESULT_OK) {
            User user = (User)data.getExtras().get("acountEmailPassword");
            edtAcount.setText(user.getUsernameAcount());
            edtConfirmationAcount.setText(user.getUsernameAcount());
            edtEmail.setText(user.getEmail());
            edtConfirmationEmail.setText(user.getEmail());
            password = user.getPassword();
            verificationCode = user.getVerificationCode();
            profilePicture = user.getProfilePicture();
        }
    }
}
