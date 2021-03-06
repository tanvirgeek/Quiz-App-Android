package com.tanvirgeek.quizeexamapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import static com.tanvirgeek.quizeexamapp.QuizContract.*;

public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Question.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public QuizDbHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTIONS + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWERNO + " INTEGER, " +
                QuestionsTable.COLUMN_CHAPTERNAME + " TEXT " + ")";

        final String SQL_CREATE_USER_TABLE = "CREATE TABLE if not exists \"user\" (\n" +
                "\t\"ID\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t\"Username\"\tTEXT,\n" +
                "\t\"Password\"\tTEXT,\n" +
                "\t\"Email\"\tTEXT,\n" +
                "\t\"Fullname\"\tTEXT,\n" +
                "\t\"College\"\tTEXT,\n" +
                "\t\"Gender\"\tTEXT,\n" +
                "\t\"DOB\"\tTEXT\n" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_USER_TABLE);
        fillQuestionsTable();
    }

    public void insertUser(ContentValues contentValues){
        getWritableDatabase().insert("user","",contentValues);
    }

    public boolean isLoginValid(String username, String password){
        String sql = "Select count(*) from user where username = '" + username + "' and password ='"+ password + "'" ;
        SQLiteStatement statement = getReadableDatabase().compileStatement(sql);
        long l = statement.simpleQueryForLong();
        if(l==1){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable(){
        Question q1 = new Question("A is correct", "A", "B","C","D",1,Question.CHAPTER_1);
        Question q2 = new Question("B is correct", "A", "B","C","D",2,Question.CHAPTER_3);

        Question q3 = new Question("A is correct", "A", "B","C","D",1,Question.CHAPTER_2);
        Question q4 = new Question("B is correct", "A", "B","C","D",2,Question.CHAPTER_1);
        addQuestion(q1);
        addQuestion(q2);
        addQuestion(q3);
        addQuestion(q4);
    }

    public void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTIONS,question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1,question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2,question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3,question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4,question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWERNO,question.getAnswerNo());
        cv.put(QuestionsTable.COLUMN_CHAPTERNAME,question.getChapterName());
        db = getReadableDatabase();
        db.insert(QuestionsTable.TABLE_NAME,null,cv);
    }

    public ArrayList<Question> getAllQuestions(){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if(c.moveToFirst()){
            do{
                Question q = new Question();
                q.setId(Integer.parseInt(c.getString((c.getColumnIndex(QuestionsTable._ID)))));
                q.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTIONS)));
                q.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                q.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                q.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                q.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                q.setAnswerNo(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWERNO)));
                q.setChapterName(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CHAPTERNAME)));
                questionList.add(q);
            }while(c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public ArrayList<Question> getQuestions(String chapterName){
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        String[] selectionArgs = new String[]{chapterName};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME + " WHERE " + QuestionsTable.COLUMN_CHAPTERNAME + " = ?", selectionArgs);
        if(c.moveToFirst()){
            do{
                Question q = new Question();
                q.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTIONS)));
                q.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                q.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                q.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                q.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                q.setAnswerNo(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWERNO)));
                q.setChapterName(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CHAPTERNAME)));
                questionList.add(q);
            }while(c.moveToNext());
        }
        c.close();
        return questionList;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(QuestionsTable.TABLE_NAME, "_ID = ?", new String[]{id});
    }

    public ArrayList<User> getAllUser(){
        ArrayList<User> userList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM user",null);
        if(c.moveToFirst()){
            do{
                User u = new User();
                u.setCollegeName(c.getString(c.getColumnIndex("College")));
                u.setDob(c.getString(c.getColumnIndex("DOB")));
                u.setId(Integer.parseInt(c.getString(c.getColumnIndex("ID"))));
                u.setUserName(c.getString(c.getColumnIndex("DOB")));
                u.setFullName(c.getString(c.getColumnIndex("Fullname")));
                u.setEmail(c.getString(c.getColumnIndex("Email")));
                u.setGender(c.getString(c.getColumnIndex("Gender")));
                userList.add(u);
            }while (c.moveToNext());
        }
        return userList;
    }

    public Integer deleteUser(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("user", "ID = ?", new String[]{id});
    }
}
