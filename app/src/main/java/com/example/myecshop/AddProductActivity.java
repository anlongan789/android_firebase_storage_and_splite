package com.example.myecshop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myecshop.helpers.FirebaseProductHelper;
import com.example.myecshop.models.Product;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class AddProductActivity extends AppCompatActivity {

    EditText edtName, edtQuantity, edtPrice, edtImageUrl, edtDesc;
    Button btnCreate, btnPreview;
    ImageView ivPreview;
    FirebaseProductHelper firebaseProductHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        firebaseProductHelper = new FirebaseProductHelper();


        edtDesc = findViewById(R.id.edtUpdateDesc);
        edtName = findViewById(R.id.edtUpdateName);
        edtQuantity = findViewById(R.id.edtUpdateQuantity);
        edtPrice = findViewById(R.id.edtUpdatePrice);
        edtImageUrl = findViewById(R.id.edtUpdateImage);

        btnCreate = findViewById(R.id.btnUpdate);
        btnPreview = findViewById(R.id.btnUpdatePreview);

        ivPreview = findViewById(R.id.ivPreviewUpdateImg);

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imgUrl = edtImageUrl.getText().toString();
                Picasso.get().load(imgUrl.equals("") ? "https://t3.ftcdn.net/jpg/03/35/13/14/360_F_335131435_DrHIQjlOKlu3GCXtpFkIG1v0cGgM9vJC.jpg" : imgUrl).into(ivPreview);
            }
        });

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = "prod" + new Date().getTime();
                String name = edtName.getText().toString();
                String desc = edtDesc.getText().toString();
                String imgUrl = edtImageUrl.getText().toString();

                if(name.equals("") || edtQuantity.getText().toString().equals("") || edtPrice.getText().toString().equals("") || desc.equals("")){
                    Toast.makeText(AddProductActivity.this, "All fields is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int quantity = Integer.parseInt(edtQuantity.getText().toString());
                double price = Double.parseDouble(edtPrice.getText().toString());
                firebaseProductHelper.createProduct(id, new Product(id, name, price, quantity, desc, imgUrl));
                Toast.makeText(AddProductActivity.this, "Product created!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}