package com.example.quanlykhachsan.View_Customers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.quanlykhachsan.Class.Rooms;
import com.example.quanlykhachsan.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomerDashboardActivity extends AppCompatActivity {
    final String SERVER = "http://192.168.1.254/ht/getRoom.php";
    EditText etSearch;
    Button btnChonphong;
    TextView txtTen , txtGoiY;
    List<Rooms> roomList = new ArrayList<>(); ;
    RoomsAdapter adapter;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);
        addControls();
        addEvens();
        loadData();
        loadTen();
        loadGoiY();
    }
    private void loadGoiY(){
        SharedPreferences bookingPrefs = getSharedPreferences("BookingPrefs", MODE_PRIVATE);
        String roomQuantity = bookingPrefs.getString("roomQuantity", "");
        txtGoiY.setText("Gợi Ý : "+roomQuantity);
    }

    private void loadTen() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String customerName = sharedPreferences.getString("CustomerName", "Khách hàng");
        txtTen.setText("Khách Hàng : " + customerName);
    }
    //String peopleCount = bookingPrefs.getString("peopleCount", "1");
    //int maxRoomSelection = getMaxRoomSelection(peopleCount);
    //adapter.setMaxSelectionLimit(maxRoomSelection);
    private void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<String> responseListener = response -> {
            try {
                roomList.clear();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int ma = jsonObject.getInt("Room_id");
                    String type = jsonObject.getString("Type");
                    String status = jsonObject.getString("Status");
                    Float price = (float) jsonObject.getDouble("Price");
                    String image = jsonObject.getString("Image");
                    roomList.add(new Rooms(ma, type, status, price, image));
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
        String url = builder.build().toString();

        StringRequest request = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        requestQueue.add(request);
    }

    private void addEvens() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterRooms(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
        btnChonphong.setOnClickListener(v -> {
            // Lấy danh sách các phòng đã chọn từ Adapter
            List<Rooms> selectedRooms = new ArrayList<>();
            for (Rooms room : roomList) {
                if (adapter.getSelectedRooms().contains(room.getRoomid())) {
                    selectedRooms.add(room);
                }
            }

            // Kiểm tra nếu không có phòng nào được chọn
            if (selectedRooms.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một phòng!", Toast.LENGTH_SHORT).show();
                return;
            }
            SharedPreferences bookingPrefs = getSharedPreferences("BookingPrefs", MODE_PRIVATE);
            String peopleCountStr = bookingPrefs.getString("peopleCount", "1");
            int peopleCount = Integer.parseInt(peopleCountStr);

            // Xác định giới hạn số phòng dựa trên số lượng người
            int maxRoomSelection;
            if (peopleCount >= 1 && peopleCount <= 4) {
                maxRoomSelection = 2;
            } else if (peopleCount >= 5 && peopleCount <= 8) {
                maxRoomSelection = 3;
            } else {
                maxRoomSelection = 1; // Mặc định nếu không thuộc các trường hợp trên
            }

            // Kiểm tra số lượng phòng đã chọn
            if (selectedRooms.size() > maxRoomSelection) {
                Toast.makeText(this, "Với " + peopleCount + " người, bạn chỉ được chọn tối đa " +
                        maxRoomSelection + " phòng!", Toast.LENGTH_SHORT).show();
                return;
            }


            // Lưu danh sách phòng đã chọn vào SharedPreferences dưới dạng chuỗi JSON
            SharedPreferences sharedPreferences = getSharedPreferences("SelectedRoomsPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            JSONArray jsonArray = new JSONArray();
            for (Rooms room : selectedRooms) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("Room_id", room.getRoomid());
                    jsonObject.put("Type", room.getRoomtype());
                    jsonObject.put("Status", room.getStatus());
                    jsonObject.put("Price", room.getPrice());
                    jsonObject.put("Image", room.getImage());
                    jsonArray.put(jsonObject);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            editor.putString("selected_rooms", jsonArray.toString());
            editor.apply();

            // Chuyển sang Activity khác
            Intent intent = new Intent(CustomerDashboardActivity.this, BillActivity.class);
            startActivity(intent);
        });


    }
    private void filterRooms(String query) {
        List<Rooms> filteredList = new ArrayList<>();
        for (Rooms room : roomList) {
            if (room.getRoomtype().toLowerCase().contains(query.toLowerCase()) ||
                    room.getStatus().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(room);
            }
        }
        adapter.updateRooms(filteredList);

        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Không tìm thấy phòng phù hợp", Toast.LENGTH_SHORT).show();
        }
    }

    private void addControls() {
        txtTen = findViewById(R.id.txtTen);
        etSearch = findViewById(R.id.etSearch);
        recyclerView = findViewById(R.id.RViewRoom);
        txtGoiY=findViewById(R.id.txtGoiY);
        btnChonphong=findViewById(R.id.btnChonphong);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RoomsAdapter(this, roomList);
        recyclerView.setAdapter(adapter);
    }


}