package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Favorites extends AppCompatActivity {

    ImageButton bckToPro;
    RecyclerView recyclerView;
    Button clearfav;
    private ArrayList<Data> favoritesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        bckToPro = findViewById(R.id.bckProf);
        clearfav = findViewById(R.id.clearfav);

        bckToPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Favorites.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        clearfav.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onClick(View v) {
                Fav.clearFav();
                Toast.makeText(Favorites.this, "Favorites cleared" , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Favorites.this, Favorites.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.favview);
        FavAdapter favAdapter = new FavAdapter(this, Fav.getFavItems());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(favAdapter);

    }

    public static class Fav {
        private static ArrayList<Data> favItems = new ArrayList<>();

        public static void addToFav(Data product) {
            favItems.add(product);
        }

        public static ArrayList<Data> getFavItems() {
            return favItems;
        }

        public static void clearFav() {
            favItems.clear();
        }

    }

    private static class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {
        private Context context;
        private ArrayList<Data> favItems;

        public FavAdapter(Context context, ArrayList<Data> favItems) {
            this.context = context;
            this.favItems = favItems;
        }

        @NonNull
        @Override
        public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleproduct, parent, false);
            return new FavViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
            Data product = favItems.get(position);
            Glide.with(context).load(product.getDataimg()).into(holder.recimage);
            holder.recName.setText(product.getDataname());
            holder.recPrice.setText(product.getDataprice());
            holder.recDesc.setText(product.getDatadesc());
            holder.recSec.setText(product.getSection());

            holder.recProd.setOnClickListener(view -> {
                Intent intent = new Intent(context, Detail.class);
                intent.putExtra("image", product.getDataimg());
                intent.putExtra("name", product.getDataname());
                intent.putExtra("price", product.getDataprice());
                intent.putExtra("description", product.getDatadesc());
                intent.putExtra("section", product.getSection());
                intent.putExtra("Key", product.getKey());
                context.startActivity(intent);
            });
        }

        @Override
        public int getItemCount() {
            return favItems.size();
        }

        static class FavViewHolder extends RecyclerView.ViewHolder {
            ImageView recimage;
            TextView recName, recDesc, recPrice, recSec;
            CardView recProd;

            public FavViewHolder(@NonNull View itemView) {
                super(itemView);
                recimage = itemView.findViewById(R.id.recimage);
                recProd = itemView.findViewById(R.id.recprod);
                recName = itemView.findViewById(R.id.recname);
                recPrice = itemView.findViewById(R.id.recprice);
                recDesc = itemView.findViewById(R.id.recdesc);
                recSec = itemView.findViewById(R.id.recsec);
            }
        }
    }
}
