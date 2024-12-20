package com.example.quanlykhachsan.View_Customers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlykhachsan.Adapter.ServicesAdapter;
import com.example.quanlykhachsan.Class.Services;
import com.example.quanlykhachsan.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {
    final String SERVER = "http://172.21.13.235/ht/getService.php";
    private EditText etDay, etTo;
    private Spinner cbbPeople, cbbChonloaiphong,cbbChonsoluong, cbbService;
    private Button  btnBooking;

    List<Services> serviceList = new ArrayList<>(); ;
    ServicesAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        addControls();
        addEvens();
        loadcbbPeople();
        loadcbbChonloaiphong(1);
        loadcbbChonsoluong(1, "Phòng Đơn");
        setupListeners();

        loadData();
    }

    private void loadcbbPeople() {
        ArrayList<String> peopleList = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            peopleList.add(String.valueOf(i));
        }
        // Tạo ArrayAdapter và gán vào Spinner
        ArrayAdapter<String> adapters = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, peopleList);
        adapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbbPeople.setAdapter(adapters);
    }
    private void loadcbbChonloaiphong(int peopleCount) {
        ArrayList<String> roomTypes = new ArrayList<>();
        if (peopleCount == 1) {
            roomTypes.add("Phòng Đơn");
        } else if (peopleCount == 2) {
            roomTypes.add("Phòng Đơn");
            roomTypes.add("Phòng Đôi");
        } else {
            roomTypes.add("Phòng Đơn");
            roomTypes.add("Phòng Đôi");
            roomTypes.add("Phòng Family");
        }

        ArrayAdapter<String> roomTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomTypes);
        roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbbChonloaiphong.setAdapter(roomTypeAdapter);
    }
    private void loadcbbChonsoluong(int peopleCount,String roomType) {
        ArrayList<String> roomQuantityList = new ArrayList<>();
        if (peopleCount == 1) {
            roomQuantityList.add("1 Phòng Đơn 1-2 Người");
        }
        else if (peopleCount == 2) {
            // Nếu chọn phòng Single, cho phép chọn 1 và 2 phòng
            if (roomType.equals("Phòng Đơn")) {
                roomQuantityList.add("1 Phòng Đơn 1-2 Người");
                roomQuantityList.add("2 Phòng Đơn");
            }
            // Nếu chọn phòng Double, chỉ có thể chọn 1 phòng
            else if (roomType.equals("Phòng Đôi")) {
                roomQuantityList.add("1 Phòng Đôi 3-4 Người");
            }
        }
        else if (peopleCount == 3){
            if(roomType.equals("Phòng Đơn")){
                roomQuantityList.add("1 Phòng Đơn + 1 Phòng Đôi");
            }
            else if (roomType.equals("Phòng Đôi")) {
                roomQuantityList.add("2 Phòng Đôi 3-4 Người");
            }
            // Nếu chọn phòng Double, chỉ có thể chọn 1 phòng
            else if (roomType.equals("Phòng Family")) {
                roomQuantityList.add("1 Phòng Family 4-5 Người");
            }
        }else if (peopleCount == 4){
            if (roomType.equals("Phòng Đôi")) {
                roomQuantityList.add("1 Phòng Đôi Và 1 Phòng Đơn 2 Người");
                roomQuantityList.add("2 Phòng Đôi");
            }
            // Nếu chọn phòng Double, chỉ có thể chọn 1 phòng
            else if (roomType.equals("Phòng Family")) {
                roomQuantityList.add("1 Phòng Family 4-5 Người");
            }
        }

            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomQuantityList);
            adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cbbChonsoluong.setAdapter(adapter1);
    }
    private boolean isDateValid(String checkInDate, String checkOutDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            sdf.setLenient(false); // Đảm bảo định dạng ngày chính xác

            Date currentDate = new Date(); // Ngày hiện tại
            Date checkIn = sdf.parse(checkInDate); // Ngày đến
            Date checkOut = sdf.parse(checkOutDate); // Ngày đi

            if (checkIn.before(currentDate)) {
                Toast.makeText(this, "Ngày Checkin phải lớn hơn hoặc bằng ngày hiện tại", Toast.LENGTH_SHORT).show();
                return false;
            }

            if (checkOut.before(checkIn) ) {
                Toast.makeText(this, "Ngày Checkout phải lớn hơn ngày đến", Toast.LENGTH_SHORT).show();
                return false;
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Định dạng ngày không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private void addEvens() {
        cbbPeople.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int peopleCount = Integer.parseInt(cbbPeople.getSelectedItem().toString());
                loadcbbChonloaiphong(peopleCount);
                loadcbbChonsoluong(peopleCount, cbbChonloaiphong.getSelectedItem().toString());  // Load lại số lượng phòng
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        cbbChonloaiphong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int peopleCount = Integer.parseInt(cbbPeople.getSelectedItem().toString());
                String typeroom= cbbChonloaiphong.getSelectedItem().toString();
                loadcbbChonsoluong(peopleCount,typeroom);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        btnBooking.setOnClickListener(view -> {
            // Thu thập dữ liệu từ các điều khiển
            String peopleCount = cbbPeople.getSelectedItem().toString();
            String roomType = cbbChonloaiphong.getSelectedItem().toString();
            String roomQuantity = cbbChonsoluong.getSelectedItem().toString();
            String checkInDate = etDay.getText().toString();
            String checkOutDate = etTo.getText().toString();

            if (checkInDate.isEmpty() || checkOutDate.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ngày đến và ngày đi", Toast.LENGTH_SHORT).show();
                return; // Dừng lại nếu ngày đến hoặc ngày đi rỗng
            }
            if (isDateValid(checkInDate, checkOutDate)) {
                // Tính số ngày ở giữa CheckIn và CheckOut
                int numberOfDays = calculateNumberOfDays(checkInDate, checkOutDate);
                // Lưu dữ liệu vào SharedPreferences
                List<Services> selectedServices = adapter.getSelectedServices();
                saveBookingDataToPreferences(peopleCount, roomType, roomQuantity, checkInDate, checkOutDate, numberOfDays,selectedServices);
                Intent intent = new Intent(this, CustomerDashboardActivity.class);
                startActivity(intent);
            }
        });
    }


    private int calculateNumberOfDays(String checkInDate, String checkOutDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date checkIn = sdf.parse(checkInDate);
            Date checkOut = sdf.parse(checkOutDate);

            // Tính số ngày
            long diffInMillis = checkOut.getTime() - checkIn.getTime();
            return (int) (diffInMillis / (1000 * 60 * 60 * 24));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Lỗi khi tính số ngày", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }


    private void saveBookingDataToPreferences(String peopleCount, String roomType, String roomQuantity,
                                              String checkInDate, String checkOutDate, int numberOfDays, List<Services> services) {
        SharedPreferences sharedPreferences = getSharedPreferences("BookingPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("peopleCount", peopleCount);
        editor.putString("roomType", roomType);
        editor.putString("roomQuantity", roomQuantity);
        editor.putString("checkInDate", checkInDate);
        editor.putString("checkOutDate", checkOutDate);
        editor.putInt("numberOfDays", numberOfDays);

        // Lưu danh sách dịch vụ dưới dạng chuỗi JSON
        JSONArray serviceArray = new JSONArray();
        for (Services service : services) {
            JSONObject serviceObject = new JSONObject();
            try {
                serviceObject.put("id", service.getId());
                serviceObject.put("name", service.getName());
                serviceObject.put("price", service.getPrice());
            } catch (Exception e) {
                e.printStackTrace();
            }
            serviceArray.put(serviceObject);
        }
        editor.putString("selectedServices", serviceArray.toString());

        editor.apply();
        Toast.makeText(this, "Vui Lòng Chọn Phòng Mà Bạn Muốn", Toast.LENGTH_SHORT).show();
    }


    private void setupListeners() {
        etDay.setOnClickListener(view -> showDatePickerDialogDay());
        etTo.setOnClickListener(view -> showDatePickerDialogTo());
    }


    private void showDatePickerDialogDay() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String checkInDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    etDay.setText(checkInDate);

                    // Kiểm tra ngày đến hợp lệ
                    String checkOutDate = etTo.getText().toString();
                    if (!checkOutDate.isEmpty()) {
                        isDateValid(checkInDate, checkOutDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private void showDatePickerDialogTo() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String checkOutDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    etTo.setText(checkOutDate);

                    // Kiểm tra ngày đi hợp lệ
                    String checkInDate = etDay.getText().toString();
                    if (!checkInDate.isEmpty()) {
                        isDateValid(checkInDate, checkOutDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }


    private void addControls() {
        etDay = findViewById(R.id.etDay);
        etTo = findViewById(R.id.etTo);

        cbbPeople = findViewById(R.id.cbbPeople);
        cbbChonloaiphong = findViewById(R.id.spinner);

        cbbChonsoluong=findViewById(R.id.spinner2);

        recyclerView = findViewById(R.id.RViewService);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ServicesAdapter(this, serviceList);
        recyclerView.setAdapter(adapter);

        btnBooking = findViewById(R.id.btnBooking);
    }


    private void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<String> responseListener = response -> {
            try {
                serviceList.clear();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int id = jsonObject.getInt("Service_id");
                    String name = jsonObject.getString("Name");
                    Double price = jsonObject.getDouble("Price");

                    serviceList.add(new Services(id, name, price));
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