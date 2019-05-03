package com.ice.weather.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ice.weather.R;
//App启动页
public class SplashActivity extends AppCompatActivity{

    private TextView tv_producer,tv_version;
    private ImageView iv_splash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //加载指定布局
        View rootView = LayoutInflater.from(this).inflate(R.layout.activity_splash, null);
        setContentView(rootView);
        initView();
    }

    private void initView() {
        iv_splash = findViewById(R.id.iv_splash);
        tv_producer = findViewById(R.id.tv_producer);
        tv_version = findViewById(R.id.tv_version);
        //使用自定义字体
        Typeface typeface1=Typeface.createFromAsset(getAssets(), "PingFang_ExtraLight.ttf");
        tv_producer.setTypeface(typeface1);
        tv_version.setTypeface(typeface1);
        //倒计时工具类 参数time是总时间，interval是间隔时间
        new CountDownTimer(3000,1000) {
            //每隔n秒调用一次
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub

            }
            //倒计时完成的方法
            @Override
            public void onFinish() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                SplashActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
            }
        }.start();


    }
}
