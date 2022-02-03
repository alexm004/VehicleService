package com.example.vehicleservice;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import ServicePackage.aidlInterface;

public class VehicleService extends Service {

    DBHelper myDB;
    ArrayList<String> menu_id,menu_name,menu_value;

    int display,val,target;
    String vehicleModel;

    public VehicleService() {

        myDB = new DBHelper(this);

        menu_id = new ArrayList<>();
        menu_name = new ArrayList<>();
        menu_value = new ArrayList<>();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return stubObject;
    }


    aidlInterface.Stub stubObject = new aidlInterface.Stub() {

        @Override
        public int menuClick(String id, int value) throws RemoteException {
            return displaymode(id,value);
        }

        @Override
        public String vehicleModel() throws RemoteException {
            return readVehicleModel();
        }

        @Override
        public void updateValues(String id, int value) throws RemoteException {
           updateValue(id,value);
        }

        @Override
        public void updateControl(String column, int value) throws RemoteException {
            updateControlValue(column,value);

        }

        @Override
        public int getTarget() throws RemoteException {
            return targetValue();
        }
    };


    public int displaymode(String id,int value){
        updateValue(id,value);
        if (id.equals("Display Mode Manual") && value ==1){
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Random random = new Random();
                    val = random.nextInt(20+2)-2;
                    if (val>0 && val%2==0){
                        display = 1;
                    }else if (val>0 && val%2!=0){
                        display = 0;
                    }else if (val<0){
                        display = -1;
                    }
                }
            },5000);

        }
        else if (id.equals("Display Mode Manual") && value ==0){
            updateValue(id,value);
            display = -1;
        }
        return display;
    }


    private String readVehicleModel() {

        try {
            int ch;
            StringBuilder stringBuilder = new StringBuilder();
            FileInputStream fileInputStream = openFileInput("Vehicle_MODEL2.txt");
            while ((ch = fileInputStream.read()) != -1) {
                stringBuilder.append((char) ch);
                vehicleModel = "" + stringBuilder;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vehicleModel;
    }


    public void updateValue(String id, int value){
        myDB.updateSettings(id,value);
    }

    public void updateControlValue(String column, int value){
        myDB.updateControl(column,value);
    }

    public int targetValue(){
        Cursor cursor = myDB.getTarget();
        if (cursor.getCount()== 0){
            Toast.makeText(this,"No data",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext()){
               target = Integer.parseInt(cursor.getString(2));
            }
        }
        return target;
    }


        public void storeDataInArrays(){
        Cursor cursor = myDB.getData();
        if (cursor.getCount()== 0){
            Toast.makeText(this,"No data",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext()){
                menu_id.add(cursor.getString(0));
                menu_name.add(cursor.getString(1));
                menu_value.add(cursor.getString(2));

            }
        }
    }

       
}