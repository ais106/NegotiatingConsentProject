package uk.ac.soton.ecs.negotiatingconsent.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.collectables.UnansweredQuestion;
import uk.ac.soton.ecs.negotiatingconsent.models.Question;

/**
 * Created by Anna Soska on 11/09/15.
 */
public class dbQuestionHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dbUnansweredQuestions";

    // Url table
    private static final String TABLE_QUESTION = "table_unanswered";

    // Status Table Columns
    private static final String ID = "id";
    private static final String QUESTION = "qst";



    public dbQuestionHandler(Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }


    public dbQuestionHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_QUESTION = "CREATE TABLE " + TABLE_QUESTION + "("
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + QUESTION +  " TEXT " + ")";
        db.execSQL(CREATE_TABLE_QUESTION);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     * @param question
     */

    // Adding new question
    public void addQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();
        Gson gson = new Gson();
        String jsonStr = gson.toJson(question);

        ContentValues values = new ContentValues();
        values.put(QUESTION, jsonStr); // Question/Action type indicated by permission

        // Inserting Row
        db.insert(TABLE_QUESTION, null, values);
        db.close(); // Closing database connection
    }


    // Get first question
  public  Question getFirstUnansweredQuestion() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<UnansweredQuestion> question = getAllQuestions();

        if(question.size()>0) {
            UnansweredQuestion unansweredQuestion = question.get(0);
            Gson gson = new Gson();
            String jsonString = unansweredQuestion.getQuestion();
            //convert the json string back to Question object
            db.close();

            return gson.fromJson(jsonString, Question.class); // return Question obj;

        }
        else {
                db.close();
                return null;
        }




    }

    // Get All Urls
    public List<UnansweredQuestion> getAllQuestions() {
        List<UnansweredQuestion> list = new ArrayList<UnansweredQuestion>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QUESTION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UnansweredQuestion question = new UnansweredQuestion();
                question.setId(Integer.parseInt(cursor.getString(0)));
                question.setQuestion(cursor.getString(1));

                // Adding results to list
                list.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        //db.close(); // Closing database connection
        // return Url list
        return list;
    }

    // Updating single url
    public int unansweredQuestion(UnansweredQuestion question) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(QUESTION, question.getQuestion());
        // updating row
        return db.update(TABLE_QUESTION, values, ID + " = ?",
                new String[] { String.valueOf(question.getId()) });

    }



    public void deleteFirstUnansweredQuestion()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        List<UnansweredQuestion> question = getAllQuestions();
        if(question.size()>0) {

            db.delete(TABLE_QUESTION, ID + " = ?", new String[]{String.valueOf(question.get(0).getId())}); //delete first question
        }

        db.close();

    }






    // Get Url Count
    public int getQuestionCount() {
        String countQuery = "SELECT  * FROM " + TABLE_QUESTION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close(); // Closing database connection
        // return count
        return cursor.getCount();
    }



}