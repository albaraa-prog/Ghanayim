package com.example.app1;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.NonNull;

import android.view.View;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;

public class upload extends AppCompatActivity {

    ImageView uploadimage;
    Button uplaodbutton;
    EditText proddesc, prodname, prodprice, prodsec;
    String imageURL;
    Uri uri;
    ImageButton uplogout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        uploadimage = findViewById(R.id.uploadimage);
        uplaodbutton = findViewById(R.id.uploadbutton);
        proddesc = findViewById(R.id.proddesc);
        prodname = findViewById(R.id.prodname);
        prodprice = findViewById(R.id.prodprice);
        prodsec = findViewById(R.id.prodsec);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        uri = data.getData();
                        uploadimage.setImageURI(uri);
                    } else {
                        Toast.makeText(upload.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                    }
                }
            }
    );
        uploadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPicker = new Intent(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        uplogout = findViewById(R.id.uplogout);
        uplogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(upload.this);
                builder.setTitle("Confirmation");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(upload.this, useroption.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(upload.this, "User logged out", Toast.LENGTH_SHORT).show();
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

        uplaodbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.adminnav);
        bottomNavigationView.setSelectedItemId(R.id.upload);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.deletepage) {
                startActivity(new Intent(getApplicationContext(), Deletepage.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                finish();
            }
            return false;
        });
    }
    public void saveData() {
        String section = prodsec.getText().toString();
        String name = prodname.getText().toString();
        String price = prodprice.getText().toString();
        String desc = proddesc.getText().toString();
        if ((uri == null) || (section == null) || section.isEmpty() || (name == null) || name.isEmpty() || (price == null) || price.isEmpty()  || (desc == null) || desc.isEmpty()) {
            Toast.makeText(upload.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();return;}

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Products Images")
                    .child(uri.getLastPathSegment());
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageURL = urlImage.toString();
                    uploadData();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    public void uploadData() {
        Log.d("UploadData", "Uploading data to Firebase");
        String section = prodsec.getText().toString();
        String name = prodname.getText().toString();
        String price = prodprice.getText().toString();
        String desc = proddesc.getText().toString();

        Data productData = new Data(section, name, price,desc, imageURL);

        String productKey = FirebaseDatabase.getInstance().getReference("uploadedproducts").push().getKey();

        FirebaseDatabase.getInstance().getReference("uploadedproducts").child(productKey)
                .setValue(productData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("UploadData", "Data uploaded successfully");
                            Toast.makeText(upload.this, "Product saved successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Log.e("UploadData", "Failed to save product", task.getException());
                            Toast.makeText(upload.this, "Failed to save product", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("UploadData", "Error: " + e.getMessage(), e);
                        Toast.makeText(upload.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        Intent intent = new Intent(upload.this, upload.class);
        startActivity(intent);
        finish();
    }
}