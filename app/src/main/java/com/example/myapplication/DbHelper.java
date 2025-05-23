package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "library";
    private static final int DATABASE_VERSION = 1;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "CREATE TABLE IF NOT EXISTS employees("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " Login TEXT NOT NULL," +
                " Password TEXT NOT NULL," +
                " Name TEXT NOT NULL)";
        String createTable2 = "CREATE TABLE IF NOT EXISTS clients(" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " FirstName TEXT NOT NULL," +
                " LastName TEXT NOT NULL," +
                " Phone TEXT NOT NULL," +
                " Email TEXT NOT NULL)";
        String createTable3 = "CREATE TABLE IF NOT EXISTS books(" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Name TEXT NOT NULL," +
                " Author TEXT NOT NULL," +
                " Description TEXT NOT NULL)";
        String createTable4 = "CREATE TABLE IF NOT EXISTS reading_journal(" +
                " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ClientId INTEGER NOT NULL," +
                " BookId INTEGER NOT NULL," +
                " EmployeeId INTEGER NOT NULL," +
                " StartDate TEXT NOT NULL," +
                " EndDate TEXT NOT NULL," +
                " Comment TEXT)";
        db.execSQL(createTable1);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        db.execSQL(createTable4);
        String insertEmployee = "INSERT INTO employees (Login, Password, Name) VALUES ('rogantan', '123', 'Andrey')";
        db.execSQL(insertEmployee);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS employees");
        onCreate(db);
    }
}
