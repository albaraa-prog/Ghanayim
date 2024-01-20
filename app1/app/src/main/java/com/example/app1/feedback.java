package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class feedback extends AppCompatActivity {

    ImageView fb, twitter, insta;
    Button Submit;

    ImageView back_to_profile;

    AlertDialog.Builder builder;
    TextInputLayout NameOfUser, EmailOfUser, feedbackOfUser;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mesum-88eef-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        NameOfUser = findViewById(R.id.NameOfUser);
        EmailOfUser = findViewById(R.id.EmailOfUser);
        feedbackOfUser = findViewById(R.id.feedbackOfUser);


        Submit = (Button) findViewById(R.id.Submit);
        builder = new AlertDialog.Builder(this);
        back_to_profile = findViewById(R.id.back_to_profile);

        back_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(feedback.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder.setTitle("Feedback")
                        .setMessage("Thank you for your feedback!")
                        .setCancelable(true)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                finish();
                            }
                        })
                        .show();
                String name = NameOfUser.getEditText().getText().toString();
                String email = EmailOfUser.getEditText().getText().toString();
                String message = feedbackOfUser.getEditText().getText().toString();
                if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                    Toast.makeText(feedback.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child("feedback").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            // sending data to firebase Realtime Database.
                            // we are using Username as unique identity of every user.
                            // so all the other details of user comes under username
                            databaseReference.child("feedback").child(name).child("email").setValue(email);
                            databaseReference.child("feedback").child(name).child("message").setValue(message);
                            //show success msg then finish activity
                            Toast.makeText(feedback.this, "Thanks for your feedback", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(feedback.this,ProfileActivity.class);
                            startActivity(intent);
                            finish();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });


    }
}