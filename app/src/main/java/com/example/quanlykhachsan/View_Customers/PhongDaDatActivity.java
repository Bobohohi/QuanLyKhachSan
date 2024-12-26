package com.example.quanlykhachsan.View_Customers;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlykhachsan.Adapter.RoomsAdapter;
import com.example.quanlykhachsan.Adapter.yeucauAdapter_Cus;
import com.example.quanlykhachsan.Class.Rooms;
import com.example.quanlykhachsan.Class.yeucau;
import com.example.quanlykhachsan.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PhongDaDatActivity extends AppCompatActivity {
    final String SERVER = "http://192.168.1.204/ht/getyeucau.php";

    List<yeucau> yeucauList = new ArrayList<>(); ;
    yeucauAdapter_Cus adapter;
    TextView txtcus;

    Button btnBack,btnThanhtoan;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phong_da_dat);
        addControls();
        addEvens();
        loadDataTheoTen();

    }

    private void loadDataTheoTen() {
        // Lấy tên khách hàng từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String customerName = sharedPreferences.getString("CustomerName", "Khách hàng");
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<String> responseListener = response -> {
            try {
                yeucauList.clear();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String Customer = jsonObject.getString("Customer");
                    String Room = jsonObject.getString("Room");
                    String Service = jsonObject.getString("Service");
                    String Date = jsonObject.getString("Date");
                    Double price = jsonObject.getDouble("Price");
                    String Status = jsonObject.getString("Status");

                    // Thêm vào danh sách nếu tên khách hàng trùng khớp
                    if (Customer.equals(customerName)) {
                        yeucauList.add(new yeucau(Customer, Room, Service, Date, price, Status));
                    }
                }
                adapter.notifyDataSetChanged();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        };

        Response.ErrorListener errorListener = error ->
                Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();

        Uri.Builder builder = Uri.parse(SERVER).buildUpon();
        builder.appendQueryParameter("action", "getall");
        builder.appendQueryParameter("customerName", customerName); // Gửi tên khách hàng
        String url = builder.build().toString();

        StringRequest request = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        requestQueue.add(request);
    }


    private void addEvens() {
        btnBack.setOnClickListener(view -> {
            // Quay lại màn hình trước đó
            finish();
        });
        btnThanhtoan.setOnClickListener(view -> {
            Toast.makeText(PhongDaDatActivity.this, "Tính năng đang phát triển", Toast.LENGTH_SHORT).show();
        });
    }

    private void addControls() {
        btnBack = findViewById(R.id.btnBack);
        btnThanhtoan = findViewById(R.id.btnThanhtoan);
        recyclerView = findViewById(R.id.RViewPhongdadat);

        // Cấu hình RecyclerView
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo adapter và gán vào RecyclerView
        adapter = new yeucauAdapter_Cus(this, yeucauList);
        recyclerView.setAdapter(adapter);
        txtcus=findViewById(R.id.txttencus);
    }
}