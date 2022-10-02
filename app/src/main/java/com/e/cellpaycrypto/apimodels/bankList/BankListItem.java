package com.e.cellpaycrypto.apimodels.bankList;

import com.google.gson.annotations.SerializedName;

public class BankListItem {

    @SerializedName("bank_name")
    private String bankName;

    @SerializedName("id")
    private String id;

    public String getBankName() {
        return bankName;
    }

    public String getId() {
        return id;
    }
}