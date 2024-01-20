package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.List;


public class men extends AppCompatActivity {
    ImageButton Gobackmen;
    RecyclerView recyclerView;
    List<Data> dataList;
    DatabaseReference databaseReference;
    MyAdapter adapter;
    AlertDialog dialog;
    ValueEventListener eventListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_men);

        Gobackmen = findViewById(R.id.gobackmen);
        Gobackmen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(men.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.menview);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(men.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();
        adapter = new MyAdapter(men.this, (ArrayList<Data>) dataList);
        recyclerView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(men.this);
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
                                if (dataClass != null && "men".equalsIgnoreCase(dataClass.getSection())) {
                                    dataList.add(dataClass);
                                }
                            }if (dataList.isEmpty()) {
                                Toast.makeText(men.this, "There is no products at the moment", Toast.LENGTH_SHORT).show();
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(men.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(men.this, "Data fetch canceled due to an error", Toast.LENGTH_SHORT).show();

                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                });
            }
        });


    }
}
