package com.example.app1;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class Delivery extends AppCompatActivity {

    ImageButton delilogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);

        delilogout = findViewById(R.id.delilogout);
        delilogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutConfirmationDialog();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.Deliveryview);
        DeliAdapter deliAdapter = new DeliAdapter(Deli.getDeliItems());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(deliAdapter);
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to log out?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Delivery.this, useroption.class);
                startActivity(intent);
                finish();
                Toast.makeText(Delivery.this, "User logged out", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do nothing or handle as needed
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static class Deli {

        private static ArrayList<Data> deliveryItemList = new ArrayList<>();

        public Deli() {
            // Private constructor to prevent instantiation.
        }

        public static void addToDeli(Data product) {
            deliveryItemList.add(product);
        }

        public static ArrayList<Data> getDeliItems() {
            return deliveryItemList;
        }

        public static void clearDeli() {
            deliveryItemList.clear();
        }
    }

    public static class DeliAdapter extends RecyclerView.Adapter<Delivery.DeliAdapter.DeliveryViewHolder> {
        private Context context;
        private List<Data> deliveryItemList;

        public DeliAdapter(List<Data> deliveryItemList) {
            this.deliveryItemList = deliveryItemList;
        }

        @NonNull
        @Override
        public DeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            context = parent.getContext();
            View view = LayoutInflater.from(context).inflate(R.layout.deliproduct, parent, false);
            return new DeliveryViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DeliveryViewHolder holder, int position) {
            Data product = deliveryItemList.get(position);

            Glide.with(context).load(product.getDataimg()).into(holder.delirecimage);
            holder.delirecName.setText(product.getDataname());
            holder.delirecPrice.setText(product.getDataprice());
            holder.delirecDesc.setText(product.getDataprice());

            holder.delirecprod.setOnClickListener(view -> {
                Intent intent = new Intent(context, deliDetail.class);
                intent.putExtra("image", product.getDataimg());
                intent.putExtra("name", product.getDataname());
                intent.putExtra("price", product.getDataprice());
                intent.putExtra("description", product.getDatadesc());
                intent.putExtra("Key", product.getKey());
                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return deliveryItemList.size();
        }

        static class DeliveryViewHolder extends RecyclerView.ViewHolder {
            ImageView delirecimage;
            TextView delirecName, delirecPrice, delirecDesc;
            CardView delirecprod;

            public DeliveryViewHolder(@NonNull View itemView) {
                super(itemView);
                delirecimage = itemView.findViewById(R.id.delirecimage);
                delirecName = itemView.findViewById(R.id.delirecname);
                delirecPrice = itemView.findViewById(R.id.delirecprice);
                delirecDesc = itemView.findViewById(R.id.delirecDesc);
                delirecprod = itemView.findViewById(R.id.delirecprod);
            }
        }
    }
}
