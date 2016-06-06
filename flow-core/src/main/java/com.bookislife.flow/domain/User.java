package com.bookislife.flow.domain;

/**
 * Created by SidneyXu on 2016/06/04.
 */
public class User extends BaseEntity {

    private String username;
    private String password;
    private String email;
    private boolean emailVerfied;
    private String phone;
    private boolean phoneVerifid;
    private String sessionToken;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerfied() {
        return emailVerfied;
    }

    public void setEmailVerfied(boolean emailVerfied) {
        this.emailVerfied = emailVerfied;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isPhoneVerifid() {
        return phoneVerifid;
    }

    public void setPhoneVerifid(boolean phoneVerifid) {
        this.phoneVerifid = phoneVerifid;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
