package com.example.postcodesearch3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

 import android.os.Handler;
        import android.os.Looper;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

        import java.io.IOException;

        import okhttp3.Call;
        import okhttp3.Callback;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //Widget宣言
    TextView txt01,txt02,txt03;
    EditText initPostCode;
    Button btn01;
    String postCode;
    final String url="http://zipcloud.ibsnet.co.jp/api/search?zipcode=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Widget初期化
        anhXa();
        //ボタンクリック
        postButtonClick();

    }

    private void postButtonClick() {
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!initPostCode.getText().toString().equals("")){
                    postCode =initPostCode.getText().toString().trim();
                    try{
                        //okhttpを利用するカスタム関数（下記）

                        httpRequest(url+postCode);
                    }catch(Exception e){
                        Log.e("Hoge",e.getMessage());
                    }
                }else{
                    Toast.makeText(MainActivity.this, "郵便番号を入力してください！", Toast.LENGTH_SHORT).show();
                }
                //httpリクエスト
            }
        });
    }

    private void anhXa() {
        initPostCode = findViewById(R.id.editTextTextPostCodeId);
        txt01 = findViewById(R.id.txt01);
        txt02 = findViewById(R.id.textView2);
        txt03 = findViewById(R.id.textView);
        btn01 = findViewById(R.id.btn01);
    }

    void httpRequest(String url) throws IOException{

        //OkHttpClinet生成
        OkHttpClient client = new OkHttpClient();

        //request生成
        Request request = new Request.Builder()
                .url(url)
                .build();

        //非同期リクエスト
        client.newCall(request)
                .enqueue(new Callback() {

                    //エラーのとき
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("Hoge",e.getMessage());
                        Toast.makeText(MainActivity.this, "エラー："+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    //正常のとき
                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        //response取り出し
                        final String jsonStr = response.body().string();
                        Log.d("Hoge","jsonStr=" + jsonStr);
                        for (int i=0;i<response.body().contentLength();i++){

                        }
                        //JSON処理
                        try{
                            //jsonパース
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

                            //親スレッドUI更新
                            Handler mainHandler = new Handler(Looper.getMainLooper());
                            String finalNameKen = nameKen;
                            String finalNameKen2 = nameKen2;
                            String finalNameKen3 = nameKen3;
                            mainHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    txt01.setText(finalNameKen);
                                    txt02.setText(finalNameKen2);
                                    txt03.setText(finalNameKen3);
                                }
                            });


                        }catch(Exception e){
                            Log.e("Hoge",e.getMessage());
                            Toast.makeText(MainActivity.this, "エラー"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}