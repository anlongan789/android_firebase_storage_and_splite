package com.example.myecshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myecshop.helpers.FirebaseCartHelper;
import com.example.myecshop.models.CartItem;
import com.example.myecshop.models.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ViewDetailsActivity extends AppCompatActivity {
    ImageView ivProductImage;
    TextView txtProductName;
    TextView txtProductDescription;
    TextView txtProductPrice;
    Button btnAddToCart;
    //    DatabaseReference databaseReference;
    FirebaseCartHelper firebaseCartHelper = new FirebaseCartHelper();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://my-ec-shop-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

    ArrayList<CartItem> currentCart;
    Product product;
    boolean inCart = false;
    CartItem currentCartItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_details);

//        databaseReference = FirebaseDatabase.getInstance("https://my-ec-shop-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        Objects.requireNonNull(getSupportActionBar()).hide();


        ivProductImage = findViewById(R.id.ivProductImage);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        txtProductName = findViewById(R.id.txtProductName);
        txtProductDescription = findViewById(R.id.txtProductDesc);
        txtProductPrice = findViewById(R.id.txtProductPrice);
        currentCart = new ArrayList<>();

        NumberFormat numberFormat = new DecimalFormat("#0");
        product = (Product) getIntent().getSerializableExtra("View Product");

        txtProductName.setText(product.getName());
        txtProductDescription.setText(product.getDescription());
        txtProductPrice.setText(numberFormat.format(product.getPrice()) + "đ");

        Picasso.get().load(product.getImgUrl()).into(ivProductImage);

        checkInCart();

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = product.getId();
                String name = product.getName();
                String imgUrl = product.getImgUrl();
                double price = product.getPrice();

//                databaseReference.child("CART").child(id).setValue(new CartItem(id, name, imgUrl, quantity, price));
                if (inCart == false) {
                    firebaseCartHelper.addToCart(id, new CartItem(id, name, imgUrl, 1, price));
                    Toast.makeText(ViewDetailsActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    firebaseCartHelper.addToCart(id, new CartItem(id, name, imgUrl, currentCartItem.getQuantity() + 1, currentCartItem.getPrice() + product.getPrice()));
                    Toast.makeText(ViewDetailsActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();
                    finish();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shop_cart, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cart:
//                Toast.makeText(this, "CART SELECTED!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ViewDetailsActivity.this, CartActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void checkInCart() {
        databaseReference.child("CART").child(product.getId()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("Firebase", "Error getting data", task.getException());
                } else {
                    if (task.getResult().getValue() != null) {
                        currentCartItem = task.getResult().getValue(CartItem.class);
//                        Log.d(" Firebase ITEM", "onComplete: " + String.valueOf(task.getResult().getValue()));
                        inCart = !inCart;
                        Log.d("IN CART", "item: " + product.getName() + " in cart " + inCart);
                    } else {
                        inCart = false;
                        Log.d("NOT IN CART", "item " + product.getName() + " not inside cart " + inCart);
                    }
                }
            }
        });
    }

}