package com.example.wichat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;

public class Jibun extends AppCompatActivity {
    private RecyclerView recyclerView;//hiển thị danh sách bạn bè
    private ArrayList<User> users;  //mảng danh sách người dùng
    private ProgressBar progressBar;//hình tròn đợi sử lý
    private JibunImageAdapter userAdapter;//bộ điều hợp người dùng
    UserAdapter.OnUserClickListener onUserClickListener;//khi mà ấn vào từng người dùng

    private SwipeRefreshLayout swipeRefreshLayout;

    String myImageUrl;
    String myImageUrl2;
    String myImageUrl3;


    private Uri imagePath;
    private TextView txtimgJibun;
    private TextView textView6;private TextView textView7;
    private Bitmap bitmap2;
    private ImageView imgProfile;
    private ImageButton imageButtonMessage;
    private ImageButton imageButtonRenrakuSaki;
    private ImageButton imageButtonHakken;
    private ImageButton imageButtonJibun;
    private ImageView cameraProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jibun);
        anhXa();
        coloMenu();
        //về chỗ nhắn tin
        imageButtonMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Jibun.this,ChatGamen.class));
            }
        });
        //về nơi liên lạc
        imageButtonRenrakuSaki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Jibun.this,Renrakuchou.class));
            }
        });
        //về nơi tìm kiếm
        imageButtonHakken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Jibun.this,Hakken.class));
            }
        });
        //thoát
        findViewById(R.id.btnLogOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chung toi muon dang xuat va bat dau 1 hoat dong moi
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Jibun.this,MainActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();//ket thuc hoat dong
            }
        });
        //thoát
        findViewById(R.id.textView10).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //chung toi muon dang xuat va bat dau 1 hoat dong moi
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Jibun.this,MainActivity.class).
                        setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();//ket thuc hoat dong
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Jibun.this,Profile.class));
            }
        });
        getUsers();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            imagePath = data.getData();
            getImageInImageView();
        }
    }

    private void getImageInImageView() {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),imagePath);
            bitmap2=bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void anhXa(){
        textView6 = findViewById(R.id.textView6);textView7 = findViewById(R.id.textView7);
        imageButtonMessage = findViewById(R.id.imgChatGamen);
        imageButtonRenrakuSaki = findViewById(R.id.imgRenrakuSaki);
        imageButtonHakken = findViewById(R.id.imgHatkken);
        imageButtonJibun = findViewById(R.id.imgJibun);
        imgProfile = findViewById(R.id.profile_img);
        cameraProfile = findViewById(R.id.cameraProfile);


        txtimgJibun = findViewById(R.id.txtimgJibun);
        users = new ArrayList<>();
    }
    private void coloMenu(){
        imageButtonJibun.setColorFilter(Color.GREEN);
        txtimgJibun.setTextColor(Color.GREEN);
    }
//    private Bitmap getImageBitmap(String url) {
//        Bitmap bm = null;
//        try {
//            URL aURL = new URL(url);
//            URLConnection conn = aURL.openConnection();
//            conn.connect();
//            InputStream is = conn.getInputStream();
//            BufferedInputStream bis = new BufferedInputStream(is);
//            bm = BitmapFactory.decodeStream(bis);
////            cameraProfile.setImageBitmap(bm);
//            bis.close();
//            is.close();
//        } catch (IOException e) {
//            Log.e("tuan", "Error getting bitmap", e);
//        }
//        return bm;
//    }
    private  void getUsers(){
        users.clear();
        FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    users.add(dataSnapshot.getValue(User.class));
                }
                userAdapter = new JibunImageAdapter(users,Jibun.this, (JibunImageAdapter.OnUserClickListener) onUserClickListener);

                for (User user: users){
                    if (user.getEmail().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                        myImageUrl2 = user.getUsernameAcount();
                        myImageUrl3 = user.getEmail();
                        textView7.setText(myImageUrl3);
                        textView6.setText(myImageUrl2);myImageUrl = user.getProfilePicture();
                        Glide.with(Jibun.this).load(myImageUrl).into(cameraProfile);

                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Jibun.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getProfilePicture(String ulr){
        //ở đây chúng ta sẽ cài đặt đường dẫn tại đây
        FirebaseDatabase.getInstance().getReference("user/" + FirebaseAuth.getInstance().getCurrentUser().getUid()+"/profilePicture").child(ulr);
    }
//    @Override
//    public void onBindViewHolder(@NonNull UserAdapter.UserHolder holder, int position) {
////        holder.txtUsername.setText(users.get(position).getUsernameAcount());
//        Glide.with(context).load(users.get(position).getProfilePicture()).error(R.drawable.account_img).placeholder(R.drawable.account_img).into(holder.imageView);
//    }


}