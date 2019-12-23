package com.tanvirgeek.quizeexamapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class Register extends AppCompatActivity {

    EditText editTextFullName, editTextUsername, editTextPassword, editTextEmail, editTextCollege, editTextDOB;
    RadioGroup radioGroupGender;
    Button btnRegister, btnCancel;
    QuizDbHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextCollege = findViewById(R.id.editTextCollege);
        editTextDOB = findViewById(R.id.editTextDateOfBirth);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextFullName = findViewById(R.id.editTextFullName);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        btnCancel = findViewById(R.id.btnCancel);
        btnRegister = findViewById(R.id.btnRegister);
        db = new QuizDbHelper(this);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameValue = editTextUsername.getText().toString();
                String passwordValue = editTextPassword.getText().toString();
                String emailValue = editTextEmail.getText().toString();
                String DOBValue = editTextDOB.getText().toString();
                String collegeValue = editTextCollege.getText().toString();
                String fullnameValue = editTextFullName.getText().toString();
                RadioButton genderChecked = findViewById(radioGroupGender.getCheckedRadioButtonId());
                String genderValue = genderChecked.getText().toString();

                if(usernameValue.length() > 1){
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("Username", usernameValue);
                    contentValues.put("Password", passwordValue);
                    contentValues.put("Email", emailValue);
                    contentValues.put("DOB", DOBValue);
                    contentValues.put("College", collegeValue);
                    contentValues.put("Fullname", fullnameValue);
                    contentValues.put("Gender", genderValue);
                    db.insertUser(contentValues);
                    Toast.makeText(Register.this,"Registered",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Register.this,"Please Enter Values",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
