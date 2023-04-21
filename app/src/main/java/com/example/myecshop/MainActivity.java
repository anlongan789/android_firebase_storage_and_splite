package com.example.myecshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.example.myecshop.adapters.GridViewCustomAdapter;
import com.example.myecshop.adapters.ListProductAdapter;
import com.example.myecshop.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ArrayList<Product> products;
    GridView gvProduct;
    GridViewCustomAdapter gridViewCustomAdapter;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://my-ec-shop-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gvProduct = findViewById(R.id.gvProduct);
        products = new ArrayList<>();
        
        gvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) gridViewCustomAdapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, ViewDetailsActivity.class);
                intent.putExtra("View Product", product);
                startActivity(intent);
//                Toast.makeText(MainActivity.this, "Item selected: " + product.getName(), Toast.LENGTH_SHORT).show();
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
        switch (item.getItemId()){
            case R.id.menu_cart:
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
//                Toast.makeText(this, "CART SELECTED!", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void getProducts() {
        databaseReference.child("PRODUCT").orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                products.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    Product prod = dataSnapshot.getValue(Product.class);
                    products.add(prod);
                }

                gridViewCustomAdapter = new GridViewCustomAdapter(products);
                gvProduct.setAdapter(gridViewCustomAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ERROR GET PRODUCT", error.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getProducts();
    }
}