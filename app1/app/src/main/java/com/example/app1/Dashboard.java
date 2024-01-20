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
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {
    TextInputEditText login_username, Login_Password;
    Button callSignup;
    Button GO;
    Button forget;
    ImageButton backtoopt3;
    ProgressBar loading;
    private String Name, Username, email, pass, conpass, Phn;
    private  FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);

        callSignup = findViewById(R.id.signup_screen);

        callSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Dashboard.this,signup.class);
                startActivity(intent);
                finish();
            }
        });

        backtoopt3= findViewById(R.id.BackToOpt3);

        backtoopt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,useroption.class);
                startActivity(intent);
                finish();
            }
        });


        forget = findViewById(R.id.forget);

        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(Dashboard.this,Forgetpassword.class);
                startActivity(intent);
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        login_username = findViewById(R.id.username);
        Login_Password = findViewById(R.id.Password);
        GO = (Button) findViewById(R.id.GO);


        GO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create method for sign in
                SignInUser();
            }
        });
        showUserdata();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    }

    private void showUserdata() {

    }


    private void SignInUser() {
        //get text from edittext to string
        String email = login_username.getText().toString();
        String password = Login_Password.getText().toString();

        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please Enter Username and Password!", Toast.LENGTH_SHORT).show();
        }else {
            //create firebase auth instance
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Dashboard.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(Dashboard.this, MainActivity.class);
                        startActivity(intent1);
                    }else {
                        Toast.makeText(Dashboard.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Dashboard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }


}