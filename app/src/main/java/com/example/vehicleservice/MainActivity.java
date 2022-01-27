package com.example.vehicleservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String vehicleModel;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DBHelper(this);

        readVehicleModel();

        insertSettings();


    }


    private void readVehicleModel() {
            String string = "";
            try {
                InputStream inputStream = getAssets().open("Vehicle_MODEL.txt");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                string = new String(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(MainActivity.this, ""+string, Toast.LENGTH_LONG).show();

        }



    private void insertSettings() {

        Boolean tableCheck = myDB.isTableEmpty();
        if (tableCheck == true){

            if (vehicleModel.equals("M1")) {
                myDB.insertSettings("touch","Touch Screen Beep",2);
                myDB.insertSettings("fuel","Fuel Saver Display in Cluster",0);
                myDB.insertSettings("display","Display Mode Manual",0);
                Toast.makeText(getApplicationContext(), ""+vehicleModel, Toast.LENGTH_LONG).show();
            }
            else {
                myDB.insertSettings("touch","Touch Screen Beep",1);
                myDB.insertSettings("display","Display Mode Manual",0);
                Toast.makeText(this, "M2", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "Not empty", Toast.LENGTH_SHORT).show();
        }
    }


}