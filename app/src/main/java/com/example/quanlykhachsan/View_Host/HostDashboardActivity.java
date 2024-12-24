package com.example.quanlykhachsan.View_Host;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykhachsan.R;
import com.example.quanlykhachsan.View_Customers.DashboardActivity;
import com.example.quanlykhachsan.View_Host.QLBillActivity;
import com.example.quanlykhachsan.View_Host.QLDichVuActivity;
import com.example.quanlykhachsan.View_SignIn.SignInActivity;

public class HostDashboardActivity extends AppCompatActivity {
    TextView txtXinChao;
    Button btnDichvu,btnBill,btnPhong,btnKhachhang,btnNhanvien,btnYeuCau,btnlogout;
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
        btnDichvu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(HostDashboardActivity.this, QLDichVuActivity.class);
                startActivity(intent);
            }
        });
        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(HostDashboardActivity.this, QLBillActivity.class);
                startActivity(intent);
            }
        });
        btnPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(HostDashboardActivity.this, QLPhongActivity.class);
                startActivity(intent);
            }
        });
        btnKhachhang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HostDashboardActivity.this, QLKhachHangActivity.class);
                startActivity(intent);
            }
        });
        btnNhanvien.setOnClickListener(view -> {
            Intent intent = new Intent(HostDashboardActivity.this, QLNhanVienActivity.class);
            startActivity(intent);
        });
        btnYeuCau.setOnClickListener(view -> {
            Intent intent = new Intent(HostDashboardActivity.this, QLYeuCauActivity.class);
            startActivity(intent);
        });
        btnlogout.setOnClickListener(view -> {
            Intent intent = new Intent(HostDashboardActivity.this, SignInActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void addControls() {
        btnlogout=findViewById(R.id.btnlogout);
        txtXinChao=findViewById(R.id.txtTen);
        btnDichvu=findViewById(R.id.btnQLDichvu);
        btnBill=findViewById(R.id.btnQLHoadon);
        btnPhong=findViewById(R.id.btnQLPhong);
        btnKhachhang=findViewById(R.id.btnQLKhachhang);
        btnNhanvien=findViewById(R.id.btnQLNhanvien);
        btnYeuCau=findViewById(R.id.btnYCDP);
    }
}