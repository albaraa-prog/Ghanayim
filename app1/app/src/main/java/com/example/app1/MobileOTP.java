package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.hbb20.CountryCodePicker;

public class MobileOTP extends AppCompatActivity {

    ImageView b2opt;
    Button GoToOTP;
    TextInputLayout EnterMobNo;

    CountryCodePicker Country_Code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mobile_otp);

        EnterMobNo = findViewById(R.id.EnterMobNo);
        Country_Code = findViewById(R.id.Country_Code);
         b2opt = findViewById(R.id.b2opt);

         b2opt.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(MobileOTP.this, signup.class);
                 startActivity(intent);
             }
         });
         GoToOTP = findViewById(R.id.GoToOTP);


    }
}