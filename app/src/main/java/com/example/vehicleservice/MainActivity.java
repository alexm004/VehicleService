package com.example.vehicleservice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private String vehicleModel;
    DBHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DBHelper(this);

        readVehicleModel();

    }


    /*private void readVehicleModel() {
            String string;
            try {
                InputStream inputStream = getAssets().open("Vehicle_MODEL.txt");
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                string = new String(buffer);
                vehicleModel = string;
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(MainActivity.this, ""+vehicleModel, Toast.LENGTH_LONG).show();

        }
     */

    private void readVehicleModel() {

        try {
            int ch;
            StringBuilder stringBuilder = new StringBuilder();
            FileInputStream fileInputStream = openFileInput("Vehicle_MODEL.txt");
            while ((ch = fileInputStream.read()) != -1) {
                stringBuilder.append((char) ch);
                vehicleModel = "" + stringBuilder;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(MainActivity.this, ""+vehicleModel, Toast.LENGTH_LONG).show();

        insertSettings();
    }



    private void insertSettings() {

        boolean tableCheck = myDB.isSettingsEmpty();
        if (tableCheck){

            if (vehicleModel.equals("M1")) {
                myDB.insertSettings("touch","Touch Screen Beep",2);
                myDB.insertSettings("fuel","Fuel Saver Display in Cluster",0);
                myDB.insertSettings("display","Display Mode Manual",0);
                myDB.insertSettings("hl on","Display Brightness HL ON",3);
                myDB.insertSettings("hl off","Display Brightness HL OFF",3);

                myDB.insertControl("control",1,50);
               // Toast.makeText(getApplicationContext(), ""+vehicleModel, Toast.LENGTH_LONG).show();
            }
            else {
                myDB.insertSettings("touch","Touch Screen Beep",1);
                myDB.insertSettings("display","Display Mode Manual",0);
                myDB.insertSettings("hl on","Display Brightness HL ON",5);
                myDB.insertSettings("hl off","Display Brightness HL OFF",5);

                myDB.insertControl("control",1,50);
                Toast.makeText(this, "M2", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "Not empty", Toast.LENGTH_SHORT).show();
        }
    }


}
