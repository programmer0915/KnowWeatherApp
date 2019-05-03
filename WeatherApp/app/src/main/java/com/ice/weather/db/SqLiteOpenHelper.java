package com.ice.weather.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;

//新建类Sqliteopenhelper 继承于SQLiteOpenHelper
public class SqLiteOpenHelper extends SQLiteOpenHelper {
    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "Data.db";
    // 用户表 包含username和password两个字段，id为主键
    private static final String CREATE_UsersDB = "create table usertable(" +
            "id integer primary key autoincrement,"+
            "username text," +
            "password text)";
    //天气数据表
    /**
     * id , city ,time,weather,weather_temp,temp_subtemp_min,temp_subtemp_max,
     * humidity,direction,strength,dressing_index,uv_index,travel_index,
     * exercise_index
     */
    private static final String CREAT_WeatherDB = "create table weathertable(" +
            "id integer primary key autoincrement," +
            "city text," + "time text,"+ "weather_temp text,"+ "temp_subtemp_max text," + "temp_subtemp_min text,"+
            "humidity text," + "direction text," + "strength text,"+ "dressing_index text,"+"uv_index text,"+"travel_index text,"+
            "exercise_index text)";
    //上下文
    private Context mContext;
    //构造方法：
    // 第一个参数Context上下文，
    // 第二个参数数据库名，
    // 第三个参数cursor允许我们在查询数据的时候返回一个自定义的光标位置，一般传入的都是null，
    // 第四个参数表示目前库的版本号（用于对库进行升级）
    public SqLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //调用SQLiteDatabase中的execSQL（）执行建表语句。
        db.execSQL(CREATE_UsersDB);
        db.execSQL(CREAT_WeatherDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新表
        db.execSQL("drop table if exists usertable");
        db.execSQL("drop table if exists weathertable");
        onCreate(db);
    }
}
