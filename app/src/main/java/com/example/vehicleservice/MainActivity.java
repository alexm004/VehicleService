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

import com.example.vehicleservice.Data.DBHelper;

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


/**
 * @brief Method to check existence of file , if not new file cam be created
 */

        File file = getBaseContext().getFileStreamPath("Vehicle_MODEL.txt");

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

/**
 * @brief Method to read the model name from the file
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

/**
 * @brief Method used to insert data into database based on model
 */

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
