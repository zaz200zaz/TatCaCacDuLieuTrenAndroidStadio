package com.example.wichat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PasswordHenko extends AppCompatActivity {
    String data;
    private FirebaseAuth auth;
//    private EditText edtPassword_henko;
//    private EditText edtConfirmationPassword_henko;

    private ArrayList<User> users;
    private UserAdapter userAdapter;//bộ điều hợp người dùng
    UserAdapter.OnUserClickListener onUserClickListener;//khi mà ấn vào từng người dùng
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_henko);
        data = getIntent().getStringExtra("acount_ninsyo");
        anhXa();

//        if (edtPassword_henko.getText().toString().isEmpty()||edtConfirmationPassword_henko.getText().toString().isEmpty()){
//            Toast.makeText(this, "入力エラー", Toast.LENGTH_SHORT).show();
//        }else if(edtPassword_henko.getText().toString().equals(edtConfirmationPassword_henko.getText().toString())){
//
//        }else{
//            Toast.makeText(this, "上と下のパスワードが同じではない", Toast.LENGTH_SHORT).show();
//        }

        findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PasswordHenko.this,AcountNinSyou.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.nextButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (edtPassword_henko.getText().toString().isEmpty()||edtConfirmationPassword_henko.getText().toString().isEmpty()){
//                    Toast.makeText(PasswordHenko.this, "入力エラー", Toast.LENGTH_SHORT).show();
//                }else if(edtPassword_henko.getText().toString().equals(edtConfirmationPassword_henko.getText().toString())){
//                    getUsers();
                    resetUserPassword(data);
                    Intent intent = new Intent(PasswordHenko.this,MiTouRoku.class);
                    startActivity(intent);
//                }else{
//                    Toast.makeText(PasswordHenko.this, "上と下のパスワードが同じではない", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
    private void anhXa(){
//        edtPassword_henko = findViewById(R.id.edtPassword_henko);
//        edtConfirmationPassword_henko = findViewById(R.id.edtConfirmationPassword_henko);
        users = new ArrayList<>();
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
    private  void getUsers(){
//        users.clear();//xóa sạch người dùng
//        FirebaseDatabase.getInstance().getReference("user")//lấy dữ liệu từ user
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                            users.add(dataSnapshot.getValue(User.class));
//                        }
//                        for (User user: users){
//                            if (data.equals(user.getEmail())){
//                                        FirebaseDatabase  database = FirebaseDatabase.getInstance();
//                                        DatabaseReference mDatabaseRef = database.getReference("user");
//
//                                        mDatabaseRef.setValue(edtPassword_henko.getText().toString());
////                                user.setPassword(edtPassword_henko.getText().toString());
//                                //updatePassword(edtPassword_henko.getText().toString());
//                                Toast.makeText(PasswordHenko.this, "パスワード変更成功", Toast.LENGTH_SHORT).show();
//                                return;
//                            }else{
//
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(PasswordHenko.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
    }
    //chuyển url chuỗi
    private void updatePassword(String ulr){
        //ở đây chúng ta sẽ cài đặt đường dẫn tại đây
//        FirebaseDatabase  database = FirebaseDatabase.getInstance();
//        DatabaseReference mDatabaseRef = database.getReference();
//
//        mDatabaseRef.child("user").child("password").setValue(ulr+"");
//        FirebaseDatabase.getInstance().getReference("user/password").setValue(ulr);
    }
//    private void updatePasswords(String ulr){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myref = database.getReference();
//        myref.child("user/password").child(ulr).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                dataSnapshot.getRef().child("leftSpace").setValue(newValue);
//                dialog.dismiss();
//
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.d("User", databaseError.getMessage());
//            }
//        });
//    }
//    }

}
