package com.example.quanlykhachsan.View_Customers;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykhachsan.R;

public class PhongDaDatActivity extends AppCompatActivity {
    Button btnBack,btnThanhtoan;
    RecyclerView recyclerView;
    TextView txtTongtien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_da_dat);
        addControls();
        addEvens();
        loadData();
    }

    private void loadData() {

    }

    private void addEvens() {
    }

    private void addControls() {
    }
}