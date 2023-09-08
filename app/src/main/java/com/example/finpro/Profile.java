package com.example.finpro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {

    private TextView usernameTextView;
    private TextView emailTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        usernameTextView = findViewById(R.id.usernameTextView);
        emailTextView = findViewById(R.id.emailTextView);

        // Mendapatkan data dari SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_account", MODE_PRIVATE);
        String username = sharedPreferences.getString("username_user", "");
        String email = sharedPreferences.getString("email_user", "");

        // Menampilkan data pada TextView
        usernameTextView.setText("Username: " + username);
        emailTextView.setText("Email: " + email);
    }
}
