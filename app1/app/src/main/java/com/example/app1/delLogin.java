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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class delLogin extends AppCompatActivity {
    Button DelReg, DelGo, forgetDel;

    String  _name5, _city5, _add5, _phn5;
    ImageButton backToOpt2;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");
    TextInputEditText DelUsername, DelPassword;
    private  FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_del_login);

        DelUsername = findViewById(R.id.Delusername);
        DelPassword = findViewById(R.id.DelPassword);

        DelReg = findViewById(R.id.DelReg);

        DelReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(delLogin.this, delReg.class);
                startActivity(intent);
                finish();
            }
        });

        forgetDel = findViewById(R.id.forgetDel);

        forgetDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(delLogin.this, DelForgetPass.class);
                startActivity(intent);
                finish();
            }
        });

        backToOpt2 = findViewById(R.id.BackToOpt2);

        backToOpt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(delLogin.this, useroption.class);
                startActivity(intent);
                finish();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();

        DelGo = findViewById(R.id.DelGo);

        DelGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInUser();
                databaseReference = FirebaseDatabase.getInstance().getReference();
            }
        });

    }
    private void SignInUser() {
        //get edit text into string
        String usernametxt = DelUsername.getText().toString();
        String passwordtxt = DelPassword.getText().toString();

        if (TextUtils.isEmpty(usernametxt) && TextUtils.isEmpty(passwordtxt)){
            Toast.makeText(this, "Please Enter Email and Password!", Toast.LENGTH_SHORT).show();
        }else {
            //create firebase auth instance
            firebaseAuth.signInWithEmailAndPassword(usernametxt,passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        /*
                        Intent intent = getIntent();
                        _name5 = intent.getStringExtra("nametxt");
                        _city5 = intent.getStringExtra("city");
                        _add5 = intent.getStringExtra("Address");
                        _phn5 = intent.getStringExtra("phoneNumber");
                        intent1.putExtra("nametxt",_name5);
                        intent1.putExtra("city",_city5);
                        intent1.putExtra("Address",_add5);
                        intent1.putExtra("phoneNumber",_phn5);
                         */
                        Intent intent1 = new Intent(delLogin.this, Delivery.class);
                        startActivity(intent1);
                        finish();
                        Toast.makeText(delLogin.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(delLogin.this, "Wrong email or password!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(delLogin.this, "Invalid Email and username", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}