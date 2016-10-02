package com.edonica.monica.lambdatest1;

/**
 * Created by me on 02/10/2016.
 */
public class Request {
    String firstNameX;
    String lastName;

    public String getFirstName() {
        return firstNameX;
    }

    public void setFirstName(String firstName) {
        this.firstNameX = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Request(String firstName, String lastName) {
        this.firstNameX = firstName;
        this.lastName = lastName;
    }

    public Request() {
    }
}
