package com.reminisense.featherq.kiosk.handlers;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.reminisense.featherq.kiosk.FeatherQKiosk;
import com.zj.btsdk.BluetoothService;

/**
 * Handles bluetooth connection.
 * Created by Nigs on 2016-04-01.
 */
public class BluetoothHandler extends Handler {


    private static final String TAG = "BluetoothHandler";
    @Override
    public void handleMessage(Message msg) {
        // based on the status of our connection do the following...
        switch (msg.what) {
            case BluetoothService.MESSAGE_STATE_CHANGE:
                switch (msg.arg1) {
                    case BluetoothService.STATE_CONNECTED:
                        Toast.makeText(FeatherQKiosk.getContext(), "Connect successful",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case BluetoothService.STATE_CONNECTING:
                        Log.d(TAG, "Connecting...");
                        break;
                    case BluetoothService.STATE_LISTEN:
                    case BluetoothService.STATE_NONE:
                        Log.d(TAG, "Waiting for a connection...");
                        break;
                }
                break;
            case BluetoothService.MESSAGE_CONNECTION_LOST:
                Toast.makeText(FeatherQKiosk.getContext(), "Device connection was lost",
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Device connection was lost!");
                break;
            case BluetoothService.MESSAGE_UNABLE_CONNECT:
                Toast.makeText(FeatherQKiosk.getContext(), "Unable to connect device",
                        Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Unable to connect to device!");
                break;
        }
    }
}
