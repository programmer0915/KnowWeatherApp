package com.ice.weather.bean;

import com.google.gson.annotations.SerializedName;

public class CalendarBean {


    /**
     * reason : Success
     * result : {"data":{"avoid":"栽种.安葬.","animalsYear":"猪","weekday":"星期五","suit":"祭祀.开光.塑绘.酬神.斋醮.订盟.纳采.嫁娶.裁衣.动土.起基.出火.拆卸.移徙.入宅.安香.修造.竖柱.上梁.纳畜.牧养.祈福.求嗣.解除.伐木.定磉.造屋.安门.","lunarYear":"己亥年","lunar":"二月廿三","year-month":"2019-3","date":"2019-3-29"}}
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
         * data : {"avoid":"栽种.安葬.","animalsYear":"猪","weekday":"星期五","suit":"祭祀.开光.塑绘.酬神.斋醮.订盟.纳采.嫁娶.裁衣.动土.起基.出火.拆卸.移徙.入宅.安香.修造.竖柱.上梁.纳畜.牧养.祈福.求嗣.解除.伐木.定磉.造屋.安门.","lunarYear":"己亥年","lunar":"二月廿三","year-month":"2019-3","date":"2019-3-29"}
         */

        private DataBean data;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * avoid : 栽种.安葬.
             * animalsYear : 猪
             * weekday : 星期五
             * suit : 祭祀.开光.塑绘.酬神.斋醮.订盟.纳采.嫁娶.裁衣.动土.起基.出火.拆卸.移徙.入宅.安香.修造.竖柱.上梁.纳畜.牧养.祈福.求嗣.解除.伐木.定磉.造屋.安门.
             * lunarYear : 己亥年
             * lunar : 二月廿三
             * year-month : 2019-3
             * date : 2019-3-29
             */

            private String avoid;
            private String animalsYear;
            private String weekday;
            private String suit;
            private String lunarYear;
            private String lunar;
            @SerializedName("year-month")
            private String yearmonth;
            private String date;

            public String getAvoid() {
                return avoid;
            }

            public void setAvoid(String avoid) {
                this.avoid = avoid;
            }

            public String getAnimalsYear() {
                return animalsYear;
            }

            public void setAnimalsYear(String animalsYear) {
                this.animalsYear = animalsYear;
            }

            public String getWeekday() {
                return weekday;
            }

            public void setWeekday(String weekday) {
                this.weekday = weekday;
            }

            public String getSuit() {
                return suit;
            }

            public void setSuit(String suit) {
                this.suit = suit;
            }

            public String getLunarYear() {
                return lunarYear;
            }

            public void setLunarYear(String lunarYear) {
                this.lunarYear = lunarYear;
            }

            public String getLunar() {
                return lunar;
            }

            public void setLunar(String lunar) {
                this.lunar = lunar;
            }

            public String getYearmonth() {
                return yearmonth;
            }

            public void setYearmonth(String yearmonth) {
                this.yearmonth = yearmonth;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }
        }
    }
}
