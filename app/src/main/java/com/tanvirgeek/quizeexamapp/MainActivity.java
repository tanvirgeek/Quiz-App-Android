package com.tanvirgeek.quizeexamapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String SHARED_PREFS = "sharedprefs";
    public static final String KEY_HIGHSCORE = "KEYHIGHSCORE";
    public static final String EXTRADIFFICULTY = "extraDifficulty";

    private TextView textViewHighScore;
    private int highScore;
    private Spinner spinnerDifficulty ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewHighScore = findViewById(R.id.text_view_highscore);
        spinnerDifficulty = findViewById(R.id.spinner_difficulty);

        String[] difficultyLevels = Question.getAlldifficultyLevels();
        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,difficultyLevels);
        spinnerDifficulty.setAdapter(adapterDifficulty);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loadHighScore();

        Button buttonStartQuiz = findViewById(R.id.button_start_quiz);
        buttonStartQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startQuiz();
            }
        });

    }

    private void startQuiz(){
        String difficulty = spinnerDifficulty.getSelectedItem().toString();

        Intent i = new Intent(MainActivity.this,QuizActivity.class);
        i.putExtra(EXTRADIFFICULTY, difficulty);
        startActivityForResult(i,REQUEST_CODE_QUIZ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_QUIZ){
            if(resultCode == RESULT_OK){
                int score = data.getIntExtra(QuizActivity.EXTRA_SCORE,0);
                if( score > highScore){
                    updateHighScore(score);
                }
            }
        }
    }

    private void loadHighScore(){
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highScore = prefs.getInt(KEY_HIGHSCORE,0);
        textViewHighScore.setText("HighScore: " + highScore);
    }

    private void updateHighScore(int highScoreNew){
        highScore = highScoreNew;
        textViewHighScore.setText("Highschore:" + highScore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE,highScore);
        editor.apply();
    }
}
