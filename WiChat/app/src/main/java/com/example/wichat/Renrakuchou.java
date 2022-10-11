package com.example.wichat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Renrakuchou extends AppCompatActivity {
    private RecyclerView recyclerView;//hiển thị danh sách bạn bè
    private ArrayList<Message> messages;  //mảng danh sách người dùng
    private ArrayList<User> users;  //mảng danh sách người dùng
    private ArrayList<User> users2;  //mảng danh sách người dùng
    private ProgressBar progressBar;//hình tròn đợi sử lý
    private renRakuchoAdapter userAdapter;//bộ điều hợp người dùng
    renRakuchoAdapter.OnUserClickListener onUserClickListener;//khi mà ấn vào từng người dùng
    String myUsername;
    String usernameOfTheRoommate,emailOfRoommate,chatRoomId;

    private SwipeRefreshLayout swipeRefreshLayout;

    String myImageUrl;

    private TextView txtChatGamen;

    private String EmailForUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    //Tham khảo cơ sở dữ liệu tại user
    private DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("user");

    private EditText edtKensaku;

    //    private String EmailForUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    //Tham khảo cơ sở dữ liệu tại user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_renrakuchou);

        anhXa();
        datBatLamMoi();
        getUsers();
        khiAnVaoTungThanhVien();

    }

    private void khiAnVaoTungThanhVien() {
        onUserClickListener = new renRakuchoAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(int positon) {//vị trí
                startActivity(new Intent(Renrakuchou.this,RenrakuChyo3.class)
                                .putExtra("username_of_roomate",users.get(positon).getUsernameAcount())//tuấn
//                        .putExtra("username_of_name",users.get(positon).getEmail())//tuấn
                                .putExtra("email_of_roommate",users.get(positon).getEmail())//tranthanhtuan199981@gmail.com
                                .putExtra("img_of_roommate",users.get(positon).getProfilePicture())//ảnh
                                .putExtra("my_image",myImageUrl)//địa chỉ ảnh
                                .putExtra("username_of_roomate2",users2.get(positon).getUsernameAcount())//tuấn
                                .putExtra("email_of_roommate2",users2.get(positon).getEmail())//tranthanhtuan199981@gmail.com
                                .putExtra("img_of_roommate2",users2.get(positon).getProfilePicture())//ảnh
                                .putExtra("my_image2",myImageUrl)//địa chỉ ảnh
                );

            }
        };
    }

    private void datBatLamMoi() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();//lấy mảng  người dùng
                swipeRefreshLayout.setRefreshing(false);//kéo làm mới tắt
            }
        });
    }

    private void getUsers() {
        users.clear();//xóa sạch người dùng
        users2.clear();//xóa sạch người dùng
        //lấy dữ liệu từ user
        databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String userGmail = dataSnapshot.getValue(User.class).getEmail();
                    String sds = EmailForUser;
                    if (!userGmail.equals(EmailForUser)){
                        users.add(dataSnapshot.getValue(User.class));
                    }
                    users2.add(dataSnapshot.getValue(User.class));
                }
                userAdapter = new renRakuchoAdapter(users,Renrakuchou.this, (renRakuchoAdapter.OnUserClickListener) onUserClickListener,users.get(0).getUsernameAcount());
                recyclerView.setLayoutManager(new LinearLayoutManager(Renrakuchou.this));
                recyclerView.setAdapter(userAdapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                //lấy ảnh người gửi
                for (User user: users2){
                    if (user.getEmail().equals(EmailForUser)){
                        myImageUrl = user.getProfilePicture();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Renrakuchou.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhXa(){
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerview);//dang xem trình tái chế
        swipeRefreshLayout = findViewById(R.id.swipeLayout);//trượt nhanh làm mới bố trí
        users = new ArrayList<>();//mảng người dùng
        users2 = new ArrayList<>();//mảng người dùng
//        imageButtonMessage = findViewById(R.id.imgChatGamen);
//        imageButtonRenrakuSaki = findViewById(R.id.imgRenrakuSaki);
//        imageButtonHakken = findViewById(R.id.imgHatkken);
//        imageButtonJibun = findViewById(R.id.imgJibun);

        txtChatGamen = findViewById(R.id.txtChatGamen);
//        txtimgRenrakuSaki = findViewById(R.id.txtimgRenrakuSaki);
//        txtimgHatkken = findViewById(R.id.txtimgHatkken);
//        txtimgJibun = findViewById(R.id.txtimgJibun);
//
//        imageButtonRenrakuSaki.setColorFilter(Color.GREEN);
//        txtimgRenrakuSaki.setTextColor(Color.GREEN);
    }
}