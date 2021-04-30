package com.trimeo.creditservice.domains;

public enum CreditOperation {
    CREDIT,DEBIT;

    public static CreditOperation getDefault(){
        return DEBIT;
    }
}
