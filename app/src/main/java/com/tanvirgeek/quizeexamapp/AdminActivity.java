package com.tanvirgeek.quizeexamapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdminActivity extends AppCompatActivity {
    Button btnCreateQuestion, btnReadQuestion, btnDeleteQuestion, btnSeeUsers, btnDeleteUser;
    EditText editTextQuestion, editTextOption1, editTextOption2, editTextOption3, editTextOption4,
        editTextAnswerNo,editTextChapterNo, editTextDeleteId, editTextDeleteUser;
    QuizDbHelper db;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://192.168.0.107/MedicalQuiz/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    UsersAPI usersAPI = retrofit.create(UsersAPI.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        btnCreateQuestion = findViewById(R.id.btnCreateQuestion);
        btnReadQuestion = findViewById(R.id.btnReadQuestions);
        btnDeleteQuestion = findViewById(R.id.btnDeleteQuestions);
        editTextQuestion = findViewById(R.id.editTextQuestion);
        editTextOption1 = findViewById(R.id.editTextOption1);
        editTextOption2 = findViewById(R.id.editTextOption2);
        editTextOption3 = findViewById(R.id.editTextOption3);
        editTextOption4 = findViewById(R.id.editTextOption4);
        editTextAnswerNo = findViewById(R.id.editTextAnswerNo);
        editTextChapterNo = findViewById(R.id.editTextChapterNo);
        editTextDeleteId = findViewById(R.id.deteleId);
        btnSeeUsers = findViewById(R.id.btnReadUsers);
        btnDeleteUser = findViewById(R.id.btnDeleteUser);
        editTextDeleteUser = findViewById(R.id.editTextDeleteUser);
        db = new QuizDbHelper(this);

        btnCreateQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = editTextQuestion.getText().toString();
                String option1 = editTextOption1.getText().toString();
                String option2 = editTextOption2.getText().toString();
                String option3 = editTextOption3.getText().toString();
                String option4 = editTextOption4.getText().toString();
                int answerNo =  0;
                String regex = "[0-9]+";
                if (!editTextAnswerNo.getText().toString().matches(regex)){
                    answerNo =0;
                }else {
                    answerNo = Integer.parseInt(editTextAnswerNo.getText().toString());
                }
                String chapterName = editTextChapterNo.getText().toString();
                if(!question.equalsIgnoreCase("") && !option1.equalsIgnoreCase("") &&
                        !option2.equalsIgnoreCase("") && !option3.equalsIgnoreCase("") &&
                        !option4.equalsIgnoreCase("") && answerNo != 0 && !chapterName.equalsIgnoreCase("")
                ){
                Question q = new Question(question,option1,option2,option3,option4,answerNo,chapterName);
                    //Question q2 = new Question()
                    db.addQuestion(q);
                    Toast.makeText(AdminActivity.this,"Question Added", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AdminActivity.this,"Please fill up", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnReadQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ArrayList<Question> allQuestions = db.getAllQuestions();
               if(allQuestions.size()< 1){
                    Toast.makeText(AdminActivity.this,"No questions in the Database", Toast.LENGTH_SHORT).show();
               }else {
                   StringBuffer buffer = new StringBuffer();
                   for (Question q: allQuestions) {
                       buffer.append("Id : " + q.getId() + "\n" );
                       buffer.append("Question : " + q.getQuestion() + "\n" );
                       buffer.append("Option1:  " + q.getOption1() + "\n");
                       buffer.append("Option2:  " + q.getOption2() + "\n");
                       buffer.append("Option3:  " + q.getOption3() + "\n");
                       buffer.append("Option4:  " + q.getOption4() + "\n");
                       buffer.append("Answer No:  " + q.getAnswerNo() + "\n");
                       buffer.append("Option4:  " + q.getChapterName() + "\n\n");
                   }

                   AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                   builder.setCancelable(true);
                   builder.setTitle("Questions");
                   builder.setMessage(buffer.toString());
                   builder.show();
               }
            }
        });
        btnDeleteQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer deleteRows = db.deleteData(editTextDeleteId.getText().toString());
                if(deleteRows > 0){
                    Toast.makeText(AdminActivity.this,"Data Deleted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AdminActivity.this,"Data Not Deleted",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSeeUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Pull Data from Mysql Database
                Call<List<User>>  call= usersAPI.getUsers();
                call.enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(AdminActivity.this,"Response code: "+response.code(),Toast.LENGTH_SHORT).show();
                            return;
                        }

                        List<User> users = response.body();

                        if(users.size()< 1){
                            Toast.makeText(AdminActivity.this,"No Users in the Database", Toast.LENGTH_SHORT).show();
                        }else {
                            StringBuffer buffer = new StringBuffer();
                            for (User u: users) {
                                buffer.append("Id : " + u.getId() + "\n" );
                                buffer.append("User Name : " + u.getUserName() + "\n" );
                                buffer.append("Email:  " + u.getEmail() + "\n");
                                buffer.append("Fullname:  " + u.getEmail() + "\n");
                                buffer.append("College:  " + u.getCollegeName() + "\n");
                                buffer.append("Gender:  " + u.getGender() + "\n");
                                buffer.append("DOB:  " + u.getDob() + "\n\n");
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                            builder.setCancelable(true);
                            builder.setTitle("Users");
                            builder.setMessage(buffer.toString());
                            builder.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(AdminActivity.this, "Error : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


                //pull users from local database
               /* ArrayList<User> allUsers = db.getAllUser();
                if(allUsers.size()< 1){
                    Toast.makeText(AdminActivity.this,"No Users in the Database", Toast.LENGTH_SHORT).show();
                }else {
                    StringBuffer buffer = new StringBuffer();
                    for (User u: allUsers) {
                        buffer.append("Id : " + u.getId() + "\n" );
                        buffer.append("User Name : " + u.getUserName() + "\n" );
                        buffer.append("Email:  " + u.getEmail() + "\n");
                        buffer.append("Fullname:  " + u.getEmail() + "\n");
                        buffer.append("College:  " + u.getCollegeName() + "\n");
                        buffer.append("Gender:  " + u.getGender() + "\n");
                        buffer.append("DOB:  " + u.getDob() + "\n");
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("Users");
                    builder.setMessage(buffer.toString());
                    builder.show();
                }*/
            }
        });

        btnDeleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int deleteId = Integer.parseInt(editTextDeleteUser.getText().toString());
                Call<Void> call = usersAPI.deletePost(deleteId);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(AdminActivity.this,"Response code: "+response.code(),Toast.LENGTH_SHORT).show();
                        return;
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AdminActivity.this, "Error : "+ t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                /*
                Integer deleteRows = db.deleteUser(editTextDeleteUser.getText().toString());
                if(deleteRows > 0){
                    Toast.makeText(AdminActivity.this,"User Deleted",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AdminActivity.this,"User Not Deleted",Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }
}
