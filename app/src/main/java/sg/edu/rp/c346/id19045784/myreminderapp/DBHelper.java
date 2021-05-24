package sg.edu.rp.c346.id19045784.myreminderapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    //TODO Define the Database properties
    private Context context;
    private static final String DATABASE_NAME = "reminders.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_REMINDER = "reminder";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EVENT= "event";
    private static final String COLUMN_DESCRIPTION = "desc";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIMESTART = "timestart";
    private static final String COLUMN_TIMEEND = "timeend";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //TODO CREATE TABLE Note
        String createTableSql = "CREATE TABLE " + TABLE_REMINDER +  "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_EVENT + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TIMESTART + " TEXT,"
                + COLUMN_TIMEEND + " TEXT)";
        db.execSQL(createTableSql);
        Log.i("info" ,"created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMINDER);
        onCreate(db);
    }

    public long insertNote(String event, String desc, String date, String timestart, String timeend) {
        //TODO insert the data into the database
        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();
        // We use ContentValues object to store the values for
        //  the db operation
        ContentValues values = new ContentValues();
        // Store the column name as key and the event as value
        values.put(COLUMN_EVENT, event);
        // Store the column name as key and the desc as value
        values.put(COLUMN_DESCRIPTION, desc);
        // Store the column name as key and the date as value
        values.put(COLUMN_DATE, date);
        // Store the column name as key and the timestart as value
        values.put(COLUMN_TIMESTART, timestart);
        // Store the column name as key and the timeend as value
        values.put(COLUMN_TIMEEND, timeend);

        // Insert the row into the TABLE_TASK
        long result = db.insert(TABLE_REMINDER, null, values);


        if (result != -1){
            Toast.makeText(context,"Inserted",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(context, "Fail Inserted", Toast.LENGTH_LONG).show();
        }
        // Close the database connection
        db.close();
        return result;
    }

    public ArrayList<Reminder> getAllReminders() {
        //TODO return records in Java objects
        ArrayList<Reminder> tasks = new ArrayList<Reminder>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_EVENT, COLUMN_DESCRIPTION, COLUMN_DATE, COLUMN_TIMESTART, COLUMN_TIMEEND};
        Cursor cursor = db.query(TABLE_REMINDER, columns, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String event = cursor.getString(1);
                String desc = cursor.getString(2);
                String date = cursor.getString(3);
                String timeStart = cursor.getString(4);
                String timeEnd = cursor.getString(5);
                Reminder obj = new Reminder(id, event, desc, date, timeStart, timeEnd);
                tasks.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }

    public int updateReminder(Reminder data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT, data.getTitle());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_DATE, data.getDate());
        values.put(COLUMN_TIMESTART, data.getStartTime());
        values.put(COLUMN_TIMESTART, data.getEndTime());
        values.put(COLUMN_ID,data.getID());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getID())};
        int result = db.update(TABLE_REMINDER, values, condition, args);
        db.close();
        return result;
    }
    public int deleteReminder(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_REMINDER, condition, args);
        db.close();
        return result;
    }

}
