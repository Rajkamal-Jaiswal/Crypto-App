package com.e.cellpaycrypto.apimodels.showPaymentOptions;

public class ModelPaymentShow {
    public class Bankdata {
        public String account_holder_name;
        public String account_number;
        public String ifsc_code;
        public String account_type;
        public String bank_name;
        public String branch_name;

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

        public String getAccount_type() {
            return account_type;
        }

        public void setAccount_type(String account_type) {
            this.account_type = account_type;
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
    }

    public class PaymentDeatil {
        public String upi_name;
        public String upi_id;
        public String paytm_no;
        public String phonepe_no;
        public String qr_code;

        public String getUpi_name() {
            return upi_name;
        }

        public void setUpi_name(String upi_name) {
            this.upi_name = upi_name;
        }

        public String getUpi_id() {
            return upi_id;
        }

        public void setUpi_id(String upi_id) {
            this.upi_id = upi_id;
        }

        public String getPaytm_no() {
            return paytm_no;
        }

        public void setPaytm_no(String paytm_no) {
            this.paytm_no = paytm_no;
        }

        public String getPhonepe_no() {
            return phonepe_no;
        }

        public void setPhonepe_no(String phonepe_no) {
            this.phonepe_no = phonepe_no;
        }

        public String getQr_code() {
            return qr_code;
        }

        public void setQr_code(String qr_code) {
            this.qr_code = qr_code;
        }
    }

    public class Root {
        public String resultCode;
        public String message;
        public PaymentDeatil paymentDeatil;
        public Bankdata bankdata;

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public PaymentDeatil getPaymentDeatil() {
            return paymentDeatil;
        }

        public void setPaymentDeatil(PaymentDeatil paymentDeatil) {
            this.paymentDeatil = paymentDeatil;
        }

        public Bankdata getBankdata() {
            return bankdata;
        }

        public void setBankdata(Bankdata bankdata) {
            this.bankdata = bankdata;
        }
    }

}
