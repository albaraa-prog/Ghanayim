package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class cartDetail extends AppCompatActivity {
    TextView cdetailDesc, cdetailprice, cdetailname;
    Button cCheckout;
    ImageView cdetailImage;
    String key = "";
    String imageUrl = "";
    ImageButton GobackcDetail;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartdetail);




        cdetailImage = findViewById(R.id.cimage);
        cdetailname = findViewById(R.id.cname);
        cdetailprice = findViewById(R.id.cprice);
        cdetailDesc = findViewById(R.id.cDesc);


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            cdetailname.setText(bundle.getString("name"));
            cdetailprice.setText(bundle.getString("price"));
            cdetailDesc.setText(bundle.getString("description"));
            key = bundle.getString("key");
            imageUrl = bundle.getString("image");
            Glide.with(this).load(bundle.getString("image")).into(cdetailImage);
        }

        GobackcDetail = findViewById(R.id.gobackcDetail);
        GobackcDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(cartDetail.this, CartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cCheckout = findViewById(R.id.cCheckout);
        cCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data product = new Data(
                        bundle.getString("section"),
                        bundle.getString("name"),
                        bundle.getString("price"),
                        bundle.getString("description"),
                        bundle.getString("image")
                );
                Delivery.Deli.addToDeli(product);


                Intent intent = new Intent(cartDetail.this, Payment.class);
                startActivity(intent);
            }
        });
    }
}