package com.example.fileluuvaomay;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    EditText editText; EditText txtName; EditText noidungtxt;
    EditText folderName; EditText txtFileName; EditText txtFileNoiDung;
    String string;
    Button taoFolder;Button taotxtFile;Button viettxtFile;Button doctxtFile;Button xoa;Button tuDongHoa;
    private static final int PER_M_S = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        taoMaCodeTuDong();

//        taoFolder();
//        taoFileTxt();
//        vietTxtFile();
//        deletetxtFile();
//        readFile();
    }

    private void viet(String Email,String verificationCode) {

               File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                       +"/WICHAT/");
                try {
                    File gpxfile = new File(file, "認証コード.txt");
                    StringBuilder text = new StringBuilder();
                    BufferedReader br = new BufferedReader(new FileReader(gpxfile));
                    String line;
                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    br.close();
                    String data = "メールの名前："+Email+"\n"+"認証コード:"+verificationCode+"\n";
                    if (text.indexOf(Email)!=(-1)){
                        Toast.makeText(this, "du lieu da ton tai", Toast.LENGTH_SHORT).show();
                    }else{
                        FileWriter fileWriter = new FileWriter(Environment.getExternalStorageDirectory().getAbsolutePath()+"/WICHAT/"+"認証コード.txt");
                        fileWriter.append(text+data);
                        fileWriter.flush();
                        fileWriter.close();
                        Toast.makeText(MainActivity.this, "thanh cong", Toast.LENGTH_SHORT).show();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "viet file ko thanh cong", Toast.LENGTH_SHORT).show();
                }
    }

    private void taoMaCodeTuDong() {
        tuDongHoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (kiemTraDauVao(folderName.getText().toString().trim(),txtFileName.getText().toString().trim(),txtFileNoiDung.getText().toString().trim())) {
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/","WICHAT");
                    if (!file.exists()){
                        file.mkdir();
                        Toast.makeText(MainActivity.this, "Tao folder thanh cong ", Toast.LENGTH_SHORT).show();
                    }
                    file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/WICHAT/","認証コード.txt");
                    if (!file.exists()){
                        try {
                            file.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this, "Tao txtFile thanh cong ", Toast.LENGTH_SHORT).show();
                    }
                    viet("tranTthanhTUan","123456");
            }
        });


    }

    private boolean kiemTraDauVao(String folderNames, String txtFileNames, String txtFileNoiDungs) {
        if (folderNames.isEmpty()||txtFileNames.isEmpty()||txtFileNoiDungs.isEmpty()){
            return false;
        }
        return true;
    }

    private void readFile() {
        doctxtFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String past = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+editText.getText().toString().trim()+"/";
                File root = new File(past);
                File gpxfile = new File(root, txtName.getText().toString().trim()+".txt");
                StringBuilder text = new StringBuilder();
                try {
                    BufferedReader br = new BufferedReader(new FileReader(gpxfile));
                    String line;

                    while ((line = br.readLine()) != null) {
                        text.append(line);
                        text.append('\n');
                    }
                    br.close();
                }
                catch (IOException e) {
                    //You'll need to add proper error handling here
                }
                TextView textView = findViewById(R.id.textView);
                textView.setText(text.toString());
            }
        });
    }

    private void deletetxtFile() {
        xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String past = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+editText.getText().toString().trim()+"/";
                File root = new File(past);
                File gpxfile = new File(root, txtName.getText().toString().trim()+".txt");
                gpxfile.delete();
                Toast.makeText(getBaseContext(), "File deleted successfully!",
                        Toast.LENGTH_SHORT).show();
            }
        });

//        newFile.renameTo(oldFile);
    }

    private void vietTxtFile() {
        viettxtFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String past = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"+editText.getText().toString().trim()+"/";
                    File root = new File(past);
                    File gpxfile = new File(root, txtName.getText().toString().trim()+".txt");
                    FileWriter fileWriter = new FileWriter(gpxfile);
                    fileWriter.append(noidungtxt.getText().toString().trim());
                    fileWriter.flush();
                    fileWriter.close();
                    Toast.makeText(getBaseContext(), "File saved successfully!",
                            Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void taoFileTxt() {
        taotxtFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String rootPath = Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + "/"+editText.getText().toString().trim()+"/";
                    File root = new File(rootPath);
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    File f = new File(rootPath + txtName.getText().toString().trim() +".txt");
                    if (f.exists()) {
                        f.delete();
                        Toast.makeText(MainActivity.this, "tao file txt khong thanh cong", Toast.LENGTH_SHORT).show();
                    }
                    f.createNewFile();
                    Toast.makeText(MainActivity.this, "tao file txt thanh cong", Toast.LENGTH_SHORT).show();

                    FileOutputStream out = new FileOutputStream(f);

                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void taoFolder() {
        taoFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                string = editText.getText().toString().trim();
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {

                    createDirectory(string);
                }else{

                    askPermission();
                }
            }
        });
    }

    private void anhXa() {
        editText = findViewById(R.id.editTextTextPersonName);
        noidungtxt = findViewById(R.id.editTextTextPersonName2);
        txtName = findViewById(R.id.editTextTextPersonName3);
        taoFolder = findViewById(R.id.button);
        taotxtFile = findViewById(R.id.button2);
        viettxtFile = findViewById(R.id.button3);
        doctxtFile = findViewById(R.id.button4);
        xoa = findViewById(R.id.button5);


        folderName = findViewById(R.id.editTextTextPersonName);
        txtFileNoiDung = findViewById(R.id.editTextTextPersonName2);
        txtFileName = findViewById(R.id.editTextTextPersonName3);
        tuDongHoa = findViewById(R.id.button6);

    }




    private String readFromFile(String name) throws IOException {
        String result= "";
        InputStream inputStream = openFileInput(name);//"読み込むファイル名.txt"
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String tempString = "";
            StringBuilder stringBuilder = new StringBuilder();

            while ((tempString = bufferedReader.readLine()) != null) {
                stringBuilder.append(tempString);
            }
            inputStream.close();
            result = stringBuilder.toString();
        }
        return result;
    }
    private void askPermission() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PER_M_S);
    }

    private void createDirectory(String string) {

        File file = new File(Environment.getExternalStorageDirectory(),string);

        if (!file.exists()){
            file.mkdir();
            Toast.makeText(this, "tao thanh cong", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "tao file ko thanh cong", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode==PER_M_S){
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                createDirectory(string);
            }else {
                Toast.makeText(this, "Permisson Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}