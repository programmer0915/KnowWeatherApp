package com.ice.weather;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.*;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.gson.Gson;
import com.ice.weather.bean.CalendarBean;
import com.ice.weather.bean.FutureWeatherBean;
import com.ice.weather.bean.WeatherBean;
import com.ice.weather.common.Constants;
import com.ice.weather.db.SqLiteOpenHelper;
import com.ice.weather.ui.AboutActivity;
import com.ice.weather.ui.CityActivity;
import com.ice.weather.ui.LoginActivity;
import com.ice.weather.utils.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.iflytek.cloud.SpeechUtility;
import com.mylhyl.circledialog.CircleDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static com.ice.weather.common.Constants.xfServicesKey;
import static java.lang.String.valueOf;

@RuntimePermissions
public class MainActivity extends AppCompatActivity  {
    //----------------------声明控件-------------------------------
    private LinearLayout ll_weather;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageButton mIb_speak;//语音播报
    private TextView mTv_lunar;//农历
    private TextView mTv_city;//城市名
    private TextView mTv_release;//发布时间
    private TextView mTv_now_time;//更新时间
    private TextView mTv_now_weather;//当前温度
    private TextView mTv_temp_max;//最高温度
    private TextView mTv_temp_min;//最低温度
    private TextView mTv_weather_type;//天气类型
    private TextView mTv_weather_api;//天气质量
    private TextView mTv_now_humidity;//当前湿度
    private TextView mTv_now_wind_direction;//当前风向
    private TextView mTv_now_wind_strength;//当前风力
    private TextView mTv_dressing_index;//穿衣指数
    private TextView mTv_uv_index;//紫外线指数
    private TextView mTv_travel_index;//旅游指数
    private TextView mTv_exercise_index;//晨练指数
    private ImageView mIv_weather_type;//天气类型图片
    //--------------侧滑抽屉相关----------------
    private DrawerLayout mDrawer_layout;//抽屉布局
    private NavigationView mNav_View;//侧滑菜单
    private TextView mTv_id;//用户名
    //创建一个Drawerlayout和Toolbar联动的开关
    private ActionBarDrawerToggle toggle;
    //----------------------End----------------------------------
    //----------------LineChart图表--------------------
    private ArrayList<Entry> values = new ArrayList<>(); //数据集
    private ArrayList<Entry> values2 = new ArrayList<>(); //数据集
    private ArrayList<PieEntry> values3 = new ArrayList<>(); //数据集
    private XAxis xAxis;                //x轴线
    private YAxis yAxis;                //y轴线
    private LineChart mLineChart;      //折线
    private Legend legend;              //图例(默认正方形)
    private Typeface mRegular;
    //------------------PieChart-----------------------
    private PieChart mPieChart;//饼状图
    private ArrayList<String> xValueList; //x
    private ArrayList<Integer> yValueList;//y
    //--------------------End--------------------------
    //-------------------数据库---------------------
    private SqLiteOpenHelper dbHelper;
    //--------------------End-----------------------
    //------------------百度地图--------------------
    //声明LocationClient类
    public LocationClient mLocationClient = null;
    //---------------------End------------------------
    //-------------------弹窗-----------------------
    private AlertDialog alertDialog;
    private String username;
    private DialogFragment dialogFragment;
    //-------------------End-----------------------
    //-------------------退出----------------------
    private static boolean isExit = false; //退出
    //-------------------End-----------------------
    //-------PermissionsDispatcher文档注释 (通过文档注释处理运行时权限)---------------
    /**
     * @NeedsPermission 当申请的权限被用户允许后，调用此方法。
     * @OnShowRationale 当第一次申请权限时，用户选择拒绝，再次申请时调用此方法，在此方法中提示用户为什么需要这个权限。
     * @OnPermissionDenied 当申请的权限被用户拒绝后，调用此方法
     * @OnNeverAskAgain 当用户点击不再询问后，调用此方法。
     */
    //------------------End-------------------------
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Intent intent = getIntent();
                    String city =  intent.getStringExtra("city");
                    //天气基本信息
                    requestWeather1(Constants.WeatherApi + city + Constants.WeatherKey);
                    requestFutureWeather1(Constants.FutureWeatherApi + city + Constants.FutureWeatherKey);
                    SharedPreferences sp = getSharedPreferences("sp_lunar", 0);
                    String getlunar = sp.getString("lunar", "");
                    mTv_lunar.setText(getlunar);
                    Toast.makeText(getApplicationContext(),city,Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    SnackBarUtil.makeShort(ll_weather, "获取天气成功!").confirm();
                    //创建一个可写入的数据库
                    dbHelper = new SqLiteOpenHelper(MainActivity.this,"Weather",null,1);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    //创建存放数据的ContentValues对象
                    ContentValues weatherValue = new ContentValues();
                    mTv_now_time.setText(RefreshTimeUtil.getString(getApplicationContext(), "now_time", "_ _"));
                    //---------------天气基本信息--------------------
                    String ParseWeather = msg.obj.toString();
                    Gson weather = new Gson();
                    WeatherBean weatherBean = weather.fromJson(ParseWeather, WeatherBean.class);
                    //-------------------End-------------------------
                    String City = weatherBean.getResult().getToday().getCity();
                    mTv_city.setText(City);
                    weatherValue.put("city",City);
                    String Time = weatherBean.getResult().getSk().getTime();
                    mTv_release.setText(Time + "发布");
                    weatherValue.put("time",Time);
                    String Temp = weatherBean.getResult().getSk().getTemp();
                    mTv_now_weather.setText(Temp);
                    weatherValue.put("weather_temp",Temp);
                    //8℃~20℃
                    String[] tempArr = weatherBean.getResult().getToday().getTemperature().split("~");
                    String temp_subtemp_min = tempArr[0].substring(0, tempArr[0].indexOf("℃"));//返回℃的位置
                    String temp_subtemp_max = tempArr[1].substring(0, tempArr[1].indexOf("℃"));//返回℃的位置
                    mTv_temp_max.setText(temp_subtemp_max);
                    weatherValue.put("temp_subtemp_max",temp_subtemp_max);
                    mTv_temp_min.setText(temp_subtemp_min);
                    weatherValue.put("temp_subtemp_min",temp_subtemp_min);
                    String Weather = weatherBean.getResult().getToday().getWeather();
                    mTv_weather_type.setText(Weather + " | ");
                    String Humidity = weatherBean.getResult().getSk().getHumidity();
                    mTv_now_humidity.setText(Humidity);
                    weatherValue.put("humidity",Humidity);
                    String Direction = weatherBean.getResult().getSk().getWind_direction();
                    mTv_now_wind_direction.setText(Direction);
                    weatherValue.put("direction",Direction);
                    String Strength = weatherBean.getResult().getSk().getWind_strength();
                    mTv_now_wind_strength.setText(Strength);
                    weatherValue.put("strength",Strength);
                    String Dressing_index = weatherBean.getResult().getToday().getDressing_index();
                    mTv_dressing_index.setText(Dressing_index);
                    weatherValue.put("dressing_index",Dressing_index);
                    String Uv_index = weatherBean.getResult().getToday().getUv_index();
                    mTv_uv_index.setText(Uv_index);
                    weatherValue.put("uv_index",Uv_index);
                    String Travel_index = weatherBean.getResult().getToday().getTravel_index();
                    mTv_travel_index.setText(Travel_index);
                    weatherValue.put("travel_index",Travel_index);
                    String Exercise_index = weatherBean.getResult().getToday().getExercise_index();
                    mTv_exercise_index.setText(Exercise_index);
                    weatherValue.put("exercise_index",Exercise_index);
                    db.insert("weathertable",null,weatherValue);
                    db.close();
                    String weather_id = weatherBean.getResult().getToday().getWeather_id().getFb();
                    if (weather_id.equals("00")){//晴天
                        mIv_weather_type.setImageResource(R.mipmap.ic_weather_sunny);
                    }else if (weather_id.equals("01")){//多云
                        mIv_weather_type.setImageResource(R.mipmap.ic_weather_cloud);
                    }else if (weather_id.equals("02")){//阴天
                        mIv_weather_type.setImageResource(R.mipmap.ic_weather_cloud);
                    }else if (weather_id.equals("03")){//阵雨
                        mIv_weather_type.setImageResource(R.mipmap.ic_weather_rain);
                    }else if (weather_id.equals("13")){//雪
                        mIv_weather_type.setImageResource(R.mipmap.ic_weather_snow);
                    }
                    else {
                        mIv_weather_type.setImageResource(R.mipmap.ic_weather_rain);
                    }
                    break;
                case 2:
                    LineChartUtil.configChart(mLineChart , mRegular,true);
                    PieChartUtil.configChart(mPieChart);
                    //---------------未来天气的温度信息--------------------
                    String ParseFutureWeather = msg.obj.toString();
                    Gson future = new Gson();

                    FutureWeatherBean futureWeatherBean = future.fromJson(ParseFutureWeather, FutureWeatherBean.class);
                    //-------------------End-------------------------

                    String aqi = futureWeatherBean.getResult().getRealtime().getAqi();
                    SharedPreferences sp_aqi = getSharedPreferences("sp_aqi", 0);
                    SharedPreferences.Editor editor = sp_aqi.edit();
                    editor.putString("sp_aqi", aqi);
                    editor.commit();
                    mTv_weather_api.setText("aqi:" + aqi);
                    //future的长度 → 5 遍历 0 1 2 3 4
                    for (int i = 0;i< futureWeatherBean.getResult().getFuture().size();i++) {

                        /**
                         *  处理数据 最低温度
                         *  原始数据8\/14℃
                         *  取2位     substring
                         *  去除'/'   replace
                         */
                        String weather_min = futureWeatherBean.getResult().getFuture().get(i).getTemperature().substring(0,2);
                        String weather_min2 = weather_min.replace("/","");
                        int weather_min3 =  Integer.parseInt(weather_min2);
                        Log.i(Constants.TAG,"最低温度:" + weather_min3 );
                        values.add(new Entry(i + 1 , weather_min3));
                    }
                    // 2,获取数据Data
                    LineDataSet lineDataSet = LineChartUtil.getLineData(
                            values, "最低温度",
                            mRegular,
                            Color.parseColor("#C0FF8C"),//曲线
                            Color.parseColor("#F5FFFA"),//文字
                            Color.parseColor("#C0FF8C"),//数据点
                            Color.parseColor("#C0FF8C"),//圆心
                            false);

                    /**
                     * 处理数据 最高温度
                     * 原始数据18\/31℃
                     * 通过"/" 分割 split 8 31℃ [1] 31℃
                     * 把"℃"去掉 replace 31
                     */
                    for (int i = 0;i<futureWeatherBean.getResult().getFuture().size();i++) {
                        String[] weather_max = futureWeatherBean.getResult().getFuture().get(i).getTemperature().split("/");
                        String weather_max2 = weather_max[1];
                        int weather_max3 = Integer.parseInt(weather_max2.replace("℃",""));
                        Log.i(Constants.TAG,"最高温度:" + weather_max3+"");
                        values2.add(new Entry(i + 1 , weather_max3));
                        // 2,获取数据Data
                        LineDataSet lineDataSet2 = LineChartUtil.getLineData(
                                values2, "最高温度",
                                mRegular,
                                Color.parseColor("#8CEAFF"),//曲线
                                Color.parseColor("#F5FFFA"),//文字
                                Color.parseColor("#8CEAFF"),//数据点
                                Color.parseColor("#8CEAFF"),//圆心
                                false);
                        //  3,初始化数据并绘制
                        LineChartUtil.initData(mLineChart,new LineData(lineDataSet,lineDataSet2));
                    }
                    for (int i = 0;i<futureWeatherBean.getResult().getFuture().size();i++) {
                        String weather_type = futureWeatherBean.getResult().getFuture().get(i).getWeather();
                        values3.add(new PieEntry(i +1 , weather_type));
                        // 2,获取数据Data
                        PieDataSet pieDataSet = PieChartUtil.getPieChartData(values3,"");
                        //  3,初始化数据并绘制
                        PieChartUtil.initData(mPieChart,new PieData(pieDataSet));
                        Log.i(Constants.TAG,"天气类型:" + weather_type +"\n");
                    }
                    break;
                case 3:
                    String ParseCalendar = msg.obj.toString();
                    Gson calendar = new Gson();
                    CalendarBean calendarBean = calendar.fromJson(ParseCalendar, CalendarBean.class);
                    String lunar = calendarBean.getResult().getData().getLunar();
                    mTv_lunar.setText(lunar);
                    SharedPreferences sp_lunar = getSharedPreferences("sp_lunar", 0);
                    SharedPreferences.Editor editor1 = sp_lunar.edit();
                    editor1.putString("lunar", lunar);
                    editor1.commit();
                    String nowTime = getNowTime();
                    mTv_now_time.setText("最近更新：" + nowTime);
                    mSwipeRefreshLayout.setRefreshing(false);
                    break;
                case 4 :
//                    Intent intent1 = getIntent();
//                    String username =  intent1.getStringExtra("Username");
                    requestLocation();
                    mLocationClient.start();
                    break;
                case 5 :
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(MainActivity.this,"刷新完毕",Toast.LENGTH_SHORT).show();
                    }
//                    SharedPreferences sp_lunar1 = getSharedPreferences("sp_lunar", 0);
//                    String getlunar2 = sp_lunar1.getString("lunar", "");
//                    mTv_lunar.setText(getlunar2);
//
//                    SharedPreferences sp_aqi1 = getSharedPreferences("sp_aqi", 0);
//                    String sp_aqi2 = sp_aqi1.getString("sp_aqi", "");
//                    mTv_weather_api.setText(sp_aqi2);

                    String nowTime1 = getNowTime();
                    mTv_now_time.setText("最近更新：" + nowTime1);

                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_appbar_nav);
        initView();
        Intent intent = getIntent();
            new Thread() {
                public void run() {
                    Message message = Message.obtain();
                    if (intent.hasExtra("city")) {
                        message.what = 0;
                    }else {
                        message.what = 4;
                    }
                    mHandler.sendMessage(message);
                };
            }.start();
        //initPermission();
        //initDialog();
        //SnackBarUtil.makeShort(ll_weather, "欢迎回来，" + username).confirm();
    }
    private void initPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            MainActivityPermissionsDispatcher.PermissionSuccessWithPermissionCheck(this);     //调用辅助类里面的方法完成应用的权限请求
        } else {
            new CircleDialog.Builder()
                    .setTitle("提示")
                    .setWidth(0.6f)
                    .setText("请在Android6.0以上设备运行！")
                    .setPositive("确定", null)
                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialog) {
                           //System.exit(0);//正常退出

                        }
                    })
                    .show(getSupportFragmentManager());
        }
    }
    //重写onRequestPermissionsResult方法
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    /**
     * 申请权限成功时
     */
    @NeedsPermission({ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE})
    void PermissionSuccess() {
        requestLocation();
        mLocationClient.start();//调用LocationClient的start()方法
    }

    /**
     * 告知用户具体需要权限的原因
     */
    @OnShowRationale({ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE})
    void ShowRationale(final PermissionRequest request) {
        new AlertDialog.Builder(this)   //弹窗
                .setMessage("需要获取一些权限")
                .setPositiveButton("下一步", (dialog, which) -> {
                    request.proceed();//继续执行请求
                })
                .show();
    }

    /**
     * 一旦用户拒绝了该权限，则调用
     */
    @OnPermissionDenied({ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION, ACCESS_WIFI_STATE})
    public void storageDenied() {
        System.exit(0);
    }

    private void initView() {
        ll_weather = findViewById(R.id.ll_weather);
        mIb_speak = findViewById(R.id.ib_speak);
        mLineChart = findViewById(R.id.mLineChart);
        mPieChart = (PieChart) findViewById(R.id.mPieChart);
        mTv_city = findViewById(R.id.tv_city);
        mTv_release = findViewById(R.id.tv_release);
        mTv_now_time = findViewById(R.id.tv_now_time);
        mTv_lunar = findViewById(R.id.tv_lunar);
        mTv_now_weather = findViewById(R.id.tv_now_weather);
        mTv_weather_api = findViewById(R.id.tv_weather_api);
        mTv_temp_max = findViewById(R.id.tv_temp_max);
        mTv_temp_min = findViewById(R.id.tv_temp_min);
        mTv_weather_type = findViewById(R.id.tv_weather_type);
        mTv_now_humidity = findViewById(R.id.tv_now_humidity);
        mTv_now_wind_direction = findViewById(R.id.tv_now_wind_direction);
        mTv_now_wind_strength = findViewById(R.id.tv_now_wind_strength);
        mTv_dressing_index = findViewById(R.id.tv_dressing_index);
        mTv_uv_index = findViewById(R.id.tv_uv_index);
        mTv_travel_index = findViewById(R.id.tv_travel_index);
        mTv_exercise_index = findViewById(R.id.tv_exercise_index);
        mRegular = Typeface.createFromAsset(getAssets(), "PingFang_Regular.ttf");
        SpeechUtility.createUtility(getApplicationContext(), xfServicesKey);
        mDrawer_layout = findViewById(R.id.drawer_layout);
        mNav_View = findViewById(R.id.nav_view);
        mIv_weather_type = findViewById(R.id.iv_weather_type);
        mSwipeRefreshLayout = findViewById(R.id.mSwipeRefreshLayout);
        //1、获取Preferences
        SharedPreferences sp = getSharedPreferences("sp_username", 0);
        //2、取出数据 默认值N/A
        String name = sp.getString("name","N/A");
        //获得头部布局，同时把其加入到抽屉布局中去
        View headerLayout = mNav_View.inflateHeaderView(R.layout.navigation_head);
        mTv_id = (TextView)headerLayout.findViewById(R.id.tv_id);
        mTv_id.setText(name);
        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();

        //修改item 字体
        Resources resource=(Resources)getBaseContext().getResources();
        ColorStateList csl=(ColorStateList)resource.getColorStateList(R.color.item);
        mNav_View.setItemTextColor(csl);

        mNav_View.getChildAt(0).setVerticalScrollBarEnabled(false);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.refresh));
        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // 这里是主线程
            // 一些比较耗时的操作，比如联网获取数据，需要放到子线程去执行
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this, "开始刷新", Toast.LENGTH_SHORT).show();
                    // 加载完数据设置为不刷新状态，将下拉进度收起来
                    Message message = Message.obtain();
                        message.what = 5;
                    mHandler.sendMessage(message);
                }
            }, 500);
        });
        mNav_View.setItemIconTintList(null);
        toggle = new ActionBarDrawerToggle(this, mDrawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer_layout.addDrawerListener(toggle);
        toggle.syncState();
        mNav_View.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                //每次点击一个Menu关闭DrawerLayout Start左 End右
                case R.id.city_list:
                    startActivity(new Intent(MainActivity.this, CityActivity.class));
                    overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                    Toast.makeText(MainActivity.this, "城市选择", Toast.LENGTH_SHORT).show();
                    mDrawer_layout.closeDrawer(GravityCompat.START);
                    break;
                case R.id.about:
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                    MainActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                    Log.i(Constants.TAG, "关于软件");
                    break;
                case R.id.relogin:
                    Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                    intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent1);
                    finish();
                    MainActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
                    break;
                case R.id.exit:
                    finish();
                    break;
            }
            return true;
        });

        mIb_speak.setOnClickListener(view -> {
            String city_speak = mTv_city.getText().toString();
            String weather_speak = mTv_now_weather.getText().toString();
            String mTv_temp_max_speak = mTv_temp_max.getText().toString();
            String mTv_temp_min_speak = mTv_temp_min.getText().toString();
            new Thread(() -> AudioUtil.getInstance(getApplicationContext()).speaking(
                    "当前城市" + city_speak + "当前温度" +
                            weather_speak + "今天最高温度" + mTv_temp_max_speak
                            + "最低温度" + mTv_temp_min_speak)
            ).start();
        });

    }

    @Override
    public void onBackPressed() {
         if (mDrawer_layout.isDrawerOpen(GravityCompat.START))
         {
             mDrawer_layout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return simpleDateFormat.format(new Date());
    }

    public void requestLocation() {
        mLocationClient = new LocationClient(getApplicationContext());//声明LocationClient类
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//设置定位模式，高精度
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        mLocationClient.setLocOption(option);//将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用

        mLocationClient.registerLocationListener(new BDAbstractLocationListener() { //注册监听函数
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {

                String FullCity = bdLocation.getCity();    //获取城市信息
                String FinalCity = FullCity.substring(0, 2);       //截取前两位

                double longitude = bdLocation.getLongitude();    //获取经度信息
                String str = bdLocation.getTime().substring(0,10);//当前时间 2019-01-11/2019-01-01
                String Time = str.replace("0", "");//把0去掉   //219-1-11 219-1-1
                String finalTime = Time.replace("219", "2019");  //2019-1-11 2219-1-1
                double error = 4.9E-324;

                if (longitude == error) {
                    SnackBarUtil.makeShort(ll_weather, "定位失败!").danger();
                    onStop();
                } else {
                        Log.i(Constants.TAG, "\n" + "City：" + FinalCity);
                        Log.i(Constants.TAG, "\n" + "Time：" + finalTime);
                        SharedPreferences sp_lunar = getSharedPreferences("sp_city", 0);
                        SharedPreferences.Editor editor = sp_lunar.edit();
                        editor.putString("city", FinalCity);
                        editor.commit();
                        mLocationClient.stop();
                        //天气基本信息
                        requestWeather(Constants.WeatherApi + FinalCity + Constants.WeatherKey);
                        //未来天气以及api
                        requestFutureWeather(Constants.FutureWeatherApi + FinalCity + Constants.FutureWeatherKey);
                        //农历
                        requestCalendar(Constants.CalendarApi + finalTime + Constants.CalendarKey);
                }
            }
        });
    }

    public void requestFutureWeather(String url) {
        HttpUtil.sendOkHttpRequest(url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String FutureData = response.body().string();
                    Message message = Message.obtain();
                    message.what = 2;
                    message.obj = FutureData;
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    public void requestWeather(String city) {

        HttpUtil.sendOkHttpRequest(city, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String ResponseData = response.body().string();

                        Message message = Message.obtain();
                        message.what = 1;
                        message.obj = ResponseData;
                        mHandler.sendMessage(message);
                }
            }
        });
    };

    private void requestCalendar(String url) {
        HttpUtil.sendOkHttpRequest(url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String ResponseData = response.body().string();
                        Message message = Message.obtain();
                        message.what = 3;
                        message.obj = ResponseData;
                        mHandler.sendMessage(message);
                };
            }
        });
    }
    public void requestFutureWeather1(String url) {
        HttpUtil.sendOkHttpRequest(url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String FutureData = response.body().string();
                    Message message = Message.obtain();
                    message.what = 2;
                    message.obj = FutureData;
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    public void requestWeather1(String city) {

        HttpUtil.sendOkHttpRequest(city, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String ResponseData = response.body().string();

                    Message message = Message.obtain();
                    message.what = 1;
                    message.obj = ResponseData;
                    mHandler.sendMessage(message);
                }
            }
        });
    };
    public void requestFutureWeather2(String url) {
        HttpUtil.sendOkHttpRequest(url, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String FutureData = response.body().string();
                    Message message = Message.obtain();
                    message.what = 6;
                    message.obj = FutureData;
                    mHandler.sendMessage(message);
                }
            }
        });
    }

    public void requestWeather2(String city) {

        HttpUtil.sendOkHttpRequest(city, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.body() != null) {
                    String ResponseData = response.body().string();

                    Message message = Message.obtain();
                    message.what = 7;
                    message.obj = ResponseData;
                    mHandler.sendMessage(message);
                }
            }
        });
    };

    @Override
    protected void onStop() {
        super.onStop();
    }

    //把时间保存
    @Override
    protected void onDestroy() {
        super.onDestroy();
        RefreshTimeUtil.putString(this, "now_time", mTv_now_time.getText().toString());
        mHandler.removeCallbacksAndMessages(null);
        SharedPreferences sp = getSharedPreferences("username", 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();

    }

    //监听返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)//如果按下返回键
        {
            ExitApp();  //调用双击退出函数
        }
        return false;//不会执行退出事件
    }

    private void ExitApp() {

        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            Timer timer = new Timer();
            timer.schedule(new TimerTask() { //调用 schedule() 方法,2000ms执行一次run里的方法
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000);// 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);//正常退出程序
        }
    }

}