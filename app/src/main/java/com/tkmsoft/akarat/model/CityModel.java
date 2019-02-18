package com.tkmsoft.akarat.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CityModel {
    /**
     * status : {"type":"success","title":"المدن "}
     * data : {"cities":[{"id":1,"name":"القاهره","created_at":"2018-07-11 16:31:47","updated_at":"2018-07-16 16:51:28","disricts":[{"id":3,"name":"الحى الاول","city_id":"1","created_at":"2018-07-11 16:37:30","updated_at":"2018-07-16 11:56:32"}],"akars":[{"id":2,"code":"7Wg4OUVK57","name":"patient","type_id":"0","category_id":"2","city_id":"1","disrict_id":"3","rooms":"3","bathrooms":"3","garage":"1","area":"5555","price":"5525","address":"dnndbdb","about":"eeeeeeeeee","lat":"55555555555555","long":"55555555555","approve":"1","condition":"1","getOrder":"0","member_id":"0","created_at":"2018-07-18 18:49:57","updated_at":"2018-07-18 18:49:57"},{"id":3,"code":"UCRSyATW16","name":"9","type_id":"1","category_id":"2","city_id":"1","disrict_id":"3","rooms":"3","bathrooms":"3","garage":"0","area":"200","price":"3000","address":"مدينة العبوررر","about":null,"lat":"1504774371","long":"-1693413832","approve":"1","condition":"0","getOrder":"1","member_id":"5","created_at":"2018-07-19 08:32:16","updated_at":"2018-07-19 08:32:16"},{"id":4,"code":"cYTGBaJy39","name":"933","type_id":"1","category_id":"1","city_id":"1","disrict_id":"3","rooms":"3","bathrooms":"3","garage":"0","area":"200","price":"201055","address":"الزهور","about":null,"lat":"1504774371","long":"-1693413832","approve":"1","condition":"0","getOrder":"1","member_id":"5","created_at":"2018-07-19 08:34:39","updated_at":"2018-07-19 08:34:39"},{"id":5,"code":"8D5hgas841","name":"villa1","type_id":"0","category_id":"8","city_id":"1","disrict_id":"3","rooms":"5","bathrooms":"5","garage":"0","area":"2000","price":"2000000","address":"الزهور,,,,,","about":"dtyry5","lat":"153581795","long":"-279019293","approve":"1","condition":"1","getOrder":"0","member_id":"0","created_at":"2018-07-19 08:39:41","updated_at":"2018-07-19 08:39:41"},{"id":7,"code":"7RWZkuaV05","name":"edd","type_id":"0","category_id":"2","city_id":"1","disrict_id":"3","rooms":"2","bathrooms":"2","garage":"0","area":"522","price":"52352252525","address":"yyththhhhhhhh","about":"fffffffffffffffffffff","lat":"1504774371","long":"-1693413832","approve":"1","condition":"0","getOrder":"0","member_id":"5","created_at":"2018-07-19 09:03:05","updated_at":"2018-07-19 09:03:49"},{"id":8,"code":"ECzQPYlA23","name":"بيت مميز","type_id":"1","category_id":"1","city_id":"1","disrict_id":"3","rooms":"7","bathrooms":"8","garage":"0","area":"1000","price":"1000000","address":"شارع بريدة","about":"بيت يتميز بأنه على 3 نواصي واماه حديقة كبيرة","lat":"153581795","long":"-279019293","approve":"1","condition":"1","getOrder":"0","member_id":"0","created_at":"2018-07-20 12:10:23","updated_at":"2018-07-20 12:11:05"},{"id":9,"code":"q8etkT0j05","name":"مستور","type_id":"1","category_id":"1","city_id":"1","disrict_id":"3","rooms":"4","bathrooms":"9","garage":"1","area":"1000","price":"800000","address":"الجامععة","about":"عقار للبيع ,,,,,,,,,","lat":"153581795","long":"-279019293","approve":"1","condition":"1","getOrder":"0","member_id":"0","created_at":"2018-07-21 05:42:05","updated_at":"2018-07-21 05:42:05"}]},{"id":2,"name":"الاسكندريه","created_at":"2018-07-11 16:31:47","updated_at":"2018-07-16 16:51:34","disricts":[{"id":4,"name":"الحى الثانى","city_id":"2","created_at":"2018-07-11 16:37:30","updated_at":"2018-07-16 11:56:38"}],"akars":[{"id":1,"code":"sOX0pjuc05","name":"getOrder","type_id":"0","category_id":"1","city_id":"2","disrict_id":"4","rooms":"2","bathrooms":"4","garage":"0","area":"5555","price":"5525","address":"eeeeeeeeee","about":null,"lat":"555555555555","long":"8888888888888","approve":"1","condition":"0","getOrder":"1","member_id":"2","created_at":"2018-07-18 18:45:05","updated_at":"2018-07-18 18:45:05"},{"id":6,"code":"W4vkueXG26","name":"3qar1","type_id":"1","category_id":"2","city_id":"2","disrict_id":"4","rooms":"3","bathrooms":"3","garage":"0","area":"200","price":"4556455","address":"jghjjkhjkhjkhjhjhjh","about":"hbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb","lat":"1504774371","long":"-1693413832","approve":"1","condition":"0","getOrder":"0","member_id":"5","created_at":"2018-07-19 09:02:26","updated_at":"2018-07-19 09:03:57"}]}]}
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
         * title : المدن
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
        private ArrayList<CitiesBean> cities;

        public ArrayList<CitiesBean> getCities() {
            return cities;
        }

        public void setCities(ArrayList<CitiesBean> cities) {
            this.cities = cities;
        }

        public static class CitiesBean {
            /**
             * id : 1
             * name : القاهره
             * created_at : 2018-07-11 16:31:47
             * updated_at : 2018-07-16 16:51:28
             * disricts : [{"id":3,"name":"الحى الاول","city_id":"1","created_at":"2018-07-11 16:37:30","updated_at":"2018-07-16 11:56:32"}]
             * akars : [{"id":2,"code":"7Wg4OUVK57","name":"patient","type_id":"0","category_id":"2","city_id":"1","disrict_id":"3","rooms":"3","bathrooms":"3","garage":"1","area":"5555","price":"5525","address":"dnndbdb","about":"eeeeeeeeee","lat":"55555555555555","long":"55555555555","approve":"1","condition":"1","getOrder":"0","member_id":"0","created_at":"2018-07-18 18:49:57","updated_at":"2018-07-18 18:49:57"},{"id":3,"code":"UCRSyATW16","name":"9","type_id":"1","category_id":"2","city_id":"1","disrict_id":"3","rooms":"3","bathrooms":"3","garage":"0","area":"200","price":"3000","address":"مدينة العبوررر","about":null,"lat":"1504774371","long":"-1693413832","approve":"1","condition":"0","getOrder":"1","member_id":"5","created_at":"2018-07-19 08:32:16","updated_at":"2018-07-19 08:32:16"},{"id":4,"code":"cYTGBaJy39","name":"933","type_id":"1","category_id":"1","city_id":"1","disrict_id":"3","rooms":"3","bathrooms":"3","garage":"0","area":"200","price":"201055","address":"الزهور","about":null,"lat":"1504774371","long":"-1693413832","approve":"1","condition":"0","getOrder":"1","member_id":"5","created_at":"2018-07-19 08:34:39","updated_at":"2018-07-19 08:34:39"},{"id":5,"code":"8D5hgas841","name":"villa1","type_id":"0","category_id":"8","city_id":"1","disrict_id":"3","rooms":"5","bathrooms":"5","garage":"0","area":"2000","price":"2000000","address":"الزهور,,,,,","about":"dtyry5","lat":"153581795","long":"-279019293","approve":"1","condition":"1","getOrder":"0","member_id":"0","created_at":"2018-07-19 08:39:41","updated_at":"2018-07-19 08:39:41"},{"id":7,"code":"7RWZkuaV05","name":"edd","type_id":"0","category_id":"2","city_id":"1","disrict_id":"3","rooms":"2","bathrooms":"2","garage":"0","area":"522","price":"52352252525","address":"yyththhhhhhhh","about":"fffffffffffffffffffff","lat":"1504774371","long":"-1693413832","approve":"1","condition":"0","getOrder":"0","member_id":"5","created_at":"2018-07-19 09:03:05","updated_at":"2018-07-19 09:03:49"},{"id":8,"code":"ECzQPYlA23","name":"بيت مميز","type_id":"1","category_id":"1","city_id":"1","disrict_id":"3","rooms":"7","bathrooms":"8","garage":"0","area":"1000","price":"1000000","address":"شارع بريدة","about":"بيت يتميز بأنه على 3 نواصي واماه حديقة كبيرة","lat":"153581795","long":"-279019293","approve":"1","condition":"1","getOrder":"0","member_id":"0","created_at":"2018-07-20 12:10:23","updated_at":"2018-07-20 12:11:05"},{"id":9,"code":"q8etkT0j05","name":"مستور","type_id":"1","category_id":"1","city_id":"1","disrict_id":"3","rooms":"4","bathrooms":"9","garage":"1","area":"1000","price":"800000","address":"الجامععة","about":"عقار للبيع ,,,,,,,,,","lat":"153581795","long":"-279019293","approve":"1","condition":"1","getOrder":"0","member_id":"0","created_at":"2018-07-21 05:42:05","updated_at":"2018-07-21 05:42:05"}]
             */

            private int id;
            private String name;
            private String created_at;
            private String updated_at;
            private ArrayList<DisrictsBean> disricts;
            private ArrayList<AkarsBean> akars;

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

            public ArrayList<DisrictsBean> getDisricts() {
                return disricts;
            }

            public void setDisricts(ArrayList<DisrictsBean> disricts) {
                this.disricts = disricts;
            }

            public List<AkarsBean> getAkars() {
                return akars;
            }

            public void setAkars(ArrayList<AkarsBean> akars) {
                this.akars = akars;
            }

            public static class DisrictsBean {
                /**
                 * id : 3
                 * name : الحى الاول
                 * city_id : 1
                 * created_at : 2018-07-11 16:37:30
                 * updated_at : 2018-07-16 11:56:32
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

            public static class AkarsBean {
                /**
                 * id : 2
                 * code : 7Wg4OUVK57
                 * name : patient
                 * type_id : 0
                 * category_id : 2
                 * city_id : 1
                 * disrict_id : 3
                 * rooms : 3
                 * bathrooms : 3
                 * garage : 1
                 * area : 5555
                 * price : 5525
                 * address : dnndbdb
                 * about : eeeeeeeeee
                 * lat : 55555555555555
                 * long : 55555555555
                 * approve : 1
                 * condition : 1
                 * getOrder : 0
                 * member_id : 0
                 * created_at : 2018-07-18 18:49:57
                 * updated_at : 2018-07-18 18:49:57
                 */

                private int id;
                private String code;
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
                private String about;
                private String lat;
                @SerializedName("long")
                private String longX;
                private String approve;
                private String condition;
                private String order;
                private String member_id;
                private String created_at;
                private String updated_at;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

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

                public String getAbout() {
                    return about;
                }

                public void setAbout(String about) {
                    this.about = about;
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

                public String getApprove() {
                    return approve;
                }

                public void setApprove(String approve) {
                    this.approve = approve;
                }

                public String getCondition() {
                    return condition;
                }

                public void setCondition(String condition) {
                    this.condition = condition;
                }

                public String getOrder() {
                    return order;
                }

                public void setOrder(String order) {
                    this.order = order;
                }

                public String getMember_id() {
                    return member_id;
                }

                public void setMember_id(String member_id) {
                    this.member_id = member_id;
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
