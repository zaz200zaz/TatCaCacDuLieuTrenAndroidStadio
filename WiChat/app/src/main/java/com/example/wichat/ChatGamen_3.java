package com.example.wichat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatGamen_3 extends AppCompatActivity {
    private String timKiem;
    private RecyclerView recyclerView;//hiển thị danh sách bạn bè
    protected FineAdapter fineAdapter;//bộ điều hợp người dùng
    private ArrayList<Message>messageLayDulieu;
    private ArrayList<FineData>tenCacPhongChat;//ten cac phong chat
    private ArrayList<String>roomName;
    protected int count;
    protected String TenPhongchat;
    protected int arrLength;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myRef2;
    protected ArrayList<FineData> fineData;
    private RecyclerView.LayoutManager linearLayoutManager;
    ArrayList<FineData> fineData324;
    private TextView edtKensaku;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_gamen3);
        AnhXa();
        timKiem= getIntent().getStringExtra("KhoaTimKiem");
        edtKensaku.setText(timKiem);
        layTenPhongChat(timKiem);
        findViewById(R.id.img_toolbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChatGamen_3.this,ChatGamen.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void AnhXa(){
        edtKensaku = findViewById(R.id.edtKensaku);
        fineData324 = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        fineData = new ArrayList<>();
        TenPhongchat = "";
        arrLength = 0;
        count = 0;
        database = FirebaseDatabase.getInstance();
        roomName = new ArrayList<>();
        tenCacPhongChat= new ArrayList<>();
        messageLayDulieu = new ArrayList<>();
    }
    private ArrayList<FineData> getFineMessagetest(String chatrmmm,String tenNguoiNhan,String noiDung) {
        fineData324 .add(new FineData(chatrmmm,tenNguoiNhan,noiDung));
        return fineData324;
    }
    private void layTenPhongChat(String hhhh){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("roomNametesr");
        tenCacPhongChat.clear();fineData.clear();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( @NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1hot: dataSnapshot.getChildren()){
                    tenCacPhongChat.add(dataSnapshot1hot.getValue(FineData.class));//thêm tin nhắn vào trong mảng mesage
                }
                int soLuongPhong = tenCacPhongChat.size();
                arrLength = soLuongPhong;
                for (int i = 0; i < tenCacPhongChat.size(); i++) {
                    messageLayDulieu.clear();
                    String nguoiGui;
                    nguoiGui =tenCacPhongChat.get(i).getSendPerson();
                    myRef2 = database.getReference("messages/"+tenCacPhongChat.get(i).getChatRoomName());
                    myRef2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange( @NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot dataSnapshot1hot: dataSnapshot.getChildren()){
                                messageLayDulieu.add(dataSnapshot1hot.getValue(Message.class));//thêm tin nhắn vào trong mảng mesage
                            }
                            count++;
                            for ( int i = 0; i<messageLayDulieu.size();i++){
                                if (messageLayDulieu.get(i).getConten().indexOf(hhhh)>-1){
                                    fineAdapter = new FineAdapter(getFineMessagetest("",nguoiGui,messageLayDulieu.get(i).getConten()));
                                    roomName.add("sender:"+nguoiGui+"=>"+"message:"+messageLayDulieu.get(i).getConten()+"\n");
                                }
                            }
                            RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(ChatGamen_3.this,DividerItemDecoration.VERTICAL);
                            recyclerView.addItemDecoration(itemDecoration);
                            recyclerView.setLayoutManager(linearLayoutManager);
                            recyclerView.setAdapter(fineAdapter);
                            recyclerView.setVisibility(View.VISIBLE);
                            messageLayDulieu.clear();
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                            Log.w("121212", "Failed to read value.", error.toException());
                        }
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
}