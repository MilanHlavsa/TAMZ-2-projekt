package com.example.hla0191_tamz2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    private static final String table_name = "Scores";
    private static final String player = "player";
    private static final String difficulty = "difficulty";
    private static final String goblins_killed = "goblins_killed";

    public Database(Context context) {
        super(context, table_name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE "+ table_name + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + player + " TEXT, " + goblins_killed + " INTEGER, " + difficulty +" TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertData(String name, String diff, int goblins) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(player, name);
        contentValues.put(difficulty, diff);
        contentValues.put(goblins_killed, goblins);

        long result = db.insert(table_name, null, contentValues);
        if (result == -1) return false;
        else return true;
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + table_name;
        Cursor data = db.rawQuery(query,null);
        return data;
    }
}
