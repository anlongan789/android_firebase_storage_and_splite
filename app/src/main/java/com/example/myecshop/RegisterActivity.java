package com.example.myecshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myecshop.helpers.FirebaseUserHelper;
import com.example.myecshop.models.User;

import java.util.Date;


public class RegisterActivity extends AppCompatActivity {
    EditText edtNewUsername, edtNewPassword, edtRetypePassword, edtEmail, edtAddress, edtPhone;
    Button btnRegister;
    FirebaseUserHelper firebaseUserHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtNewUsername = findViewById(R.id.edtNewUsername);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtAddress = findViewById(R.id.edtAddress);
        edtEmail = findViewById(R.id.edtEmail);
        edtRetypePassword = findViewById(R.id.edtRetypePassword);
        edtPhone = findViewById(R.id.edtPhoneNumber);

        btnRegister = findViewById(R.id.btnRegister);

        firebaseUserHelper = new FirebaseUserHelper();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = "user" + new Date().getTime();
                String name = edtNewUsername.getText().toString();
                String password = edtNewPassword.getText().toString();
                String retypePass = edtRetypePassword.getText().toString();
                String email = edtEmail.getText().toString();
                String address = edtAddress.getText().toString();
                String phone = edtPhone.getText().toString();

                if(name.equals("") || password.equals("") || retypePass.equals("") || email.equals("") || address.equals("") || phone.equals("")){
                    Toast.makeText(RegisterActivity.this, "All field is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!retypePass.equals(password)){
                    Toast.makeText(RegisterActivity.this, "Retype password does not match!", Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseUserHelper.createUser(id, new User(id, name, password, address, phone, email, "User"));
                Toast.makeText(RegisterActivity.this, "REGISTER SUCCESSFULLY!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}