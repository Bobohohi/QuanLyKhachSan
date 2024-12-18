package com.example.quanlykhachsan.View_Customers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykhachsan.R;

import org.json.JSONArray;
import org.json.JSONObject;

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
        loadTen();
    }

    private void loadData() {
        // Lấy dữ liệu từ BookingPrefs
        SharedPreferences bookingPrefs = getSharedPreferences("BookingPrefs", MODE_PRIVATE);
        String roomType = bookingPrefs.getString("roomType", "N/A");
        String roomQuantity = bookingPrefs.getString("roomQuantity", "N/A");
        String checkInDate = bookingPrefs.getString("checkInDate", "N/A");
        String checkOutDate = bookingPrefs.getString("checkOutDate", "N/A");
        int numberOfDays = bookingPrefs.getInt("numberOfDays", 0);

        // Lấy thông tin tên phòng từ SharedPreferences trong CustomerDashboardActivity
        SharedPreferences customerDashboardPrefs = getSharedPreferences("SelectedRoom", MODE_PRIVATE);
        String selectedRoomName = customerDashboardPrefs.getString("room_name", "Phòng chưa được chọn");
        int selectedRoomId = customerDashboardPrefs.getInt("room_id", -1);
        float selectedRoomPrice = customerDashboardPrefs.getFloat("room_price", 0.0f);

        // Danh sách dịch vụ đã chọn
        String servicesJson = bookingPrefs.getString("selectedServices", "[]");
        StringBuilder serviceDetails = new StringBuilder();
        double totalServiceCost = 0.0;

        try {
            JSONArray serviceArray = new JSONArray(servicesJson);
            for (int i = 0; i < serviceArray.length(); i++) {
                JSONObject serviceObject = serviceArray.getJSONObject(i);

                // Lấy tên và giá của từng dịch vụ
                String serviceName = serviceObject.getString("name");
                double servicePrice = serviceObject.getDouble("price");

                // Thêm vào chi tiết dịch vụ
                serviceDetails.append("- ").append(serviceName).append("\n");
                totalServiceCost += servicePrice;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tổng số tiền
        double roomPrice = selectedRoomPrice * numberOfDays;

        double totalPayment = roomPrice + totalServiceCost;

        // Hiển thị dữ liệu lên TextView
        txtRoom.setText(String.format("%s - %d", selectedRoomName, selectedRoomId));
        txtNight.setText(numberOfDays + " đêm");
        txtDay.setText(checkInDate + " đến " + checkOutDate);
        txtService.setText(serviceDetails.toString().trim());
        txtTotalService.setText(String.format("%,.0f VND", totalServiceCost));
        txtPromos.setText(String.format("%,.0f VND",roomPrice));
        txtTotal.setText(String.format("%,.0f VND", totalPayment));
    }




    private void loadTen() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String customerName = sharedPreferences.getString("CustomerName", "Khách hàng");
        txtTen.setText("Khách Hàng : " + customerName);
    }
    private void addEvens() {
        btnBack.setOnClickListener(v -> finish());
        btnOrder.setOnClickListener(v -> {
            Intent i = new Intent(this, DashboardActivity.class);
            startActivity(i);
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