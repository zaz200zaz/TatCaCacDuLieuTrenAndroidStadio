package com.example.wichat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AcountNinSyou extends AppCompatActivity {
    private static final int MY_QUEST_CODE = 10;
    private EditText edtAcount_ninSyo;
    private EditText edtConfirmationPassword_ninSyo;
    private ArrayList<User> users;
    private boolean aBoolean = false;
    private UserAdapter userAdapter;//bộ điều hợp người dùng
    UserAdapter.OnUserClickListener onUserClickListener;//khi mà ấn vào từng người dùng
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acount_nin_syou);

        anhXa();

        if (edtAcount_ninSyo.getText().toString().isEmpty()||edtConfirmationPassword_ninSyo.getText().toString().isEmpty()){
            Toast.makeText(this, "入力エラー", Toast.LENGTH_SHORT).show();
        }

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcountNinSyou.this,MiTouRoku.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.nextButton_ninSyo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getUsers();
            }
        });
    }

    private void anhXa(){
        edtAcount_ninSyo = findViewById(R.id.edtAcount_ninSyo);
        edtConfirmationPassword_ninSyo = findViewById(R.id.edtConfirmationPassword_ninSyo);
        users = new ArrayList<>();
    }
    private  void getUsers(){
        users.clear();//xóa sạch người dùng
        FirebaseDatabase.getInstance().getReference("user")//lấy dữ liệu từ user
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                            users.add(dataSnapshot.getValue(User.class));
                        }

                        aBoolean = false;
                        for (User user: users){
                            int x = Integer.parseInt(edtConfirmationPassword_ninSyo.getText().toString());
                            if (user.getEmail().equals(edtAcount_ninSyo.getText().toString())&&user.getVerificationCode()==x) {
                                aBoolean = true;
                                if (aBoolean) {
                                    String s =edtAcount_ninSyo.getText().toString().trim();
                                    resetUserPassword(s);
                                    Intent intent = new Intent(AcountNinSyou.this, MiTouRoku.class);
//                                    intent.putExtra("acount_ninsyo",s);
                                    startActivity(intent);
                                }
                                return;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AcountNinSyou.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void resetUserPassword(String email){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("verifying..");
        progressDialog.show();

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "パスワードのリセット手順がメールに送信されました",
                            Toast.LENGTH_SHORT).show();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            "メールが存在しません", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
