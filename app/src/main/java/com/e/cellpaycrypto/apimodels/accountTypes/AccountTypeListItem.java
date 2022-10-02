package com.e.cellpaycrypto.apimodels.accountTypes;

import com.google.gson.annotations.SerializedName;

public class AccountTypeListItem {

    @SerializedName("account_type")
    private String accountType;

    @SerializedName("id")
    private String id;

    public String getAccountType() {
        return accountType;
    }

    public String getId() {
        return id;
    }
}