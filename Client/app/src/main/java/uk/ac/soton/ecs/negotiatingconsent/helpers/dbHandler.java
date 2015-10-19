package uk.ac.soton.ecs.negotiatingconsent.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import uk.ac.soton.ecs.negotiatingconsent.models.Status;

/**
 * Created by Anna Soska on 30/06/15.
 */

public class dbHandler extends SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dbLog";

    // Url table
    private static final String TABLE_STATUS = "table_status";

    // Status Table Columns
    private static final String ID = "id";
    private static final String PERMISSION = "permission";
    private static final String POINTS = "points";
    private static final String QID = "question_id";
    private static final String QRESPONSE = "question_response";
    private static final String PENDING = "pending";
    private static final String SAVED_TO_SERVER = "savedtoserver";




    public dbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_STATUS = "CREATE TABLE " + TABLE_STATUS + "("
                + ID + " INTEGER PRIMARY KEY," + PERMISSION + " TEXT, "
                + POINTS + " TEXT, " +  QID + " TEXT, "+   QRESPONSE + " TEXT, " + PENDING + " INTEGER, " + SAVED_TO_SERVER + " INTEGER " + ")";
        db.execSQL(CREATE_TABLE_STATUS);
        // Remove repeating tables
        //db.execSQL("DELETE FROM " + TABLE_URL + " WHERE " + URL + "=" + URL);
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
    public void addStatus(Status status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PERMISSION, status.getStatusPermission()); // Question/Action type indicated by permission
        values.put(POINTS, status.getPoints()); // Points
        values.put(QID, status.getQuestion_id());
        values.put(QRESPONSE, status.getQuestion_response());
        values.put(PENDING, status.getPending());
        values.put(SAVED_TO_SERVER, status.getSavedToServer());

        // Inserting Row
        db.insert(TABLE_STATUS, null, values);
        db.close(); // Closing database connection
    }

    // Get single status
    Status getStatus(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STATUS, new String[]{ID,
                        PERMISSION, POINTS, QID, QRESPONSE, PENDING, SAVED_TO_SERVER}, ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Status status = new Status(Integer.parseInt(cursor.getString(0)),cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3),cursor.getString(4),Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)));
        // return status
        return status;
    }

    // Get All Urls
    public List<Status> getAllStatus() {
        List<Status> statusList = new ArrayList<Status>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STATUS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Status status = new Status();
                status.setId(Integer.parseInt(cursor.getString(0)));
                status.setStatusPermission(cursor.getString(1));
                status.setPoints(Integer.parseInt(cursor.getString(2)));
                status.setQuestion_id(cursor.getString(3));
                status.setQuestion_response(cursor.getString(4));
                status.setPending(Integer.parseInt(cursor.getString(5)));
                status.setSavedToServer(Integer.parseInt(cursor.getString(6)));

                // Adding results to list
                statusList.add(status);
            } while (cursor.moveToNext());
        }
        db.close(); // Closing database connection
        // return Url list
        return statusList;
    }

    // Updating single url
    public int updateStatus(Status status) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PERMISSION, status.getStatusPermission());
        values.put(POINTS, status.getPoints());
        values.put(QID, status.getQuestion_id());
        values.put(QRESPONSE, status.getQuestion_response());
        values.put(PENDING, status.getPending());

        // updating row
        return db.update(TABLE_STATUS, values, ID + " = ?",
                new String[] { String.valueOf(status.getId()) });

    }

    public void updateShareStatus(int statusId, int pending)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(PENDING, pending);
        db.update(TABLE_STATUS, values, ID + " = ?",
                new String[]{String.valueOf(statusId)});

        db.close(); // Closing database connection
    }


    // Get Ids of pending data of specific type
    public List<Status> getPendingStatus(String permission) {
        List<Status> statusList = new ArrayList<Status>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STATUS + " WHERE pending="+"\"1\" AND permission=\""+permission+"\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Status status = new Status();
                status.setId(Integer.parseInt(cursor.getString(0)));
                status.setStatusPermission(cursor.getString(1));
                status.setPoints(Integer.parseInt(cursor.getString(2)));
                status.setQuestion_id(cursor.getString(3));
                status.setQuestion_response(cursor.getString(4));
                status.setPending(Integer.parseInt(cursor.getString(5)));
                status.setSavedToServer(Integer.parseInt(cursor.getString(6)));

                // Adding results to list
                statusList.add(status);
            } while (cursor.moveToNext());
        }
        // return Status list
        cursor.close();
        return statusList;
    }


    public List<Status> getUnsavedStatus()
    {
        List<Status> statusList = new ArrayList<Status>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_STATUS + " WHERE "+ SAVED_TO_SERVER +"="+"\"0\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Status status = new Status();
                status.setId(Integer.parseInt(cursor.getString(0)));
                status.setStatusPermission(cursor.getString(1));
                status.setPoints(Integer.parseInt(cursor.getString(2)));
                status.setQuestion_id(cursor.getString(3));
                status.setQuestion_response(cursor.getString(4));
                status.setPending(Integer.parseInt(cursor.getString(5)));
                status.setSavedToServer(Integer.parseInt(cursor.getString(6)));

                // Adding results to list
                statusList.add(status);
            } while (cursor.moveToNext());
        }
        db.close(); // Closing database connection
        // return status list
        return statusList;
    }


    public void updatePendingStatus(String permission)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Status> status = getPendingStatus(permission);
        for (Status list: status){
            ContentValues values = new ContentValues();

            values.put(PENDING, 0);
            db.update(TABLE_STATUS, values, ID + " = ?", new String[]{String.valueOf(list.getId())});

        }
        db.close();
    }

    public void cancelSharedData(String permission)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Status> status = getPendingStatus(permission);
        for (Status list: status){
            ContentValues values = new ContentValues();

            values.put(QRESPONSE, "Do not share"); // change answer to No
            values.put(SAVED_TO_SERVER, 0); // Set the saved to server flag to 0
            db.update(TABLE_STATUS, values, ID + " = ?", new String[]{String.valueOf(list.getId())});

        }
        db.close();
    }


    public void deleteCancelledData(String permission)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Status> status = getPendingStatus(permission);
        for (Status list: status){
           
            db.delete(TABLE_STATUS, ID + " = ?", new String[]{String.valueOf(list.getId())});

        }
        db.close();
    }
    
    
    

    public void updateSavedStatus(int statusId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Status status = getStatus(statusId);
        ContentValues values = new ContentValues();
        values.put(SAVED_TO_SERVER, 1);
        db.update(TABLE_STATUS, values, ID + " = ?", new String[]{String.valueOf(status.getId())});
        db.close();
    }


    // Deleting single url
    public void deleteContact(Status status) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STATUS, ID + " = ?",
                new String[]{String.valueOf(status.getId())});
        db.close();
    }


    // Get Url Count
    public int getStatusCount() {
        String countQuery = "SELECT  * FROM " + TABLE_STATUS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        db.close(); // Closing database connection
        // return count
        return cursor.getCount();
    }




}
