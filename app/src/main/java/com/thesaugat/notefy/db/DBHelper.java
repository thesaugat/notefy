package com.thesaugat.notefy.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + NoteEntry.TABLE_NAME + " (" +
                    NoteEntry._ID + " INTEGER PRIMARY KEY," +
                    NoteEntry.COLUMN_NAME_TITLE + " TEXT," +
                    NoteEntry.COLUMN_NAME_DESC + " TEXT," +
                    NoteEntry.COLUMN_NAME_DATE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + NoteEntry.TABLE_NAME;
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "notify.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public Boolean insertNote(String title, String note) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date = sdf.format(new Date());
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NoteEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(NoteEntry.COLUMN_NAME_DESC, note);
        contentValues.put(NoteEntry.COLUMN_NAME_DATE, date);
        long result = DB.insert(NoteEntry.TABLE_NAME, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateNote(String name, String contact, String dob) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("note", contact);
        contentValues.put("date", dob);
        Cursor cursor = DB.rawQuery("Select * from Notes where title = ?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.update("Notes", contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deleteData(Long id) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " + NoteEntry.TABLE_NAME + " where _id = ?", new String[]{id.toString()});
        if (cursor.getCount() > 0) {
            long result = DB.delete(NoteEntry.TABLE_NAME, "_id=?", new String[]{id.toString()});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public List<NoteDataClass> getdata() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from " + NoteEntry.TABLE_NAME + " ORDER BY date DESC", null);
        List<NoteDataClass> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(NoteEntry._ID));
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteEntry.COLUMN_NAME_TITLE));
            String desc = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteEntry.COLUMN_NAME_DESC));
            String date = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteEntry.COLUMN_NAME_DATE));
            NoteDataClass noteDataClass = new NoteDataClass(itemId, title, desc, date);
            notes.add(noteDataClass);
        }
        cursor.close();
        return notes;
    }

    public List<NoteDataClass> getFilteredData() {
//        SQLiteDatabase DB = this.getWritableDatabase();
//        Cursor cursor = DB.rawQuery("Select * from Userdetails", null);
//        return cursor;

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                NoteEntry.COLUMN_NAME_TITLE,
                NoteEntry.COLUMN_NAME_DESC,
                NoteEntry.COLUMN_NAME_DATE
        };

        String selection = NoteEntry.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {"*"};

        String sortOrder =
                NoteEntry.COLUMN_NAME_DESC + " DESC";

        Cursor cursor = db.query(
                NoteEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        List<NoteDataClass> notes = new ArrayList<>();
        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(NoteEntry._ID));
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteEntry.COLUMN_NAME_TITLE));
            String desc = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteEntry.COLUMN_NAME_DESC));
            String date = cursor.getString(
                    cursor.getColumnIndexOrThrow(NoteEntry.COLUMN_NAME_DATE));
            NoteDataClass noteDataClass = new NoteDataClass(itemId, title, desc, date);
            notes.add(noteDataClass);
        }
        cursor.close();
        return notes;
    }
}