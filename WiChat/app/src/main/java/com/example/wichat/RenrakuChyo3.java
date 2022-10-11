package com.example.wichat;

import androidx.annotation.NonNull;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.LinearLayoutManager;

        import android.graphics.drawable.Drawable;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.ImageView;
        import android.widget.Toast;

        import com.bumptech.glide.Glide;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
        import java.util.List;

public class RenrakuChyo3 extends AppCompatActivity {
    private ArrayList<User> users;  //mảng danh sách người dùng

    private DatabaseReference mDatabase;
// ...


    private DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("user");
    //    private String UserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private ImageView imageViewProfile;
    private EditText editTextName;
    private Button buttonHenSyuu;
//    private String imageUrl = getIntent().getStringExtra("img_of_roommate");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renraku_chyo3);
        getSupportActionBar().setTitle("連絡先");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        anhXa();
        caiDatLaiTen();

//        editTextName.setText(UserUid);
        Glide.with(RenrakuChyo3.this).load(getIntent().getStringExtra("img_of_roommate")).placeholder(R.drawable.account_img).error(R.drawable.account_img).into(imageViewProfile);
        String sdas = getIntent().getStringExtra("username_of_roomate");
//        Glide.with(MainActivity2.this).load(getIntent().getStringExtra("username_of_roomate")).into(editTextName);
        editTextName.setText(sdas);
    }


    private void anhXa() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        users = new ArrayList<>();
        editTextName = findViewById(R.id.editTextTextPersonName);
        buttonHenSyuu = findViewById(R.id.button);
        imageViewProfile = findViewById(R.id.img_toolbar);
    }

    private void caiDatLaiTen() {
        buttonHenSyuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextName.setEnabled(true);
                resetName(editTextName.getText().toString().trim());
//                updateName("test");
            }
        });
        findViewById(R.id.MainActivity2Id).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextName.setEnabled(false);
            }
        });
    }

    private void resetName(String name) {
        List<String> stringList = new ArrayList<>();
        stringList.clear();
        users.clear();//xóa sạch người dùng
//        users2.clear();//xóa sạch người dùng
        //lấy dữ liệu từ user
        databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String userGmail = dataSnapshot.getValue(User.class).getEmail();//email cua moi nguoi
                    String ult = dataSnapshot.getKey();//ma UID cua email
                    String sds = getIntent().getStringExtra("email_of_roommate");//ten nguoi dc an
                    if (userGmail.equals(sds)){
//                        stringList.add(ult);
//                        users.add(dataSnapshot.getValue(User.class));
                        updateName(name,ult);
                        return;
                    }
                    Log.d("TAG", "onDataChange: "+ult);
//                    users2.add(dataSnapshot.getValue(User.class));
                }

//                restName = users.get(0).getUsernameAcount();
//                userAdapter = new UserAdapter(users,MainActivity.this,onUserClickListener,users.get(0).getUsernameAcount());
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//                recyclerView.setAdapter(userAdapter);
//                progressBar.setVisibility(View.GONE);
//                recyclerView.setVisibility(View.VISIBLE);
                //lấy ảnh người gửi
//                for (User user: users2){
//                    if (user.getEmail().equals(EmailForUser)){
//                        myImageUrl = user.getProfilePicture();
//                        return;
//                    }
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RenrakuChyo3.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void updateName(String name , String UserUids){
        //ở đây chúng ta sẽ cài đặt đường dẫn tại đây
//        FirebaseDatabase.getInstance().getReference("user/" + UserUids+"/usernameAcount").setValue(name);
        mDatabase.child("user").child(UserUids).child("usernameAcount").setValue(name);
    }
}