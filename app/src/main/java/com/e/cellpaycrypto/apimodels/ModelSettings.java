package com.e.cellpaycrypto.apimodels;

public class ModelSettings {

    public class Root{
        public String resultCode;
        public String message;
        public Settings settings;
    }

    public static class Settings{
        public String coin_today_cost;
        public String min_deposit_coin;
        public String min_deposit_coin_online;
        public String withdrawal_limit;
        public String p2p_sell_limit;
        public String trade_limit;
        public String admin_fee_onsell;
        public String account_holder_name;
        public String account_number;
        public String ifsc_code;
        public String bank_name;
        public String branch_name;
        public String upi_id;
        public String binance_id;


        public String getCoin_today_cost() {
            return coin_today_cost;
        }

        public void setCoin_today_cost(String coin_today_cost) {
            this.coin_today_cost = coin_today_cost;
        }

        public String getMin_deposit_coin() {
            return min_deposit_coin;
        }

        public void setMin_deposit_coin(String min_deposit_coin) {
            this.min_deposit_coin = min_deposit_coin;
        }

        public String getMin_deposit_coin_online() {
            return min_deposit_coin_online;
        }

        public void setMin_deposit_coin_online(String min_deposit_coin_online) {
            this.min_deposit_coin_online = min_deposit_coin_online;
        }

        public String getWithdrawal_limit() {
            return withdrawal_limit;
        }

        public void setWithdrawal_limit(String withdrawal_limit) {
            this.withdrawal_limit = withdrawal_limit;
        }

        public String getP2p_sell_limit() {
            return p2p_sell_limit;
        }

        public void setP2p_sell_limit(String p2p_sell_limit) {
            this.p2p_sell_limit = p2p_sell_limit;
        }

        public String getTrade_limit() {
            return trade_limit;
        }

        public void setTrade_limit(String trade_limit) {
            this.trade_limit = trade_limit;
        }

        public String getAdmin_fee_onsell() {
            return admin_fee_onsell;
        }

        public void setAdmin_fee_onsell(String admin_fee_onsell) {
            this.admin_fee_onsell = admin_fee_onsell;
        }

        public String getAccount_holder_name() {
            return account_holder_name;
        }

        public void setAccount_holder_name(String account_holder_name) {
            this.account_holder_name = account_holder_name;
        }

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
        }

        public String getIfsc_code() {
            return ifsc_code;
        }

        public void setIfsc_code(String ifsc_code) {
            this.ifsc_code = ifsc_code;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBranch_name() {
            return branch_name;
        }

        public void setBranch_name(String branch_name) {
            this.branch_name = branch_name;
        }

        public String getUpi_id() {
            return upi_id;
        }

        public void setUpi_id(String upi_id) {
            this.upi_id = upi_id;
        }

        public String getBinance_id() {
            return binance_id;
        }

        public void setBinance_id(String binance_id) {
            this.binance_id = binance_id;
        }

        public Settings(String coin_today_cost, String min_deposit_coin, String min_deposit_coin_online, String withdrawal_limit, String p2p_sell_limit, String trade_limit, String admin_fee_onsell, String account_holder_name, String account_number, String ifsc_code, String bank_name, String branch_name, String upi_id, String binance_id) {
            this.coin_today_cost = coin_today_cost;
            this.min_deposit_coin = min_deposit_coin;
            this.min_deposit_coin_online = min_deposit_coin_online;
            this.withdrawal_limit = withdrawal_limit;
            this.p2p_sell_limit = p2p_sell_limit;
            this.trade_limit = trade_limit;
            this.admin_fee_onsell = admin_fee_onsell;
            this.account_holder_name = account_holder_name;
            this.account_number = account_number;
            this.ifsc_code = ifsc_code;
            this.bank_name = bank_name;
            this.branch_name = branch_name;
            this.upi_id = upi_id;
            this.binance_id = binance_id;
        }
    }
}
