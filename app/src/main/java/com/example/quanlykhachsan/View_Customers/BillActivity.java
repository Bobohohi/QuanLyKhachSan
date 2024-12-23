package com.example.quanlykhachsan.View_Customers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlykhachsan.Class.yeucau;
import com.example.quanlykhachsan.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class BillActivity extends AppCompatActivity {
    final String SERVER = "http://10.0.2.2/ht/postyeucau.php";
    private Button btnBack, btnOrder;
    private TextView txtTen, txtRoom, txtNight, txtPayment, txtPromos, txtService, txtDay, txtTotalService, txtTotal;
    SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    String formattedDate = "";
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
        String checkInDate = bookingPrefs.getString("checkInDate", "N/A");
        String checkOutDate = bookingPrefs.getString("checkOutDate", "N/A");
        int numberOfDays = bookingPrefs.getInt("numberOfDays", 0);

        // Lấy thông tin các phòng đã chọn từ SharedPreferences
        SharedPreferences selectedRoomsPrefs = getSharedPreferences("SelectedRoomsPrefs", MODE_PRIVATE);
        String selectedRoomsJson = selectedRoomsPrefs.getString("selected_rooms", "[]");
        StringBuilder roomDetails = new StringBuilder();
        double totalRoomPrice = 0.0;

        try {
            JSONArray roomsArray = new JSONArray(selectedRoomsJson);
            for (int i = 0; i < roomsArray.length(); i++) {
                JSONObject roomObject = roomsArray.getJSONObject(i);

                // Lấy thông tin từng phòng
                int roomId = roomObject.getInt("Room_id");
                String roomType = roomObject.getString("Type");
                double roomPrice = roomObject.getDouble("Price");

                // Thêm thông tin phòng vào StringBuilder
                if (i > 0) {
                    roomDetails.append("\n");
                }
                roomDetails.append(roomType).append(" - ").append(roomId);

                // Cộng dồn giá phòng
                totalRoomPrice += roomPrice;
            }
        } catch (Exception e) {
            e.printStackTrace();
            roomDetails.append("Không có thông tin phòng");
        }

        // Lấy và tính toán giá dịch vụ
        String servicesJson = bookingPrefs.getString("selectedServices", "[]");
        StringBuilder serviceDetails = new StringBuilder();
        double totalServiceCost = 0.0;

        try {
            JSONArray serviceArray = new JSONArray(servicesJson);
            for (int i = 0; i < serviceArray.length(); i++) {
                JSONObject serviceObject = serviceArray.getJSONObject(i);

                String serviceName = serviceObject.getString("name");
                double servicePrice = serviceObject.getDouble("price");

                serviceDetails.append("- ").append(serviceName).append("\n");
                totalServiceCost += servicePrice;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Tính tổng tiền phòng cho tất cả các đêm
        double totalRoomCost = totalRoomPrice * numberOfDays;

        // Tổng thanh toán
        double totalPayment = totalRoomCost + totalServiceCost;

        // Hiển thị dữ liệu
        txtRoom.setText(roomDetails.toString().trim());
        txtNight.setText(numberOfDays + " đêm");
        txtDay.setText(checkInDate + " đến " + checkOutDate);
        txtService.setText(serviceDetails.toString().trim());
        txtTotalService.setText(String.format("%,.0f VND", totalServiceCost));
        txtPromos.setText(String.format("%,.0f VND", totalRoomCost));
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

            try {
                Date date = inputFormat.parse(txtDay.getText().toString());
                formattedDate = outputFormat.format(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Chuyển đổi giá tiền
            double price = 0.0;
            try {
                price = Double.parseDouble(txtTotal.getText().toString().replace(",", "").replace("VND", "").trim());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            // Tạo đối tượng yeucau
            String Customer = txtTen.getText().toString();
            String Room = txtRoom.getText().toString();
            String Service = txtService.getText().toString();
            yeucau y = new yeucau(Customer, Room, Service, formattedDate, price);
            postRequest_add(y);
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
    private  void postRequest_add(yeucau y){
        RequestQueue requestQueue = Volley.newRequestQueue(
                BillActivity.this
        );
        StringRequest stringRequest=new StringRequest(
                Request.Method.POST,
                SERVER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // Server trả về một chuỗi response có dạng đối tượng JSON, nên ta ép nó thành JSONObject
                            JSONObject jsonObject = new JSONObject(response);

                            boolean result = jsonObject.getBoolean("message");
                            if (result) {
                                Toast.makeText(
                                        BillActivity.this,
                                        "Order thành công",
                                        Toast.LENGTH_SHORT
                                ).show();

                            } else {
                                Toast.makeText(
                                        BillActivity.this,
                                        "Thêm thất bại",
                                        Toast.LENGTH_SHORT
                                ).show();
                            }
                        } catch (Exception ex) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(
                                BillActivity.this,
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        )
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> postparam = new HashMap<>();
                postparam.put("action", "insert");
                postparam.put("Customer", y.getCustomer());
                postparam.put("Room", y.getRoom());
                postparam.put("Service", y.getService());
                postparam.put("Date", formattedDate);  // Sử dụng ngày đã định dạng
                postparam.put("Price", String.valueOf(y.getPrice()));  // Đảm bảo giá tiền được định dạng đúng
                return postparam;
            }
        };
        requestQueue.add(stringRequest);
    }
}