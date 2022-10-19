package com.example.mydatabasehelper1;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class CustomTable extends LinearLayout {
    Context context;
    TextView txtTen,txtPhi,txtGhiChu;
    public CustomTable(Context context) {
        super(context);
    }

    public CustomTable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dong_chi_tieu,this);
        txtTen = view.findViewById(R.id.tvTenId);
        txtPhi = view.findViewById(R.id.tvPhiId);
        txtGhiChu = view.findViewById(R.id.tvGhiChuId);
//        Cursor cursor = db.GetData("SELECT * FROM chiTieu");
//        txtTen.setText("1");
//        txtPhi.setText("1");
//        txtGhiChu.setText("1");
    }
}
