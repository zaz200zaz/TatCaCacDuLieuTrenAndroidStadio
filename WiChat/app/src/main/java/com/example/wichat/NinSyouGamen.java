package com.example.wichat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class NinSyouGamen extends AppCompatActivity {

    private static final int MY_QUEST_CODE = 10;
    private String UsernameAcount;//tên
    private String Email;//email
    private String Password;
    private String edtConfirmationPassword;
    private int verificationCode;
    private String profilePicture;
    private EditText edtVerificationCode;
    private TextView txtVerificationCode;

    private Button finishButton;
    private Button backButton;//nút quay về

    private Button button;
    private ImageButton verificationCodeChangeImageButton;
    AcountSakuSeiGamen acountSakuSeiGamen;
    PasswordSettei passwordSettei;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nin_syou_gamen);
        anhxa();

        User user = (User) getIntent().getExtras().get("acountEmailPassword");

        UsernameAcount = user.getUsernameAcount();
        Email = user.getEmail();
        Password = user.getPassword();
        verificationCode = user.getVerificationCode();
        profilePicture = user.getProfilePicture();
        edtVerificationCode.setText("");

        verificationCode = random();
        txtVerificationCode.setText(verificationCode+"");

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backActivity();
            }
        });

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSignUp();
            }
        });

        verificationCodeChangeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificationCode = random();
                txtVerificationCode.setText(verificationCode+"");
            }
        });
    }
    private void anhxa(){
        UsernameAcount = "";
        Email = "";
        Password = "";
        verificationCode =0;
        profilePicture = "";

        backButton= findViewById(R.id.backButton);
        edtVerificationCode = findViewById(R.id.edtVerificationCode);
        txtVerificationCode = findViewById(R.id.txtVerificationCode);

        finishButton = findViewById(R.id.finishButton);
        verificationCodeChangeImageButton = findViewById(R.id.verificationCodeChangeImageButton);
    }
    private int random(){
        return (int) Math.floor(((Math.random()*899999)+100000));
    }
        private void handleSignUp(){
        if (edtVerificationCode.getText().toString().isEmpty()||UsernameAcount.toString().isEmpty()||Email.toString().isEmpty()||
                Password.toString().isEmpty()){
            Toast.makeText(this, "入力エラー", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtVerificationCode.getText().equals(txtVerificationCode.getText())){
            Toast.makeText(this, "認証コード入力エラー", Toast.LENGTH_SHORT).show();
            return;
        }
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(Email.toString(),Password.toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(new User(
                                        UsernameAcount.toString(),
                                        Email.toString(),
                                        Password.toString(),
                                        verificationCode,"")
                                );
                            startActivity(new Intent(NinSyouGamen.this,ChatGamen.class));
                            Toast.makeText(NinSyouGamen.this,"Sign UP Successful",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(NinSyouGamen.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
        });
    }

    private void backActivity() {

        Toast.makeText(NinSyouGamen.this, "戻る", Toast.LENGTH_SHORT).show();
        User user = new User(UsernameAcount,Email,Password,verificationCode,profilePicture);
        Bundle bundle = new Bundle();
        bundle.putSerializable("emailAcountPasswordverificationCode",user);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }
}
