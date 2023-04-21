package com.example.myecshop.helpers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.myecshop.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseUserHelper {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://my-ec-shop-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

    public void createUser(String id, User newUser) {
        databaseReference.child("USER").child(id).setValue(newUser);
    }


}
