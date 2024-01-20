package com.example.app1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<Data> dataList;
    DatabaseReference databaseReference;
    MyAdapter adapter;
    AlertDialog dialog;
    ValueEventListener eventListener;
    SearchView searchView;
    ImageButton menbutton;
    ImageButton womenbutton;
    ImageButton kidsbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        menbutton = findViewById(R.id.menbutton);
        womenbutton = findViewById(R.id.womenbutton);
        kidsbutton = findViewById(R.id.kidsbutton);

        menbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, men.class);
                startActivity(intent);
                finish();
            }
        });
        womenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, women.class);
                startActivity(intent);
                finish();
            }
        });
        kidsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, kids.class);
                startActivity(intent);
                finish();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.Home);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.Cart) {
                startActivity(new Intent(getApplicationContext(), CartActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            } else if (item.getItemId() == R.id.Profile) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
            return false;
        });

        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.search);
        searchView.clearFocus();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        dataList = new ArrayList<>();
        adapter = new MyAdapter(MainActivity.this, dataList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("uploadedproducts");

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        dialog = builder.create();
        dialog.show();
 
    eventListener = databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        dataList.clear();
        for (DataSnapshot itemSnapshot: snapshot.getChildren()){
            Data dataClass = itemSnapshot.getValue(Data.class);
            dataClass.setKey(itemSnapshot.getKey());
            dataList.add(dataClass);
        }
        adapter.notifyDataSetChanged();
        dialog.dismiss();
    }
    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        dialog.dismiss();
    }
    });
       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }
           @Override
           public boolean onQueryTextChange(String newText) {
               searchList(newText);
               return true;
           }
       });
    }
    public void searchList(String text){
        ArrayList<Data> searchList = new ArrayList<>();
        for (Data data: dataList){
            if (data.getDataname().toLowerCase().contains(text.toLowerCase())){
                searchList.add(data);
            }
        }
        adapter.searchDataList(searchList);
    }
}


