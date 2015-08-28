package com.ep.jyq.mmphoto.bean;

/**
 * Created by Joy on 2015/8/29.
 */
public class CityW {

    /**
     * weatherinfo : {"img2":"n1.gif","img1":"d1.gif","temp1":"15℃","temp2":"5℃","weather":"多云","ptime":"08:00","cityid":"101010100","city":"北京"}
     */
    private WeatherinfoEntity weatherinfo;

    public void setWeatherinfo(WeatherinfoEntity weatherinfo) {
        this.weatherinfo = weatherinfo;
    }

    public WeatherinfoEntity getWeatherinfo() {
        return weatherinfo;
    }

    public static class WeatherinfoEntity {
        /**
         * img2 : n1.gif
         * img1 : d1.gif
         * temp1 : 15℃
         * temp2 : 5℃
         * weather : 多云
         * ptime : 08:00
         * cityid : 101010100
         * city : 北京
         */
        private String img2;
        private String img1;
        private String temp1;
        private String temp2;
        private String weather;
        private String ptime;
        private String cityid;
        private String city;

        public void setImg2(String img2) {
            this.img2 = img2;
        }

        public void setImg1(String img1) {
            this.img1 = img1;
        }

        public void setTemp1(String temp1) {
            this.temp1 = temp1;
        }

        public void setTemp2(String temp2) {
            this.temp2 = temp2;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public void setPtime(String ptime) {
            this.ptime = ptime;
        }

        public void setCityid(String cityid) {
            this.cityid = cityid;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getImg2() {
            return img2;
        }

        public String getImg1() {
            return img1;
        }

        public String getTemp1() {
            return temp1;
        }

        public String getTemp2() {
            return temp2;
        }

        public String getWeather() {
            return weather;
        }

        public String getPtime() {
            return ptime;
        }

        public String getCityid() {
            return cityid;
        }

        public String getCity() {
            return city;
        }
    }
}
