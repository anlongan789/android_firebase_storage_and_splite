package com.example.myecshop.helpers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myecshop.models.CartItem;
import com.example.myecshop.models.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseCartHelper {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://my-ec-shop-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();


    public void addToCart(String id, CartItem cartItem) {
        databaseReference.child("CART").child(id).setValue(cartItem);
    }

    public void getCart(ArrayList<CartItem> cartItems) {
        databaseReference.child("CART").orderByChild("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartItems.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    CartItem item = dataSnapshot.getValue(CartItem.class);
                    cartItems.add(item);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("GET CART ERROR", error.toString());
            }
        });
    }


    public void placeOrder(String id, Order order) {
        databaseReference.child("ORDER").child(id).setValue(order);
    }

    public void removeFromCart(String id) {
        databaseReference.child("CART").child(id).removeValue();
    }

    public void removeAllCart(ArrayList<CartItem> cartItems) {
        for (CartItem cart : cartItems
        ) {
            removeFromCart(cart.getId());
        }
    }
}
