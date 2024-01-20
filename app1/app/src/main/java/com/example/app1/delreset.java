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

public class delreset extends AppCompatActivity {
    ImageButton backToEmail;
    TextInputLayout DelUser, DelNewPass, DelConfirm;
    Button ResetDel;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_delreset);

        DelUser = findViewById(R.id.ConfirmDelUser);
        DelNewPass = findViewById(R.id.DelNewPass);
        DelConfirm = findViewById(R.id.ConfirmDelPass);
        backToEmail = findViewById(R.id.backToEmail);
        ResetDel = findViewById(R.id.resetDel);

        backToEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(delreset.this, DelForgetPass.class);
                startActivity(intent);
                finish();
            }
        });

        ResetDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String newpasstxt = DelNewPass.getEditText().getText().toString();
                 String confirmpasstxt = DelConfirm.getEditText().getText().toString();
                 String confirmusertxt = DelUser.getEditText().getText().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference usersRef = database.getReference("Delivery");

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
                                Toast.makeText(delreset.this, "Password updated successfully!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Display an error message
                            Toast.makeText(delreset.this, "No user found with the provided username!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(delreset.this, "Failed to update password: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}