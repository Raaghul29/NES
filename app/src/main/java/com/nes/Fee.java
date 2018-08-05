package com.nes;

public class Fee {

    private String fee_name;
    private String fee_amt;

    @Override
    public String toString() {
        return "Fee{" +
                "fee_name='" + fee_name + '\'' +
                ", fee_amt='" + fee_amt + '\'' +
                '}';
    }

    public String getFee_name() {
        return fee_name;
    }

    public void setFee_name(String fee_name) {
        this.fee_name = fee_name;
    }

    public String getFee_amt() {
        return fee_amt;
    }

    public void setFee_amt(String fee_amt) {
        this.fee_amt = fee_amt;
    }
}
