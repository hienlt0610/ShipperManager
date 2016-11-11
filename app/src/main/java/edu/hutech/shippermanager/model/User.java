package edu.hutech.shippermanager.model;

/**
 * Created by hienl on 11/9/2016.
 */

public class User {
    private String userID;
    private String email;
    private String fullName;

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
