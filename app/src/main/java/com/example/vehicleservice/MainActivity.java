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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readVehicleModel();

        //readModel();
    }

    private void readVehicleModel() {

        try {
            int ch;
            StringBuilder builder = new StringBuilder();
            FileInputStream fileInputStream = openFileInput("Vehicle_MODEL.txt");
            while((ch = fileInputStream.read()) != -1) {
                builder.append((char)ch);
            }
            Toast.makeText(getApplicationContext(), ""+builder, Toast.LENGTH_LONG).show();
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
        Toast.makeText(MainActivity.this, ""+string, Toast.LENGTH_LONG).show();
    }*/



}