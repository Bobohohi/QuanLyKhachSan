package com.example.quanlykhachsan.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class yeucauAdapter_Cus extends RecyclerView.Adapter<yeucauAdapter_Cus.yeucauViewHolder>{
    @NonNull
    @Override
    public yeucauAdapter_Cus.yeucauViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull yeucauAdapter_Cus.yeucauViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class yeucauViewHolder extends RecyclerView.ViewHolder {
        public yeucauViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
