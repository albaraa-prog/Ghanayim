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

public class DelForgetPass extends AppCompatActivity {
    TextInputEditText Delemail;
    ImageButton backToDel;
    Button DelNext;

    private String email1;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_del_forget_pass);

        Delemail = findViewById(R.id.Delemail);
        DelNext = findViewById(R.id.DelNext);
        backToDel = findViewById(R.id.backToDel);

        backToDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DelForgetPass.this, delLogin.class);
                startActivity(intent);
                finish();
            }
        });

        DelNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Delivery");

                email1 = Delemail.getText().toString();
                if (email1.isEmpty()){
                    Toast.makeText(DelForgetPass.this, "Enter your Email!", Toast.LENGTH_SHORT).show();
                }else {
                    forgetPassword();
                }
            }
        });
    }

    private void forgetPassword() {
        auth.sendPasswordResetEmail(email1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(DelForgetPass.this, "Email has been sent", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DelForgetPass.this, Dashboard.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(DelForgetPass.this, "Invalid email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}