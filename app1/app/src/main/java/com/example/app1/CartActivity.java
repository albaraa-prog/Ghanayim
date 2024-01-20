package com.example.app1;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private static final int REQUEST_DELIVERY = 123;


    private TextView cartAmount;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        cartAmount = findViewById(R.id.cartamount);

        double totalPrice = Cart.calculateTotalPrice();
        cartAmount.setText("Total Amount: OMR " + totalPrice);



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Cart);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            } else if (item.getItemId() == R.id.Profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
            return false;
        });

        RecyclerView recyclerView = findViewById(R.id.cartview);
        CartAdapter cartAdapter = new CartAdapter(Cart.getCartItems());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(cartAdapter);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_DELIVERY && resultCode == RESULT_OK) {
            startActivity(new Intent(CartActivity.this, Payment.class));
        }
    }

    public static class Cart {
        static ArrayList<Data> cartItems = new ArrayList<>();


        public static void addToCart(Data product) {
            if (product != null) {
                cartItems.add(product);
            }
        }

        public static ArrayList<Data> getCartItems() {
            return cartItems;
        }

        public static void clearCart() {
            cartItems.clear();
        }

        public static double calculateTotalPrice() {
            double totalPrice = 0.0;
            for (Data product : cartItems) {
                try {
                    double productPrice = Double.parseDouble(product.getDataprice());
                    totalPrice += productPrice;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
            return totalPrice;
        }
    }

    private class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
        private ArrayList<Data> cartItems;

        CartAdapter(ArrayList<Data> cartItems) {
            this.cartItems = cartItems;
        }

        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleproduct, parent, false);
            return new CartViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
            Data product = cartItems.get(position);
            Glide.with(holder.itemView.getContext()).load(product.getDataimg()).into(holder.recimage);
            holder.recName.setText(product.getDataname());
            holder.recPrice.setText(product.getDataprice());
            holder.recDesc.setText(product.getDatadesc());
            holder.recSec.setText(product.getSection());

            holder.recProd.setOnClickListener(view -> {
                Intent intent = new Intent(view.getContext(), cartDetail.class);
                intent.putExtra("image", product.getDataimg());
                intent.putExtra("name", product.getDataname());
                intent.putExtra("price", product.getDataprice());
                intent.putExtra("description", product.getDatadesc());
                intent.putExtra("section", product.getSection());
                intent.putExtra("Key", product.getKey());
                view.getContext().startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return cartItems.size();
        }

        class CartViewHolder extends RecyclerView.ViewHolder {
            ImageView recimage;
            TextView recName, recDesc, recPrice, recSec;
            CardView recProd;
            CartViewHolder(@NonNull View itemView) {
                super(itemView);
                recimage = itemView.findViewById(R.id.recimage);
                recProd = itemView.findViewById(R.id.recprod);
                recName = itemView.findViewById(R.id.recname);
                recPrice = itemView.findViewById(R.id.recprice);
                recDesc = itemView.findViewById(R.id.recdesc);
                recSec = itemView.findViewById(R.id.recsec);
            }
        }
    }
}
