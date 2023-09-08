package com.example.finpro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView registerLink;
    EditText usernameField, passwordField;
    Button loginButton;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailField = findViewById(R.id.et_emailLogin);
        EditText passwordField = findViewById(R.id.et_passwordLogin);
        loginButton = findViewById(R.id.btn_login);
        registerLink = findViewById(R.id.tv_registerLink);

        sharedPreferences = getSharedPreferences("user_account",MODE_PRIVATE);

        registerLink.setOnClickListener(v->{
            Intent registerIntent = new Intent(Login.this, Register.class);
            startActivity(registerIntent);
        });

        loginButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(Login.this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent toWelcomePage = new Intent(Login.this, Home.class);
                                    startActivity(toWelcomePage);
                                } else {
                                    if (task.getException() != null) {
                                        String errorMessage = task.getException().getMessage();
                                        if (errorMessage.contains("password is invalid")) {
                                            Toast.makeText(Login.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                                        } else if (errorMessage.contains("no user record")) {
                                            Toast.makeText(Login.this, "Email hasn't registered", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Login.this, "Login failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(Login.this, "Login failed.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });


    }
}