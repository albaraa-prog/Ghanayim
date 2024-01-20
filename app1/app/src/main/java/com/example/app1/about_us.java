package com.example.app1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class about_us extends AppCompatActivity {

    ImageButton backfromAbout;
    AppCompatButton instagram, facebook, twitter, Gmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_about_us);

        backfromAbout = findViewById(R.id.backfromAbout);

        backfromAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(about_us.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        instagram = findViewById(R.id.instagram);

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insta = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/mesumm8/"));
                startActivity(insta);
            }
        });

        facebook = findViewById(R.id.facebook);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent fb = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://profile/mesum.hussain.77"));
                if (!isPackageInstalled("com.facebook.katana", getPackageManager())) {
                    fb = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/mesum.hussain.77"));
                }
                startActivity(fb);
            }
            private boolean isPackageInstalled(String packagename, PackageManager packageManager) {
                try {
                    packageManager.getPackageInfo(packagename, 0);
                    return true;
                } catch (PackageManager.NameNotFoundException e) {
                    return false;
                }
            }
        });

        twitter = findViewById(R.id.twitter);

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twit = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Cobratate?ref_src=twsrc%5Egoogle%7Ctwcamp%5Eserp%7Ctwgr%5Eauthor"));
                startActivity(twit);
            }


        });

        Gmail = findViewById(R.id.Gmail);

        Gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gmail = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mail.google.com/mail/u/0/?view=cm&fs=1&to=mesummm8@gmail.com&su=Subject&body=MessageBody&bcc=mesummm8@gmail.com&tf=1"));
                startActivity(gmail);
            }
        });

    }


}