package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "librar.db";
    private static final int DATABASE_VERSION = 1;
    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "CREATE TABLE IF NOT EXISTS employees("
                + " _id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " Login TEXT NOT NULL," +
                " Password TEXT NOT NULL," +
                " Name TEXT NOT NULL)";
        String createTable2 = "CREATE TABLE IF NOT EXISTS clients(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " FirstName TEXT NOT NULL," +
                " LastName TEXT NOT NULL," +
                " Phone TEXT NOT NULL," +
                " Email TEXT NOT NULL)";
        String createTable3 = "CREATE TABLE IF NOT EXISTS books(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Name TEXT NOT NULL," +
                " Author TEXT NOT NULL," +
                " Description TEXT NOT NULL)";
        String createTable4 = "CREATE TABLE IF NOT EXISTS reading_journal(" +
                " _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ClientId INTEGER NOT NULL," +
                " BookId INTEGER NOT NULL," +
                " StartDate TEXT NOT NULL," +
                " EndDate TEXT NOT NULL," +
                " Comment TEXT," +
                " FOREIGN KEY (ClientId) REFERENCES clients (id) ON DELETE CASCADE," +
                " FOREIGN KEY (BookId) REFERENCES books (id) ON DELETE CASCADE)";
        db.execSQL(createTable1);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        db.execSQL(createTable4);
        String insertEmployee = "INSERT INTO employees (Login, Password, Name) VALUES ('rogantan', '123', 'Andrey')";
        db.execSQL(insertEmployee);
        String insertBook = "INSERT INTO books (Name, Author, Description) VALUES ('Колобок', 'Народ', 'Сказка о колобке')";
        db.execSQL(insertBook);
        String insertClient = "INSERT INTO clients (FirstName, LastName, Phone, Email) VALUES ('Иван', 'Иванов', '79138882055', 'ivan@gmail.com')";
        db.execSQL(insertClient);
        String insertReading = "INSERT INTO reading_journal (ClientId, BookId, StartDate, EndDate) VALUES (1, 1, '2025-5-29', '2025-06-05')";
        db.execSQL(insertReading);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS employees");
        db.execSQL("DROP TABLE IF EXISTS clients");
        db.execSQL("DROP TABLE IF EXISTS books");
        db.execSQL("DROP TABLE IF EXISTS reading_journal");
        onCreate(db);
    }

    public long insertBook(String name, String author, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Author", author);
        values.put("Description", description);
        long id = db.insert("books", null, values);
        db.close();
        return id;
    }

    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM books ORDER BY Name", null);
    }

    public int updateBook(int id, String name, String author, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", name);
        values.put("Author", author);
        values.put("Description", description);
        int count = db.update("books", values, "_id = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return count;
    }

    public void deleteBook(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("books", "_id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public long insertClient(String firstName, String lastName, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FirstName", firstName);
        values.put("LastName", lastName);
        values.put("Phone", phone);
        values.put("Email", email);
        long id = db.insert("clients", null, values);
        db.close();
        return id;
    }

    public Cursor getAllClients() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM clients ORDER BY LastName, FirstName", null);
    }

    public int updateClient(int id, String firstName, String lastName, String phone, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FirstName", firstName);
        values.put("LastName", lastName);
        values.put("Phone", phone);
        values.put("Email", email);
        int count = db.update("clients", values, "_id = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return count;
    }

    public void deleteClient(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("clients", "_id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Получение журнала чтения для клиента
    public Cursor getClientReadingJournal(int clientId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(
                "SELECT rj._id, rj.StartDate, rj.EndDate, rj.Comment, " +
                        "b.Name AS BookName FROM reading_journal rj " +
                        "JOIN books b ON rj.BookId = b._id " +
                        "WHERE rj.ClientId = ? ORDER BY rj.StartDate DESC",
                new String[]{String.valueOf(clientId)});
    }
}
