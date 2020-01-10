package com.example.sqlitetest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sqlitetest.Database.SqliteDatabase;
import com.example.sqlitetest.R;

public class SignUpActivity extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    EditText name, phoneNumber, password;
    Button buttonSignUp;
    ImageButton buttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        openHelper = new SqliteDatabase(this);
        name = findViewById(R.id.name);
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonClose = findViewById(R.id.buttonClose);


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = openHelper.getWritableDatabase();
                String nameStr = name.getText().toString().trim();
                String phoneNumberStr = phoneNumber.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();

                if (nameStr.isEmpty() || phoneNumberStr.isEmpty() || passwordStr.isEmpty()){

                    Toast.makeText(SignUpActivity.this, "Input something", Toast.LENGTH_SHORT).show();

                }else{

                    insertData(nameStr, phoneNumberStr, passwordStr);
                    Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();

                }

            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
                finish();

            }
        });

    }

    public void insertData(String nameStr, String phoneNumberStr, String passwordStr){

        ContentValues contentValues = new ContentValues();
        contentValues.put(SqliteDatabase.COLUMN_USER_NAME, nameStr);
        contentValues.put(SqliteDatabase.COLUMN_USER_NUMBER, phoneNumberStr);
        contentValues.put(SqliteDatabase.COLUMN_USER_PASSWORD, passwordStr);

        long id = db.insert(SqliteDatabase.TABLE_USER, null, contentValues);

    }

}
