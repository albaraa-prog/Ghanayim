package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {
    TextInputEditText fullname, user, email, password, conPassword, phone, Rcity;
    Button registerbtn, loginbtn;
    String UserId, Nametxt, usertxt, emailtxt, PhoneNumber, passwordtxt, conpasstxt, city;
    private FirebaseAuth firebaseAuth;

    //create object of Database Reference class to access firebase's Realtime Database

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullname = findViewById(R.id.reg_name);
        user =  findViewById(R.id.reg_User);
        Rcity =  findViewById(R.id.reg_city);
        email =  findViewById(R.id.reg_email);
        password =  findViewById(R.id.reg_Password);
        conPassword =  findViewById(R.id.reg_confirmPass);
        phone =  findViewById(R.id.reg_phone);

        //create firebase instance
        firebaseAuth = FirebaseAuth.getInstance();

        loginbtn = findViewById(R.id.regToLoginBtn);


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(signup.this, Dashboard.class);
                startActivity(intent);
            }
        });
        registerbtn = (Button) findViewById(R.id.reg_Btn);
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create a method for Signup
                SignUpUser();

            }

        });


    }



    public void SignUpUser() {
        Nametxt = fullname.getText().toString();
        usertxt = user.getText().toString();
        emailtxt = email.getText().toString();
        passwordtxt = password.getText().toString();
        conpasstxt = conPassword.getText().toString();
        PhoneNumber =phone.getText().toString();
        city = Rcity.getText().toString();

        if (TextUtils.isEmpty(Nametxt) && TextUtils.isEmpty(usertxt) && TextUtils.isEmpty(emailtxt) && TextUtils.isEmpty(PhoneNumber) && TextUtils.isEmpty(passwordtxt) &&
                TextUtils.isEmpty(conpasstxt) && TextUtils.isEmpty(city)){
            Toast.makeText(this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
        } else if (!passwordtxt.equals(conpasstxt)) {
            Toast.makeText(this, "Password do not match", Toast.LENGTH_SHORT).show();
        } else {
            firebaseAuth.createUserWithEmailAndPassword(emailtxt,passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        if (firebaseAuth.getCurrentUser()!=null){
                            UserId=firebaseAuth.getCurrentUser().getUid();
                            Toast.makeText(signup.this, "User Successfully registered", Toast.LENGTH_SHORT).show();
                            storeNewData();
                            Intent intent = new Intent(signup.this, Dashboard.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(signup.this, "User cannot be registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void storeNewData() {
        FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
        databaseReference = rootnode.getReference("Users");
        UserModel model = new UserModel(Nametxt,usertxt,emailtxt,PhoneNumber,city);
        databaseReference.child(usertxt).setValue(model);
    }
}
