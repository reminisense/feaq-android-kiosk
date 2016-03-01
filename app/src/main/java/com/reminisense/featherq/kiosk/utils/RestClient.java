package com.reminisense.featherq.kiosk.utils;

import com.reminisense.featherq.kiosk.managers.UrlManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ruffyheredia on 11/02/2016.
 */
public class RestClient {

    private static final String BASE_URL = UrlManager.BASE_URL;
    private FeaqEndpoint apiService;

    public RestClient() {
//        OkHttpClient client = new OkHttpClient();
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        client.interceptors().add(interceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
//                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(FeaqEndpoint.class);
    }

    public FeaqEndpoint getApiService() {
        return apiService;
    }

}
