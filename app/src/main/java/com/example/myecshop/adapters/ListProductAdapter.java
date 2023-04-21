package com.example.myecshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myecshop.R;
import com.example.myecshop.models.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class ListProductAdapter extends BaseAdapter {

    private ArrayList<Product> listProducts;
    private LayoutInflater layoutInflater;
    private Context context;

    public ListProductAdapter(ArrayList<Product> listProducts) {
        this.listProducts = listProducts;
    }

    @Override
    public int getCount() {
        return listProducts.size();
    }

    @Override
    public Object getItem(int position) {
        return listProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewProducts;
        if(convertView == null){
            viewProducts = View.inflate(parent.getContext(), R.layout.item_list_products, null);
        }
        else{
            viewProducts = convertView;
        }

        NumberFormat numberFormat = new DecimalFormat("#0");
        Product product = (Product) getItem(position);
        ((TextView) viewProducts.findViewById(R.id.txtProductManagementName)).setText(String.format("" + product.getName()));
        ((TextView) viewProducts.findViewById(R.id.txtProductManagementDesc)).setText(String.format("" + product.getDescription()));
        ((TextView) viewProducts.findViewById(R.id.txtProductManagementPrice)).setText(String.format("" + numberFormat.format(product.getPrice()) + "đ"));
        ((TextView) viewProducts.findViewById(R.id.txtProductManagementQuantity)).setText(String.format("Quantity: " + product.getQuantity()));
        Picasso.get().load(product.getImgUrl()).into((ImageView) viewProducts.findViewById(R.id.ivProductImg));

        return viewProducts;
    }
}
