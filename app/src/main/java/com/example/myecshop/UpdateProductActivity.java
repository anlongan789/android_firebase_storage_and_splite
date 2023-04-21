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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;

public class UpdateProductActivity extends AppCompatActivity {

    EditText edtName, edtQuantity, edtPrice, edtImageUrl, edtDesc;
    Button btnUpdate, btnPreview, btnDelete;
    ImageView ivPreview;
    FirebaseProductHelper firebaseProductHelper;
    Product updateProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);

        updateProduct = (Product) getIntent().getSerializableExtra("Product");

        edtName = findViewById(R.id.edtUpdateName);
        edtDesc = findViewById(R.id.edtUpdateDesc);
        edtQuantity = findViewById(R.id.edtUpdateQuantity);
        edtPrice = findViewById(R.id.edtUpdatePrice);
        edtImageUrl = findViewById(R.id.edtUpdateImage);

        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnPreview = findViewById(R.id.btnUpdatePreview);

        ivPreview = findViewById(R.id.ivPreviewUpdateImg);

        firebaseProductHelper = new FirebaseProductHelper();
        NumberFormat numberFormat = new DecimalFormat("#0");

        edtName.setText(updateProduct.getName());
        edtPrice.setText(numberFormat.format(updateProduct.getPrice()) + "");
        edtQuantity.setText(updateProduct.getQuantity() + "");
        edtDesc.setText(updateProduct.getDescription());
        edtImageUrl.setText(updateProduct.getImgUrl());

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imgUrl = edtImageUrl.getText().toString();
                Picasso.get().load(imgUrl.equals("") ? "https://t3.ftcdn.net/jpg/03/35/13/14/360_F_335131435_DrHIQjlOKlu3GCXtpFkIG1v0cGgM9vJC.jpg" : imgUrl).into(ivPreview);
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String desc = edtDesc.getText().toString();
                String imgUrl = edtImageUrl.getText().toString();

                if(name.equals("") || edtQuantity.getText().toString().equals("") || edtPrice.getText().toString().equals("") || desc.equals("")){
                    Toast.makeText(UpdateProductActivity.this, "All fields is required!", Toast.LENGTH_SHORT).show();
                    return;
                }
                int quantity = Integer.parseInt(edtQuantity.getText().toString());
                double price = Double.parseDouble(edtPrice.getText().toString());
                firebaseProductHelper.createProduct(updateProduct.getId(), new Product(updateProduct.getId(), name, price, quantity, desc, imgUrl));
                Toast.makeText(UpdateProductActivity.this, "Product updated!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UpdateProductActivity.this, updateProduct.getName()+" Deleted!", Toast.LENGTH_SHORT).show();
                firebaseProductHelper.deleteProduct(updateProduct.getId());
                finish();
            }
        });
    }
}