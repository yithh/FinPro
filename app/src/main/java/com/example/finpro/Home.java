package com.example.finpro;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class Home extends AppCompatActivity {

    private ImageView imageView;
    private Button profileButton;
    private Button buttonBehindImage;
    private boolean isImageDisplayed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageView = findViewById(R.id.imageView);
        profileButton = findViewById(R.id.profileButton);

        imageView.setOnClickListener(view -> {
            Intent desc = new Intent(Home.this, Description.class);
            startActivity(desc);
        });

        profileButton.setOnClickListener(view -> {
            Intent profile = new Intent(Home.this, Profile.class);
            startActivity(profile);
        });

    }
}
