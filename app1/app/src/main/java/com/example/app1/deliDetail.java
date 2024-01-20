
package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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


public class deliDetail extends AppCompatActivity {
    TextView  deliprice, deliname, deliDesc;
    Button  accept;
    ImageView deliImage;
    String key = "";
    String imageUrl = "";
    ImageButton GobackdeliDetail;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delidetail);
        deliImage = findViewById(R.id.deliimage);
        deliname = findViewById(R.id.deliname);
        deliprice = findViewById(R.id.deliprice);
        deliDesc = findViewById(R.id.deliDesc);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            deliname.setText(bundle.getString("name"));
            deliprice.setText(bundle.getString("price"));
            deliDesc.setText(bundle.getString("description"));
            key = bundle.getString("key");
            imageUrl = bundle.getString("image");
            Glide.with(this).load(bundle.getString("image")).into(deliImage);
        }
        accept = findViewById(R.id.accept);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent(deliDetail.this, Payment.class);
                setResult(RESULT_OK, resultIntent);
                finish();
                Intent intent = new Intent(deliDetail.this, Delivery.class);
                startActivity(intent);
                Delivery.Deli.clearDeli();
                finish();
                Toast.makeText(deliDetail.this, "Order accepted", Toast.LENGTH_SHORT).show();
            }
        });
        GobackdeliDetail = findViewById(R.id.gobackdeliDetail);
        GobackdeliDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(deliDetail.this, Delivery.class);
                startActivity(intent);
            }
        });
    }
}

