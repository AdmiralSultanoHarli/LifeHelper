package com.example.sqlitetest.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.sqlitetest.Data;

import java.nio.DoubleBuffer;
import java.util.ArrayList;

public class SqliteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "data.db";

    public static final String TABLE_DATA = "data";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_COMPANY_NAME = "companyname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_IMAGE = "image";

    public static final String TABLE_USER = "user";
    public static final String COLUMN_USER_ID = "id";
    public static final String COLUMN_USER_NAME = "name";
    public static final String COLUMN_USER_NUMBER = "number";
    public static final String COLUMN_USER_PASSWORD = "password";

    public SqliteDatabase (Context context){

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /*String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_DATA + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_NO + " INTEGER, " +
                COLUMN_EMAIL + " TEXT " + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);*/

        String CREATE_DATA_TABLE = "CREATE TABLE " + TABLE_DATA + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_COMPANY_NAME + " TEXT, " +
                COLUMN_EMAIL + " TEXT, " +
                COLUMN_PASSWORD + " TEXT " + ")";

        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_USER_NAME + " TEXT, " +
                COLUMN_USER_NUMBER + " TEXT, " +
                COLUMN_USER_PASSWORD + " TEXT " + ")";

        String DATA_TABLE = "INSERT INTO " + TABLE_DATA + "(companyname, email, password)" +
                "VALUES ('BCA', 'Admiral Sultano Harly', '1234'), " +
                "('MANDIRI', 'Admiral', '1234'), " +
                "('MUAMALAT', 'Admiral', '1234')";

        String DATA_USER = "INSERT INTO " + TABLE_USER + "(name, number, password)" +
                "VALUES ('Admiral', '0895636823830', 'aaaa')";

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_DATA_TABLE);
        db.execSQL(DATA_TABLE);
        db.execSQL(DATA_USER);

        /*db.execSQL(" CREATE TABLE " + DATABASE_MARKSTABLE + " (" +
                KEY_STUID + " TEXT PRIMARY KEY, " +
                KEY_SUB1 + " TEXT NOT NULL, " +
                KEY_SUB2 + " TEXT NOT NULL, " +
                KEY_SUB3 + " TEXT NOT NULL, " +
                KEY_MARKS1 + " INTEGER NOT NULL, " +
                KEY_MARKS2 + " INTEGER NOT NULL, " +
                KEY_MARKS3 + " INTEGER NOT NULL);"
        );*/

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);

    }

    public ArrayList<Data> listData(){

        String sql = "select * from " + TABLE_DATA;
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Data> storeData = new ArrayList<>();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()){

            do {
                int id = Integer.parseInt(cursor.getString(0));
                String companyName = cursor.getString(1);
                String email = cursor.getString(2);
                String password = cursor.getString(3);
                storeData.add(new Data(id, companyName, email, password));
            } while (cursor.moveToNext());

        }

        cursor.close();
        return storeData;

    }

    public void addData(Data data){

        ContentValues values = new ContentValues();

        if (values.equals(COLUMN_COMPANY_NAME)){

            return;

        }

        values.put(COLUMN_COMPANY_NAME, data.getCompanyName());
        values.put(COLUMN_EMAIL, data.getEmail());
        values.put(COLUMN_PASSWORD, data.getPassword());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_DATA,null, values);

    }

    public void updateData(Data data){

        ContentValues values = new ContentValues();
        values.put(COLUMN_COMPANY_NAME, data.getCompanyName());
        values.put(COLUMN_EMAIL, data.getEmail());
        values.put(COLUMN_PASSWORD, data.getPassword());
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_DATA, values, COLUMN_ID	+ "	= ?", new String[] { String.valueOf(data.getId())});

    }

    public Data findData(String name){

        String query = "Select * FROM " + TABLE_DATA + " WHERE " + COLUMN_COMPANY_NAME + " = " + "name";
        SQLiteDatabase db = this.getWritableDatabase();
        Data data = null;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()){

            int id = Integer.parseInt(cursor.getString(0));
            String dataCompanyName = cursor.getString(1);
            String dataEmail = cursor.getString(2);
            String dataPassword = cursor.getString(3);
            data = new Data(id, dataCompanyName, dataEmail, dataPassword);

        }
        cursor.close();
        return data;

    }

    public void deleteData(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DATA, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});

    }

}
