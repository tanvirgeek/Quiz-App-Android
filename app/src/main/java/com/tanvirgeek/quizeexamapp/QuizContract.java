package com.tanvirgeek.quizeexamapp;

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract(){}
    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTIONS = "questions";
        public static final String COLUMN_OPTION1= "options1";
        public static final String COLUMN_OPTION2 = "options2";
        public static final String COLUMN_OPTION3 = "options3";
        public static final String COLUMN_OPTION4 = "options4";
        public static final String COLUMN_ANSWERNO = "answers_no";
        public static final String COLUMN_CHAPTERNAME = "chapterName";

    }
}
