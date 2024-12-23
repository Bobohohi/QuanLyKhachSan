package com.example.quanlykhachsan.View_Host;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykhachsan.Adapter.BillAdapter;
import com.example.quanlykhachsan.Class.Bill;
import com.example.quanlykhachsan.Class.Services;
import com.example.quanlykhachsan.R;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QLBillActivity extends AppCompatActivity {

    final String SERVER = "http://10.0.2.2/ht/getBill.php";

    Button btnBack, BtnXem, BtnXoa;
    EditText etSearch;
    Spinner spDay, spMonth;
    ListView lvBill;

    List<Bill> billList = new ArrayList<>();
    List<Bill> originalBillList = new ArrayList<>();
    BillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qlbill);

        addControl();
        addEvent();
        loadData();
    }

    private void addEvent() {
        btnBack.setOnClickListener(v -> finish());
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    searchServices(s.toString());
                } else {
                    // If search is empty, reset the list to original data
                    adapter.updateData(originalBillList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void addControl() {
        btnBack = findViewById(R.id.btnBack);
        BtnXem = findViewById(R.id.btnXemDS);
        BtnXoa = findViewById(R.id.btnXoa);
        spDay = findViewById(R.id.cbbNgay);
        spMonth = findViewById(R.id.cbbThang);
        lvBill = findViewById(R.id.lvDSHoaDon);
        etSearch = findViewById(R.id.etSearch);

        adapter = new BillAdapter(this, billList);
        lvBill.setAdapter(adapter);
    }

    private void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<String> responseListener = response -> {
            try {
                billList.clear();
                originalBillList.clear();

                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int bookingDetailId = jsonObject.optInt("Booking_detail_id", -1);
                    int bookingId = jsonObject.optInt("Booking_id", -1);
                    int roomId = jsonObject.optInt("Room_id", -1);
                    double price = jsonObject.optDouble("Price", 0.0);
                    int numberOfNights = jsonObject.optInt("NumberOfNight", 0);
                    double totalPrice = jsonObject.optDouble("Total_Price", 0.0);

                    Bill bill = new Bill(bookingDetailId, bookingId, roomId, price, numberOfNights, totalPrice);
                    billList.add(bill);
                    originalBillList.add(bill);
                }

                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error loading data", Toast.LENGTH_SHORT).show();
            }
        };

        Response.ErrorListener errorListener = error -> {
            Toast.makeText(this, "Error fetching data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
        };

        Uri.Builder builder = Uri.parse(SERVER).buildUpon();
        builder.appendQueryParameter("action", "getall");
        String url = builder.build().toString();

        StringRequest request = new StringRequest(Request.Method.GET, url, responseListener, errorListener);
        requestQueue.add(request);
    }

    private void searchServices(String query) {
        List<Bill> filteredList = new ArrayList<>();
        for (Bill bill : originalBillList) {
            if (String.valueOf(bill.getBookingDetailId()).toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(bill);
            }
        }

        adapter.updateData(filteredList);
    }
}
