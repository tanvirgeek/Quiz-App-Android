package com.tanvirgeek.quizeexamapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    EditText editTextFullName, editTextUsername, editTextPassword, editTextEmail, editTextCollege, editTextDOB;
    RadioGroup radioGroupGender;
    Button btnRegister, btnCancel;
    QuizDbHelper db;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.107/MedicalQuiz/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    UsersAPI usersAPI = retrofit.create(UsersAPI.class);

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

                //Get data from input
                String usernameValue = editTextUsername.getText().toString();
                String passwordValue = editTextPassword.getText().toString();
                String emailValue = editTextEmail.getText().toString();
                String DOBValue = editTextDOB.getText().toString();
                String collegeValue = editTextCollege.getText().toString();
                String fullnameValue = editTextFullName.getText().toString();
                RadioButton genderChecked = findViewById(radioGroupGender.getCheckedRadioButtonId());
                String genderValue = genderChecked.getText().toString();
                User user = new User(fullnameValue,usernameValue,collegeValue,DOBValue,emailValue,genderValue,passwordValue);
                Gson gs= new Gson();
                 gs.toJson(user);
                //push to remote database
                Call<User> call= usersAPI.createUser(usernameValue,passwordValue,emailValue,fullnameValue,collegeValue,genderValue,DOBValue);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(Register.this, "Code: "+ response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        User postResponse = response.body();
                        //buffer.append("Id : " + postResponse.getId() + "\n");
                        buffer.append("User Name : " + postResponse.getUserName() + "\n");
                        buffer.append("Email:  " + postResponse.getEmail() + "\n");
                        buffer.append("Fullname:  " + postResponse.getFullName() + "\n");
                        buffer.append("College:  " + postResponse.getCollegeName() + "\n");
                        buffer.append("Gender:  " + postResponse.getGender() + "\n");
                        buffer.append("DOB:  " + postResponse.getDob() + "\n\n");
                        AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                        builder.setCancelable(true);
                        builder.setTitle("User Created");
                        builder.setMessage(buffer.toString());
                        builder.show();

                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(Register.this, "Error Failed : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                        //Log.d("Error",t);
                    }
                });

                /* //Push to local database


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
                }*/
            }
        });
    }
}
