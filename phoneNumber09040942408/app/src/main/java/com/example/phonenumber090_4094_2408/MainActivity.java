package com.example.phonenumber090_4094_2408;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editText;private EditText editText2;
    String dau3 = ""; String dau7 = "";String dau11 = "";
    Button button;
    char sad[];
    String phoneNew;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.editTextTextPersonName_userNameId);
        editText2 = findViewById(R.id.editTextTextPersonNameTextPasswordsId);
        button = findViewById(R.id.button);
        editText.addTextChangedListener(textWatcher);
        String dasd = "09040942408";
        chuyenStringThanhChar(dasd);
        Log.d(TAG, "onCreate: "+sad.length+"|"+dau3+"|"+dau7+"|"+dau11+"|"+phoneNew);
    }

    private void chuyenStringThanhChar(String dasd) {
        sad = dasd.toCharArray();
        for (int i=0;i<sad.length;i++){
            if (i<3){
                dau3+=sad[i];
            }
            if (i>=3&&i<7){
                dau7+=sad[i];
            }if (i>=7){
                dau11+=sad[i];
            }

        }
        phoneNew = dau3+"-"+dau7+"-"+dau11;
    }

    private void thayDoi(String dasd) {

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String useName = editText.getText().toString().trim();
            String passwords = editText2.getText().toString().trim();
            if (useName.length()>2){
                useName+="-";
                editText2.setText(useName);
            }
            if (passwords.length()>7){
                editText.append("-");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}