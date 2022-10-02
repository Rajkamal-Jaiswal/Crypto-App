package com.e.cellpaycrypto.apimodels.coinList;

import com.google.gson.annotations.SerializedName;

public class CoinListItem {

    @SerializedName("overview")
    private String overview;

    @SerializedName("cost")
    private String cost;

    @SerializedName("logo_1")
    private String logo1;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    public String getOverview() {
        return overview;
    }

    public String getCost() {
        return cost;
    }

    public String getLogo1() {
        return logo1;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}