package com.example.vehicleservice;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    Context context;

    public static final String DATABASE_NAME = "VehicleDB";
    public static int DATABASE_VERSION = 1;

    private static final String TABLE_NAME1 = "M1Settings";
    private static String CREATE_TABLE1 = "create table "+ TABLE_NAME1 + "(menu text , value text)";

    private static final String TABLE_NAME2 = "M2Settings";
    private static String CREATE_TABLE2 = "create table "+ TABLE_NAME2 + "(menu text , value text)";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE1);
            db.execSQL(CREATE_TABLE2);

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME2);
        onCreate(db);
    }
}
