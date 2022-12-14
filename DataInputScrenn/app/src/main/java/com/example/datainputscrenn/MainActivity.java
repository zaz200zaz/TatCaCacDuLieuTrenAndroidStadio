package com.example.datainputscrenn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String[] type;
    ArrayAdapter<String> adapter;
    AutoCompleteTextView autoCompleteTextView;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button postCodeButton;
    private TextView nameHoDem;
    private TextView nameTen;
    private TextView nameCachDocHoDem;
    private TextView nameCachDocTen;
    private TextView country;
    private TextView postCode;
    private TextView area;
    private TextView areaChirun;
    private TextView homeNameAndNumber;
    private TextView phoneNumber;
//    private String postCode;
    final String url="http://zipcloud.ibsnet.co.jp/api/search?zipcode=";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        initDatePicker();
        setSeiBetsu();
        postButtonClick();
    }

    private void postButtonClick() {
        postCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!postCode.getText().toString().equals("")){
                    String postCode2 = postCode.getText().toString().trim();
                    String postCode3 = postCode2.replaceAll("[-_*+.,;:`\\[\\]\\{\\}]","");
//                    postCode =initPostCode.getText().toString().trim();
                    try{
                        //okhttp?????????????????????????????????????????????

                        httpRequest(url+postCode3);
                    }catch(Exception e){
                        Log.e("Hoge",e.getMessage());
                    }
                }else{
                    Toast.makeText(MainActivity.this, "??????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                }
                //http???????????????
            }
        });
    }
    private void httpRequest(String url) throws IOException{

        //OkHttpClinet??????
        OkHttpClient client = new OkHttpClient();

        //request??????
        Request request = new Request.Builder()
                .url(url)
                .build();

        //????????????????????????
        client.newCall(request)
                .enqueue(new Callback() {

                    //??????????????????
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("Hoge",e.getMessage());
                        Toast.makeText(MainActivity.this, "????????????"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    //???????????????
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        //response????????????
                        final String jsonStr = response.body().string();
                        Log.d("Hoge","jsonStr=" + jsonStr);
                        for (int i=0;i<response.body().contentLength();i++){

                        }
                        //JSON??????
                        try{
                            //json?????????
                            JSONObject json = new JSONObject(jsonStr);
                            JSONArray asdas = json.getJSONArray("results");
                            String nameKen = "",nameKen2 = "",nameKen3 = "";
                            for (int i = 0 ; i<asdas.length();i++){
                                nameKen = asdas.getJSONObject(i).getString("address1");
                                nameKen2 = asdas.getJSONObject(i).getString("address2");
                                nameKen3 = asdas.getJSONObject(i).getString("address3");
                            }
                            final String status = json.getString("results");
                            String dsa="[A-Za-z0-9\\[\\]\\{\\}\\,]";
                            String result = status.replaceAll(dsa,"");

                            //???????????????UI??????
                            Handler mainHandler = new Handler(Looper.getMainLooper());
                            String finalNameKen = nameKen;
                            String finalNameKen2 = nameKen2;
                            String finalNameKen3 = nameKen3;
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    area.setText(finalNameKen);
                                    areaChirun.setText(finalNameKen2+finalNameKen3);
//                                    txt03.setText(finalNameKen3);
                                }
                            });


                        }catch(Exception e){
                            Log.e("Hoge",e.getMessage());
//                            Toast.makeText(MainActivity.this, "?????????"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void anhXa() {
        type = new String[]{"???","???","??????"};
        adapter = new ArrayAdapter<>(this,R.layout.drop_dow_item,type);
        autoCompleteTextView = findViewById(R.id.filled_exposed);
        dateButton = findViewById(R.id.buttonId);
        nameHoDem = findViewById(R.id.nameHoDem);
        nameTen = findViewById(R.id.nameTen);
        nameCachDocHoDem = findViewById(R.id.nameCachDocHoDem);
        nameCachDocTen = findViewById(R.id.nameCachDocTen);
        country = findViewById(R.id.countryId);
        postCode = findViewById(R.id.psotCodeId);
        area = findViewById(R.id.areaId);
        areaChirun = findViewById(R.id.areaChirenId);
        homeNameAndNumber = findViewById(R.id.homeNameAndNunberId);
        phoneNumber = findViewById(R.id.phoneNumberId);
        postCodeButton = findViewById(R.id.postCodeButtonId);
    }

    private String makeDateString(int year, int month, int day) {
        return year+"???"+month+"???"+day+"???";
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int mont, int day) {
                mont = mont+1;
                String date = makeDateString(year,mont,day);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this,style,onDateSetListener,year,month,day);

    }
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private void setSeiBetsu() {
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this, autoCompleteTextView.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}