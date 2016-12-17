package com.edonica.somfy;

import static org.junit.Assert.*;

public class ProtocolTest {

    static String username = "XXXXXXXXXXXXXX";
    static String password = "XXXXXXXXXXXX";

    @org.junit.Test
    public void login() throws Exception {

        Protocol p = new Protocol();
        p.login(username,password);
        System.out.println("We got somewhere :)");
    }

    @org.junit.Test
    public void getSetup() throws Exception {

        Protocol p = new Protocol();
        p.login(username,password);
        p.getSetup();
        System.out.println("We got somewhere :)");
    }

    @org.junit.Test
    public void openOrCloseWindow() throws Exception {

        Protocol p = new Protocol();
        p.login(username,password);
        p.getSetup();
        p.closeWindow();
        System.out.println("We got somewhere :)");
    }

}