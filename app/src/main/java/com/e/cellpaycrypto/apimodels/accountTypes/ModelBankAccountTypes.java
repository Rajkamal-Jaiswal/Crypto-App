package com.e.cellpaycrypto.apimodels.accountTypes;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelBankAccountTypes {

    @SerializedName("account_type_list")
    private List<AccountTypeListItem> accountTypeList;

    @SerializedName("resultCode")
    private String resultCode;

    @SerializedName("message")
    private String message;

    public List<AccountTypeListItem> getAccountTypeList() {
        return accountTypeList;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }
}