package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;


public class Detail extends AppCompatActivity {
    TextView detailDesc, detailprice, detailname;
    Button buybutton;
    ImageView detailImage;
    String key = "";
    String imageUrl = "";
    ImageButton GobackDetail, save2fav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailImage = findViewById(R.id.image);
        detailname = findViewById(R.id.name);
        detailprice = findViewById(R.id.price);
        detailDesc = findViewById(R.id.Desc);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            detailname.setText(bundle.getString("name"));
            detailprice.setText(bundle.getString("price"));
            detailDesc.setText(bundle.getString("description"));
            key = bundle.getString("key");
            imageUrl = bundle.getString("image");
            Glide.with(this).load(bundle.getString("image")).into(detailImage);
        }

        GobackDetail = findViewById(R.id.gobackDetail);
        GobackDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Detail.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        buybutton = findViewById(R.id.buybutton);
        buybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data product = new Data(
                        bundle.getString("section"),
                        bundle.getString("name"),
                        bundle.getString("price"),
                        bundle.getString("description"),
                        bundle.getString("image")
                );
                CartActivity.Cart cart = new CartActivity.Cart();
                cart.addToCart(product);
                Toast.makeText(Detail.this, "Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
        save2fav = findViewById(R.id.save2fav);
        save2fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Data product = new Data(
                        bundle.getString("section"),
                        bundle.getString("name"),
                        bundle.getString("price"),
                        bundle.getString("description"),
                        bundle.getString("image")
                );
                Favorites.Fav fav = new Favorites.Fav();
                Favorites.Fav.addToFav(product);
                Toast.makeText(Detail.this, "Product added to favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}