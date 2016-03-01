package com.reminisense.featherq.kiosk.managers;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.reminisense.featherq.kiosk.R;
import com.reminisense.featherq.kiosk.models.api.BusinessDetail;
import com.reminisense.featherq.kiosk.models.api.UserInfo;
import com.reminisense.featherq.kiosk.models.api.businessdetail.Service;

/**
 * Created by RUFFY on 29 Feb 2016.
 */
public class CacheManager {

    private static final String KEY_USER_INFO = "userInfo";
    private static final String KEY_BUSINESS_DETAILS = "businessDetails";
    private static final String KEY_SELECTED_SERVICE = "selectedService";
    private static final String KEY_SELECTED_SERVICE_ID = "selectedServiceId";

    public static void storeUserInfo(Context context, UserInfo userInfo) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(userInfo);
        editor.putString(KEY_USER_INFO, json);

        editor.apply();
    }

    public static UserInfo retrieveUserInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_USER_INFO, null);
        if ( json != null ) {
            UserInfo userInfo = gson.fromJson(json, UserInfo.class);
            return userInfo;
        } else {
            return null;
        }
    }

    public static void storeBusinessDetails(Context context, BusinessDetail businessDetail) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(businessDetail);
        editor.putString(KEY_BUSINESS_DETAILS, json);

        editor.apply();
    }

    public static BusinessDetail retrieveBusinessDetails(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_BUSINESS_DETAILS, null);
        if ( json != null ) {
            BusinessDetail businessDetail = gson.fromJson(json, BusinessDetail.class);
            return businessDetail;
        } else {
            return null;
        }
    }

    public static void storeSelectedService(Context context, Service service) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(service);
        editor.putString(KEY_SELECTED_SERVICE, json);

        editor.apply();
    }

    public static Service retrieveSelectedService(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_key), Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(KEY_SELECTED_SERVICE, null);
        if ( json != null ) {
            Service service = gson.fromJson(json, Service.class);
            return service;
        } else {
            return null;
        }
    }

    public static void storeSelectedServiceId(Context context, int selectedServiceId) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_key), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_SELECTED_SERVICE_ID, selectedServiceId);

        editor.apply();
    }

    public static int retrieveSelectedServiceId(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.preference_key), Context.MODE_PRIVATE);
        return sharedPreferences.getInt(KEY_SELECTED_SERVICE_ID, 0);
    }
}