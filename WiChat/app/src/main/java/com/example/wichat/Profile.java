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
        progressDialog.setTitle("アップロード中...");
        //hien thi doan hoi thoai do cuoi cung
        progressDialog.show();
        //hinh anh nay o day va chung toi goi tep
        //va ten tep se la duong dan cua chung toi
        FirebaseStorage.getInstance().getReference("images/"+ UUID.randomUUID().toString()).putFile(imagePath).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                //neu tac vu thanh cong
                if (task.isSuccessful()){
                    //lưu ảnh đã tải xuống vào image của user và chỉ hiển thị được khi quá trình dowload Url kết thúc
                    //lên phải cài 1 trình nghe hoàn thất
                    task.getResult().getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){//khi nhiệm vụ thành công thì sẽ update
                                updateProfilePicture(task.getResult().toString());
                            }
                        }
                    });
                    //sau do xuat ra thong báo có nội dung là đã upLoad ảnh thành công
                    Toast.makeText(Profile.this, "写真アップロード成功", Toast.LENGTH_SHORT).show();
                }else{
                    //thông báo đã xảy ra lỗi
                    Toast.makeText(Profile.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                //cho dù task có thành công hay không thì tiến trình của hộp thoại phải được loại bỏ
                progressDialog.dismiss();
            }
            //đặt trình nghe tiến trinh xem và theo dõi được bao nhiêu ảnh tải lên firebase
            //và khi nó thay đổi nó sẽ cập nhật tiến trình cho người xem
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                //biến kép nghĩa là tiến bộ
                double progress = 100.0 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount();
                //thông báo đại loại, chuyển đổi thành số nguyên và thêm phần trăm khi upLoad ảnh len firebase
                progressDialog.setMessage("アップロード"+(int) progress + "%");
            }
        });
    }
    //chuyển url chuỗi
    private void updateProfilePicture(String ulr){
        //ở đây chúng ta sẽ cài đặt đường dẫn tại đây
        FirebaseDatabase.getInstance().getReference("user/" + UserUid+"/profilePicture").setValue(ulr);
    }
}