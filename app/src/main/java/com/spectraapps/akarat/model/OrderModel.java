package com.spectraapps.akarat.model;

import com.google.gson.annotations.SerializedName;

public class OrderModel {

    /**
     * status : {"type":"success","title":"تم اضافه البيانات بنجاح بنتظار موافقه الادمن"}
     * data : {"order_info":{"name":"test36","type_id":"0","category_id":"1","city_id":"1","disrict_id":"1","rooms":"5","bathrooms":"2","garage":"0","area":"0987","price":"55525","address":"asdadasd","condition":"1","lat":"30.04422","long":"31.653225","code":"xbpRxeIa38","order":1,"approve":1,"member_id":27,"updated_at":"2018-10-06 10:24:38","created_at":"2018-10-06 10:24:38","id":34,"type":"ايجار","category_name":"البيوت","city_name":"جدة","disrict_name":"الشاطيء","category":{"id":1,"name":"البيوت","photo":"1.jpg","created_at":"2018-08-27 14:41:28","updated_at":"2018-08-28 11:31:38"},"city":{"id":1,"name":"جدة","created_at":"2018-08-27 13:16:40","updated_at":"2018-09-05 06:36:31"},"disrict":{"id":1,"name":"الشاطيء","city_id":"1","created_at":"2018-08-27 13:16:51","updated_at":"2018-09-05 06:37:33"}}}
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
         * title : تم اضافه البيانات بنجاح بنتظار موافقه الادمن
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
         * order_info : {"name":"test36","type_id":"0","category_id":"1","city_id":"1","disrict_id":"1","rooms":"5","bathrooms":"2","garage":"0","area":"0987","price":"55525","address":"asdadasd","condition":"1","lat":"30.04422","long":"31.653225","code":"xbpRxeIa38","order":1,"approve":1,"member_id":27,"updated_at":"2018-10-06 10:24:38","created_at":"2018-10-06 10:24:38","id":34,"type":"ايجار","category_name":"البيوت","city_name":"جدة","disrict_name":"الشاطيء","category":{"id":1,"name":"البيوت","photo":"1.jpg","created_at":"2018-08-27 14:41:28","updated_at":"2018-08-28 11:31:38"},"city":{"id":1,"name":"جدة","created_at":"2018-08-27 13:16:40","updated_at":"2018-09-05 06:36:31"},"disrict":{"id":1,"name":"الشاطيء","city_id":"1","created_at":"2018-08-27 13:16:51","updated_at":"2018-09-05 06:37:33"}}
         */

        private OrderInfoBean order_info;

        public OrderInfoBean getOrder_info() {
            return order_info;
        }

        public void setOrder_info(OrderInfoBean order_info) {
            this.order_info = order_info;
        }

        public static class OrderInfoBean {
            /**
             * name : test36
             * type_id : 0
             * category_id : 1
             * city_id : 1
             * disrict_id : 1
             * rooms : 5
             * bathrooms : 2
             * garage : 0
             * area : 0987
             * price : 55525
             * address : asdadasd
             * condition : 1
             * lat : 30.04422
             * long : 31.653225
             * code : xbpRxeIa38
             * order : 1
             * approve : 1
             * member_id : 27
             * updated_at : 2018-10-06 10:24:38
             * created_at : 2018-10-06 10:24:38
             * id : 34
             * type : ايجار
             * category_name : البيوت
             * city_name : جدة
             * disrict_name : الشاطيء
             * category : {"id":1,"name":"البيوت","photo":"1.jpg","created_at":"2018-08-27 14:41:28","updated_at":"2018-08-28 11:31:38"}
             * city : {"id":1,"name":"جدة","created_at":"2018-08-27 13:16:40","updated_at":"2018-09-05 06:36:31"}
             * disrict : {"id":1,"name":"الشاطيء","city_id":"1","created_at":"2018-08-27 13:16:51","updated_at":"2018-09-05 06:37:33"}
             */

            private String name;
            private String type_id;
            private String category_id;
            private String city_id;
            private String disrict_id;
            private String rooms;
            private String bathrooms;
            private String garage;
            private String area;
            private String price;
            private String address;
            private String condition;
            private String lat;
            @SerializedName("long")
            private String longX;
            private String code;
            private int order;
            private int approve;
            private int member_id;
            private String updated_at;
            private String created_at;
            private int id;
            private String type;
            private String category_name;
            private String city_name;
            private String disrict_name;
            private CategoryBean category;
            private CityBean city;
            private DisrictBean disrict;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getType_id() {
                return type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getCategory_id() {
                return category_id;
            }

            public void setCategory_id(String category_id) {
                this.category_id = category_id;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getDisrict_id() {
                return disrict_id;
            }

            public void setDisrict_id(String disrict_id) {
                this.disrict_id = disrict_id;
            }

            public String getRooms() {
                return rooms;
            }

            public void setRooms(String rooms) {
                this.rooms = rooms;
            }

            public String getBathrooms() {
                return bathrooms;
            }

            public void setBathrooms(String bathrooms) {
                this.bathrooms = bathrooms;
            }

            public String getGarage() {
                return garage;
            }

            public void setGarage(String garage) {
                this.garage = garage;
            }

            public String getArea() {
                return area;
            }

            public void setArea(String area) {
                this.area = area;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getCondition() {
                return condition;
            }

            public void setCondition(String condition) {
                this.condition = condition;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }

            public String getLongX() {
                return longX;
            }

            public void setLongX(String longX) {
                this.longX = longX;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public int getApprove() {
                return approve;
            }

            public void setApprove(int approve) {
                this.approve = approve;
            }

            public int getMember_id() {
                return member_id;
            }

            public void setMember_id(int member_id) {
                this.member_id = member_id;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCategory_name() {
                return category_name;
            }

            public void setCategory_name(String category_name) {
                this.category_name = category_name;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public String getDisrict_name() {
                return disrict_name;
            }

            public void setDisrict_name(String disrict_name) {
                this.disrict_name = disrict_name;
            }

            public CategoryBean getCategory() {
                return category;
            }

            public void setCategory(CategoryBean category) {
                this.category = category;
            }

            public CityBean getCity() {
                return city;
            }

            public void setCity(CityBean city) {
                this.city = city;
            }

            public DisrictBean getDisrict() {
                return disrict;
            }

            public void setDisrict(DisrictBean disrict) {
                this.disrict = disrict;
            }

            public static class CategoryBean {
                /**
                 * id : 1
                 * name : البيوت
                 * photo : 1.jpg
                 * created_at : 2018-08-27 14:41:28
                 * updated_at : 2018-08-28 11:31:38
                 */

                private int id;
                private String name;
                private String photo;
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

                public String getPhoto() {
                    return photo;
                }

                public void setPhoto(String photo) {
                    this.photo = photo;
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

            public static class CityBean {
                /**
                 * id : 1
                 * name : جدة
                 * created_at : 2018-08-27 13:16:40
                 * updated_at : 2018-09-05 06:36:31
                 */

                private int id;
                private String name;
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

            public static class DisrictBean {
                /**
                 * id : 1
                 * name : الشاطيء
                 * city_id : 1
                 * created_at : 2018-08-27 13:16:51
                 * updated_at : 2018-09-05 06:37:33
                 */

                private int id;
                private String name;
                private String city_id;
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

                public String getCity_id() {
                    return city_id;
                }

                public void setCity_id(String city_id) {
                    this.city_id = city_id;
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
}
