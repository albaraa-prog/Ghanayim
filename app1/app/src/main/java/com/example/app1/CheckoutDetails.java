/*
package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class CheckoutDetails extends AppCompatActivity {

    ImageButton BackCheckout;
    Button nxt;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    TextInputEditText UserFullname, UserCity, Address, UserPhn;
    String _UserName, _Name, _email, _Phn, avatar, _city, address;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_details);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        UserFullname= findViewById(R.id.UserFullName);
        UserCity= findViewById(R.id.UserCity);
        Address= findViewById(R.id.Address);
        UserPhn= findViewById(R.id.UserPhn);

        BackCheckout = findViewById(R.id.BackCheckOutDetails);
        BackCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckoutDetails.this, CartActivity.class);
                startActivity(intent);
                finish();
            }
        });

        nxt = findViewById(R.id.nxt);
        nxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateData();
            }
        });

        Query query = databaseReference.orderByChild("emailtxt").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()){
                    _Name = "" + s.child("nametxt").getValue();
                    _Phn = "" + s.child("phoneNumber").getValue();
                    _city = "" + s.child("city").getValue();
                    address = "" + s.child("Address").getValue();


                    UserFullname.setText(_Name);
                    UserCity.setText(_city);
                    UserPhn.setText(_Phn);
                    Address.setText(address);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void updateData() {
        if (isNameChanged() || isCityChanged() || isPhoneChanged() || isAddressChanged()){
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Data is same cannot be updated", Toast.LENGTH_SHORT).show();


            Intent intent = new Intent(CheckoutDetails.this, Payment.class);
            intent.putExtra("nametxt",_Name);
            intent.putExtra("phoneNumber",_Phn);
            intent.putExtra("city",_city);
            intent.putExtra("Address",address);
            startActivity(intent);
            finish();
        }
    }
    private boolean isAddressChanged() {
        if (!address.equals(Address.getText().toString())){
            databaseReference.child(_UserName).child("Address").setValue(Address.getText().toString());
            address = Address.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isPhoneChanged() {
        if (!_Phn.equals(UserPhn.getText().toString())){
            databaseReference.child(_UserName).child("phoneNumber").setValue(UserPhn.getText().toString());
            _Phn = UserPhn.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isCityChanged() {
        if (!_city.equals(UserCity.getText().toString())){
            databaseReference.child(_UserName).child("usertxt").setValue(UserCity.getText().toString());
            _UserName = UserCity.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isNameChanged() {
        if (!_Name.equals(UserFullname.getText().toString())){
            databaseReference.child(_UserName).child("nametxt").setValue(UserFullname.getText().toString());
            _Name = UserFullname.getText().toString();
            return true;
        }else {
            return false;
        }
    }
}

 */


