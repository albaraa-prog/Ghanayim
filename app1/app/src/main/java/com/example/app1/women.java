package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class women extends AppCompatActivity {

    ImageButton Gobackwomen;
    RecyclerView recyclerView;
    ArrayList<Data> dataList;
    DatabaseReference databaseReference;
    MyAdapter adapter;
    AlertDialog dialog;
    ValueEventListener eventListener;


    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_women);
        Gobackwomen = findViewById(R.id.gobackwomen);
        Gobackwomen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(women.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        recyclerView = findViewById(R.id.womenview);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(women.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();
        adapter = new MyAdapter(women.this, dataList);
        recyclerView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(women.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        dialog.show();


        databaseReference = FirebaseDatabase.getInstance().getReference("uploadedproducts");

        eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dataList != null) {
                            dataList.clear();
                            for (DataSnapshot itemSnapshot : snapshot.getChildren()) {
                                Data dataClass = itemSnapshot.getValue(Data.class);
                                if (dataClass != null && "women".equalsIgnoreCase(dataClass.getSection())) {
                                    dataList.add(dataClass);
                                }
                            }if (dataList.isEmpty()) {
                                Toast.makeText(women.this, "There is no products at the moment", Toast.LENGTH_SHORT).show();
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(women.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
                        }

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(women.this, "Data fetch canceled due to an error", Toast.LENGTH_SHORT).show();

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
            }
        });
    }
}