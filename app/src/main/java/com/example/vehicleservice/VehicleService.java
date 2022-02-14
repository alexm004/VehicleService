/**
 * author@ Alex_M_Paul
 * file - Service app VehicleService
 */

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
import java.util.Random;

import ServicePackage.aidlInterface;

public class VehicleService extends Service {

    DBHelper myDB;

    int display,val,target,value,displayVal;
    String vehicleModel;

    public VehicleService() {

        myDB = new DBHelper(this);

    }


    @Override
    public IBinder onBind(Intent intent) {
        return stubObject;
    }


    aidlInterface.Stub stubObject = new aidlInterface.Stub() {

//  below functions are AIDL functions used for connecting HMI and service
//  all these functions call separate functions for working

//  menuClick() is works when 'display mode manual' menu is clicked

        @Override
        public int menuClick(String id, int value) throws RemoteException {
            return displaymode(id,value);
        }

//  vehicleModel() is used to return the vehicle model to HMI after reading it from file in internal storage

        @Override
        public String vehicleModel() throws RemoteException {
            return readVehicleModel();
        }

//  updateValues() is used to update the values of settings menu in database when they are clicked

        @Override
        public void updateValues(String id, int value) throws RemoteException {
           updateValue(id,value);
        }

//  updateControl() is used to update the values like target value & pressure unit of control in database when they are clicked

        @Override
        public void updateControl(String column, int value) throws RemoteException {
            updateControlValue(column,value);

        }

//  getTarget() is used to return the target value from database to control fragment in HMI

        @Override
        public int getTarget() throws RemoteException {
            return targetValue();
        }

//  getValue() is used to return values of settings menu from database to HMI

        @Override
        public int getValue(String menu) throws RemoteException {
            return menuValue(menu);
        }

        @Override
        public int getDisplay() throws RemoteException {
            return getDisplayValue();
        }

        @Override
        public void updateDisplay(int value) throws RemoteException {
            updateDisplayVal(value);
        }
    };


//  Below are separate FUNCTIONS used by aidl Functions

//  This function is used to generate a random number when display mode manual menu is clicked in settings HMI
//  based on the random number variable 'display' is initialized with -1/0/1
//  variable is then returned to HMI for enabling or disabling the HlOn and HlOff menu in settings

    public int displaymode(String id,int value){

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (id.equals("Display Mode Manual") && value ==1){
                        Random random = new Random();
                        val = random.nextInt(20-0)+0;
                        if (val>0 && val%2==0){
                            display = 1;
                        }else if (val>0 && val%2!=0){
                            display = 0;
                        }
                    }
                    else if (id.equals("Display Mode Manual") && value ==0){
                        updateValue(id,value);
                    }
                }
            },5000);

        return display;
    }


//  This function reads the model name from file in internal storage of the device
//  it is then returned to HMI when called

    private String readVehicleModel() {

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
        return vehicleModel;
    }


//  This function is used to update the default values of settings menu in database
//  its called in aidl function updateValues()

    public void updateValue(String id, int value){
        myDB.updateSettings(id,value);
    }

    public void updateDisplayVal(int value){
        myDB.updateDisplay(value);
    }


//  this function is used to update the values like target value & pressure unit of control in database when they are clicked
//  its called in aidl function updateControl()

    public void updateControlValue(String column, int value){
        myDB.updateControl(column,value);
    }


//  This function is used to get the target value from database to show in control fragment in HMI
//  its called in aidl function getTarget()

    public int targetValue(){
        Cursor cursor = myDB.getControlValue();
        int index = 2;
        if (cursor.getCount()== 0){
            Toast.makeText(this,"No data",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext()){
               target = Integer.parseInt(cursor.getString(index));
            }
        }
        return target;
    }


//  This function is used to get the default value of settings menu from database to show in settings fragment in HMI
//  its called in aidl function getValue()

    public int menuValue(String menu){
        Cursor cursor = myDB.getValue(menu);
        if (cursor.getCount()== 0){
            Toast.makeText(this,"No data",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext()){
                value = Integer.parseInt(cursor.getString(0));
            }
        }
        return value;
    }

    public int getDisplayValue(){
        Cursor cursor = myDB.getDisplay();
        int index = 3;
        if (cursor.getCount()== 0){
            Toast.makeText(this,"No data",Toast.LENGTH_LONG).show();
        }
        else {
            while (cursor.moveToNext()){
               displayVal  = Integer.parseInt(cursor.getString(index));
            }
        }
        return displayVal;
    }

       
}