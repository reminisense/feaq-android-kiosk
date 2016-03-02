package com.reminisense.featherq.kiosk.print.bean;

/**
 * Data holder for queue details printing.
 *
 * @author Nico 3/2/2016
 */
public class QueueDetails {
    private String assignedNumber;
    private String serviceName;
    private String userName;
    private String email;
    private String contactNumber;

    public String getAssignedNumber() {
        return assignedNumber;
    }

    public void setAssignedNumber(String assignedNumber) {
        this.assignedNumber = assignedNumber;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
