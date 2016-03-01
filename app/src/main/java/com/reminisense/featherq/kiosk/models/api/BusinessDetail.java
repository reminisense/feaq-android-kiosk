package com.reminisense.featherq.kiosk.models.api;

import com.google.gson.annotations.SerializedName;
import com.reminisense.featherq.kiosk.models.api.businessdetail.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RUFFY on 29 Feb 2016.
 */
public class BusinessDetail {

    @SerializedName("business_id")
    private int businessId;

    @SerializedName("business_name")
    private String businessName;

    @SerializedName("estimated_time")
    private int estimatedTime;

    private List<Service> services = new ArrayList<Service>();

    /**
     *
     * @return
     * The businessId
     */
    public int getBusinessId() {
        return businessId;
    }

    /**
     *
     * @param businessId
     * The business_id
     */
    public void setBusinessId(int businessId) {
        this.businessId = businessId;
    }

    /**
     *
     * @return
     * The businessName
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     *
     * @param businessName
     * The business_name
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    /**
     *
     * @return
     * The estimatedTime
     */
    public int getEstimatedTime() {
        return estimatedTime;
    }

    /**
     *
     * @param estimatedTime
     * The estimated_time
     */
    public void setEstimatedTime(int estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    /**
     *
     * @return
     * The services
     */
    public List<Service> getServices() {
        return services;
    }

    /**
     *
     * @param services
     * The services
     */
    public void setServices(List<Service> services) {
        this.services = services;
    }
    
}
