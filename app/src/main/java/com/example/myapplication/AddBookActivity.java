package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;
import android.widget.Button;
import android.widget.ListView;

public class AddBookActivity extends AppCompatActivity {
    EditText nameEdit, authorEdit, descEdit;
    DbHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbook);
        nameEdit = findViewById(R.id.editTextText2);
        authorEdit = findViewById(R.id.editTextText3);
        descEdit = findViewById(R.id.editTextText4);
        helper = new DbHelper(this);
    }

    public void Save_book(View view) {
        String name = nameEdit.getText().toString();
        String author = authorEdit.getText().toString();
        String desc = descEdit.getText().toString();
        SQLiteDatabase db = helper.getWritableDatabase();
        if (name.isEmpty() || author.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Введите все поля", Toast.LENGTH_SHORT).show();
        }
        db.execSQL("INSERT INTO books (Name, Author, Description) VALUES ('" +
                name + "', '" + author + "', '" + desc + "')");
        Toast.makeText(this, "Книга успешно сохранена!", Toast.LENGTH_SHORT).show();
    }

    public void Cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
