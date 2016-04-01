package com.reminisense.featherq.kiosk.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.reminisense.featherq.kiosk.R;
import com.reminisense.featherq.kiosk.handlers.BluetoothHandler;
import com.reminisense.featherq.kiosk.models.api.PriorityNumber;
import com.reminisense.featherq.kiosk.print.adapters.PrintNumberAdapter;
import com.reminisense.featherq.kiosk.print.bean.QueueDetails;
import com.zj.btsdk.BluetoothService;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Nico on 3/1/2016.
 */
public class PriorityNumberActivity extends AppCompatActivity {

    @Bind(R.id.lblUserName)
    TextView userName;
    @Bind(R.id.lblPriorityNumber)
    TextView priorityNumber;
    @Bind(R.id.lblServiceName)
    TextView serviceName;
    @Bind(R.id.btnPrint)
    Button print;
    @Bind(R.id.btnClose)
    Button close;
    @Bind(R.id.layoutKioskRightContainer)
    View layoutKioskRightContainer;

    private String userNameData;
    private String priorityNumberData;
    private String serviceNameData;
    private String businessNameData;
    // BT SDK service
    BluetoothService mService = null;
    BluetoothDevice btPrinter = null;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    private static final String TAG = "PriorityNumberActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority_number);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        userNameData = bundle.getString("user_name");
        priorityNumberData = bundle.getString("priority_number");
        serviceNameData = bundle.getString("service_name");
        businessNameData = bundle.getString("business_name");

        mService = new BluetoothService(this, new BluetoothHandler());
        if (!mService.isAvailable()) {
            Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        userName.setText(userNameData);
        priorityNumber.setText(priorityNumberData);
        serviceName.setText(serviceNameData);

        print.setOnClickListener(new PrintClickListener());
        close.setOnClickListener(new CloseClickListener());

        // ask user to open BT
        if (!mService.isBTopen()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }

    private class PrintClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Context context = view.getContext();

            if (mService.getState() != BluetoothService.STATE_CONNECTED) {
                Log.d(TAG, "Connecting to bluetooth...");
                // start activity to connect to bluetooth
                Intent serverIntent = new Intent(context, DeviceListActivity.class);
                startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
            } else {
                doPrint();
            }


            // Get a PrintManager instance
//            PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
//
//            // Set job name, which will be displayed in the print queue
//            String jobName = "Print FeatherQ Kiosk Ticket";
//
//            // Start a print job, passing in a PrintDocumentAdapter implementation
//            // to handle the generation of a print document
//            QueueDetails qd = new QueueDetails();
//            qd.setAssignedNumber(priorityNumberData);
//            qd.setServiceName(serviceNameData);
//            qd.setUserName(userNameData);
//            qd.setBusinessName(businessNameData);
//
//            printManager.print(jobName, new PrintNumberAdapter(context, layoutKioskRightContainer, qd), null);
        }
    }


    public void doPrint() {
        Log.d(TAG, "Printing...");
        StringBuilder sb = new StringBuilder();
        byte[] cmd = new byte[3];
        cmd[0] = 0x1b;
        cmd[1] = 0x21;
        cmd[2] |= 0x10;
        mService.write(cmd);
        mService.sendMessage("Congratulations!\n", "GBK");
        cmd[2] &= 0xEF;
        mService.write(cmd);

        sb.append("Business: " + businessNameData + "\n\n");
        sb.append("Service: " + serviceNameData + "\n\n");
        sb.append("Name: " + userNameData + "\n\n");
        sb.append("Priority Number: " + priorityNumberData + "\n\n");

        mService.sendMessage(sb.toString(), "GBK");

    }

    private class CloseClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            mService.stop();
            finish();
        }
    }

    /**
     * Process the result of the DeviceListActivity
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // request to open bluetooth
            case REQUEST_ENABLE_BT:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "Bluetooth has been opened!", Toast.LENGTH_LONG).show();
                } else {
                    // user not allowed to open bluetooth
                    finish();
                }
                break;
            case REQUEST_CONNECT_DEVICE:
                if (resultCode == Activity.RESULT_OK) {
                    // get MAC address of device
                    String address = data.getExtras()
                            .getString(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
                    btPrinter = mService.getDevByMac(address);

                    mService.connect(btPrinter);
                    doPrint();
                }
                break;
        }
    }


}
