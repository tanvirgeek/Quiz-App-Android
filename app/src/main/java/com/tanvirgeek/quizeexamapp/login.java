package com.tanvirgeek.quizeexamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class login extends AppCompatActivity {

    private final String ADMIN_USERNAME = "T";
    private final String ADMIN_PASSWORD = "T";
    EditText editTextUsername, editTextPassword,editTextAdminUserName,editTextAdminPassword;
    Button btnLogin, btnRegister, btnAdminLogin;
    QuizDbHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextAdminUserName = findViewById(R.id.editTextAdminUsername);
        editTextAdminPassword = findViewById(R.id.editTextAdminPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnAdminLogin = findViewById(R.id.btnAdminLogin);
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
        btnAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameValu = editTextAdminUserName.getText().toString();
                String passwordValu = editTextAdminPassword.getText().toString();
                Boolean condition1 = usernameValu.equals(ADMIN_USERNAME);
                Boolean condition2 = passwordValu.equals(ADMIN_PASSWORD);
                if( condition1 && condition2 ){
                    Intent in = new Intent(login.this,AdminActivity.class);
                    startActivity(in);
                    Toast.makeText(login.this,"Login Successful", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(login.this,"Invalid USER or PassWord", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
