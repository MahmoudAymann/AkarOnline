package com.tkmsoft.akarat.model;

public class RegisterModel {


    /**
     * status : {"type":"success","title":"تم التسجيل بنجاح"}
     * data : {"api_token":"bc7RNXBAUjRpmwEGu6EmT0hQNQhN5uQYJgMi7mv6TIWFh71zxexGKYIWzQAc"}
     */

    private StatusBean status;
    private DataBean data;

    public StatusBean getStatus() {
        return status;
    }

    public void setStatus(StatusBean status) {
        this.status = status;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class StatusBean {
        /**
         * type : success
         * title : تم التسجيل بنجاح
         */

        private String type;
        private String title;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class DataBean {
        /**
         * api_token : bc7RNXBAUjRpmwEGu6EmT0hQNQhN5uQYJgMi7mv6TIWFh71zxexGKYIWzQAc
         */

        private String api_token;

        public String getApi_token() {
            return api_token;
        }

        public void setApi_token(String api_token) {
            this.api_token = api_token;
        }
    }
}
