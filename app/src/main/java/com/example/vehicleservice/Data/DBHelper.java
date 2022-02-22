package com.example.vehicleservice.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper {

    Context context;

    public static final String DATABASE_NAME = "VehicleDB";
    public static int DATABASE_VERSION = 7;
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MENU = "menu";
    private static final String COLUMN_VALUE = "value";
    private static final String COLUMN_DISPLAY = "display";

    private static final String COLUMN_UNIT = "unit";
    private static final String COLUMN_TARGET = "target";


    private static final String TABLE_NAME1 = "Settings";
    private static String CREATE_TABLE1 = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME1 + "(id text PRIMARY KEY , menu text , value text, display text)";

    private static final String TABLE_NAME2 = "Control";
    private static String CREATE_TABLE2 = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME2 + "(id text PRIMARY KEY , unit text  , target text)";

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


    public boolean insertSettings(String id , String menu, Integer value, Integer display){
      SQLiteDatabase myDB = this.getWritableDatabase();
      ContentValues cv = new ContentValues();
      cv.put(COLUMN_ID,id);
      cv.put(COLUMN_MENU,menu);
      cv.put(COLUMN_VALUE,value);
      cv.put(COLUMN_DISPLAY,display);
      long result = myDB.insertOrThrow(TABLE_NAME1,null,cv);

      if (result== -1){
          return false;
      }else {
          return true;
      }
    };


    public boolean insertControl(String id , Integer unit, Integer target){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID,id);
        cv.put(COLUMN_UNIT,unit);
        cv.put(COLUMN_TARGET,target);
        long result = myDB.insertOrThrow(TABLE_NAME2,null,cv);

        if (result== -1){
            return false;
        }else {
            return true;
        }
    };


    public boolean isSettingsEmpty(){
        SQLiteDatabase myDB = this.getWritableDatabase();
        long NoOfRows = DatabaseUtils.queryNumEntries(myDB,TABLE_NAME1);
        return NoOfRows == 0;
    }

    public Cursor getDisplay(){
        String id ="display";
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = null;
        if (myDB != null){
            cursor = myDB.rawQuery("SELECT * FROM " + TABLE_NAME1 +" WHERE id = ?",new String[]{id});
        }
        return cursor;
    }

    public void updateDisplay(int value){
        String id ="display";
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DISPLAY,value);
        myDB.update(TABLE_NAME1,cv,"id=?",new String[]{id});
    }

    public void updateSettings(String id, int value){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_VALUE,value);
        myDB.update(TABLE_NAME1,cv,"menu=?",new String[]{id});
    }

    public void updateControl(String column, int value){
        String id ="control";
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(column,value);
        myDB.update(TABLE_NAME2,cv,"id=?",new String[]{id});
    }

    public Cursor getControlValue(){
        String id ="control";
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = null;
        if (myDB != null){
            cursor = myDB.rawQuery("SELECT * FROM " + TABLE_NAME2 +" WHERE id = ?",new String[]{id});
        }
        return cursor;
    }

    public Cursor getValue(String menu){

        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = null;
        if (myDB != null){
            cursor = myDB.rawQuery("SELECT " + COLUMN_VALUE + " FROM " + TABLE_NAME1 +" WHERE menu = ?",new String[]{menu});
        }
        return cursor;
    }


}
