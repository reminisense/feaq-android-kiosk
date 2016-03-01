package com.reminisense.featherq.kiosk.models.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RUFFY on 1 Mar 2016.
 */
public class PriorityNumber {

    @SerializedName("number_assigned")
    private String numberAssigned;

    public String getNumberAssigned() {
        return numberAssigned;
    }

    public void setNumberAssigned(String numberAssigned) {
        this.numberAssigned = numberAssigned;
    }
}
