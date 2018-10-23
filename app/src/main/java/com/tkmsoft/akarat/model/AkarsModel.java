package com.tkmsoft.akarat.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AkarsModel {

    /**
     * status : {"type":"success","title":"الاقسام "}
     * data : {"categories":[{"id":1,"name":"البيوت","photo":"http://3qaronline.net/storage/uploads/cats/1.jpg","created_at":"2018-08-27 14:41:28","updated_at":"2018-08-28 11:31:38","akars":[{"id":2,"code":"8AzGId3Q12","name":"kjhgf","type_id":"0","category_id":"1","city_id":"1","disrict_id":"1","rooms":"2","bathrooms":"1","garage":"0","area":"6532","price":"464523","address":"likugjyfhtdrsev","about":"khjghfds \r\njhgfds\r\nkuytrsewas","secret":"pugfdwvgxxjg\r\n.kjhgfd\r\noizs","lat":"31.29823590296569","long":"31.530048734403522","approve":"1","condition":"1","order":"0","member_id":"0","created_at":"2018-08-28 07:44:12","updated_at":"2018-08-28 07:44:12","type":"ايجار","category_name":"البيوت","city_name":"القاهرة","disrict_name":"مدينه نصر","category":{"id":1,"name":"البيوت","photo":"1.jpg","created_at":"2018-08-27 14:41:28","updated_at":"2018-08-28 11:31:38"},"city":{"id":1,"name":"القاهرة","created_at":"2018-08-27 13:16:40","updated_at":"2018-08-27 13:16:40"},"disrict":{"id":1,"name":"مدينه نصر","city_id":"1","created_at":"2018-08-27 13:16:51","updated_at":"2018-08-27 13:16:51"},"images":[{"id":1,"name":"http://3qaronline.net/storage/uploads/akar/1535442252_question-mark-filled.png","akar_id":"2","created_at":"2018-08-28 07:44:12","updated_at":"2018-08-28 07:44:12"},{"id":2,"name":"http://3qaronline.net/storage/uploads/akar/1535442252_sorting-options.png","akar_id":"2","created_at":"2018-08-28 07:44:12","updated_at":"2018-08-28 07:44:12"},{"id":3,"name":"http://3qaronline.net/storage/uploads/akar/1535442252_ask-question-filled.png","akar_id":"2","created_at":"2018-08-28 07:44:12","updated_at":"2018-08-28 07:44:12"}]}]},{"id":2,"name":"الشقق","photo":"http://3qaronline.net/storage/uploads/cats/p4.jpg","created_at":"2018-08-27 14:41:36","updated_at":"2018-08-28 11:31:31","akars":[{"id":17,"code":"toWb64sa01","name":"hhhh","type_id":"0","category_id":"2","city_id":"2","disrict_id":"2","rooms":"4","bathrooms":"3","garage":"0","area":"777","price":"700","address":"hhhhhh","about":"Hhhhhhhhhhhhhh","secret":null,"lat":"30.044281","long":"31.340002","approve":"1","condition":"1","order":"0","member_id":"5","created_at":"2018-08-30 11:26:01","updated_at":"2018-08-30 04:31:59","type":"ايجار","category_name":"الشقق","city_name":"الجيزة","disrict_name":"العبور","category":{"id":2,"name":"الشقق","photo":"p4.jpg","created_at":"2018-08-27 14:41:36","updated_at":"2018-08-28 11:31:31"},"city":{"id":2,"name":"الجيزة","created_at":"2018-08-28 09:18:41","updated_at":"2018-08-28 09:18:41"},"disrict":{"id":2,"name":"العبور","city_id":"1","created_at":"2018-08-28 08:53:38","updated_at":"2018-08-28 08:53:38"},"images":[{"id":12,"name":"http://3qaronline.net/storage/uploads/akar/1535628361_imgs[2].jpg","akar_id":"17","created_at":"2018-08-30 11:26:01","updated_at":"2018-08-30 11:26:01"},{"id":13,"name":"http://3qaronline.net/storage/uploads/akar/1535628361_imgs[1].jpg","akar_id":"17","created_at":"2018-08-30 11:26:01","updated_at":"2018-08-30 11:26:01"},{"id":14,"name":"http://3qaronline.net/storage/uploads/akar/1535628361_imgs[0].jpg","akar_id":"17","created_at":"2018-08-30 11:26:01","updated_at":"2018-08-30 11:26:01"}]}]},{"id":3,"name":"الفيلل","photo":"http://3qaronline.net/storage/uploads/cats/p6.jpg","created_at":"2018-08-27 14:41:42","updated_at":"2018-08-28 11:31:22","akars":[]}]}
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
         * title : الاقسام
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
        private ArrayList<CategoriesBean> categories;

        public ArrayList<CategoriesBean> getCategories() {
            return categories;
        }

        public void setCategories(ArrayList<CategoriesBean> categories) {
            this.categories = categories;
        }

        public static class CategoriesBean {
            /**
             * id : 1
             * name : البيوت
             * photo : http://3qaronline.net/storage/uploads/cats/1.jpg
             * created_at : 2018-08-27 14:41:28
             * updated_at : 2018-08-28 11:31:38
             * akars : [{"id":2,"code":"8AzGId3Q12","name":"kjhgf","type_id":"0","category_id":"1","city_id":"1","disrict_id":"1","rooms":"2","bathrooms":"1","garage":"0","area":"6532","price":"464523","address":"likugjyfhtdrsev","about":"khjghfds \r\njhgfds\r\nkuytrsewas","secret":"pugfdwvgxxjg\r\n.kjhgfd\r\noizs","lat":"31.29823590296569","long":"31.530048734403522","approve":"1","condition":"1","order":"0","member_id":"0","created_at":"2018-08-28 07:44:12","updated_at":"2018-08-28 07:44:12","type":"ايجار","category_name":"البيوت","city_name":"القاهرة","disrict_name":"مدينه نصر","category":{"id":1,"name":"البيوت","photo":"1.jpg","created_at":"2018-08-27 14:41:28","updated_at":"2018-08-28 11:31:38"},"city":{"id":1,"name":"القاهرة","created_at":"2018-08-27 13:16:40","updated_at":"2018-08-27 13:16:40"},"disrict":{"id":1,"name":"مدينه نصر","city_id":"1","created_at":"2018-08-27 13:16:51","updated_at":"2018-08-27 13:16:51"},"images":[{"id":1,"name":"http://3qaronline.net/storage/uploads/akar/1535442252_question-mark-filled.png","akar_id":"2","created_at":"2018-08-28 07:44:12","updated_at":"2018-08-28 07:44:12"},{"id":2,"name":"http://3qaronline.net/storage/uploads/akar/1535442252_sorting-options.png","akar_id":"2","created_at":"2018-08-28 07:44:12","updated_at":"2018-08-28 07:44:12"},{"id":3,"name":"http://3qaronline.net/storage/uploads/akar/1535442252_ask-question-filled.png","akar_id":"2","created_at":"2018-08-28 07:44:12","updated_at":"2018-08-28 07:44:12"}]}]
             */

            private int id;
            private String name;
            private String photo;
            private String created_at;
            private String updated_at;
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

            public ArrayList<AkarsBean> getAkars() {
                return akars;
            }

            public void setAkars(ArrayList<AkarsBean> akars) {
                this.akars = akars;
            }

            public static class AkarsBean {
                /**
                 * id : 2
                 * code : 8AzGId3Q12
                 * name : kjhgf
                 * type_id : 0
                 * category_id : 1
                 * city_id : 1
                 * disrict_id : 1
                 * rooms : 2
                 * bathrooms : 1
                 * garage : 0
                 * area : 6532
                 * price : 464523
                 * address : likugjyfhtdrsev
                 * about : khjghfds
                 jhgfds
                 kuytrsewas
                 * secret : pugfdwvgxxjg
                 .kjhgfd
                 oizs
                 * lat : 31.29823590296569
                 * long : 31.530048734403522
                 * approve : 1
                 * condition : 1
                 * order : 0
                 * member_id : 0
                 * created_at : 2018-08-28 07:44:12
                 * updated_at : 2018-08-28 07:44:12
                 * type : ايجار
                 * category_name : البيوت
                 * city_name : القاهرة
                 * disrict_name : مدينه نصر
                 * category : {"id":1,"name":"البيوت","photo":"1.jpg","created_at":"2018-08-27 14:41:28","updated_at":"2018-08-28 11:31:38"}
                 * city : {"id":1,"name":"القاهرة","created_at":"2018-08-27 13:16:40","updated_at":"2018-08-27 13:16:40"}
                 * disrict : {"id":1,"name":"مدينه نصر","city_id":"1","created_at":"2018-08-27 13:16:51","updated_at":"2018-08-27 13:16:51"}
                 * images : [{"id":1,"name":"http://3qaronline.net/storage/uploads/akar/1535442252_question-mark-filled.png","akar_id":"2","created_at":"2018-08-28 07:44:12","updated_at":"2018-08-28 07:44:12"},{"id":2,"name":"http://3qaronline.net/storage/uploads/akar/1535442252_sorting-options.png","akar_id":"2","created_at":"2018-08-28 07:44:12","updated_at":"2018-08-28 07:44:12"},{"id":3,"name":"http://3qaronline.net/storage/uploads/akar/1535442252_ask-question-filled.png","akar_id":"2","created_at":"2018-08-28 07:44:12","updated_at":"2018-08-28 07:44:12"}]
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
                private String secret;
                private String lat;
                @SerializedName("long")
                private String longX;
                private String approve;
                private String condition;
                private String order;
                private String member_id;
                private String created_at;
                private String updated_at;
                private String type;
                private String category_name;
                private String city_name;
                private String disrict_name;
                private CategoryBean category;
                private CityBean city;
                private DisrictBean disrict;
                private ArrayList<ImagesBean> images;

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

                public String getSecret() {
                    return secret;
                }

                public void setSecret(String secret) {
                    this.secret = secret;
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

                public ArrayList<ImagesBean> getImages() {
                    return images;
                }

                public void setImages(ArrayList<ImagesBean> images) {
                    this.images = images;
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
                     * name : القاهرة
                     * created_at : 2018-08-27 13:16:40
                     * updated_at : 2018-08-27 13:16:40
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
                     * name : مدينه نصر
                     * city_id : 1
                     * created_at : 2018-08-27 13:16:51
                     * updated_at : 2018-08-27 13:16:51
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

                public static class ImagesBean {
                    /**
                     * id : 1
                     * name : http://3qaronline.net/storage/uploads/akar/1535442252_question-mark-filled.png
                     * akar_id : 2
                     * created_at : 2018-08-28 07:44:12
                     * updated_at : 2018-08-28 07:44:12
                     */

                    private int id;
                    private String name;
                    private String akar_id;
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

                    public String getAkar_id() {
                        return akar_id;
                    }

                    public void setAkar_id(String akar_id) {
                        this.akar_id = akar_id;
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
}
