package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class reset extends AppCompatActivity {
    Button reset1;
    TextInputEditText newpass, confirmpass, oldpass;
    ImageView backing;
    AlertDialog.Builder builder;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        newpass = findViewById(R.id.restnewpass);
        confirmpass = findViewById(R.id.resetconfirmpass);
        oldpass = findViewById(R.id.confirmuser);
        firebaseAuth = FirebaseAuth.getInstance();

        reset1 = (Button) findViewById(R.id.reset1);
        builder = new AlertDialog.Builder(this);

        reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldpassword = oldpass.getText().toString().trim();
                String newP = newpass.getText().toString().trim();
                String confrim = confirmpass.getText().toString().trim();

                if (TextUtils.isEmpty(oldpassword)){
                    Toast.makeText(reset.this, "Enter Current password", Toast.LENGTH_SHORT).show();
                }else if (!newP.equals(confrim)){
                    Toast.makeText(reset.this, "Password do not match", Toast.LENGTH_SHORT).show();
                } else if (newP.length()<6) {
                    Toast.makeText(reset.this, "Password must be atleast 6 characters", Toast.LENGTH_SHORT).show();
                }
                UpdatePassword(oldpassword, newP);
            }
        });
              /*  builder.setTitle("Reset Password")
                        .setMessage("You have successfully updated your password")
                        .setCancelable(true)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                finish();
                            }
                        })
                        .show(); */

        backing = findViewById(R.id.backing);

        backing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(reset.this,UserProfile.class);
                startActivity(intent);
            }
        });
    }

    private void UpdatePassword(String oldpassword, String newP) {
        //get current user
        FirebaseUser user = firebaseAuth.getCurrentUser();
        //before changing password re-authenticate user
        AuthCredential authCredential = EmailAuthProvider.getCredential(user.getEmail(), oldpassword);
        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                user.updatePassword(newP)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(reset.this, "Password updated!", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(reset.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(reset.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}