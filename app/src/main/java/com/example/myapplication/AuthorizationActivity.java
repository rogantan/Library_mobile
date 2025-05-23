package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.Toast;
import android.widget.EditText;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;
import android.widget.Button;

public class AuthorizationActivity extends AppCompatActivity {
    Cursor userCursor;
    DbHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        helper = new DbHelper(this);
        db = helper.getWritableDatabase();

    }

    public void Authorization(View view) {
        EditText loginEdit = findViewById(R.id.editTextText);
        EditText passwordEdit = findViewById(R.id.editTextTextPassword);
        String loginText = loginEdit.getText().toString();
        String passwordText = passwordEdit.getText().toString();
        if (loginText.isEmpty() || passwordText.isEmpty()) {
            Toast.makeText(this, "Введите все поля!", Toast.LENGTH_SHORT).show();
        } else {
            userCursor = db.rawQuery("SELECT * FROM employees WHERE login = ? AND password = ?",
                    new String[]{loginText, passwordText});
            if (userCursor.getCount() > 0) {
                userCursor.moveToFirst();
                String userName = userCursor.getString(3);
                Toast.makeText(this, "Добро пожаловать, " + userName + "!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
            }
            userCursor.close();
        }
    }
}
