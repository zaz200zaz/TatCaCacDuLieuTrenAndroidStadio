package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;//hiển thị danh sách bạn bè
    private ArrayList<User> users;  //mảng danh sách người dùng
    private ArrayList<User> users2;  //mảng danh sách người dùng
    private ProgressBar progressBar;//hình tròn đợi sử lý
    private UserAdapter userAdapter;//bộ điều hợp người dùng
    UserAdapter.OnUserClickListener onUserClickListener;//khi mà ấn vào từng người dùng
    String myUsername;
    private SwipeRefreshLayout swipeRefreshLayout;
    String myImageUrl;
//    private String EmailForUser = FirebaseAuth.getInstance().getCurrentUser().getEmail();
    private String EmailForUser = "tranthanhtuan199981@gmail.com";
    //Tham khảo cơ sở dữ liệu tại user
    private DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("user");
    private DatabaseReference databaseReferenceMessage = FirebaseDatabase.getInstance().getReference("messages");
    private EditText edtKensaku;
    private DatabaseReference mDatabase;
//    private String timKiem;
//    private RecyclerView recyclerView;//hiển thị danh sách bạn bè
//    protected FineAdapter fineAdapter;//bộ điều hợp người dùng
//    private ArrayList<Message> messageLayDulieu;
//    private ArrayList<FineData>tenCacPhongChat;//ten cac phong chat
//    private ArrayList<String>roomName;
    protected int count;
    protected String TenPhongchat;
    protected int arrLength;
//    private FirebaseDatabase database;
//    private DatabaseReference myRef;
//    private DatabaseReference myRef2;
    protected ArrayList<FineData> fineData;
//    private RecyclerView.LayoutManager linearLayoutManager;
    ArrayList<FineData> fineData324;
    List<String> sdas;
//    private TextView edtKensaku;
    private String tenPhongChat;private String tenPhongChatMoi;
    String tenCu,tenMoi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);anhXa();
        String sadasd = "tuyêttutuấn";
        if (sadasd.indexOf(tenPhongChatMoi)==-1){
            Log.d("TAG", "onCreate: false");
        }else{
            Log.d("TAG", "onCreate: true");
            Log.d("TAG", "onCreate: true"+sadasd.replace("tuyêttutuấn","tuyêttuấn"));
        }

        catChuuoi();
//        datBatLamMoi();
//        getUsers();
//        khiAnVaoTungThanhVien();
//        timKiemRenrakusaki();
//       xemDataMessage();

    }

    private void catChuuoi() {
        String []dasd = EmailForUser.split("\\@",2);
        for (String das:dasd
             ) {
            Log.d("TAG", "catChuuoi: "+das);
        }
        Log.d("TAG", "catChuuoi: "+dasd[0]);
    }

    private void xemDataMessage() {
        databaseReferenceMessage.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                 for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                     String s= "";
                     s = String.valueOf(dataSnapshot.getKey());
                     boolean sdas= false;
                     if (s.equals(tenPhongChat)){
                         suaTenPhongChat();
                         sdas = true;
                     }else{
                         sdas = false;
                     }
                     boolean sdsadas= sdas;
//                     Log.d("TAG", "onDataChange: "+s);
//                     Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
                }
            }

            private void suaTenPhongChat() {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void sangActivity2() {
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
                finish();
            }
        });
    }

    private void themTheTrongFirebaseDatabase() {
        String ten = "TRANTHANH TUAN";
        String cachDocTen = "チャンタン トゥアン";
        String gioiTinh = "男性";
        String ngayThangNamSinh = "1999/08/24";
        String quocGia = "ベトナム";
        String soBuuDien = "252-0324";
        String diaChiSong = "神奈川県相模原市南区相武台3丁目10-15ハイツ松風103号室";
        String sdt = "090-4094-2408";
        FirebaseDatabase.getInstance()
                .getReference("user/"+ "NFz7mXkEpfcu8i97nYkmfAilT2C2"+"/個人情報")
                .setValue(new PersonaInformation(ten,cachDocTen,gioiTinh,ngayThangNamSinh,quocGia,soBuuDien,diaChiSong,sdt));
    }

    private void timKiemRenrakusaki() {
        findViewById(R.id.img_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edtKensaku.getText().toString().trim().isEmpty()){
                    Intent intent = new Intent(MainActivity.this,RenrakuSakiGamen2.class);
                    intent.putExtra("KhoaTimKiem",edtKensaku.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(MainActivity.this, "検索のところに入力してください！", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void khiAnVaoTungThanhVien() {
        onUserClickListener = new UserAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(int positon) {//vị trí
                startActivity(new Intent(MainActivity.this,MainActivity2.class)
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
                userAdapter = new UserAdapter(users,MainActivity.this,onUserClickListener,users.get(0).getUsernameAcount());
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
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
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAcount() {
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

    private void anhXa() {
        tenCu = "lan";
        tenMoi = "lanCho";
        tenPhongChatMoi = "tuyêttu";
        tenPhongChat = "tuyêttutuấn";
        sdas= new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);//dang xem trình tái chế
        myUsername ="";
        edtKensaku = findViewById(R.id.edtKensaku);
        progressBar = findViewById(R.id.progressBar);//hình tròn quay ở giữa
        users = new ArrayList<>();//mảng người dùng
        users2 = new ArrayList<>();//mảng người dùng
//        recyclerView = findViewById(R.id.recyclerview);//dang xem trình tái chế
        swipeRefreshLayout = findViewById(R.id.swipeLayout);//trượt nhanh làm mới bố trí

//        edtKensaku = findViewById(R.id.edtKensaku);
        fineData324 = new ArrayList<>();
//        recyclerView = findViewById(R.id.recyclerview);
//        linearLayoutManager = new LinearLayoutManager(this);
        fineData = new ArrayList<>();
        TenPhongchat = "";
        arrLength = 0;
        count = 0;
//        database = FirebaseDatabase.getInstance();
//        roomName = new ArrayList<>();
//        tenCacPhongChat= new ArrayList<>();
//        messageLayDulieu = new ArrayList<>();
    }
}