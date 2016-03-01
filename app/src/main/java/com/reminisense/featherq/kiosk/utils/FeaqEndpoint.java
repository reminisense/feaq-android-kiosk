package com.reminisense.featherq.kiosk.utils;

import com.reminisense.featherq.kiosk.models.api.BusinessDetail;
import com.reminisense.featherq.kiosk.models.api.LoginEmail;
import com.reminisense.featherq.kiosk.models.api.PriorityNumber;
import com.reminisense.featherq.kiosk.models.api.UserInfo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by RUFFY on 29 Feb 2016.
 */
public interface FeaqEndpoint {

    @POST("/mobile/email-login")
    Call<UserInfo> loginByEmail(@Body LoginEmail login);

    @GET("/rest/business-details-by-user/{user_id}")
    Call<BusinessDetail> getBusinessDetails(@Path("user_id") String userId);

    @GET("/rest/business-details-by-facebook/{facebook_id}")
    Call<BusinessDetail> getBusinessDetailsFacebook(@Path("facebook_id") String facebookId);

    @GET("/rest/queue-number/{service_id}/{name}/{phone}/{email}")
    Call<PriorityNumber> getPriorityNumber(@Path("service_id") int serviceId,
                                           @Path("name") String name,
                                           @Path("phone") String phone,
                                           @Path("email") String email);
}
