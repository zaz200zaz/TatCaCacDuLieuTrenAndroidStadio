package com.example.test2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FineAdapter extends RecyclerView.Adapter<FineAdapter.FineHolder> {

    private ArrayList<FineData> arrayList;

    public FineAdapter(ArrayList<FineData> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public FineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fine_holder,parent,false);
        return new FineHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FineHolder holder, int position) {
        FineData fineData = arrayList.get(position);
        if (fineData!=null){
            holder.txtMessage.setText(fineData.getMessage());
            holder.txtUserName.setText(fineData.getSendPerson());
        }else{
            return;
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class FineHolder extends RecyclerView.ViewHolder {
        TextView txtMessage; TextView txtUserName;
        public FineHolder(@NonNull View itemView) {
            super(itemView);

            txtMessage = itemView.findViewById(R.id.txtMessage);
            txtUserName = itemView.findViewById(R.id.txtUserName);
        }

    }
}
