package com.ep.jyq.mmphoto.bean;

import java.io.Serializable;

/**
 * Created by Joy on 2015/8/28.
 */
public class Cityinfo implements Serializable {

        private String id;
        private String city_name;

        Cityinfo() {

        }

        public Cityinfo(String city_name, String id) {
            this.city_name = city_name;
            this.id = id;

        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        @Override
        public String toString() {
            return city_name;
        }
}
