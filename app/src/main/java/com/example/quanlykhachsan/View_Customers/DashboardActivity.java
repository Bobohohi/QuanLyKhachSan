package com.example.quanlykhachsan.View_Customers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykhachsan.R;

public class DashboardActivity extends AppCompatActivity {
    TextView txtTen;
    Button btnDatPhong, btnPhongDaDat, btnKhuyenMai, btnThongTin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addControls();
        addEvens();
        loadTen();

    }

    private void addEvens() {
        btnDatPhong.setOnClickListener(v -> {
            Intent intent = new Intent(DashboardActivity.this, BookingActivity.class);
            startActivity(intent);
        });

    }

    private void addControls() {
        txtTen = findViewById(R.id.txtTen);
        btnDatPhong = findViewById(R.id.btnDatPhong);
        btnPhongDaDat = findViewById(R.id.btnPhongDaDat);
        btnKhuyenMai = findViewById(R.id.btnKhuyenMai);
        btnThongTin = findViewById(R.id.btnThongTin);
    }

    private void loadTen() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String customerName = sharedPreferences.getString("CustomerName", "Khách hàng");
        txtTen.setText("Xin Chào : " + customerName);
    }
}