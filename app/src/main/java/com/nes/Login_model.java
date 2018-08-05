package com.nes;

public class Login_model extends ConnectionStatus{

    private String status;
    private String username;
    private String userType;
    private String userid;
    private String dadMob;
    private String momMob;
    private String dadEmail;
    private String momEmail;
    private String parId;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDadMob() {
        return dadMob;
    }

    public void setDadMob(String dadMob) {
        this.dadMob = dadMob;
    }

    public String getMomMob() {
        return momMob;
    }

    public void setMomMob(String momMob) {
        this.momMob = momMob;
    }

    public String getDadEmail() {
        return dadEmail;
    }

    public void setDadEmail(String dadEmail) {
        this.dadEmail = dadEmail;
    }

    public String getMomEmail() {
        return momEmail;
    }

    public void setMomEmail(String momEmail) {
        this.momEmail = momEmail;
    }

    public String getParId() {
        return parId;
    }

    public void setParId(String parId) {
        this.parId = parId;
    }

    @Override
    public String toString() {
        return "Login_model{" +
                "username='" + username + '\'' +
                ", userType='" + userType + '\'' +
                ", userid='" + userid + '\'' +
                ", dadMob='" + dadMob + '\'' +
                ", momMob='" + momMob + '\'' +
                ", dadEmail='" + dadEmail + '\'' +
                ", momEmail='" + momEmail + '\'' +
                ", parId='" + parId + '\'' +
                '}';
    }
}
