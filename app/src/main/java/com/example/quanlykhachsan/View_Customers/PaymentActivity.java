package com.example.quanlykhachsan.View_Customers;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykhachsan.R;

public class PaymentActivity extends AppCompatActivity {
    TextView txttotal;
    Button btnbackdash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        addControls();
        addEvens();
        Intent intent = getIntent();
        // Lấy dữ liệu từ Intent
        String total = intent.getStringExtra("total");
        txttotal.setText(total);

    }

    private void addEvens() {
        btnbackdash.setOnClickListener(view -> {
            Intent intent = new Intent(PaymentActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void addControls() {
        txttotal=findViewById(R.id.txttotal);
        btnbackdash=findViewById(R.id.btnbacktohome);
    }

}