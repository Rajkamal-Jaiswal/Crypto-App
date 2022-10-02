package com.e.cellpaycrypto.apimodels.coinList;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelCoinList {

    @SerializedName("coin_list")
    private List<CoinListItem> coinList;

    @SerializedName("resultCode")
    private String resultCode;

    @SerializedName("message")
    private String message;

    public List<CoinListItem> getCoinList() {
        return coinList;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getMessage() {
        return message;
    }
}