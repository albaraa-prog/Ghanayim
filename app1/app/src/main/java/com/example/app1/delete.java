
package com.example.app1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
public class delete extends AppCompatActivity {

    TextView deldesc, delname, delprice;
    ImageView delimage;
    Button delButton;
    String key = "";
    String imageUrl = "";
    ImageButton gobackdel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        delname = findViewById(R.id.delname);
        delprice = findViewById(R.id.delprice);
        delimage = findViewById(R.id.delimage);
        deldesc = findViewById(R.id.deldesc);
        delButton = findViewById(R.id.deletebutton);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            delname.setText(bundle.getString("name"));
            delprice.setText(bundle.getString("price"));
            deldesc.setText(bundle.getString("description"));
            key = bundle.getString("key");
            imageUrl = bundle.getString("image");
            Glide.with(this).load(bundle.getString("image")).into(delimage);
        }
        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(delete.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to delete the product?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("uploadedproducts");
                        FirebaseStorage storage = FirebaseStorage.getInstance();

                        StorageReference storageReference = storage.getReferenceFromUrl(imageUrl);
                        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                reference.child(key).removeValue();
                                Toast.makeText(delete.this, "Deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Deletepage.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(delete.this, "Failed to delete: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
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
        gobackdel = findViewById(R.id.gobackdel);
        gobackdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent intent = new Intent(delete.this, Deletepage.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

