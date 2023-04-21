package com.example.myecshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myecshop.adapters.CartRecyclerAdapter;
import com.example.myecshop.helpers.FirebaseCartHelper;
import com.example.myecshop.models.CartItem;
import com.example.myecshop.models.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

public class CartActivity extends AppCompatActivity {
    RecyclerView rvCart;
    TextView txtTotalPrice;
    Button btnProceed;
    double totalPrice = 0;
    FirebaseCartHelper firebaseCartHelper = new FirebaseCartHelper();
    ArrayList<CartItem> cartItems;
    CartRecyclerAdapter cartAdapter ;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://my-ec-shop-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.rvCart);
        rvCart.setHasFixedSize(true);
        rvCart.setLayoutManager(new LinearLayoutManager(this));

        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        btnProceed = findViewById(R.id.btnProceed);

        cartItems = new ArrayList<>();
        getCart();
//        cartItems = firebaseCartHelper.getCart();

        btnProceed.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                String id = "order" + new Date().getTime();
                Intent intent = new Intent(CartActivity.this, OrderDetailsActivity.class);
                intent.putExtra("LISTCART", cartItems);
                Bundle b = new Bundle();
                b.putDouble("Total", totalPrice);
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }

    public void getCart() {
        databaseReference.child("CART").orderByChild("name").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItems.clear();
                totalPrice = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    CartItem item = dataSnapshot.getValue(CartItem.class);
                    totalPrice = totalPrice + item.getPrice();
                    cartItems.add(item);
                }
                cartAdapter = new CartRecyclerAdapter(CartActivity.this, cartItems);
//                Log.d("Cart items", cartItems.toString());
                rvCart.setAdapter(cartAdapter);
                NumberFormat numberFormat = new DecimalFormat("#0");
                txtTotalPrice.setText("Total: " + numberFormat.format(totalPrice) + "đ");
                cartAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}