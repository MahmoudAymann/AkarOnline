package com.tkmsoft.akarat.model;

import java.util.List;

public class UserInfModel {
    /**
     * status : {"type":"success","title":"user info"}
     * data : {"user_info":{"id":5,"name":"esmaillll","username":"esmaillllmpuq821","phone":"01113639837527","approve":"1","email":"esmailllll@yahoo.com","avatar":"default.jpg","created_at":"2018-07-18 10:54:21","updated_at":"2018-07-18 11:32:07","akars":[]}}
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
         * title : user info
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
         * user_info : {"id":5,"name":"esmaillll","username":"esmaillllmpuq821","phone":"01113639837527","approve":"1","email":"esmailllll@yahoo.com","avatar":"default.jpg","created_at":"2018-07-18 10:54:21","updated_at":"2018-07-18 11:32:07","akars":[]}
         */

        private UserInfoBean user_info;

        public UserInfoBean getUser_info() {
            return user_info;
        }

        public void setUser_info(UserInfoBean user_info) {
            this.user_info = user_info;
        }

        public static class UserInfoBean {
            /**
             * id : 5
             * name : esmaillll
             * username : esmaillllmpuq821
             * phone : 01113639837527
             * approve : 1
             * email : esmailllll@yahoo.com
             * avatar : default.jpg
             * created_at : 2018-07-18 10:54:21
             * updated_at : 2018-07-18 11:32:07
             * akars : []
             */

            private int id;
            private String name;
            private String username;
            private String phone;
            private String approve;
            private String email;
            private String avatar;
            private String created_at;
            private String updated_at;
            private List<?> akars;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getApprove() {
                return approve;
            }

            public void setApprove(String approve) {
                this.approve = approve;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public List<?> getAkars() {
                return akars;
            }

            public void setAkars(List<?> akars) {
                this.akars = akars;
            }
        }
    }
}
