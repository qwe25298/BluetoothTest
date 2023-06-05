package com.example.bluetoothtest;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;


public class boundedList extends AppCompatActivity {

    private static final int DANGEROUS_RESULT_CODE = 1;


    @RequiresApi(api = Build.VERSION_CODES.S)
    @SuppressLint({"HardwareIds", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bounded_list);


        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.BLUETOOTH_CONNECT},
//                DANGEROUS_RESULT_CODE);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.BLUETOOTH_ADMIN},
                DANGEROUS_RESULT_CODE);



        ArrayList<String> list = null;
        if (adapter != null) {

            list = new ArrayList<>();

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "NOT GRANTED2");
                return;
            } else {
                Log.d(TAG, "GRANTED");
            }
            Log.d(TAG, "working");
            String name = adapter.getName();
            Log.d(TAG, "working");
            Log.d(TAG, name);

            Set<BluetoothDevice> pairedDevices = adapter.getBondedDevices();


            if (pairedDevices.size() > 0) {
                for (BluetoothDevice device : pairedDevices) {

                    list.add(device.getName() + "  -  " + device.getAddress());
                }
            }
        }

        ListView myList = findViewById(R.id.List);

        ArrayAdapter<String> ad = new ArrayAdapter<>(boundedList.this, android.R.layout.simple_list_item_1, list);

        myList.setAdapter(ad);


    }

    public void goList_2(View view) {
        Intent intent = new Intent(this, findDev.class);
        startActivity(intent);


    }


}
