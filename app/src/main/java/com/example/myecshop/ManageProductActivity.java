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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myecshop.adapters.ListProductAdapter;
import com.example.myecshop.models.Product;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageProductActivity extends AppCompatActivity {

    ListView lvProducts;
    ArrayList<Product> products;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://my-ec-shop-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
    ListProductAdapter listProductAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);
        lvProducts = findViewById(R.id.lvProducts);

        products = new ArrayList<>();

        lvProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) listProductAdapter.getItem(position);
                Intent intent = new Intent(ManageProductActivity.this, UpdateProductActivity.class);
                intent.putExtra("Product", product);
                startActivity(intent);
            }
        });
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

                listProductAdapter = new ListProductAdapter(products);
                lvProducts.setAdapter(listProductAdapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.product_management_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_product:
                Intent intent = new Intent(ManageProductActivity.this, AddProductActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}