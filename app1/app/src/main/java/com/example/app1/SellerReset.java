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

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SellerReset extends AppCompatActivity {

    ImageButton BckToSellerEmail;

    TextInputLayout SellerUser, SellerNewPass, SellerConfirm;

    Button SellerResetPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_seller_reset);

        BckToSellerEmail = findViewById(R.id.BckToSellerEmail);
        SellerUser = findViewById(R.id.SellerUser);
        SellerNewPass = findViewById(R.id.SellerNewPass);
        SellerConfirm = findViewById(R.id.SellerConfirm);
        SellerResetPass = findViewById(R.id.SellerReset);

        BckToSellerEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerReset.this, SellerForgetPassword.class);
                startActivity(intent);
                finish();
            }
        });

        SellerResetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String newpasstxt = SellerNewPass.getEditText().getText().toString();
                 String confirmpasstxt = SellerConfirm.getEditText().getText().toString();
                 String confirmusertxt = SellerUser.getEditText().getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("Seller");

                usersRef.orderByChild("Name").equalTo(confirmusertxt).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                String userKey = userSnapshot.getKey();

                                // Update the password
                                DatabaseReference userRef = usersRef.child(userKey);
                                userRef.child("password").setValue(newpasstxt);

                                // Display a success message
                                Toast.makeText(SellerReset.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Display an error message
                            Toast.makeText(SellerReset.this, "No user found with the provided username!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(SellerReset.this, "Failed to update password: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}