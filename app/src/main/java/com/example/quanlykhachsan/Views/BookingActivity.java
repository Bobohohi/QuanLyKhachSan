package com.example.quanlykhachsan.Views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlykhachsan.R;

import java.util.ArrayList;
import java.util.Calendar;

public class BookingActivity extends AppCompatActivity {
    final String SERVER = "http://192.168.1.205/ht/getService.php";
    private EditText etDay, etTo;
    private Spinner cbbPeople, cbbChonloaiphong,cbbChonsoluong, cbbService;
    private Button  btnBooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        addControls();
        addEvens();
        loadcbbPeople();
        loadcbbChonloaiphong(1);
        loadcbbChonsoluong(1, "Single");
        setupListeners();
        loadcbbService();
    }

    private void loadcbbPeople() {
        ArrayList<String> peopleList = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            peopleList.add(String.valueOf(i));
        }
        // Tạo ArrayAdapter và gán vào Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, peopleList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbbPeople.setAdapter(adapter);
    }
    private void loadcbbChonloaiphong(int peopleCount) {
        ArrayList<String> roomTypes = new ArrayList<>();
        if (peopleCount == 1) {
            roomTypes.add("Single");
        } else if (peopleCount == 2) {
            roomTypes.add("Single");
            roomTypes.add("Double");
        } else {
            roomTypes.add("Single");
            roomTypes.add("Double");
            roomTypes.add("Suite");
        }

        ArrayAdapter<String> roomTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomTypes);
        roomTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbbChonloaiphong.setAdapter(roomTypeAdapter);
    }
    private void loadcbbChonsoluong(int peopleCount,String roomType) {
        ArrayList<String> roomQuantityList = new ArrayList<>();
        if (peopleCount == 1) {
            roomQuantityList.add("1");
        }
        else if (peopleCount == 2) {
            // Nếu chọn phòng Single, cho phép chọn 1 và 2 phòng
            if (roomType.equals("Single")) {
                roomQuantityList.add("1");
                roomQuantityList.add("2");
            }
            // Nếu chọn phòng Double, chỉ có thể chọn 1 phòng
            else if (roomType.equals("Double")) {
                roomQuantityList.add("1");
            }
        }
        else if (peopleCount == 3){
            if(roomType.equals("Single")){
                roomQuantityList.add("1 Single + 1 Double");
            }
            else if (roomType.equals("Double")) {
                roomQuantityList.add("2");
            }
            // Nếu chọn phòng Double, chỉ có thể chọn 1 phòng
            else if (roomType.equals("Suite")) {
                roomQuantityList.add("1");
            }
        }else if (peopleCount == 4){
            if (roomType.equals("Double")) {
                roomQuantityList.add("1 + 1 Single");
                roomQuantityList.add("2");
            }
            // Nếu chọn phòng Double, chỉ có thể chọn 1 phòng
            else if (roomType.equals("Suite")) {
                roomQuantityList.add("1");
            }
        }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomQuantityList);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            cbbChonsoluong.setAdapter(adapter);
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
            Intent intent = new Intent(this, CustomerDashboardActivity.class);
            intent.putExtra("peopleCount", cbbPeople.getSelectedItem().toString());
            intent.putExtra("roomType", cbbChonloaiphong.getSelectedItem().toString());
            intent.putExtra("roomQuantity", cbbChonsoluong.getSelectedItem().toString());
            intent.putExtra("checkInDate", etDay.getText().toString());
            intent.putExtra("checkOutDate", etTo.getText().toString());
            startActivity(intent);
        });
    }
    private void loadcbbService() {
        // Danh sách các dịch vụ
        ArrayList<String> serviceList = new ArrayList<>();
        serviceList.add("Dọn dẹp");
        serviceList.add("Thức ăn");
        serviceList.add("Thức uống");
        serviceList.add("Xông phòng");

        // Tạo ArrayAdapter và gán vào Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, serviceList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbbService.setAdapter(adapter);
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
                    etDay.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                }, year, month, day);
        datePickerDialog.show();
    }private void showDatePickerDialogTo() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    etTo.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                }, year, month, day);
        datePickerDialog.show();
    }
    private void addControls() {
        etDay = findViewById(R.id.etDay);
        etTo = findViewById(R.id.etTo);

        cbbPeople = findViewById(R.id.cbbPeople);
        cbbChonloaiphong = findViewById(R.id.spinner);
        cbbService = findViewById(R.id.cbbService);
        cbbChonsoluong=findViewById(R.id.spinner2);


        btnBooking = findViewById(R.id.btnBooking);
    }
}