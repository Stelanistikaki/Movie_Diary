package com.example.admin.movie_diary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "movie_db4";
    public static final String TABLE_NAME = "movie_table";
    public static final String IDCOL = "ID";
    public static final String TITLECOL = "TITLE";
    public static final String YEARCOL = "YEAR";
    public static final String DIRECTORCOL = "DIRECTOR";
    public static final String GENRECOL = "GENRE";
    public static final String RATINGCOL = "RATING";
    public static final String RECOMMENDCOL = "RECOMMEND";
    public static final String COMMENTSCOL = "COMMENTS";

    private static final String TAG = DatabaseHelper.class.getName();



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT, YEAR TEXT, DIRECTOR TEXT, GENRE TEXT, RATING FLOAT, RECOMMEND TEXT, COMMENTS TEXT) ";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String title, String year, String director, String genre, float rating, String recommend, String comments){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLECOL, title);
        contentValues.put(YEARCOL, year);
        contentValues.put(DIRECTORCOL, director);
        contentValues.put(GENRECOL, genre);
        contentValues.put(RATINGCOL, rating);
        contentValues.put(RECOMMENDCOL, recommend);
        contentValues.put(COMMENTSCOL, comments);

        Log.d(TAG, "insertData: " + title);
        Log.d(TAG, "insertData: " + year);
        Log.d(TAG, "insertData: " + director);
        Log.d(TAG, "insertData: " + genre);
        Log.d(TAG, "insertData: " + rating);
        Log.d(TAG, "insertData: " + recommend);
        Log.d(TAG, "insertData: " + comments);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return(result != -1);

    }

    public Cursor getItemData(String name){
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + TITLECOL + " = '" + name + "' ";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor result = db.rawQuery(query, null);
        return result;
    }

    public Cursor searchData(String value, String column){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + column + " COLLATE NOCASE LIKE '%" + value + "%' "; //!!!!!
        Log.d(TAG, "insertData: " + value);
        Cursor result = db.rawQuery(query, null);
        return result;
    }

    public boolean UpdateData(int id, String title, String year, String director, String genre, float rating, String recommend, String comments){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(IDCOL, id);
        contentValues.put(TITLECOL, title);
        contentValues.put(YEARCOL, year);
        contentValues.put(DIRECTORCOL, director);
        contentValues.put(GENRECOL, genre);
        contentValues.put(RATINGCOL, rating);
        contentValues.put(RECOMMENDCOL, recommend);
        contentValues.put(COMMENTSCOL, comments);

        db.update(TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(id)});

        Log.d(TAG, "updateData: " + title);
        return true;
    }

    public int deleteData (int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ?", new String[]{String.valueOf(id)});
    }
}
