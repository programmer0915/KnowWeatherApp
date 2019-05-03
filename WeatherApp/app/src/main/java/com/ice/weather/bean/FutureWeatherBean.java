package com.ice.weather.bean;

import java.util.List;

public class FutureWeatherBean {

    /**
     * reason : 查询成功!
     * result : {"city":"杭州","realtime":{"temperature":"13","humidity":"47","info":"多云","wid":"01","direct":"东南风","power":"2级","aqi":"42"},"future":[{"date":"2019-03-31","temperature":"8/14℃","weather":"多云转晴","wid":{"day":"01","night":"00"},"direct":"东北风"},{"date":"2019-04-01","temperature":"9/18℃","weather":"晴转多云","wid":{"day":"00","night":"01"},"direct":"东北风转持续无风向"},{"date":"2019-04-02","temperature":"12/17℃","weather":"小雨","wid":{"day":"07","night":"07"},"direct":"东北风"},{"date":"2019-04-03","temperature":"10/15℃","weather":"小雨","wid":{"day":"07","night":"07"},"direct":"持续无风向"},{"date":"2019-04-04","temperature":"11/16℃","weather":"小到中雨转小雨","wid":{"day":"21","night":"07"},"direct":"持续无风向"}]}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * city : 杭州
         * realtime : {"temperature":"13","humidity":"47","info":"多云","wid":"01","direct":"东南风","power":"2级","aqi":"42"}
         * future : [{"date":"2019-03-31","temperature":"8/14℃","weather":"多云转晴","wid":{"day":"01","night":"00"},"direct":"东北风"},{"date":"2019-04-01","temperature":"9/18℃","weather":"晴转多云","wid":{"day":"00","night":"01"},"direct":"东北风转持续无风向"},{"date":"2019-04-02","temperature":"12/17℃","weather":"小雨","wid":{"day":"07","night":"07"},"direct":"东北风"},{"date":"2019-04-03","temperature":"10/15℃","weather":"小雨","wid":{"day":"07","night":"07"},"direct":"持续无风向"},{"date":"2019-04-04","temperature":"11/16℃","weather":"小到中雨转小雨","wid":{"day":"21","night":"07"},"direct":"持续无风向"}]
         */

        private String city;
        private RealtimeBean realtime;
        private List<FutureBean> future;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public RealtimeBean getRealtime() {
            return realtime;
        }

        public void setRealtime(RealtimeBean realtime) {
            this.realtime = realtime;
        }

        public List<FutureBean> getFuture() {
            return future;
        }

        public void setFuture(List<FutureBean> future) {
            this.future = future;
        }

        public static class RealtimeBean {
            /**
             * temperature : 13
             * humidity : 47
             * info : 多云
             * wid : 01
             * direct : 东南风
             * power : 2级
             * aqi : 42
             */

            private String temperature;
            private String humidity;
            private String info;
            private String wid;
            private String direct;
            private String power;
            private String aqi;

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getInfo() {
                return info;
            }

            public void setInfo(String info) {
                this.info = info;
            }

            public String getWid() {
                return wid;
            }

            public void setWid(String wid) {
                this.wid = wid;
            }

            public String getDirect() {
                return direct;
            }

            public void setDirect(String direct) {
                this.direct = direct;
            }

            public String getPower() {
                return power;
            }

            public void setPower(String power) {
                this.power = power;
            }

            public String getAqi() {
                return aqi;
            }

            public void setAqi(String aqi) {
                this.aqi = aqi;
            }
        }

        public static class FutureBean {
            /**
             * date : 2019-03-31
             * temperature : 8/14℃
             * weather : 多云转晴
             * wid : {"day":"01","night":"00"}
             * direct : 东北风
             */

            private String date;
            private String temperature;
            private String weather;
            private WidBean wid;
            private String direct;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public WidBean getWid() {
                return wid;
            }

            public void setWid(WidBean wid) {
                this.wid = wid;
            }

            public String getDirect() {
                return direct;
            }

            public void setDirect(String direct) {
                this.direct = direct;
            }

            public static class WidBean {
                /**
                 * day : 01
                 * night : 00
                 */

                private String day;
                private String night;

                public String getDay() {
                    return day;
                }

                public void setDay(String day) {
                    this.day = day;
                }

                public String getNight() {
                    return night;
                }

                public void setNight(String night) {
                    this.night = night;
                }
            }
        }
    }
}
