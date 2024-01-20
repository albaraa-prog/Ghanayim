package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class useroption extends AppCompatActivity {

    Button DelBtn, SellerBtn, CustomerBtn;
    String  _name4, _city4, _add4, _phn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_useroption);

        DelBtn = findViewById(R.id.DelBtn);
        SellerBtn = findViewById(R.id.SellerBtn);
        CustomerBtn = findViewById(R.id.CustomerBtn);

        CustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(useroption.this, Dashboard.class);
                startActivity(intent1);
                finish();
            }
        });

        SellerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(useroption.this, sellerLogin.class);
                startActivity(intent2);
                finish();
            }
        });

        DelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                _name4 = intent.getStringExtra("nametxt");
                _city4 = intent.getStringExtra("city");
                _add4 = intent.getStringExtra("Address");
                _phn4 = intent.getStringExtra("phoneNumber");
                Intent intent3 = new Intent(useroption.this, delLogin.class);
                intent3.putExtra("nametxt",_name4);
                intent3.putExtra("city",_city4);
                intent3.putExtra("Address",_add4);
                intent3.putExtra("phoneNumber",_phn4);
                startActivity(intent3);
                finish();
            }
        });

    }
}