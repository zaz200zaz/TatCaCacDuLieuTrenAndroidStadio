package com.example.postcodesearch4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public String getAPI(){
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        String result = "";
        String str = "";
        try {
            URL url = new URL("http://zipcloud.ibsnet.co.jp/api/search?zipcode=2520324");
            // 接続先URLへのコネクションを開く．まだ接続されていない
            urlConnection = (HttpURLConnection) url.openConnection();
            // 接続タイムアウトを設定
            urlConnection.setConnectTimeout(10000);
            // レスポンスデータの読み取りタイムアウトを設定
            urlConnection.setReadTimeout(10000);
            // ヘッダーにUser-Agentを設定
            urlConnection.addRequestProperty("message", "results");
            // ヘッダーにAccept-Languageを設定
            urlConnection.addRequestProperty("Accept-Language", Locale.getDefault().toString());
            // HTTPメソッドを指定
            urlConnection.setRequestMethod("GET");
            //リクエストボディの送信を許可しない
            urlConnection.setDoOutput(false);
            //レスポンスボディの受信を許可する
            urlConnection.setDoInput(true);
            // 通信開始
            urlConnection.connect();
            // レスポンスコードを取得
            int statusCode = urlConnection.getResponseCode();
            // レスポンスコード200は通信に成功したことを表す
            if (statusCode == 200){
                inputStream = urlConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
                // 1行ずつレスポンス結果を取得しstrに追記
                result = bufferedReader.readLine();
                while (result != null){
                    str += result;
                    result = bufferedReader.readLine();
                }
                bufferedReader.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // レスポンス結果のJSONをString型で返す
        return str;
    }

}