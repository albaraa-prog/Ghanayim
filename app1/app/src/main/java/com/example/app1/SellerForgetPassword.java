package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SellerForgetPassword extends AppCompatActivity {

    ImageButton BackToSeller;
    TextInputEditText Selleremail;
    Button next2;

    private String email;
    private FirebaseAuth auth;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_seller_forget_password);

        BackToSeller = findViewById(R.id.backToSeller);
        Selleremail = findViewById(R.id.Selleremail);
        next2 = findViewById(R.id.next2);

        BackToSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerForgetPassword.this, sellerLogin.class);
                startActivity(intent);
                finish();
            }
        });

        next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("Seller");

                email = Selleremail.getText().toString();
                if (email.isEmpty()){
                    Toast.makeText(SellerForgetPassword.this, "Enter your Email!", Toast.LENGTH_SHORT).show();
                }else {
                    forgetPassword();
                }
            }
        });
    }

    private void forgetPassword() {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(SellerForgetPassword.this, "Email has been sent", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SellerForgetPassword.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(SellerForgetPassword.this, "Invalid email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}