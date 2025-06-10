package com.example.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddClientActivity extends AppCompatActivity {
    EditText firstNameEdit, lastNameEdit, phoneEdit, emailEdit;
    DbHelper helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addclient);
        firstNameEdit = findViewById(R.id.editTextText5);
        lastNameEdit = findViewById(R.id.editTextText6);
        phoneEdit = findViewById(R.id.editTextText7);
        emailEdit = findViewById(R.id.editTextText8);

        helper = new DbHelper(this);
    }

    public void SaveClient(View view) {
        String firstName = firstNameEdit.getText().toString();
        String lastName = lastNameEdit.getText().toString();
        String phone = phoneEdit.getText().toString();
        String email = emailEdit.getText().toString();

        SQLiteDatabase db = helper.getWritableDatabase();

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            db.execSQL("INSERT INTO clients (FirstName, LastName, Phone, Email) VALUES ('" +
                    firstName + "', '" + lastName + "', '" + phone + "', '" + email + "')");
            Toast.makeText(this, "Клиент успешно сохранен!", Toast.LENGTH_SHORT).show();

            // Очистка полей после успешного сохранения
            firstNameEdit.setText("");
            lastNameEdit.setText("");
            phoneEdit.setText("");
            emailEdit.setText("");

        } catch (Exception e) {
            Toast.makeText(this, "Ошибка при сохранении клиента: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void Cancel(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void DeleteClient(View view) {
        // Здесь можно реализовать логику удаления клиента
        // Например, по email или phone
        String email = emailEdit.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Введите email клиента для удаления", Toast.LENGTH_SHORT).show();
            return;
        }

        SQLiteDatabase db = helper.getWritableDatabase();
        int deletedRows = db.delete("clients", "Email = ?", new String[]{email});

        if (deletedRows > 0) {
            Toast.makeText(this, "Клиент удален", Toast.LENGTH_SHORT).show();
            // Очистка полей
            firstNameEdit.setText("");
            lastNameEdit.setText("");
            phoneEdit.setText("");
            emailEdit.setText("");
        } else {
            Toast.makeText(this, "Клиент не найден", Toast.LENGTH_SHORT).show();
        }
    }
}
