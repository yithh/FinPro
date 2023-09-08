package com.example.finpro;

import android.annotation.SuppressLint;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    TextView loginLink;
    EditText usernameField, emailField, passwordField, confPasswordField;
    Button registerButton;
    SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameField = findViewById(R.id.et_usernameRegister);
        emailField = findViewById(R.id.et_emailRegister);
        passwordField = findViewById(R.id.et_passwordRegister);
        confPasswordField = findViewById(R.id.et_confPasswordRegister);
        registerButton = findViewById(R.id.btn_register);
        loginLink = findViewById(R.id.tv_loginLink);

        sharedPreferences = getSharedPreferences("user_account", MODE_PRIVATE);

        loginLink.setOnClickListener(v->{
            Intent loginIntent = new Intent(Register.this, Login.class);
            startActivity(loginIntent);
        });

        registerButton.setOnClickListener(v->{
            if(usernameField.getText().toString().length() < 5){
                Toast.makeText(this, "Username must be more than 5 characters.", Toast.LENGTH_SHORT).show();
            }else if(!emailField.getText().toString().contains("@") || !emailField.getText().toString().endsWith(".com")){
                Toast.makeText(this, "Email must have '@' and ends with '.com'.", Toast.LENGTH_SHORT).show();
            }else if(passwordField.getText().toString().length() < 8){
                Toast.makeText(this, "Password must be more than 8 characters.", Toast.LENGTH_SHORT).show();
            }else if(!confPasswordField.getText().toString().equals(passwordField.getText().toString())){
                Toast.makeText(this, "Password and confirm password doesn't match", Toast.LENGTH_SHORT).show();
            }else{
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("username_user",usernameField.getText().toString());
                                    editor.putString("password_user",passwordField.getText().toString());
                                    editor.putString("email_user",emailField.getText().toString());
                                    editor.commit();
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Intent toLoginPage = new Intent(Register.this, Login.class);
                                    startActivity(toLoginPage);
                                } else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        Toast.makeText(Register.this, "Email has been used.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Register.this, "Registration Faild.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        });
            }
        });

    }
}