package com.example.wichat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class Profile extends AppCompatActivity {
    private Button btlUpload;
    private ImageView imgProfile;
    private Uri imagePath;
    private TextView textView_profile_name;
    private String UserUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//    private UploadTask uploadFileImage = FirebaseStorage.getInstance().getReference("images/"+ UUID.randomUUID().toString()).putFile(imagePath);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btlUpload = findViewById(R.id.btnUploadImage);
        imgProfile = findViewById(R.id.profile_img);
        textView_profile_name = findViewById(R.id.txtUserEmail);

        btlUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upLoadImage();
            }
        });

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cho 1 hanh dong moi
                Intent photoIntent = new Intent(Intent.ACTION_PICK);
                //cai dat loai cho hanh dong cu the o day la hinh anh image
                photoIntent.setType("image/*");
                //cai dat lenh mo file image cua android
                startActivityForResult(photoIntent,1);
            }
        });
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
        } catch (IOException e) {
            e.printStackTrace();
        }
//        imgProfile.setImageURI(imagePath);
        imgProfile.setImageBitmap(bitmap);
    }
    //tao boi canh hop thoai tien do
    private void upLoadImage(){
        //this se la lop nay
        ProgressDialog progressDialog = new ProgressDialog(this);
        //dat tieu de cho tien trinh do
        progressDialog.setTitle("?????????????????????...");
        //hien thi doan hoi thoai do cuoi cung
        progressDialog.show();
        //hinh anh nay o day va chung toi goi tep
        //va ten tep se la duong dan cua chung toi
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
                                updateProfilePicture(task.getResult().toString());
                            }
                        }
                    });
                    //sau do xuat ra thong b??o c?? n???i dung l?? ???? upLoad ???nh th??nh c??ng
                    Toast.makeText(Profile.this, "??????????????????????????????", Toast.LENGTH_SHORT).show();
                }else{
                    //th??ng b??o ???? x???y ra l???i
                    Toast.makeText(Profile.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
                progressDialog.setMessage("??????????????????"+(int) progress + "%");
            }
        });
    }
    //chuy???n url chu???i
    private void updateProfilePicture(String ulr){
        //??? ????y ch??ng ta s??? c??i ?????t ???????ng d???n t???i ????y
        FirebaseDatabase.getInstance().getReference("user/" + UserUid+"/profilePicture").setValue(ulr);
    }
}