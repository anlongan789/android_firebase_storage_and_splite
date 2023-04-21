package com.example.myecshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myecshop.helpers.FirebaseCartHelper;
import com.example.myecshop.helpers.UserDBHelper;
import com.example.myecshop.models.CartItem;
import com.example.myecshop.models.Order;
import com.example.myecshop.models.User;

import java.util.ArrayList;
import java.util.Date;

public class OrderDetailsActivity extends AppCompatActivity {

    TextView txtName, txtPhone, txtEmail, txtAddress;
    RadioButton rbCod, rbOnline;
    RadioGroup rgPayment;
    Button btnOrder;
    ArrayList<CartItem> cartItems;
    UserDBHelper userDBHelper;
    User currentUser;
    String payment ="";
    FirebaseCartHelper firebaseCartHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        txtName = findViewById(R.id.txtOrderUsername);
        txtAddress = findViewById(R.id.txtOrderAddress);
        txtEmail = findViewById(R.id.txtOrderEmail);
        txtPhone = findViewById(R.id.txtOrderPhone);

        rbCod = findViewById(R.id.rbCod);
        rbOnline = findViewById(R.id.rbOnline);

        rgPayment = findViewById(R.id.rgPayment);

        btnOrder = findViewById(R.id.btnOrder);

        userDBHelper = new UserDBHelper(this);
        firebaseCartHelper= new FirebaseCartHelper();

        cartItems = new ArrayList<>();
        cartItems =(ArrayList<CartItem>) getIntent().getSerializableExtra("LISTCART");
        Bundle b = getIntent().getExtras();
        double totalPrice = b.getDouble("Total");

        currentUser = userDBHelper.getUser();
        Log.d("USER", "user: " + currentUser.getName());

        txtName.setText(currentUser.getName());
        txtPhone.setText(currentUser.getPhone());
        txtEmail.setText(currentUser.getEmail());
        txtAddress.setText(currentUser.getAddress());

        rgPayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rbCod:
                        payment = "Shipping COD";
                        break;
                    case R.id.rbOnline:
                        payment = "Online Payment";
                        break;
                }
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = "order" + new Date().getTime();
                firebaseCartHelper.placeOrder(id, new Order(id, cartItems, totalPrice, payment));
                Toast.makeText(OrderDetailsActivity.this, "A new order hve been created!", Toast.LENGTH_SHORT).show();
                firebaseCartHelper.removeAllCart(cartItems);
                Intent intent = new Intent(OrderDetailsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}