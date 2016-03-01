package com.reminisense.featherq.kiosk.models.api;

/**
 * Created by ruffyheredia on 16/02/2016.
 */
public class LoginEmail {

    private String email;
    private String password;

    public LoginEmail(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
