package com.tkmsoft.akarat.model;

public class ProfileModel {
    /**
     * status : {"type":"success","title":"تم تحديث البيانات الشخصيه بنجاح"}
     * data : {"user_info":{"id":10,"name":"esmail","username":"esmailxlhki06","phone":"123456789","approve":"1","email":"esmailhalal@yahoo.com","avatar":"http://3qaronline.net/storage/uploads/members/avatars/1533722324944939_1679696285637914_4507909813984788158_n.jpg","created_at":"2018-08-08 09:57:22","updated_at":"2018-08-08 10:00:06"}}
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
         * title : تم تحديث البيانات الشخصيه بنجاح
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
         * user_info : {"id":10,"name":"esmail","username":"esmailxlhki06","phone":"123456789","approve":"1","email":"esmailhalal@yahoo.com","avatar":"http://3qaronline.net/storage/uploads/members/avatars/1533722324944939_1679696285637914_4507909813984788158_n.jpg","created_at":"2018-08-08 09:57:22","updated_at":"2018-08-08 10:00:06"}
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
             * id : 10
             * name : esmail
             * username : esmailxlhki06
             * phone : 123456789
             * approve : 1
             * email : esmailhalal@yahoo.com
             * avatar : http://3qaronline.net/storage/uploads/members/avatars/1533722324944939_1679696285637914_4507909813984788158_n.jpg
             * created_at : 2018-08-08 09:57:22
             * updated_at : 2018-08-08 10:00:06
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
        }
    }
}
