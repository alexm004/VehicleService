package com.example.vehicleservice;

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
    public static int DATABASE_VERSION = 4;
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MENU = "menu";
    private static final String COLUMN_VALUE = "value";


    private static final String TABLE_NAME1 = "Settings";
    private static String CREATE_TABLE1 = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME1 + "(id text PRIMARY KEY , menu text , value text)";



     DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE1);

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME1);

        onCreate(db);
    }

    public boolean insertSettings(String id , String menu, Integer value){
      SQLiteDatabase myDB = this.getWritableDatabase();
      ContentValues cv = new ContentValues();
      cv.put(COLUMN_ID,id);
      cv.put(COLUMN_MENU,menu);
      cv.put(COLUMN_VALUE,value);
      long result = myDB.insertOrThrow(TABLE_NAME1,null,cv);

      if (result== -1){
          return false;
      }else {
          return true;
      }
    };

    public boolean isTableEmpty(){
        SQLiteDatabase myDB = this.getWritableDatabase();
        long NoOfRows = DatabaseUtils.queryNumEntries(myDB,TABLE_NAME1);
        return NoOfRows == 0;
    }

    Cursor getData(){
        String query = "SELECT * FROM " + TABLE_NAME1;
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = null;
        if (myDB != null){
            cursor = myDB.rawQuery(query,null);
        }
        return cursor;
    }

    public void updateData(String id, int value){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_VALUE,value);
        myDB.update(TABLE_NAME1,cv,"menu=?",new String[]{id});
    }

}
