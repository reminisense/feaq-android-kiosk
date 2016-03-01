package com.reminisense.featherq.kiosk.models.api.businessdetail;

import com.google.gson.annotations.SerializedName;

/**
 * Created by RUFFY on 29 Feb 2016.
 */
public class Service {

    @SerializedName("service_id")
    private int serviceId;

    @SerializedName("branch_id")
    private int branchId;

    @SerializedName("business_id")
    private int businessId;

    private String name;

    @SerializedName("people_in_queue")
    private int peopleInQueue;

    @SerializedName("next_available_number")
    private int nextAvailableNumber;

    /**
     *
     * @return
     * The serviceId
     */
    public int getServiceId() {
        return serviceId;
    }

    /**
     *
     * @param serviceId
     * The service_id
     */
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    /**
     *
     * @return
     * The branchId
     */
    public int getBranchId() {
        return branchId;
    }

    /**
     *
     * @param branchId
     * The branch_id
     */
    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The peopleInQueue
     */
    public int getPeopleInQueue() {
        return peopleInQueue;
    }

    /**
     *
     * @param peopleInQueue
     * The people_in_queue
     */
    public void setPeopleInQueue(int peopleInQueue) {
        this.peopleInQueue = peopleInQueue;
    }

    /**
     *
     * @return
     * The nextAvailableNumber
     */
    public int getNextAvailableNumber() {
        return nextAvailableNumber;
    }

    /**
     *
     * @param nextAvailableNumber
     * The next_available_number
     */
    public void setNextAvailableNumber(int nextAvailableNumber) {
        this.nextAvailableNumber = nextAvailableNumber;
    }
    
}
