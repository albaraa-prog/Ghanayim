
package com.example.app1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Deletepage extends AppCompatActivity {
    ImageButton logout;
    RecyclerView recyclerView;
    ArrayList<Data> delItems;
    DelAdapter adapter;
    DatabaseReference databaseReference;
    ValueEventListener eventListener;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletepage);

        BottomNavigationView bottomNavigationView = findViewById(R.id.adminnav);
        bottomNavigationView.setSelectedItemId(R.id.deletepage);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.upload) {
                startActivity(new Intent(getApplicationContext(), upload.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
            return false;
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Deletepage.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Deletepage.this, useroption.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(Deletepage.this, "User logged out", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // User clicked "No," do nothing or handle as needed
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        recyclerView = findViewById(R.id.delview);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(Deletepage.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        delItems = new ArrayList<>();
        adapter = new DelAdapter(Deletepage.this, delItems);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("uploadedproducts");

        AlertDialog.Builder builder = new AlertDialog.Builder(Deletepage.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        dialog.show();

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                delItems.clear();
                for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    Data dataClass = itemSnapshot.getValue(Data.class);
                    dataClass.setKey(itemSnapshot.getKey());
                    delItems.add(dataClass);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (eventListener != null) {
            databaseReference.removeEventListener(eventListener);
        }
    }
}


