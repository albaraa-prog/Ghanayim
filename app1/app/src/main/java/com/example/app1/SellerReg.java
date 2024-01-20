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
import com.google.firebase.firestore.FirebaseFirestore;

public class SellerReg extends AppCompatActivity {
    TextInputEditText fullname, user, email, password, conPassword, phone, Rcity1;
    Button BackToLogin, RegSellBtn;
    String UserId, Nametxt, usertxt, emailtxt, PhoneNumber, passwordtxt, conpasstxt,city;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_reg);

        fullname =  findViewById(R.id.reg_name1);
        user =  findViewById(R.id.reg_User1);
        Rcity1 =  findViewById(R.id.reg_city1);
        email = findViewById(R.id.reg_email1);
        password =  findViewById(R.id.reg_Password1);
        conPassword = findViewById(R.id.reg_confirmPass1);
        phone = findViewById(R.id.reg_phone1);
        //creating instance
        firebaseAuth = FirebaseAuth.getInstance();

        BackToLogin = findViewById(R.id.BackToLogin);

        BackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SellerReg.this, sellerLogin.class);
                startActivity(intent);
                finish();
            }
        });

        RegSellBtn = findViewById(R.id.RegSellBtn);

        RegSellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //method for signup as Seller
                SignUpUser();
            }
        });
    }

    private void SignUpUser() {
        Nametxt = fullname.getText().toString();
        usertxt = user.getText().toString();
        emailtxt = email.getText().toString();
        passwordtxt = password.getText().toString();
        conpasstxt = conPassword.getText().toString();
        PhoneNumber =phone.getText().toString();
        city =Rcity1.getText().toString();
        if (TextUtils.isEmpty(Nametxt) && TextUtils.isEmpty(usertxt) && TextUtils.isEmpty(emailtxt) && TextUtils.isEmpty(PhoneNumber) && TextUtils.isEmpty(passwordtxt) && TextUtils.isEmpty(conpasstxt)
                && TextUtils.isEmpty(city)){
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
                            storenewdata();
                            Toast.makeText(SellerReg.this, "User Successfully registered", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SellerReg.this, sellerLogin.class);
                        }else {
                            Toast.makeText(SellerReg.this, "User cannot be registered", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SellerReg.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void storenewdata() {
        FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootnode.getReference("Seller");
        UserModel model = new UserModel(Nametxt,usertxt,emailtxt,PhoneNumber,city);
        reference.child(usertxt).setValue(model);
    }
}