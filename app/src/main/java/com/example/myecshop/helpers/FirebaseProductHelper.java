package com.example.myecshop.helpers;

import com.example.myecshop.models.Product;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseProductHelper {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://my-ec-shop-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

    public void createProduct(String id, Product product) {
        databaseReference.child("PRODUCT").child(id).setValue(product);
    }

    public void deleteProduct(String id) {
        databaseReference.child("PRODUCT").child(id).removeValue();
    }
}
