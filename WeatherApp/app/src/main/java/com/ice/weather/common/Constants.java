package com.ice.weather.common;

import android.graphics.Color;

/*
* 常量类
* TAG 标签
* */
public class Constants {

    public static final String TAG = "WeatherApp";

    //--------------------------------------天气预报-------------------------------------------
    public static final String WeatherApi = "http://v.juhe.cn/weather/index?cityname=";//天气Api

    public static final String WeatherKey = "&key=03d0d375b15731052e0e91e6d75d6bb7";//密钥
    //----------------------------------------End---------------------------------------------

    //--------------------------------------天气预报（未来）-------------------------------------------
    public static final String FutureWeatherApi = "http://apis.juhe.cn/simpleWeather/query?city=";

    public static final String FutureWeatherKey ="&key=d32d55030c3c8c0f755a52781f4fe067";
    //----------------------------------------End---------------------------------------------

    //--------------------------------------万年历-------------------------------------------
    public static final String CalendarApi = "http://v.juhe.cn/calendar/day?" +
            "date=";
    public static final String CalendarKey = "&key=e0a62a7a1ddd740f97a6e1f90623c1ac";
    //---------------------------------------End---------------------------------------------
    //*-------------------------------------科大讯飞-----------------------------------------
    public static final String xfServicesKey ="appid=5cb72470";

}
