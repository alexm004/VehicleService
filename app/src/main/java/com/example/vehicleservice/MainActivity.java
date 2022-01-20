package com.example.vehicleservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

public class MainActivity extends AppCompatActivity {

    private String vehicleModel;
    DBHelper myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DBHelper(this);

        readVehicleModel();
        //readModel();

        Boolean tableCheck = myDB.isTableEmpty();
        if (tableCheck == true){

            if (vehicleModel.equals("M1")) {
                myDB.insertSettings("Touch Screen Beep",2);
                myDB.insertSettings("Fuel Saver Display in Cluster",0);
                myDB.insertSettings("Display Mode Manual",0);
                Toast.makeText(getApplicationContext(), ""+vehicleModel, Toast.LENGTH_LONG).show();
            }
            else {
                myDB.insertSettings("Touch Screen Beep",1);
                myDB.insertSettings("Display Mode Manual",0);
                Toast.makeText(this, "M2", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "Not empty", Toast.LENGTH_SHORT).show();
        }

    }


    private void readVehicleModel() {
        try {
            int ch;
            StringBuilder builder = new StringBuilder();
            FileInputStream fileInputStream = openFileInput("Vehicle_MODEL.txt");
            while((ch = fileInputStream.read()) != -1) {
                builder.append((char)ch);
            }
            vehicleModel = ""+builder;
        }
        catch (FileNotFoundException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    /*private void readModel() {
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
        vehicleModel = string;
    }*/



}