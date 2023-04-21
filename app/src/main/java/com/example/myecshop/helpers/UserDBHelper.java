package com.example.myecshop.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.myecshop.models.User;

public class UserDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "JewelryShop.db";

    private static final String TABLE_USER = "User";

    public UserDBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " ( Id TEXT PRIMARY KEY, Username TEXT, Password TEXT, Address LONG, Email TEXT, Phone TEXT)";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS USER");
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Id", user.getId());
        values.put("Username", user.getName());
        values.put("Password", user.getPassword());
        values.put("Address", user.getAddress());
        values.put("Email", user.getEmail());
        values.put("Phone", user.getPhone());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public User getUser() {
        User user = new User();

        String selectQuery = "SELECT * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                user.setId(cursor.getString(0));
                user.setName(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setAddress(cursor.getString(3));
                user.setEmail(cursor.getString(4));
                user.setPhone(cursor.getString(5));

            } while (cursor.moveToNext());
        }
        return user;
    }

    public void deleteAllUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }
}
