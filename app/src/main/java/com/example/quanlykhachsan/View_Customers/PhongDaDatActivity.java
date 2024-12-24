package com.example.quanlykhachsan.View_Customers;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlykhachsan.Adapter.RoomsAdapter;
import com.example.quanlykhachsan.Class.Rooms;
import com.example.quanlykhachsan.Class.yeucau;
import com.example.quanlykhachsan.R;

import java.util.ArrayList;
import java.util.List;

public class PhongDaDatActivity extends AppCompatActivity {
    final String SERVER = "http://10.0.2.2/ht/getyeucau.php";

    List<yeucau> yeucauList = new ArrayList<>(); ;
    RoomsAdapter adapter;


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
        btnBack.setOnClickListener(view -> {
            // Quay lại màn hình trước đó
            finish();
        });
        btnThanhtoan.setOnClickListener(view -> {

        });
    }

    private void addControls() {
        btnBack = findViewById(R.id.btnBack);
        btnThanhtoan = findViewById(R.id.btnThanhtoan);
        recyclerView = findViewById(R.id.RViewPhongdadat);
    }
}