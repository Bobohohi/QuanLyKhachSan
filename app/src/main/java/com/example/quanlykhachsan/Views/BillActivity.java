package com.example.quanlykhachsan.Views;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykhachsan.R;

public class BillActivity extends AppCompatActivity {
    private Button btnBack, btnOrder;
    private TextView txtTen, txtRoom, txtNight, txtPayment, txtPromos, txtService, txtDay, txtTotalService, txtTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        addControls();
        addEvens();
        loadData();
    }

    private void loadData() {

    }

    private void addEvens() {
        btnBack.setOnClickListener(v -> finish());
        btnOrder.setOnClickListener(v -> {


        });
    }

    private void addControls() {
        btnBack = findViewById(R.id.btnBack);
        btnOrder = findViewById(R.id.btnOrder);
        txtTen = findViewById(R.id.txtTen);
        txtRoom = findViewById(R.id.txtRoom);
        txtNight = findViewById(R.id.txtNight);
        txtPayment = findViewById(R.id.txtPayment);
        txtPromos = findViewById(R.id.txtPromos);
        txtService = findViewById(R.id.txtService);
        txtDay = findViewById(R.id.txtDay);
        txtTotalService = findViewById(R.id.txtTotalService);
        txtTotal = findViewById(R.id.txtTotal);
    }
}