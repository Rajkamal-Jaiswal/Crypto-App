package com.e.cellpaycrypto.apimodels;

import java.util.ArrayList;
import java.util.List;

public class ModelCurrency {

    public class Root {
        public String resultCode;
        public String message;
        public ArrayList<CategoryList> category_list;
    }

    public class CategoryList {
        public String id;
        public String currency;

        public CategoryList(String id, String currency) {
            this.id = id;
            this.currency = currency;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
    }
}
