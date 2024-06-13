package com.propertymanagement.PropertyManagement.config;

public class Constants {
    public static final String AUTH_URL = "http://172.105.90.112:8080/paymentexpress/v1/client/users/authenticate";
    public static final String USERNAME = "ikoaqua-mpesa-user";
    public static final String PASSWORD = "F5Hm5CNDg0kG";
    public static final String C2B_URL = "http://172.105.90.112:8080/paymentexpress/v1/paymentrequest/initiate";
    public static final int PAYMENT_CODE = 174379;
    public static final String PAYMENT_OPTION = "MPESA";
    public static final String C2B_SERVICE_CODE = "SITEMANAGER-COLLECTIONS";
    public static final String ACCOUNT_NUMBER = "TestAccount";

    public static final String PARTNER_CALLBACK_URL = "https://67be-102-166-31-2.ngrok-free.app";
    //public static final String PARTNER_CALLBACK_URL = "https://302b-197-136-108-11.ngrok-free.app";
    public static final String NARRATION = "Making Test Payment";

    private Constants() {}
}
