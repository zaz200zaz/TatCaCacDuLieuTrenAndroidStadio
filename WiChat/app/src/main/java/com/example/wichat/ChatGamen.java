package com.example.wichat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatGamen extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE_CODE = 0x01;
    private static String[] mPermissions = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };
    private RecyclerView recyclerView;//hiển thị danh sách bạn bè
    private ArrayList<Message> messages;  //mảng danh sách người dùng
    private ArrayList<User> users;  //mảng danh sách người dùng
    private ArrayList<User> users2;  //mảng danh sách người dùng
    private ProgressBar progressBar;//hình tròn đợi sử lý
    private UserAdapter userAdapter;//bộ điều hợp người dùng
    UserAdapter.OnUserClickListener onUserClickListener;//khi mà ấn vào từng người dùng
    String myUsername;
    String usernameOfTheRoommate,emailOfRoommate,chatRoomId;

    private SwipeRefreshLayout swipeRefreshLayout;

    String myImageUrl;

    private TextView txtChatGamen;

    private String EmailForUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    //Tham khảo cơ sở dữ liệu tại user
    private DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("user");

    private ImageButton imageButtonMessage;
    private ImageButton imageButtonRenrakuSaki;
    private ImageButton imageButtonHakken;
    private ImageButton imageButtonJibun;
    private EditText edtKensaku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_gamen);
        int readPermission = ContextCompat.checkSelfPermission(ChatGamen.this, mPermissions[0]);
        int writePermission = ContextCompat.checkSelfPermission(ChatGamen.this, mPermissions[1]);

        if (writePermission != PackageManager.PERMISSION_GRANTED ||
                readPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ChatGamen.this,
                    mPermissions,
                    REQUEST_EXTERNAL_STORAGE_CODE
            );
        }
        anhXa();
        getAcount();
        //đặt bật làm mới Layout
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();//lấy mảng  người dùng
                swipeRefreshLayout.setRefreshing(false);//kéo làm mới tắt
            }
        });
        //khi ấn vào từng người dùng sẽ lấy ra các giá trị như tên , mail, ảnh ,tên phòn chát
        onUserClickListener = new UserAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(int positon) {//vị trí
                startActivity(new Intent(ChatGamen.this,MessagerActivity.class)
                        .putExtra("username_of_roomate",users.get(positon).getUsernameAcount())//tuấn
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
        //gọi người sử dụng ra
        getUsers();
        // ánh xạ các thành phần của layout

        //chuyển đến renraku
        imageButtonRenrakuSaki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ChatGamen.this,Renrakuchou.class));
            }
        });
        //chuyển đến hakken
        imageButtonHakken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ChatGamen.this,Hakken.class));
            }
        });
        //chuyển đến jibun
        imageButtonJibun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ChatGamen.this,Jibun.class));
            }
        });
        findViewById(R.id.img_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtKensaku.getText().toString().trim().isEmpty()){
                    Intent intent = new Intent(ChatGamen.this,ChatGamen_3.class);
                    intent.putExtra("KhoaTimKiem",edtKensaku.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(ChatGamen.this, "検索のところに入力してください！", Toast.LENGTH_SHORT).show();
                }
                
            }
        });

    }
    //ánh xạ các thành phần
    private void anhXa(){
        edtKensaku = findViewById(R.id.edtKensaku);
        progressBar = findViewById(R.id.progressBar);//hình tròn quay ở giữa
        users = new ArrayList<>();//mảng người dùng
        users2 = new ArrayList<>();//mảng người dùng
        recyclerView = findViewById(R.id.recyclerview);//dang xem trình tái chế
        swipeRefreshLayout = findViewById(R.id.swipeLayout);//trượt nhanh làm mới bố trí

        imageButtonMessage = findViewById(R.id.imgChatGamen);
        imageButtonRenrakuSaki = findViewById(R.id.imgRenrakuSaki);
        imageButtonHakken = findViewById(R.id.imgHatkken);
        imageButtonJibun = findViewById(R.id.imgJibun);

        txtChatGamen = findViewById(R.id.txtChatGamen);

        imageButtonMessage.setColorFilter(Color.GREEN);
        txtChatGamen.setTextColor(Color.GREEN);
    }

    //lấy dữ liêu người dùng
    private  void getUsers(){
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
                userAdapter = new UserAdapter(users,ChatGamen.this,onUserClickListener,myUsername);
                recyclerView.setLayoutManager(new LinearLayoutManager(ChatGamen.this));
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
                Toast.makeText(ChatGamen.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getAcount(){
        FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
             myUsername = snapshot.getValue(User.class).getUsernameAcount();//lấy tên người dùng trong database
            return;
        }


        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    });
    }

}
