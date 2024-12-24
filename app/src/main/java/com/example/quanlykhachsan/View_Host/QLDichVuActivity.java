package com.example.quanlykhachsan.View_Host;

import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlykhachsan.Adapter.QLServiceAdapter;
import com.example.quanlykhachsan.Class.Services;
import com.example.quanlykhachsan.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QLDichVuActivity extends AppCompatActivity {

    final String SERVER = "http://10.0.2.2/ht/getService.php";
    Button btnBack, BtnXoa, BtnLuu;
    EditText etMa, etTen, etGia, etSearch;
    ListView lvDichvu;

    List<Services> serviceList = new ArrayList<>();
    List<Services> originalServiceList = new ArrayList<>();
    QLServiceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qldich_vu);
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

                    adapter.updateData(originalServiceList);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });


//        BtnLuu.setOnClickListener(v -> saveService());
//
//        lvDichvu.setOnItemLongClickListener((parent, view, position, id) -> {
//            Services serviceToDelete = serviceList.get(position);
//            deleteService(serviceToDelete);
//            return true;
//        });
    }

    private void addControl() {
        btnBack = findViewById(R.id.btnBack);
        BtnXoa = findViewById(R.id.btnXoa);
        BtnLuu = findViewById(R.id.btnLuu);

        etMa = findViewById(R.id.etMadv);
        etTen = findViewById(R.id.etTendv);
        etGia = findViewById(R.id.etGiaDV);
        etSearch = findViewById(R.id.etSearch);

        lvDichvu = findViewById(R.id.lvDSDichVu);


        adapter = new QLServiceAdapter(this, serviceList);
        lvDichvu.setAdapter(adapter);
    }
//    private void deleteService(Services service) {
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        Uri.Builder builder = Uri.parse(SERVER).buildUpon();
//        builder.appendQueryParameter("action", "delete");
//        builder.appendQueryParameter("id", String.valueOf(service.getId()));
//
//        String url = builder.build().toString();
//
//        StringRequest request = new StringRequest(Request.Method.POST, url,
//                response -> {
//                    // Handle successful delete
//                    Toast.makeText(this, "Service deleted successfully!", Toast.LENGTH_SHORT).show();
//                    loadData(); // Reload the data after deletion
//                },
//                error -> {
//                    // Handle error
//                    Toast.makeText(this, "Error deleting service: " + error.getMessage(), Toast.LENGTH_LONG).show();
//                }
//        );
//
//        requestQueue.add(request);
//    }
//
//    private void saveService() {
//        String id = etMa.getText().toString();
//        String name = etTen.getText().toString();
//        String price = etGia.getText().toString();
//
//        if (id.isEmpty() || name.isEmpty() || price.isEmpty()) {
//            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        Uri.Builder builder = Uri.parse(SERVER).buildUpon();
//        builder.appendQueryParameter("action", "save");
//        builder.appendQueryParameter("id", id);
//        builder.appendQueryParameter("name", name);
//        builder.appendQueryParameter("price", price);
//
//        String url = builder.build().toString();
//
//        StringRequest request = new StringRequest(Request.Method.POST, url,
//                response -> {
//                    // Handle successful save
//                    Toast.makeText(this, "Service saved successfully!", Toast.LENGTH_SHORT).show();
//                    loadData(); // Reload the data after saving
//                },
//                error -> {
//                    // Handle error
//                    Toast.makeText(this, "Error saving service: " + error.getMessage(), Toast.LENGTH_LONG).show();
//                }
//        );
//
//        requestQueue.add(request);
//    }

    private void searchServices(String query) {
        List<Services> filteredList = new ArrayList<>();
        for (Services service : originalServiceList) {

            if (service.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(service);
            }
        }


        adapter.updateData(filteredList);
    }

    private void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<String> responseListener = response -> {
            try {
                serviceList.clear();
                originalServiceList.clear();

                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("Service_id");
                    String name = jsonObject.getString("Name");
                    Double price = jsonObject.getDouble("Price");

                    Services service = new Services(id, name, price);
                    serviceList.add(service);
                    originalServiceList.add(service);
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
}
