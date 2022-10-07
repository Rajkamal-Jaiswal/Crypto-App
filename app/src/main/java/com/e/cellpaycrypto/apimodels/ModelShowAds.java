package com.e.cellpaycrypto.apimodels;

import java.util.ArrayList;

public class ModelShowAds {
    public static class DepositList {
        public String id;
        public String coin_cost;
        public String qty;
        public String total_cost;
        public String status;
        public String created_date;

        public DepositList(String id, String coin_cost, String qty, String total_cost, String status, String created_date) {
            this.id = id;
            this.coin_cost = coin_cost;
            this.qty = qty;
            this.total_cost = total_cost;
            this.status = status;
            this.created_date = created_date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCoin_cost() {
            return coin_cost;
        }

        public void setCoin_cost(String coin_cost) {
            this.coin_cost = coin_cost;
        }

        public String getQty() {
            return qty;
        }

        public void setQty(String qty) {
            this.qty = qty;
        }

        public String getTotal_cost() {
            return total_cost;
        }

        public void setTotal_cost(String total_cost) {
            this.total_cost = total_cost;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreated_date() {
            return created_date;
        }

        public void setCreated_date(String created_date) {
            this.created_date = created_date;
        }
    }

    public class Root {
        public String resultCode;
        public String message;
        public ArrayList<DepositList> deposit_list;
    }
}
