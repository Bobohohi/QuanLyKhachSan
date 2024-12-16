package com.example.quanlykhachsan.Views;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykhachsan.R;

public class HostDashboardActivity extends AppCompatActivity {
    TextView txtXinChao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_dashboard);
        addControls();
        addEvens();
        loadData();
    }

    private void loadData() {
        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        if (isAdmin) {
            txtXinChao.setText("Xin chào Ông Chủ");
        } else {
            txtXinChao.setText("Chào mừng bạn đến với hệ thống");
        }
    }

    private void addEvens() {
    }

    private void addControls() {
        txtXinChao=findViewById(R.id.txtTen);
    }
}