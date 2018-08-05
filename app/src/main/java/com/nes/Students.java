package com.nes;

public class Students extends ConnectionStatus{

    private String GRNo;
    private String StdName;
    private String newAdFlag;
    private String bg;
    private String dob;
    private String doj;
    private String dor;
    private String gen;
    private String secName;
    private String clsName;

    @Override
    public String toString() {
        return "Students{" +
                "GRNo='" + GRNo + '\'' +
                ", StdName='" + StdName + '\'' +
                ", newAdFlag='" + newAdFlag + '\'' +
                ", bg='" + bg + '\'' +
                ", dob='" + dob + '\'' +
                ", doj='" + doj + '\'' +
                ", dor='" + dor + '\'' +
                ", gen='" + gen + '\'' +
                ", secName='" + secName + '\'' +
                ", clsName='" + clsName + '\'' +
                '}';
    }

    public String getGRNo() {
        return GRNo;
    }

    public void setGRNo(String GRNo) {
        this.GRNo = GRNo;
    }

    public String getStdName() {
        return StdName;
    }

    public void setStdName(String stdName) {
        StdName = stdName;
    }

    public String getNewAdFlag() {
        return newAdFlag;
    }

    public void setNewAdFlag(String newAdFlag) {
        this.newAdFlag = newAdFlag;
    }

    public String getBg() {
        return bg;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoj() {
        return doj;
    }

    public void setDoj(String doj) {
        this.doj = doj;
    }

    public String getDor() {
        return dor;
    }

    public void setDor(String dor) {
        this.dor = dor;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getClsName() {
        return clsName;
    }

    public void setClsName(String clsName) {
        this.clsName = clsName;
    }
}
