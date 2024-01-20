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

public class delReg extends AppCompatActivity {
    TextInputEditText fullname, user, email, password, conPassword, phone, Rcity2;
    Button BackToLogin2, RegDelBtn;
    private FirebaseAuth firebaseAuth;
    String UserId, Nametxt, usertxt, emailtxt, PhoneNumber, passwordtxt, conpasstxt, city;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_del_reg);

        fullname = findViewById(R.id.reg_name2);
        user =  findViewById(R.id.reg_User2);
        Rcity2 =  findViewById(R.id.reg_city2);
        email = findViewById(R.id.reg_email2);
        password = findViewById(R.id.reg_Password2);
        conPassword =  findViewById(R.id.reg_confirmPass2);
        phone = findViewById(R.id.reg_phone2);

        firebaseAuth = FirebaseAuth.getInstance();
        BackToLogin2 = findViewById(R.id.BackToLogin2);

        BackToLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(delReg.this, delLogin.class);
                startActivity(intent);
                finish();
            }
        });

        RegDelBtn = findViewById(R.id.RegDelBtn);

        RegDelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nametxt = fullname.getText().toString();
                usertxt = user.getText().toString();
                emailtxt = email.getText().toString();
                passwordtxt = password.getText().toString();
                conpasstxt = conPassword.getText().toString();
                PhoneNumber =phone.getText().toString();
                city = Rcity2.getText().toString();

                if (TextUtils.isEmpty(Nametxt) && TextUtils.isEmpty(usertxt) && TextUtils.isEmpty(emailtxt) && TextUtils.isEmpty(PhoneNumber) && TextUtils.isEmpty(passwordtxt) && TextUtils.isEmpty(conpasstxt) && TextUtils.isEmpty(city)){
                    Toast.makeText(delReg.this, "Please fill all the fields!", Toast.LENGTH_SHORT).show();
                } else if (!passwordtxt.equals(conpasstxt)) {
                    Toast.makeText(delReg.this, "Password do not match", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.createUserWithEmailAndPassword(emailtxt,passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                if (firebaseAuth.getCurrentUser()!=null){
                                    UserId=firebaseAuth.getCurrentUser().getUid();
                                    storedata();
                                    Toast.makeText(delReg.this, "User Successfully registered", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(delReg.this, delLogin.class);
                                }else {
                                    Toast.makeText(delReg.this, "User cannot be registered", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(delReg.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    private void storedata() {
        FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootnode.getReference("Delivery");
        UserModel model = new UserModel(Nametxt,usertxt,emailtxt,PhoneNumber,city);
        reference.child(usertxt).setValue(model);
    }
}