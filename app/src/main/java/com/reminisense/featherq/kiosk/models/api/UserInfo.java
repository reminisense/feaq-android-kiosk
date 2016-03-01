package com.reminisense.featherq.kiosk.models.api;

import com.google.gson.annotations.SerializedName;
import com.reminisense.featherq.kiosk.models.api.userinfo.User;

/**
 * Created by ruffyheredia on 16/02/2016.
 */
public class UserInfo {

    private int success;
    private User user;

    @SerializedName("access_token")
    private String accessToken;

    /**
     *
     * @return
     * The success
     */
    public int getSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(int success) {
        this.success = success;
    }

    /**
     *
     * @return
     * The user
     */
    public User getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     *
     * @param accessToken
     * The access_token
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
