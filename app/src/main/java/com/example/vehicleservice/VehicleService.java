package com.example.vehicleservice;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import ServicePackage.aidlInterface;

public class VehicleService extends Service {

    DBHelper myDB;
    ArrayList<String> menu_id,menu_name,menu_value;
    int display,val;

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
        public int calc(int n1, int n2) throws RemoteException {
            //Toast.makeText(getApplicationContext(),""+n2,Toast.LENGTH_LONG).show();
            return n1+n2;
        }

        @Override
        public int menuClick(String id, int value) throws RemoteException {
            return displaymode(id,value);
        }
    };


    public int displaymode(String id,int value){
        if (id.equals("display") && value ==1){
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    Random random = new Random();
                    val = random.nextInt(20+10)-10;
                    if (val>0 && val%2==0){
                        display = 1;
                    }else if (val>0 && val%2!=0){
                        display = 0;
                    }else if (val<0){
                        display = -1;
                    }
                }
            },5000);
            return display;
        }
        else return -1;
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