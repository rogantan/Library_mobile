package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {
    ListView list;
    Button btn1, btn2;
    DbHelper helper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        btn1 = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);
        helper = new DbHelper(this);
        btn1.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddBookActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        db = helper.getReadableDatabase();
        userCursor = db.rawQuery("SELECT * FROM books", null);
        String[] headers = new String[]{"Name", "Author"};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        list.setAdapter(userAdapter);
    }

    public void ClientActivity(View view) {
        Intent intent = new Intent(this, ClientsActivity.class);
        startActivity(intent);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
        userCursor.close();
    }
}