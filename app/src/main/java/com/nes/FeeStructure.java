package com.nes;

import java.util.ArrayList;

public class FeeStructure {

    public static ArrayList<FeesBase> calculate(String tot_fee[], String due_fee[]){


        ArrayList<FeesBase> feeDispList = new ArrayList<>();
        ArrayList<Fee> totfeelist = new ArrayList<>();
        ArrayList<Fee> duefeelist = new ArrayList<>();
        for (int i = 0; i < tot_fee.length; i++) {
            String[] fee = tot_fee[i].split(":");
            Fee fees = new Fee();
            fees.setFee_name(fee[0]);
            fees.setFee_amt(fee[1]);
            System.out.println(fees);
            totfeelist.add(fees);
        }
        System.out.println("Due Fee :  " +due_fee[0]);
        if (due_fee != null && !due_fee[0].equals("All Fees are Paid")) {
            System.out.println("DueFees");
            for (int i = 0; i < due_fee.length; i++) {
                String[] fee = due_fee[i].split(":");
                Fee feesdue = new Fee();
                feesdue.setFee_name(fee[0]);
                feesdue.setFee_amt(fee[1]);
                System.out.println(feesdue);
                duefeelist.add(feesdue);
            }
        } else {
            System.out.println("DueFees");
            Fee fees = new Fee();
            fees.setFee_name("all paid");
            fees.setFee_amt("nil");
            duefeelist.add(fees);
        }

//        if (duefeelist.get(0).getFee_name().equals("all paid")) {
//            for (int i = 0; i < totfeelist.size(); i++) {
//                FeesBase fd = new FeesBase();
//                fd.setFees_name(totfeelist.get(i).getFee_name());
//                fd.setTotal_fees(totfeelist.get(i).getFee_amt());
//                fd.setDue_fees("0.0");
//                fd.setStatus("Paid");
//                feeDispList.add(fd);
//            }
//        } else {
            for (int i = 0; i < totfeelist.size(); i++) {
                FeesBase fd = new FeesBase();
                for (int j = 0; j < duefeelist.size(); j++) {
                    if (totfeelist.get(i).getFee_name().equals(duefeelist.get(j).getFee_name())) {
                        fd.setFees_name(totfeelist.get(i).getFee_name());
                        fd.setTotal_fees(totfeelist.get(i).getFee_amt());
                        fd.setDue_fees(duefeelist.get(j).getFee_amt());
                        fd.setStatus("Pending");
                        break;

                    } else {
                        fd.setFees_name(totfeelist.get(i).getFee_name());
                        fd.setTotal_fees(totfeelist.get(i).getFee_amt());
                        fd.setDue_fees("0.0");
                        fd.setStatus("Paid");
                    }
                }
                feeDispList.add(fd);
            }
//        }
        return feeDispList;


    }

}
