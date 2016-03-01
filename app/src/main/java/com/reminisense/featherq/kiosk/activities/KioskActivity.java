package com.reminisense.featherq.kiosk.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.Profile;
import com.reminisense.featherq.kiosk.BuildConfig;
import com.reminisense.featherq.kiosk.R;
import com.reminisense.featherq.kiosk.managers.CacheManager;
import com.reminisense.featherq.kiosk.models.api.BusinessDetail;
import com.reminisense.featherq.kiosk.models.api.PriorityNumber;
import com.reminisense.featherq.kiosk.models.api.UserInfo;
import com.reminisense.featherq.kiosk.models.api.businessdetail.Service;
import com.reminisense.featherq.kiosk.utils.FeaqEndpoint;
import com.reminisense.featherq.kiosk.utils.MenuLinearLayoutManager;
import com.reminisense.featherq.kiosk.utils.RestClient;
import com.reminisense.featherq.kiosk.views.adapters.ServiceAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KioskActivity extends AppCompatActivity {

    private FeaqEndpoint apiService;
    private Context context;
    private BusinessDetail businessDetail;
    private List<Service> serviceList = new ArrayList<>();

    @Bind(R.id.recyclerServices) RecyclerView recyclerServices;
    @Bind(R.id.edtName) EditText name;
    @Bind(R.id.edtPhone) EditText phone;
    @Bind(R.id.edtEmail) EditText email;
    @Bind(R.id.btnGetNumber) AppCompatButton getNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kiosk);
        ButterKnife.bind(this);

        context = KioskActivity.this;

        // Setup the layout manager for items
        recyclerServices.setHasFixedSize(false);
        MenuLinearLayoutManager layoutManager =
                new MenuLinearLayoutManager(context, MenuLinearLayoutManager.VERTICAL, false);
        recyclerServices.setLayoutManager(layoutManager);

        getNumber.setOnClickListener(new GetNumberListener());
        name.addTextChangedListener(new InputTextListener());
        phone.addTextChangedListener(new InputTextListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        apiService = new RestClient().getApiService();
        getBusinessDetails();
    }

    @Override
    protected void onResume() {
        super.onResume();
        CacheManager.storeSelectedService(context, null);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);}
    }

    private void getBusinessDetails() {
        // get business details, such as services/data
        // check where user is loggedin
        Profile profile = Profile.getCurrentProfile();
        UserInfo userInfo = CacheManager.retrieveUserInfo(KioskActivity.this);

        Call<BusinessDetail> call;
        if (userInfo != null) {
            call = apiService.getBusinessDetails(String.valueOf(userInfo.getUser().getUserId()));
        } else if (profile != null) {
            call = apiService.getBusinessDetailsFacebook(profile.getId());
        } else {
            Log.e("ERROR", "Unable to detect logged in user!");
            return;
        }

        call.enqueue(new Callback<BusinessDetail>() {
            @Override
            public void onResponse(Call<BusinessDetail> call, Response<BusinessDetail> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        businessDetail = response.body();
                        retrieveServices(businessDetail);
                    } else {
                        Toast.makeText(KioskActivity.this, "Error encountered, no details found", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(KioskActivity.this, "Error connecting to server", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<BusinessDetail> call, Throwable t) {
                Toast.makeText(KioskActivity.this, "Error connecting to server", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void getPriorityNumber(int serviceId, final String name, final String phone, final String email) {
        Call<PriorityNumber> call = apiService.getPriorityNumber(serviceId, name, phone, email);

        call.enqueue(new Callback<PriorityNumber>() {
            @Override
            public void onResponse(Call<PriorityNumber> call, Response<PriorityNumber> response) {
                if ( response.code() == 200 ) {
                    PriorityNumber number = response.body();
                    if ( number != null ) {
                        // success! handle the new number/service/etc details
                        // also open new intent
                        Intent intent = new Intent();
                        intent.setClassName(BuildConfig.APPLICATION_ID,
                                BuildConfig.APPLICATION_ID + ".activities.PriorityNumberActivity");
                        intent.putExtra("user_name", name);
                        intent.putExtra("priority_number", number.getNumberAssigned());
                        intent.putExtra("service_name", CacheManager.retrieveSelectedService(context).getName());

                        clearInputs();
                        startActivity(intent);
                    } else {
                        Toast.makeText(context, "Error getting data from the server, please try again", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "Error connecting to the server, please try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PriorityNumber> call, Throwable t) {
                Toast.makeText(context, "Error connecting to the server, please try again", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    private void retrieveServices(BusinessDetail businessDetail) {
        serviceList = businessDetail.getServices();
        ServiceAdapter adapter = new ServiceAdapter(serviceList, context);
        recyclerServices.setAdapter(adapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //Handle the back button
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClassName(BuildConfig.APPLICATION_ID,
                    BuildConfig.APPLICATION_ID + ".activities.ConfirmExitActivity");
            startActivity(intent);
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }

    public void setGetNumberActive(boolean isActive) {
        if ( isActive ) {
            getNumber.setEnabled(true);

            ColorStateList colorStateList = ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.colorPrimaryDark));
            getNumber.setSupportBackgroundTintList(colorStateList);
        } else {
            getNumber.setEnabled(false);

            ColorStateList colorStateList = ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.caption_grey));
            getNumber.setSupportBackgroundTintList(colorStateList);
        }

    }

    public void clearInputs() {
        email.setText("");
        email.clearFocus();

        phone.setText("");
        phone.clearFocus();

        name.setText("");
        name.clearFocus();
    }

    public class InputTextListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {}

        @Override
        public void afterTextChanged(Editable editable) {
            // check if edit texts have inputs
            // if yes, enable button
            if ( !name.getText().toString().isEmpty()
                && !phone.getText().toString().isEmpty() ) {
                // name and phone have inputs!
                // thus, enable get number button
                setGetNumberActive(true);
            } else {
                // one or both fields are empty!
                // thus, disable number button
                setGetNumberActive(false);
            }
        }
    }

    public class GetNumberListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Service service = CacheManager.retrieveSelectedService(context);

            if ( service == null ) {
                Toast.makeText(KioskActivity.this, "Please select a service from the list first", Toast.LENGTH_LONG).show();
            } else {
                // success! do things to give user a number here
                /*Toast.makeText(KioskActivity.this, "You selected " + service.getServiceId() + "!", Toast.LENGTH_SHORT).show();*/
                String nameData = name.getText().toString();
                String phoneData = phone.getText().toString();
                String emailData = email.getText().toString();

                getPriorityNumber(service.getServiceId(), nameData, phoneData, emailData);
            }
        }
    }
}
