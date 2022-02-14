/**
 * author@ Alex_M_Paul
 * file - Service app MainActivity
 */

package com.example.vehicleservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String vehicleModel;
    DBHelper myDB;

    EditText etInput;
    Button btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInput = findViewById(R.id.et_input);
        btnUpdate = findViewById(R.id.btn_save);

        myDB = new DBHelper(this);

//  file named Vehicle_MODEL.txt is checked if it exists in internal storage

        File file = getBaseContext().getFileStreamPath("Vehicle_MODEL.txt");

//  if file exist readVehicleModel() is called to read vehicle model name from it
//  user can also update the value in the file to M1 or M2 based on their need

        if (file.exists()){

            readVehicleModel();

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    file.delete();

                    try {
                        FileOutputStream fileOutputStream = openFileOutput("Vehicle_MODEL.txt", Context.MODE_PRIVATE);
                        //String data = input the data
                        fileOutputStream.write(etInput.getText().toString().getBytes());
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        Toast.makeText(MainActivity.this, "File Saved", Toast.LENGTH_LONG).show();

                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });

        }

//  or else user can create file with content 'M1' or 'M2' and will be stored in internal storage on clicking button
        else {

            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        FileOutputStream fileOutputStream = openFileOutput("Vehicle_MODEL.txt", Context.MODE_PRIVATE);
                        //String data = input the data
                        fileOutputStream.write(etInput.getText().toString().getBytes());
                        fileOutputStream.flush();
                        fileOutputStream.close();
                        Toast.makeText(MainActivity.this, "File Saved", Toast.LENGTH_LONG).show();

                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            });

        }
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

             insertSettings();
        }
     */

//  This function is used to read the model name from the file

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

//  This function is used to insert to the database all the required values based on the vehicle model

    private void insertSettings() {

        boolean tableCheck = myDB.isSettingsEmpty();
        if (tableCheck){

            if (vehicleModel.equals("M1")) {
                myDB.insertSettings("touch","Touch Screen Beep",2,0);
                myDB.insertSettings("fuel","Fuel Saver Display in Cluster",0,0);
                myDB.insertSettings("display","Display Mode Manual",0,0);
                myDB.insertSettings("hl on","Display Brightness HL ON",3,0);
                myDB.insertSettings("hl off","Display Brightness HL OFF",3,0);

                myDB.insertControl("control",1,50);
               // Toast.makeText(getApplicationContext(), ""+vehicleModel, Toast.LENGTH_LONG).show();
            }
            else {
                myDB.insertSettings("touch","Touch Screen Beep",1,0);
                myDB.insertSettings("fuel","Fuel Saver Display in Cluster",0,0);
                myDB.insertSettings("display","Display Mode Manual",0,0);
                myDB.insertSettings("hl on","Display Brightness HL ON",5,0);
                myDB.insertSettings("hl off","Display Brightness HL OFF",5,0);

                myDB.insertControl("control",1,50);
                Toast.makeText(this, "M2", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "Not empty", Toast.LENGTH_SHORT).show();
        }
    }


}
