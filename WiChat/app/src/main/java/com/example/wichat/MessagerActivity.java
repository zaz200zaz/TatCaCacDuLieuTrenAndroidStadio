package com.example.wichat;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class MessagerActivity extends AppCompatActivity {
    private Bitmap circularBitmap;
    private String urlImageMessage;
    private boolean ImageOrMessage;
    private String UserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private Uri imagePath;
    private static final int MY_REQUEST_CODE = 1;
    private String curentPhotoPath;//???????????????
    private int i =1;
    private CardView cardView;
    private ImageView ButtonSendCamera;
    private ImageView imgSendMessageView;

    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private EditText edtMessageInput;
    private TextView txtChattingWith;
    private ProgressBar progressBar;

    private ImageView imgToolbar,imgSend;

    private ImageView cameraImageView1;

    private MessageAdapter messageAdapter;
    private ImageMessageAdapter imageMessageAdapter;
    private ArrayList<Message> message;
    private ArrayList<String> chatRoomName;

    private Message message2;

    String usernameOfTheRoommate,emailOfRoommate,chatRoomId;
    private FirebaseUser userDatabase =FirebaseAuth.getInstance().getCurrentUser();
    private ImageButton imgSendCamera;
    //max alex => ph??ng ph???i c?? t??n l?? alexmax

    //id of the chat room for max and alex


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messager);
        anhXa();

        usernameOfTheRoommate = getIntent().getStringExtra("username_of_roomate");
        emailOfRoommate = getIntent().getStringExtra("email_of_roommate");

        ButtonSendCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraBar();
            }
        });
        txtChattingWith.setText(usernameOfTheRoommate);
        //?????t tr??nh nghe onclickListen
        imgSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //t???o b???ng messages v???i t??n ph??ng ch??t
                addMassage();
            }
        });

        messageAdapter = new MessageAdapter(message,getIntent().getStringExtra("my_image2"),getIntent().getStringExtra("img_of_roommate"),MessagerActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(messageAdapter);



        Glide.with(MessagerActivity.this).load(getIntent().getStringExtra("img_of_roommate")).placeholder(R.drawable.account_img).error(R.drawable.account_img).into(imgToolbar);

        setUpChatRoom();

        cameraImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = "photo";
                File storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                try {
                    File file = File.createTempFile(fileName,".jpg",storageDirectory);
                    curentPhotoPath = file.getAbsolutePath();
                    Uri uri = FileProvider.getUriForFile(MessagerActivity.this, "com.example.wichat.fileprovider", file);
                    imagePath = uri;
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
                    startActivityForResult(intent,MY_REQUEST_CODE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void anhXa() {
        chatRoomName = new ArrayList<>();
        urlImageMessage = "";
        ImageOrMessage = true;
        imgSendMessageView= findViewById(R.id.imgSendMessageView);
        cameraImageView1 = findViewById(R.id.imageView1);
        ButtonSendCamera = findViewById(R.id.imgSendCamera);
        cardView = findViewById(R.id.cardView);
        recyclerView = findViewById(R.id.recyclerviewMessages);
//        recyclerView2 = findViewById(R.id.recyclerviewMessages);
        imgSend = findViewById(R.id.imgSendMessage);
        edtMessageInput = findViewById(R.id.edtText);
        txtChattingWith = findViewById(R.id.txtChattingwWith);
        progressBar = findViewById(R.id.progressMessager);
        imgToolbar = findViewById(R.id.img_toolbar);

        //tr?? chuy???n v???i thanh ng?????i d??ng
        txtChattingWith.setText(usernameOfTheRoommate);
        message = new ArrayList<>();
    }

    private String Time() {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.getDefault(Locale.Category.FORMAT));

        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.systemDefault());

        String formattedDate = dateTime.format(formatter);
        String string = formattedDate;
        String[] parts = string.split(",", 2);
        String part1 = parts[0]; // time00???00???00
        String part2 = parts[1]; // ?????????2022/08/23
        String Time[] = part1.split(":");
//        Toast.makeText(this, part1+"  and  "+ part2, Toast.LENGTH_SHORT).show();
        return Time[0]+":"+Time[1];
    }
    private String Year() {
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.MEDIUM)
                .withLocale(Locale.getDefault(Locale.Category.FORMAT));

        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.systemDefault());

        String formattedDate = dateTime.format(formatter);
        String string = formattedDate;
        String[] parts = string.split(",", 2);
        String part1 = parts[0]; // time00???00???00
        String part2 = parts[1]; // ?????????2022/08/23
        String Time[] = part1.split(":");
//        Toast.makeText(this, part1+"  and  "+ part2, Toast.LENGTH_SHORT).show();
        return part2;
    }

    //l???y t??n ng?????i d??ng
    private void setUpChatRoom(){
        //FirebaseDatabase.getInstance().getReference l???y d??? li???u t??? firebase theo ???????ng d???n
        FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String myUsername = snapshot.getValue(User.class).getUsernameAcount();//l???y t??n ng?????i d??ng trong database
                if (usernameOfTheRoommate.compareTo(myUsername)>0){//max compe alex
                    chatRoomId = myUsername + usernameOfTheRoommate;
                }else if (usernameOfTheRoommate.compareTo(myUsername)==00){//alex alex
                    chatRoomId = myUsername + usernameOfTheRoommate;
                }else{
                    chatRoomId = usernameOfTheRoommate+myUsername; //alex max
                }
                attachMessageListener(chatRoomId);
                layTenNguoiNhan(chatRoomId,usernameOfTheRoommate);
                if (chatRoomName==null){
                    chatRoomName.add(chatRoomId);
//                    FirebaseDatabase.getInstance().getReference("roomNametesr").push().setValue(chatRoomId);
                }else{
                    for (String kiemTra:chatRoomName
                         ) {
                        if (kiemTra.equals(chatRoomId)) {
                            break;
                        }else{
                            chatRoomName.add(chatRoomId);
//                            FirebaseDatabase.getInstance().getReference("roomNametesr").push().setValue(chatRoomId);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void attachMessageListener(String chatRoomId){//ai ch??t v???i


        FirebaseDatabase.getInstance().getReference("messages/"+chatRoomId).addValueEventListener(new ValueEventListener() {//t???o ph??ng ch??t v???i chatroomID
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //d??? li??u s??? thay ?????i m???i khi nh???n ???????c tin nh???n m???i
                message.clear(); //x??a s???ch m???ng tin nh???n
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    message.add(dataSnapshot.getValue(Message.class));//th??m tin nh???n v??o trong m???ng mesage
                }
                //sau khi nh???n ???????c th??ng b??o
                messageAdapter.notifyDataSetChanged();//th??ng b??o ???? thay ?????i t???p d??? li???u
                //cho d?? c?? nhi???u tin nh???n ?????n bao nhi??u th?? khi xem s??? l?? tin nh???n cu???i c??ng
                recyclerView.scrollToPosition(message.size()-1);//tin nh???n s??? ???????c hi???n th??? t??? d?????i l??n
                recyclerView.setVisibility(View.VISIBLE);//hi???n th??? recylerView
                progressBar.setVisibility(View.GONE);//che progressBar ??i
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void layTenNguoiNhan(String chatRoomId,String TenNguoiNhan){
        FirebaseDatabase.getInstance().getReference("roomNametesr/"+chatRoomId).setValue(new FineData(chatRoomId,TenNguoiNhan,""));
    }
    private void addMassage(){
        DatabaseReference diaChiPhongChat = FirebaseDatabase.getInstance().getReference("messages/"+chatRoomId).push();
            diaChiPhongChat.setValue(new Message(userDatabase.getEmail(),emailOfRoommate,edtMessageInput.getText().toString(),"",Time(),Year()));

        imgSendMessageView.setVisibility(View.GONE);
        edtMessageInput.setText("");
    }
    private void addImgMassage(String s){
        DatabaseReference diaChiPhongChat = FirebaseDatabase.getInstance().getReference("messages/"+chatRoomId).push();
            diaChiPhongChat.setValue(new Message(userDatabase.getEmail(),emailOfRoommate,"??????",s,Time(),Year()));
        imgSendMessageView.setVisibility(View.GONE);
        edtMessageInput.setText("");
    }

    private void cameraBar(){
        if (i>2){
            i=0;
        }else{
            i++;
        }
        if (i%2==0){
            cardView.setVisibility(View.VISIBLE);
        }else  cardView.setVisibility(View.GONE);
    }
    private void BotronAnh(){
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.kirito);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
        ImageView circularImageView = (ImageView) findViewById(R.id.imageView);
        circularImageView.setImageBitmap(circularBitmap);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==MY_REQUEST_CODE&&resultCode==RESULT_OK){
//            imagePath = data.getData();
//            upLoadImage();

            upLoadImage();
            String sfdsafs = urlImageMessage;

            Bitmap bitmap = BitmapFactory.decodeFile(curentPhotoPath);
            circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
//            ImageView imgSendMessageView = findViewById(R.id.imgSendMessageView);
            imgSendMessageView.setImageBitmap(circularBitmap);
            imgSendMessageView.setVisibility(View.VISIBLE);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void upLoadImage(){
        ProgressDialog progressDialog = new ProgressDialog(this);
        //dat tieu de cho tien trinh do
        progressDialog.setTitle("?????????...");
        //hien thi doan hoi thoai do cuoi cung
        progressDialog.show();
        FirebaseStorage.getInstance().getReference("images/"+ UUID.randomUUID().toString()).putFile(imagePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                //neu tac vu thanh cong
                if (task.isSuccessful()){
                    //l??u ???nh ???? t???i xu???ng v??o image c???a user v?? ch??? hi???n th??? ???????c khi qu?? tr??nh dowload Url k???t th??c
                    //l??n ph???i c??i 1 tr??nh nghe ho??n th???t
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){//khi nhi???m v??? th??nh c??ng th?? s??? update
                                //updateProfilePicture(task.getResult().toString());
                                urlImageMessage =task.getResult().toString();
                                updateProfilePicture(urlImageMessage);
                            }
                        }
                    });
                    //sau do xuat ra thong b??o c?? n???i dung l?? ???? upLoad ???nh th??nh c??ng
                    Toast.makeText(MessagerActivity.this, "????????????????????????", Toast.LENGTH_SHORT).show();
                }else{
                    //th??ng b??o ???? x???y ra l???i
                    Toast.makeText(MessagerActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                //cho d?? task c?? th??nh c??ng hay kh??ng th?? ti???n tr??nh c???a h???p tho???i ph???i ???????c lo???i b???
                progressDialog.dismiss();
            }
            //?????t tr??nh nghe ti???n trinh xem v?? theo d??i ???????c bao nhi??u ???nh t???i l??n firebase
            //v?? khi n?? thay ?????i n?? s??? c???p nh???t ti???n tr??nh cho ng?????i xem
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                //bi???n k??p ngh??a l?? ti???n b???
                double progress = 100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount();
                //th??ng b??o ?????i lo???i, chuy???n ?????i th??nh s??? nguy??n v?? th??m ph???n tr??m khi upLoad ???nh len firebase
                progressDialog.setMessage("??????("+(int) progress + "%)");
            }
        });
    }
    //chuy???n url chu???i
    private void updateProfilePicture(String ulr){
        addImgMassage(ulr);
    }
}