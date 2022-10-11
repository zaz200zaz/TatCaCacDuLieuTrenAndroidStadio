package com.example.ankiapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EditText editMessageInput;
    private TextView txtChattingWith;
    private ProgressBar progressBar;
    private ImageView imgToolbar,imgSend;
    private MessageAdapter messageAdapter;

    private ArrayList<com.example.ankiapp.Message>messages;

    String usernameOfRoommate, emailOfRoomMate,chatRoomId;
    String my_img;
    String img_of_roommate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        anhXa();
        tapTinNhan();
        guiTinNhan();
        hienThiAnhDoiPhuong();
        setUpChatRoom();
    }

    private void hienThiAnhDoiPhuong() {
        Glide.with(MessageActivity.this)
                .load(getIntent()
                        .getStringExtra("img_of_roommate"))
                .error(R.drawable.account_img)
                .placeholder(R.drawable.account_img).into(imgToolbar);
    }

    private void guiTinNhan() {
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("messages/"+chatRoomId).push().setValue(new com.example.ankiapp.Message(FirebaseAuth.getInstance().getCurrentUser().getEmail(),emailOfRoomMate,editMessageInput.getText().toString()));
                editMessageInput.setText("");

            }
        });
    }

    private void tapTinNhan() {
        messageAdapter=new MessageAdapter(messages,my_img,img_of_roommate,MessageActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);
    }

    private void anhXa() {
        img_of_roommate= getIntent().getStringExtra("img_of_roommate");
        my_img = getIntent().getStringExtra("my_img");
        usernameOfRoommate= getIntent().getStringExtra("username_of_roommate");
        emailOfRoomMate=getIntent().getStringExtra("email_of_roommate");

        recyclerView=findViewById(R.id.recyclerMessages);
        imgSend=findViewById(R.id.imgSendMessage);
        editMessageInput=findViewById(R.id.edtText);
        txtChattingWith=findViewById(R.id.txtChattingWith);
        progressBar=findViewById(R.id.progressMessages);
        imgToolbar=findViewById(R.id.img_toolbar);
        txtChattingWith.setText(usernameOfRoommate);

        messages=new ArrayList<>();
    }

    private void setUpChatRoom(){
        FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String myUsername=snapshot.getValue(com.example.ankiapp.User.class).getUsername();
                if (usernameOfRoommate.compareTo(myUsername)>0){
                    chatRoomId=myUsername+usernameOfRoommate;
                }else if (usernameOfRoommate.compareTo(myUsername)==0){
                    chatRoomId=myUsername+usernameOfRoommate;
                }else {
                    chatRoomId = usernameOfRoommate + myUsername;
                }
                attachMessageListener(chatRoomId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private  void attachMessageListener(String chatRoomId){
        FirebaseDatabase.getInstance().getReference("messages/"+chatRoomId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    messages.add(dataSnapshot.getValue(com.example.ankiapp.Message.class));

                }
                messageAdapter.notifyDataSetChanged();
                recyclerView.scrollToPosition(messages.size()-1);
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}