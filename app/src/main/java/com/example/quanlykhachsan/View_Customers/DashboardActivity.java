package com.example.quanlykhachsan.View_Customers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykhachsan.R;
import com.example.quanlykhachsan.View_SignIn.SignInActivity;

public class DashboardActivity extends AppCompatActivity {
    TextView txtTen;
    Button btnDatPhong, btnPhongDaDat, btnKhuyenMai, btnThongTin,btnlogout;
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
        btnPhongDaDat.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, PhongDaDatActivity.class);
            startActivity(intent);
        });
        btnlogout.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });
        btnKhuyenMai.setOnClickListener(view -> {
            Toast.makeText(DashboardActivity.this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
        });
        btnThongTin.setOnClickListener(view -> {
            Toast.makeText(DashboardActivity.this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
        });
    }

    private void addControls() {
        txtTen = findViewById(R.id.txtTen);
        btnDatPhong = findViewById(R.id.btnDatPhong);
        btnPhongDaDat = findViewById(R.id.btnPhongDaDat);
        btnKhuyenMai = findViewById(R.id.btnKhuyenMai);
        btnThongTin = findViewById(R.id.btnThongTin);
        btnlogout=findViewById(R.id.btnlogout);

    }

    private void loadTen() {
        String customerName = getIntent().getStringExtra("CustomerName");
        if (customerName == null || customerName.isEmpty()) {
            // Lấy từ SharedPreferences nếu Intent không có dữ liệu
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            customerName = sharedPreferences.getString("CustomerName", "Khách hàng");
        }
        txtTen.setText("Xin Chào : " + customerName);
    }
}