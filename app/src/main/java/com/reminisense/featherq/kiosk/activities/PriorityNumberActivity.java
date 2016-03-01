package com.reminisense.featherq.kiosk.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.reminisense.featherq.kiosk.R;

import butterknife.Bind;
import butterknife.ButterKnife;

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

        }
    }

    private class CloseClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

}
