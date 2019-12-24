package com.tanvirgeek.quizeexamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    Button btnLogin, btnRegister;
    QuizDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegister = findViewById(R.id.btnRegister);
        db = new QuizDbHelper(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameValue = editTextUsername.getText().toString();
                String passwordValue = editTextPassword.getText().toString();
                if(db.isLoginValid(usernameValue,passwordValue)){
                    Intent i = new Intent(login.this, MainActivity.class);
                    startActivity(i);
                    Toast.makeText(login.this,"Login Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(login.this,"Invalid User or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this,Register.class);
                startActivity(i);
            }
        });
    }
}
