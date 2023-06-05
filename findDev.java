package com.example.bluetoothtest;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class findDev extends AppCompatActivity {

    private static final int DANGEROUS_RESULT_CODE = 1;

    private ListView listDevices;

    private Button scanButton;

    private ArrayAdapter<String> arrayAdapter;
    ArrayList<String> stringArrayList = new ArrayList<>();
    BluetoothAdapter myAdapter = BluetoothAdapter.getDefaultAdapter();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint({"ResourceType", "MissingInflatedId"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_dev);

        listDevices = findViewById(R.id.myList);
        scanButton = findViewById(R.id.butt);


            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.BLUETOOTH_SCAN},
                    DANGEROUS_RESULT_CODE);



        int requestCode = 1;
        Intent discoverableIntent =
                new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivityForResult(discoverableIntent, requestCode);

//        ActivityCompat.requestPermissions(this,
//                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
//                DANGEROUS_RESULT_CODE);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                DANGEROUS_RESULT_CODE);


        scanButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(findDev.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "NOT GRANTED1");
                    return;

                }

                    if (myAdapter.isDiscovering()) {
                        myAdapter.cancelDiscovery();
                    }
                    myAdapter.startDiscovery();

                    if (myAdapter.isDiscovering()) {
                        Log.d(TAG, "is discovering");
                    } else {
                        Log.d(TAG, "isnt discovering");
                    }

                    IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                    registerReceiver(myReciever, intentFilter);

                }

        });

             arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, stringArrayList);
             listDevices.setAdapter(arrayAdapter);




    }

    BroadcastReceiver myReciever = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (ActivityCompat.checkSelfPermission(findDev.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {

                    return;
                }
                if (device.getName() != null) {
                    stringArrayList.add(device.getName());
                    arrayAdapter.notifyDataSetChanged();
                }
            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(myReciever);
    }

}





