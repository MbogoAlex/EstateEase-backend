package com.propertymanagement.PropertyManagement.entity;

import com.propertymanagement.PropertyManagement.config.Constants;
import lombok.Data;

@Data
public class STKPushPayload {
    private Integer paymentCode;
    private String paymentOption;
    private String serviceCode;
    private String msisdn;
    private String accountNumber;
    private String partnerCallbackUrl;
    private double amount;
    private String partnerReferenceID;
    private String narration;


    public STKPushPayload(String phoneNumber, double amount, String uniqueId) {
        this.paymentCode = Constants.PAYMENT_CODE;
        this.paymentOption = Constants.PAYMENT_OPTION;
        this.serviceCode = Constants.C2B_SERVICE_CODE;
        this.msisdn = phoneNumber;
        this.accountNumber = Constants.ACCOUNT_NUMBER;
        this.partnerCallbackUrl = Constants.PARTNER_CALLBACK_URL + "/api/handleCallback";
        this.amount = amount;
        this.partnerReferenceID = uniqueId;
        this.narration = Constants.NARRATION;
    }
}
