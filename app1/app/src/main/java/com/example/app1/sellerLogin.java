package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class sellerLogin extends AppCompatActivity {
    Button SellReg, SellGo, ForgetSell;
    ImageButton backtoOpt;

    TextInputEditText SellerUsername, SellerPassword;

    private FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_seller_login);

        SellReg = findViewById(R.id.SellReg);

        SellReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sellerLogin.this, SellerReg.class);
                startActivity(intent);
                finish();
            }
        });

        backtoOpt = findViewById(R.id.BackToOpt);

        backtoOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sellerLogin.this, useroption.class);
                startActivity(intent);
                finish();
            }
        });

        ForgetSell = findViewById(R.id.forgetSell);

        ForgetSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sellerLogin.this, SellerForgetPassword.class);
                startActivity(intent);
                finish();
            }
        });

        SellerUsername = findViewById(R.id.Sellerusername);
        SellerPassword = findViewById(R.id.SellerPassword);

        firebaseAuth = FirebaseAuth.getInstance();

        SellGo = findViewById(R.id.SellGo);

        SellGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create method for sign in
                SignInUser();
                databaseReference = FirebaseDatabase.getInstance().getReference();
            }
        });
    }

    private void SignInUser() {
        String usernametxt = SellerUsername.getText().toString();
        String passwordtxt = SellerPassword.getText().toString();

        if (TextUtils.isEmpty(usernametxt) && TextUtils.isEmpty(passwordtxt)){
            Toast.makeText(this, "Please Enter Email and Password!", Toast.LENGTH_SHORT).show();
        }else {
            //create firebase auth instance
            firebaseAuth.signInWithEmailAndPassword(usernametxt,passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Intent intent = new Intent(sellerLogin.this, upload.class);
                        startActivity(intent);
                        Toast.makeText(sellerLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(sellerLogin.this, "Wrong email or password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(sellerLogin.this, "Invalid Email and username", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}