package com.nes;

import java.io.Serializable;

public class FeesBase implements Serializable{

    private String fees_name;
    private String total_fees;
    private String due_fees;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFees_name() {
        return fees_name;
    }

    public void setFees_name(String fees_name) {
        this.fees_name = fees_name;
    }

    public String getTotal_fees() {
        return total_fees;
    }

    public void setTotal_fees(String total_fees) {
        this.total_fees = total_fees;
    }

    public String getDue_fees() {
        return due_fees;
    }

    public void setDue_fees(String due_fees) {
        this.due_fees = due_fees;
    }
}
