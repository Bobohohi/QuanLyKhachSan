package com.example.quanlykhachsan.View_SignIn;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.quanlykhachsan.Class.Customers;
import com.example.quanlykhachsan.R;
import com.example.quanlykhachsan.View_Customers.DashboardActivity;
import com.example.quanlykhachsan.View_Host.HostDashboardActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity {
    final String SERVER = "http://10.0.2.2/ht/getCustomer.php";
    List<Customers> CustomersList = new ArrayList<>();
    EditText etTK , etMK;
    Button btnDangNhap,btnsigingg,btnsiginfb;
    Customers validCustomer = null;

    private ActivityResultLauncher<Intent> googleSignInLauncher;
    FirebaseAuth auth;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        setupGoogleSignInLauncher();
        addControls();
        addEvens();
        loadData();
    }

    private void loadData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        Response.Listener<String> responseListener = response -> {
            try {
                CustomersList.clear();
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    int ma = jsonObject.getInt("Customer_id");
                    String name = jsonObject.getString("Name");
                    String phone = jsonObject.getString("Phone");
                    String email = jsonObject.getString("Email");
                    String username = jsonObject.getString("Username");
                    String password = jsonObject.getString("Password");
                    CustomersList.add(new Customers(ma, name,phone,email,username,password));
                }
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
    private void setupGoogleSignInLauncher() {
        googleSignInLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // Xử lý kết quả đăng nhập Google
                        Intent data = result.getData();
                        if (data != null) {
                            // Lấy thông tin người dùng từ GoogleSignInAccount
                            GoogleSignIn.getSignedInAccountFromIntent(data)
                                    .addOnSuccessListener(googleAccount -> {
                                        // Chuyển đến DashboardActivity sau khi đăng nhập thành công
                                        Intent i = new Intent(this, DashboardActivity.class);
                                        i.putExtra("CustomerName", googleAccount.getDisplayName());
                                        startActivity(i);
                                        finish();
                                    })
                                    .addOnFailureListener(e ->
                                            Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_SHORT).show()
                                    );
                        }
                    }
                }
        );
    }

    private void addEvens() {
        btnDangNhap.setOnClickListener(view -> {
            String user = etTK.getText().toString();
            String pass = etMK.getText().toString();
            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Username or password cannot be empty", Toast.LENGTH_SHORT).show();
            } else if (user.equals("admin") && pass.equals("admin")){
                Intent i = new Intent(this, HostDashboardActivity.class);
                i.putExtra("isAdmin", true);
                startActivity(i);
                finish();
            }
            else{
                boolean isValidUser = false;

                // Kiểm tra danh sách khách hàng để xác thực người dùng
                for (Customers customer : CustomersList) {
                    if (customer.getUsername().equals(user) && customer.getPassword().equals(pass)) {
                        isValidUser = true;
                        validCustomer = customer;
                        break;  // Nếu tìm thấy, thoát khỏi vòng lặp
                    }
                }

                // Nếu người dùng hợp lệ, chuyển màn hình
                if (isValidUser) {
                    // Lưu tên khách hàng vào SharedPreferences
                    SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("CustomerName", validCustomer.getName());  // Lưu tên khách hàng
                    editor.putString("CustomerUsername", validCustomer.getUsername()); // Lưu username (tuỳ chọn)
                    editor.apply();  // Lưu lại
                    // Chuyển đến BookingActivity (hoặc màn hình tương ứng)
                    Intent i = new Intent(this, DashboardActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    // Nếu không hợp lệ, hiển thị thông báo lỗi
                    Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnsigingg.setOnClickListener(view -> {
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if (account != null) {
                Toast.makeText(this, "You are already signed in with: " + account.getEmail(), Toast.LENGTH_SHORT).show();
                // Đăng xuất trước khi đăng nhập lại
                googleSignInClient.signOut().addOnCompleteListener(task -> {
                    Intent intent = googleSignInClient.getSignInIntent();
                    googleSignInLauncher.launch(intent);
                });
            } else {
                Intent intent = googleSignInClient.getSignInIntent();
                googleSignInLauncher.launch(intent);
            }
        });
        btnsiginfb.setOnClickListener(view -> {

        });
    }

    private void addControls() {
        etTK= findViewById(R.id.etUsername);
        etMK=findViewById(R.id.etPassword);
        btnDangNhap=findViewById(R.id.btnContinue);
        btnsigingg=findViewById(R.id.btnsigingg);
        btnsiginfb=findViewById(R.id.btnsiginfb);
        GoogleSignInOptions options=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,options);
        auth=FirebaseAuth.getInstance();
    }
}