package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Rating extends AppCompatActivity {

AppCompatButton later;
AlertDialog.Builder builder;

AppCompatButton rateNowbtn;
    private float userRate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FirebaseApp.initializeApp(this);
        final RatingBar ratingbar = findViewById(R.id.Ratingbar);
        final ImageView ratingimage = findViewById(R.id.ratingimage);
        AppCompatButton rateNowbtn = findViewById(R.id.rateNowbtn);

        later = findViewById(R.id.later);

        later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Rating.this,ProfileActivity.class);
                startActivity(intent);
            }
        });


        rateNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");
                        DatabaseReference ratingRef = rootRef.child("ratings");

                        // Store the rating value at the "ratings" node in the Realtime Database
                        ratingRef.setValue(ratingBar);
                    }
                });
            }
        });


        ratingbar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                if (rating <= 1){
                    ratingimage.setImageResource(R.drawable.angry);
                }
                else if (rating <= 2) {
                    ratingimage.setImageResource(R.drawable.sad);
                }
                else if (rating <= 3) {
                    ratingimage.setImageResource(R.drawable.mid);
                }
                else if (rating <= 4) {
                    ratingimage.setImageResource(R.drawable.alright);
                }
                else if (rating <= 5) {
                    ratingimage.setImageResource(R.drawable.happy);
                }

                // animate emoji image
                animateImage(ratingimage);

                userRate = rating;
            }
            private void animateImage(ImageView ratingimage){
                ScaleAnimation scaleAnimation = new ScaleAnimation(0,1f,0,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);

                scaleAnimation.setFillAfter(true);
                scaleAnimation.setDuration(200);
                ratingimage.startAnimation(scaleAnimation);
            }
        });

        rateNowbtn = (AppCompatButton) findViewById(R.id.rateNowbtn);
        builder = new AlertDialog.Builder(this);

        rateNowbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Rating")
                        .setMessage("Thank you for rating our app")
                        .setCancelable(true)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                finish();
                            }
                        })
                        .show();
            }
        });


    }
}