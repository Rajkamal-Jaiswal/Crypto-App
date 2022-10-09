package com.e.cellpaycrypto.apimodels;

public class ModelAssets {

    public class Spc {
        public String user_id;
        public String total_spc;

        public Spc(String user_id, String total_spc) {
            this.user_id = user_id;
            this.total_spc = total_spc;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getTotal_spc() {
            return total_spc;
        }

        public void setTotal_spc(String total_spc) {
            this.total_spc = total_spc;
        }
    }


    public class Root {
        public String resultCode;
        public String message;
        public Spc spc;

    }
}
