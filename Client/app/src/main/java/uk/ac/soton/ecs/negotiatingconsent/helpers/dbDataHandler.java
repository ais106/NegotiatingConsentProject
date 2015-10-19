package uk.ac.soton.ecs.negotiatingconsent.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.collectables.DataTable;

/**
 * Created by Anna Soska on 16/08/15.
 */
public class dbDataHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dbData";

    // Data table
    private static final String TABLE_DATA = "table_data";

    // Data Table Columns
    private static final String ID = "id";
    private static final String PERMISSION = "permission";
    private static final String DATA_CONTENT = "data_content";
    private static final String QID = "question_id";
    private static final String QRESPONSE = "question_response";
    

    public dbDataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_DATA = "CREATE TABLE " + TABLE_DATA + "("
                + ID + " INTEGER PRIMARY KEY," + PERMISSION + " TEXT,"
                + DATA_CONTENT +")";
        db.execSQL(CREATE_TABLE_DATA);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_URL);

        //Remove redundancy in the table
        //deleteDuplicates(db);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new status
    public void addData(DataTable data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PERMISSION, data.getPermission()); // Question/Action type indicated by permission
        values.put(DATA_CONTENT, data.getDataContent()); // Data Content

        // Inserting Row
        db.insert(TABLE_DATA, null, values);
        db.close(); // Closing database connection
    }

    // Get single status
    DataTable getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DATA, new String[]{ID,
                        PERMISSION, DATA_CONTENT}, ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DataTable data = new DataTable(Integer.parseInt(cursor.getString(0)),cursor.getString(1), cursor.getString(2));
        // return data
        return data;
    }

    // Get All Urls
    public List<DataTable> getAllData() {
        List<DataTable> dataList = new ArrayList<DataTable>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataTable data = new DataTable();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setPermission(cursor.getString(1));
                data.setDataContent(cursor.getString(2));
                // Adding results to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return Url list
        return dataList;
    }

    // Get All Calendar Events
    public List<DataTable> getAllCalendar() {
        List<DataTable> dataList = new ArrayList<DataTable>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DATA + " WHERE+  permission=\"calendar\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataTable data = new DataTable();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setPermission(cursor.getString(1));
                data.setDataContent(cursor.getString(2));
                // Adding results to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return Url list
        return dataList;
    }


    // Get All Sms Messages
    public List<DataTable> getAllSms() {
        List<DataTable> dataList = new ArrayList<DataTable>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DATA + " WHERE permission=\"sms\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataTable data = new DataTable();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setPermission(cursor.getString(1));
                data.setDataContent(cursor.getString(2));
                // Adding results to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return Url list
        return dataList;
    }


    // Get All Browser History
    public List<DataTable> getAllBrowser() {
        List<DataTable> dataList = new ArrayList<DataTable>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DATA + " WHERE permission=\"browser history\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataTable data = new DataTable();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setPermission(cursor.getString(1));
                data.setDataContent(cursor.getString(2));
                // Adding results to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return Url list
        return dataList;
    }

    // Get All Contacts
    public List<DataTable> getAllContacts() {
        List<DataTable> dataList = new ArrayList<DataTable>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DATA + " WHERE permission=\"contacts\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataTable data = new DataTable();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setPermission(cursor.getString(1));
                data.setDataContent(cursor.getString(2));
                // Adding results to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return Url list
        return dataList;
    }


    // Get All Phone State Info
    public List<DataTable> getAllPhoneState() {
        List<DataTable> dataList = new ArrayList<DataTable>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DATA + " WHERE permission=\"phonestate\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataTable data = new DataTable();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setPermission(cursor.getString(1));
                data.setDataContent(cursor.getString(2));
                // Adding results to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }
        return dataList;
    }


    // Get All Location Info
    public List<DataTable> getAllLocation() {
        List<DataTable> dataList = new ArrayList<DataTable>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DATA + " WHERE permission=\"location\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataTable data = new DataTable();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setPermission(cursor.getString(1));
                data.setDataContent(cursor.getString(2));
                // Adding results to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }
        return dataList;
    }


    public List<DataTable> getSpecificData(String permission) {
        List<DataTable> dataList = new ArrayList<DataTable>();
        // Select All Queries for specific data type
        String selectQuery = "SELECT * FROM " + TABLE_DATA + " WHERE permission=\"" + permission +"\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataTable data = new DataTable();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setPermission(cursor.getString(1));
                data.setDataContent(cursor.getString(2));
                // Adding results to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }
        return dataList;
    }

    public void deleteSpecificRows(String permission) {
        List<DataTable> dataList = new ArrayList<DataTable>();
        // Select All Queries for specific data type
        String selectQuery = "SELECT * FROM " + TABLE_DATA + " WHERE permission=\"" + permission +"\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DataTable data = new DataTable();
                db.delete(TABLE_DATA, ID + " = ?",
                        new String[]{String.valueOf(data.getId())});
            } while (cursor.moveToNext());

        }
        db.close();

    }

    public void deleteRows(String permission) {
        List<DataTable> data = getSpecificData(permission);
        SQLiteDatabase db = this.getWritableDatabase();
        for(DataTable list: data){
                db.delete(TABLE_DATA, ID + " = ?", new String[]{String.valueOf(list.getId())});

        }
        db.close();

    }


        // Updating single data
    public int updateData(DataTable data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PERMISSION, data.getPermission());
        values.put(DATA_CONTENT, data.getDataContent());

        // updating row
        return db.update(TABLE_DATA, values, ID + " = ?",
                new String[] { String.valueOf(data.getId()) });
    }

    // Deleting data
    public void deleteData(DataTable data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA, ID + " = ?",
                new String[]{String.valueOf(data.getId())});
        db.close();
    }

    // Deleting data
    public void deleteSpecificData(DataTable data) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA, ID + " = ?",
                new String[]{String.valueOf(data.getId())});
        db.close();
    }


    // Get Data Count
    public int getDataCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }


    // Group Data Count
    public int groupData(String colname, String permission) {
        String countQuery = "SELECT  *, COUNT("+ colname + ") FROM " + TABLE_DATA + "GROUP BY"+colname;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }


}

