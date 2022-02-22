/**
 * author@ Alex_M_Paul
 * file - Service app VehicleService
 */

package com.example.vehicleservice.Service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.widget.Toast;

import com.example.vehicleservice.Data.DBHelper;

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

     /**
     * @brief  implementation of AIDL functions
     */

        @Override
        public int menuClick(String id, int value) throws RemoteException {
            return displayMode(id,value);
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




    /**
     * @param id : menu name
     * @param value : value of menu to be stored
     * @return int : value based on the random number
     * @brief Method used to generate random number and then return a int value based on the random number
     */

    public int displayMode(String id, int value){

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


    /**
     * @return string : vehicle model name
     * @brief Method used to read the vehicle model and return the same to HMI
     */

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


    /**
     * @param id : Menu name
     * @param value : default value of menu to be stored in database
     * @brief Method used to update the default value of respective menu when its clicked
     */

    public void updateValue(String id, int value){
        myDB.updateSettings(id,value);
    }


    /**
     * @param value : return value from displayMode()
     * @brief Method used to store the return value in database
     * @brief its done to maintain the previous state of HlOn and HlOff menu when app is restarted
     */

    public void updateDisplayVal(int value){
        myDB.updateDisplay(value);
    }


    /**
     * @param column : column name in database
     * @param value : value to be stored
     * @brief Method used to update the values like target value & pressure unit of control in database
     */

    public void updateControlValue(String column, int value){
        myDB.updateControl(column,value);
    }


    /**
     * @return int : current target value from database
     * @brief Method used to get the target value from database to show in keypad in HMI
     */

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


    /**
     *
     * @param menu : menu name
     * @return int : default values of menu
     * @brief Method used to get the default value of settings menu from database,
     * @brief values are used to maintain previous state of each menu when app is started
     */

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


    /**
     * @return int : value from database
     * @brief Method is used to return the value from column display in settings table from database
     * @brief This value is used to maintain prev state of HlOn and HlOff menu when app is started
     */

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