package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class ClientsActivity extends AppCompatActivity {
    ListView list;
    Button btn1, btn2;
    DbHelper helper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);
        list = findViewById(R.id.list1);
        btn1 = findViewById(R.id.button6);
        btn2 = findViewById(R.id.button7);
        helper = new DbHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        db = helper.getReadableDatabase();
        userCursor = db.rawQuery("SELECT * FROM clients", null);
        String[] headers = new String[]{"LastName", "FirstName"};
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        list.setAdapter(userAdapter);
    }

    public void AddClient(View view) {

    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
        userCursor.close();
    }
}
