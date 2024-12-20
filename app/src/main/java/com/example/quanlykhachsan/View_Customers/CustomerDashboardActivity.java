package com.example.quanlykhachsan.View_Customers;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import java.util.List;

public class CustomerDashboardActivity extends AppCompatActivity {
    final String SERVER = "http://172.21.13.235/ht/getRoom.php";
    EditText etSearch;
    TextView txtTen;
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
    }

    private void loadTen() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String customerName = sharedPreferences.getString("CustomerName", "Khách hàng");
        txtTen.setText("Khách Hàng : " + customerName);
    }

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


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RoomsAdapter(this, roomList);
        recyclerView.setAdapter(adapter);
    }


}