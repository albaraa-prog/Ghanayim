package com.example.app1;



import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    AppCompatButton feedback;
    AppCompatButton About;
    AppCompatButton logout;
    AppCompatButton rate;
    AppCompatButton UserProfile;
    AppCompatButton Fav;
    FirebaseUser user;
    FirebaseAuth firebaseAuth;
    String _Name, _name3, _city3, _add3, _phn3;
    TextView profile;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://ghanayim-6a3a1-default-rtdb.asia-southeast1.firebasedatabase.app/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

        profile = findViewById(R.id.profile);

        rate = findViewById(R.id.rate);

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ProfileActivity.this,Rating.class);
                startActivity(intent);
            }
        });

        logout = findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseAuth.signOut();
                        signoutUser();
                        Intent intent = getIntent();
                        _name3 = intent.getStringExtra("nametxt");
                        _city3 = intent.getStringExtra("city");
                        _add3 = intent.getStringExtra("Address");
                        _phn3 = intent.getStringExtra("phoneNumber");

                        Intent intent1 = new Intent(ProfileActivity.this, useroption.class);
                        intent1.putExtra("nametxt",_name3);
                        intent1.putExtra("city",_city3);
                        intent1.putExtra("Address",_add3);
                        intent1.putExtra("phoneNumber",_phn3);
                        startActivity(intent1);
                        finish();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }

        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Home) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            } else if (item.getItemId() == R.id.Cart) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
            return false;
        });

        feedback = findViewById(R.id.feedback);
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(ProfileActivity.this, feedback.class);
                startActivity(intent);
                finish();
            }
        });
        About = findViewById(R.id.About);

        About.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, about_us.class);
                startActivity(intent);
                finish();
            }
        });

        UserProfile = findViewById(R.id.UserProfile);
        UserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, UserProfile.class);
                startActivity(intent);
                finish();
            }
        });

        Fav = findViewById(R.id.Fav);

        Fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, Favorites.class);
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
                    _Name = "" + s.child("usertxt").getValue(String.class);

                    profile.setText(_Name);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void signoutUser() {
        Intent intent = new Intent(ProfileActivity.this, useroption.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}