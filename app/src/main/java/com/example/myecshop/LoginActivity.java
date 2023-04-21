package com.example.myecshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myecshop.helpers.FirebaseUserHelper;
import com.example.myecshop.helpers.UserDBHelper;
import com.example.myecshop.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername, edtPassword;
    Button btnLogin, btnToRegister;
    ArrayList<User> listUsers;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://my-ec-shop-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
    UserDBHelper userDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userDBHelper = new UserDBHelper(this);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnToRegister = findViewById(R.id.btnResgisterScreen);

        listUsers = new ArrayList<>();
//        getUsers();
        Log.d("USERS", "List users " + listUsers.toString());
        btnToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();
                if (name.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, "Missing field!", Toast.LENGTH_SHORT).show();
                    return;
                }

                for (User user : listUsers
                ) {
                    if (name.equals(user.getName()) && password.equals(user.getPassword())) {
                        if (user.getRole().equals("User")) {
                            Intent userIntent = new Intent(LoginActivity.this, MainActivity.class);
                            Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                            userDBHelper.addUser(user);
                            startActivity(userIntent);

                        } else {
                            Intent adminIntent = new Intent(LoginActivity.this, ManageProductActivity.class);
                            Toast.makeText(LoginActivity.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                            startActivity(adminIntent);
                        }
                    }

                }
            }
        });
    }



    public void getUsers() {
        databaseReference.child("USER").orderByChild("id").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()
                ) {
                    User user = dataSnapshot.getValue(User.class);
                    System.out.println("User loaded " + user.getName());
                    listUsers.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("GET USER ERROR", "onCancelled: " + error.getMessage());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        userDBHelper.deleteAllUser();
        getUsers();
    }
}