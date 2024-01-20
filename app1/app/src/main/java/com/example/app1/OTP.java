package com.example.app1;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OTP extends AppCompatActivity {

    Button CheckOTP;
    ImageView close;
    PinView pin;
    String codebysystem;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_otp);

        pin = (PinView) findViewById(R.id.pin);

        String phone = getIntent().getStringExtra("fullNumber");

        SendVerificationCodeToUser(phone);

    }

    private void SendVerificationCodeToUser(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                OTP.this,
                mCallbacks);
    }
    private  PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codebysystem = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code!=null){
                pin.setText(code);
                verifycode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(OTP.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifycode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codebysystem,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth fauth = FirebaseAuth.getInstance();
        fauth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(OTP.this, "yes", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(OTP.this, "no", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void CallOTP(View view) {
        String code = pin.getText().toString();
        if (!code.isEmpty()){
            verifycode(code);
        }
    }
}