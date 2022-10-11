package com.example.test2;

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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class MainActivity2 extends AppCompatActivity {
    private TextView txtNameId;
    private TextView txtHuRiGaNaNameId;
    private TextView txtseibetsuId;
    private TextView txtTanjyoBiId;
    private TextView txtKokusekiId;
    private TextView txtYuuBinBanGoId;
    private TextView txtJyuSyoId;
    private TextView txtDenWaBangGoId;
    private DatabaseReference mDatabase;
//    private ArrayList<User> users;  //mảng danh sách người dùng
//
//    private DatabaseReference mDatabase;
//// ...
//
//
//    private DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("user");
////    private String UserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//    private ImageView imageViewProfile;
//    private EditText editTextName;
//    private Button buttonHenSyuu;
//    private String tenTruocKhiThayDoi;private String tenSauKhiThayDoi;
//
//    int count = 1;
List<String> sdas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        anhXa();
        //Để đọc hoặc ghi dữ liệu từ cơ sở dữ liệu, bạn cần một phiên bản của DatabaseReference

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String s1 = "H0HpRZc4ZhN2wxdMC2CuHJ2KjwP2";
                String s2 = "user/"+s1+"/個人情報";
                if (snapshot.hasChild(s2)) {
                    // run some code
                    ganDuLieu();
                }else{
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        mDatabase.child("user/NFz7mXkEpfcu8i97nYkmfAilT2C2").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                sdas.add(task.getResult().getValue(User.class).getProfilePicture());
//                sdas.add(task.getResult().getValue(User.class).getEmail());
//                sdas.add(task.getResult().getValue(User.class).getUsernameAcount());
//                sdas.add(task.getResult().getValue(User.class).getPassword());
////                sdas.add(task.getResult().getValue(User.class).getUsernameAcount());
//                String safasfas = String.valueOf(task.getResult().getValue(User.class).getVerificationCode());
//                sdas.add(safasfas);
//                boolean dsadsa = false;
//                for (int i=0;i<sdas.size();i++){
//                    if (!sdas.get(i).equals("個人情報")){
//                        dsadsa = false;
//                    }else{
//                        dsadsa = true;
//                    }
//                }
//                if (dsadsa){
//                    ganDuLieu();
//                }
//            }
//        });
//        getSupportActionBar().setTitle("連絡先");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        anhXa();
//        caiDatLaiTen();
//
//        Glide.with(MainActivity2.this).load(getIntent().getStringExtra("img_of_roommate")).placeholder(R.drawable.account_img).error(R.drawable.account_img).into(imageViewProfile);
//        String sdas = getIntent().getStringExtra("username_of_roomate");
//
//        editTextName.setText(sdas);
//        tenTruocKhiThayDoi = editTextName.getText().toString().trim();
    }

    private void ganDuLieu() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("user/NFz7mXkEpfcu8i97nYkmfAilT2C2").child("個人情報").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    String name ="";
                    name = String.valueOf(task.getResult().getValue(PersonaInformation.class).getFullName());
                    String sfsadf = name;
                    txtNameId.setText(name);
                    txtHuRiGaNaNameId.setText(String.valueOf(task.getResult().getValue(PersonaInformation.class).getHowToReadName()));
                    txtseibetsuId.setText(String.valueOf(task.getResult().getValue(PersonaInformation.class).getSex()));
                    txtTanjyoBiId.setText(String.valueOf(task.getResult().getValue(PersonaInformation.class).getBirthDay()));
                    txtKokusekiId.setText(String.valueOf(task.getResult().getValue(PersonaInformation.class).getCountry()));
                    txtYuuBinBanGoId.setText(String.valueOf(task.getResult().getValue(PersonaInformation.class).getPostCode()));
                    txtJyuSyoId.setText(String.valueOf(task.getResult().getValue(PersonaInformation.class).getAddress()));
                    txtDenWaBangGoId.setText(String.valueOf(task.getResult().getValue(PersonaInformation.class).getPhoneNumber()));

                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                    Log.d("name:", name);
                }
            }
        });
    }


    private void anhXa() {
        sdas = new ArrayList<>();
        txtNameId = findViewById(R.id.txtNameId);
        txtHuRiGaNaNameId = findViewById(R.id.txtHuRiGaNaNameId);
        txtseibetsuId = findViewById(R.id.txtseibetsuId);
        txtTanjyoBiId = findViewById(R.id.txtTanjyoBiId);
        txtKokusekiId = findViewById(R.id.txtKokusekiId);
        txtYuuBinBanGoId = findViewById(R.id.txtYuuBinBanGoId);
        txtJyuSyoId = findViewById(R.id.txtJyuSyoId);
        txtDenWaBangGoId = findViewById(R.id.txtDenWaBangGoId);
//        mDatabase = FirebaseDatabase.getInstance().getReference();
//        users = new ArrayList<>();
////        editTextName = findViewById(R.id.editTextTextPersonName);
////        buttonHenSyuu = findViewById(R.id.button);
//        imageViewProfile = findViewById(R.id.img_toolbar);
    }

//    private void caiDatLaiTen() {
//        buttonHenSyuu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                count++;//1-2
//               if (count==3||count<1){//1-2
//                   count=1;editTextName.setEnabled(true);
//               }
//
//                   if (count%2==0){
////                       editTextName.setEnabled(true);
//                       tenSauKhiThayDoi = editTextName.getText().toString().trim();
//                       if (tenSauKhiThayDoi!=tenTruocKhiThayDoi){
//                           resetName(editTextName.getText().toString().trim());
//                       }
//                       editTextName.setEnabled(true);
//
//                   } else{
//                       editTextName.setEnabled(false);
//                   }
//
//
//
//            }
//        });
////        findViewById(R.id.MainActivity2Id).setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                editTextName.setEnabled(false);
////                count=1;
////            }
////        });
//    }

//    private void resetName(String name) {
//        List<String> stringList = new ArrayList<>();
//        stringList.clear();
//        users.clear();//xóa sạch người dùng
//
//        databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    String userGmail = dataSnapshot.getValue(User.class).getEmail();//email cua moi nguoi
//                    String ult = dataSnapshot.getKey();//ma UID cua email
//                    String sds = getIntent().getStringExtra("email_of_roommate");//ten nguoi dc an
//                    if (userGmail.equals(sds)){
//
//                        updateName(name,ult);
//                        return;
//                    }
//                    Log.d("TAG", "onDataChange: "+ult);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(MainActivity2.this, error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//    private void updateName(String name , String UserUids){
//        //ở đây chúng ta sẽ cài đặt đường dẫn tại đây
////        FirebaseDatabase.getInstance().getReference("user/" + UserUids+"/usernameAcount").setValue(name);
//        mDatabase.child("user").child(UserUids).child("usernameAcount").setValue(name);
//    }
}