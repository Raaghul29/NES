package com.nes;

class StudentData extends ConnectionStatus{

    private String stuid;
    private String std_gen;
    private String dadEmail;
    private String stugrn;
    private String stuname;
    private String classname;
    private String fathername;
    private String fees;
    private String duefees;
    private String paidstatus;
    private String download;
    private String[] allfee;
    private String[] duefee;

    public String getStd_gen() {
        return std_gen;
    }

    public void setStd_gen(String std_gen) {
        this.std_gen = std_gen;
    }

    public String getStuid() {
        return stuid;
    }

    public void setStuid(String stuid) {
        this.stuid = stuid;
    }

    public String getDadEmail() {
        return dadEmail;
    }

    public void setDadEmail(String dadEmail) {
        this.dadEmail = dadEmail;
    }

    public String getStugrn() {
        return stugrn;
    }

    public void setStugrn(String stugrn) {
        this.stugrn = stugrn;
    }

    public String getStuname() {
        return stuname;
    }

    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getDuefees() {
        return duefees;
    }

    public void setDuefees(String duefees) {
        this.duefees = duefees;
    }

    public String getPaidstatus() {
        return paidstatus;
    }

    public void setPaidstatus(String paidstatus) {
        this.paidstatus = paidstatus;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String[] getAllfee() {
        return allfee;
    }

    public void setAllfee(String[] allfee) {
        this.allfee = allfee;
    }

    public String[] getDuefee() {
        return duefee;
    }

    public void setDuefee(String[] duefee) {
        this.duefee = duefee;
    }
}
