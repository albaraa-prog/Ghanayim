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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class Forgetpassword extends AppCompatActivity {

    TextInputEditText forEmail;
    Button next;
    ImageButton back;
    private String email;
    private String password;
    private  FirebaseAuth auth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_forgetpassword);
        forEmail = findViewById(R.id.email);
        back = findViewById(R.id.back);
        auth = FirebaseAuth.getInstance();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Forgetpassword.this,Dashboard.class);
                startActivity(intent);
            }
        });
        next = findViewById(R.id.next);
        auth = FirebaseAuth.getInstance();
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                email = forEmail.getText().toString();
                if (email.isEmpty()){
                    Toast.makeText(Forgetpassword.this, "Enter your Email!", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(Forgetpassword.this, "Email has been sent", Toast.LENGTH_SHORT).show();
                    updatedata();
                    Intent intent = new Intent(Forgetpassword.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(Forgetpassword.this, "Invalid email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updatedata() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child("usertxt").setValue("password");
    }
}