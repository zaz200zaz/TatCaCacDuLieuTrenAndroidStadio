package com.example.mydatabasehelper1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class ChiTieuAdapter extends BaseAdapter {

    Context myContext;
    int myLayout;
    List<ChiTieu> arrayChiTieu;

    public ChiTieuAdapter(Context myContext, int myLayout, List<ChiTieu> arrayChiTieu) {
        this.myContext = myContext;
        this.myLayout = myLayout;
        this.arrayChiTieu = arrayChiTieu;
    }

    @Override
    public int getCount() {
        return arrayChiTieu.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayChiTieu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder {
        TextView txtTen,txtPhi,txtGhiChu;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if (view == null){
            view = inflater.inflate(myLayout,null);
            viewHolder.txtTen = view.findViewById(R.id.tvTenId);
            viewHolder.txtPhi = view.findViewById(R.id.tvPhiId);
            viewHolder.txtGhiChu = view.findViewById(R.id.tvGhiChuId);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        DecimalFormat format = new DecimalFormat("###,###,###");
        viewHolder.txtTen.setText(arrayChiTieu.get(position).Ten);
        viewHolder.txtPhi.setText(format.format(arrayChiTieu.get(position).ChiPhi)+"円");
        viewHolder.txtGhiChu.setText(arrayChiTieu.get(position).GhiChu);
        return view;
    }
}
