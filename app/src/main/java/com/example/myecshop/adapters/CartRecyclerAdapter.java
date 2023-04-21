package com.example.myecshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myecshop.R;
import com.example.myecshop.helpers.FirebaseCartHelper;
import com.example.myecshop.models.CartItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class CartRecyclerAdapter extends RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder> {

    Context context;
    ArrayList<CartItem> cartItems;
    FirebaseCartHelper firebaseCartHelper = new FirebaseCartHelper();

    public CartRecyclerAdapter(Context context, ArrayList<CartItem> cartItems) {
        this.context = context;
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem item = cartItems.get(position);
        NumberFormat numberFormat = new DecimalFormat("#0");

        Picasso.get().load(item.getImgUrl()).into(holder.ivCartItem);
        holder.txtCartItemName.setText(item.getName());
        holder.txtCartItemQuantity.setText("Quantity: " + item.getQuantity());
        holder.txtCartItemPrice.setText(numberFormat.format(item.getPrice()) + "đ");

        holder.btnRemoveFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Remove item " + item.getName(), Toast.LENGTH_SHORT).show();
                firebaseCartHelper.removeFromCart(item.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCartItem;
        TextView txtCartItemName;
        TextView txtCartItemQuantity;
        TextView txtCartItemPrice;
        Button btnRemoveFromCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivCartItem = itemView.findViewById(R.id.ivCartItem);
            txtCartItemName = itemView.findViewById(R.id.txtCartItemName);
            txtCartItemPrice = itemView.findViewById(R.id.txtCartItemPrice);
            txtCartItemQuantity = itemView.findViewById(R.id.txtCartItemQuantity);
            btnRemoveFromCart = itemView.findViewById(R.id.btnRemoveFromCart);
        }
    }
}
