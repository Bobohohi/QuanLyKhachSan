package com.example.quanlykhachsan.View_Host;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykhachsan.R;

public class QLKhachHangActivity extends AppCompatActivity {
    Button btnBack, BtnXem, BtnXoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qlkhach_hang);
        addControls();
        addEvens();
        loadData();

    }

    private void loadData() {
    }

    private void addEvens() {
        btnBack.setOnClickListener(v -> finish());
    }

    private void addControls() {
        btnBack=findViewById(R.id.btnBack);
        BtnXem=findViewById(R.id.btnXemDS);
        BtnXoa=findViewById(R.id.btnXoa);

    }
}