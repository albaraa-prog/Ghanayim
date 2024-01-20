package com.example.app1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.PendingIntentCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.util.zip.Inflater;

public class UserProfile extends AppCompatActivity {

    TextView ProfileName, ProfileUserName;
    ImageButton BackFromEditProfile;
    ImageView UserProfile;
    TextInputEditText City, Uemail, Phn, Fullname, Address2;
    Button edit, UpdatePassword;
    String _UserName, _Name, _email, _Phn, avatar, _city, address;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");


        Fullname = findViewById(R.id.FullName);
        City = findViewById(R.id.City);
        Phn = findViewById(R.id.Phn);
        edit = findViewById(R.id.edit);
        ProfileName = findViewById(R.id.ProfileName);
        ProfileUserName = findViewById(R.id.ProfileUserName);
        UpdatePassword = findViewById(R.id.UpdatePassword);
        UserProfile = findViewById(R.id.User_Profile);
        Address2 = findViewById(R.id.Address2);


        UpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, reset.class);
                startActivity(intent);
                finish();
            }
        });

        //showAllData();

        Query query = databaseReference.orderByChild("emailtxt").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot s : snapshot.getChildren()){
                    _Name = "" + s.child("nametxt").getValue();
                    _UserName = "" + s.child("usertxt").getValue();
                    _email = "" + s.child("emailtxt").getValue();
                    _Phn = "" + s.child("phoneNumber").getValue();
                    _city = "" + s.child("city").getValue();
                    address = "" + s.child("Address").getValue();

                    ProfileName.setText(_Name);
                    ProfileUserName.setText(_email);
                    Fullname.setText(_Name);
                    City.setText(_city);
                    Phn.setText(_Phn);
                    Address2.setText(address);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        BackFromEditProfile = findViewById(R.id.BackFromEditProfile);

        BackFromEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                updateData();
            }
        });
    }


    private void updateData() {
        if (isNameChanged() || isCityChanged() || isPhoneChanged() || isAddressChanged()){
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Data is same cannot be updated", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isAddressChanged() {
        if (!address.equals(Address2.getText().toString())){
            databaseReference.child(_UserName).child("Address").setValue(Address2.getText().toString());
            address = Address2.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isPhoneChanged() {
        if (!_Phn.equals(Phn.getText().toString())){
            databaseReference.child(_UserName).child("phoneNumber").setValue(Phn.getText().toString());
            _Phn = Phn.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isCityChanged() {
        if (!_city.equals(City.getText().toString())){
            databaseReference.child(_UserName).child("usertxt").setValue(City.getText().toString());
            _UserName = City.getText().toString();
            return true;
        }else {
            return false;
        }
    }

    private boolean isNameChanged() {
        if (!_Name.equals(Fullname.getText().toString())){
            databaseReference.child(_UserName).child("nametxt").setValue(Fullname.getText().toString());
            _Name = Fullname.getText().toString();
            return true;
        }else {
            return false;
        }
    }

  /*  private void showAllData() {
        Intent intent = getIntent();

        _UserName = intent.getStringExtra("user");
        _Name = intent.getStringExtra("name");
        _email = intent.getStringExtra("email");
        _Phn = intent.getStringExtra("Phn");

        ProfileName.setText(_Name);
        ProfileUserName.setText(_UserName);
        Fullname.setText(_Name);
        username.setText(_UserName);
        Uemail.setText(_email);
        Phn.setText(_Phn);
    }  */
}