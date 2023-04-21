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
import java.util.Arrays;

public class GridViewCustomAdapter extends BaseAdapter {
    private ArrayList<Product> productArrayList;
    private LayoutInflater inflater;
    private Context context;
    private int mTypeCustom;

    public GridViewCustomAdapter(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    @Override
    public int getCount() {
        return productArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return productArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewProduct;
        if(view == null){
            viewProduct = View.inflate(viewGroup.getContext(), R.layout.item_grid_view_custom, null);
        }
        else{
            viewProduct = view;
        }

        Product product = (Product) getItem(i);
        NumberFormat numberFormat = new DecimalFormat("#0");
        ((TextView) viewProduct.findViewById(R.id.txtName)).setText(String.format("" + product.getName()));
        ((TextView) viewProduct.findViewById(R.id.txtPrice)).setText(String.format(numberFormat.format(product.getPrice()) + "đ"));
        Picasso.get().load(product.getImgUrl()).into(((ImageView) viewProduct.findViewById(R.id.ivProduct)));
        return viewProduct;
    }
}
