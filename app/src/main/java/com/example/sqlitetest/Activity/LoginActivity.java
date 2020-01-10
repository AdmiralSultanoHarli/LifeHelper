package com.example.sqlitetest.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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

public class LoginActivity extends AppCompatActivity {

    EditText phoneNumber, password;
    Button buttonSignIn, buttonSignUp;
    ImageButton buttonClose;

    private SQLiteDatabase db;
    private SQLiteOpenHelper openHelper;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        openHelper = new SqliteDatabase(this);
        db = openHelper.getReadableDatabase();
        phoneNumber = findViewById(R.id.phoneNumber);
        password = findViewById(R.id.password);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonClose = findViewById(R.id.buttonClose);

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phoneNumberStr = phoneNumber.getText().toString().trim();
                String passwordStr = password.getText().toString().trim();

                if (phoneNumberStr.isEmpty() && passwordStr.isEmpty()){

                    Toast.makeText(LoginActivity.this, "Input something", Toast.LENGTH_SHORT).show();

                }else if (phoneNumberStr.isEmpty()){

                    Toast.makeText(LoginActivity.this, "Please input phone number", Toast.LENGTH_SHORT).show();
                    
                }else if(passwordStr.isEmpty()){

                    Toast.makeText(LoginActivity.this, "Please input password", Toast.LENGTH_SHORT).show();

                }else{

                    cursor = db.rawQuery("SELECT * FROM " + SqliteDatabase.TABLE_USER + " WHERE " + SqliteDatabase.COLUMN_USER_NUMBER + "=? AND " + SqliteDatabase.COLUMN_USER_PASSWORD + "=?", new String[]{phoneNumberStr, passwordStr});
                    if (cursor != null){

                        if (cursor.getCount() > 0){

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();

                        }else {

                            Toast.makeText(LoginActivity.this, "No data", Toast.LENGTH_SHORT).show();

                        }

                    }

                }

            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();

            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finishAffinity();

            }
        });

    }
}
