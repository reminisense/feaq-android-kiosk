package com.reminisense.featherq.kiosk.activities;

import android.content.Context;
import android.os.Bundle;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.reminisense.featherq.kiosk.R;
import com.reminisense.featherq.kiosk.adapters.PrintNumberAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Nico on 3/1/2016.
 */
public class PriorityNumberActivity extends AppCompatActivity {

    @Bind(R.id.lblUserName) TextView userName;
    @Bind(R.id.lblPriorityNumber) TextView priorityNumber;
    @Bind(R.id.lblServiceName) TextView serviceName;
    @Bind(R.id.btnPrint) Button print;
    @Bind(R.id.btnClose) Button close;

    private String userNameData;
    private String priorityNumberData;
    private String serviceNameData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_priority_number);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        userNameData = bundle.getString("user_name");
        priorityNumberData = bundle.getString("priority_number");
        serviceNameData = bundle.getString("service_name");

    }

    @Override
    protected void onStart() {
        super.onStart();

        userName.setText(userNameData);
        priorityNumber.setText(priorityNumberData);
        serviceName.setText(serviceNameData);

        print.setOnClickListener(new PrintClickListener());
        close.setOnClickListener(new CloseClickListener());
    }

    private class PrintClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            // Get a PrintManager instance
            PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);

            // Set job name, which will be displayed in the print queue
            String jobName = "Print FeatherQ Kiosk Ticket";

            // Start a print job, passing in a PrintDocumentAdapter implementation
            // to handle the generation of a print document
            printManager.print(jobName, new PrintNumberAdapter(context, priorityNumberData, serviceNameData), null);
        }
    }

    private class CloseClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

}
