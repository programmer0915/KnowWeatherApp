package com.ice.weather.ui;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.ice.weather.R;
import com.ice.weather.db.SqLiteOpenHelper;
import com.mylhyl.circledialog.CircleDialog;

public class RegisterActivity  extends AppCompatActivity implements View.OnClickListener {

    private android.support.v7.widget.Toolbar mToolbar;
    private TextView mTv_register_title;
    private RelativeLayout mRl_username;
    private RelativeLayout mRl_username_title;
    private TextView mTv_username_title;
    private RelativeLayout mRl_set_username;
    private EditText mEt_username;
    private RelativeLayout mRl_password;
    private RelativeLayout mRl_password_title;
    private TextView mTv_set_password_title;
    private RelativeLayout mRl_set_password;
    private EditText mEt_password;
    private RelativeLayout mRl_password_again;
    private RelativeLayout mRl_password_again_title;
    private TextView mTv_set_password_again_title;
    private RelativeLayout mRl_set_password_again;
    private EditText mEt_password_again;
    private RelativeLayout mRl_register;
    private Button mBtn_register;
    private RelativeLayout mRl_foot;
    private TextView mTv_hasaccount;
    private TextView mTv_nowlogin;

    //用户名，密码，再次输入的密码的控件的获取值
    private String userName,psw,pswAgain;
    private SqLiteOpenHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        getSupportActionBar().setDisplayShowTitleEnabled(false);//去除左侧标题
        //创建一个可写入的数据库
        dbHelper = new SqLiteOpenHelper(this,"User",null,1);
    }

    private void initView() {
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        mTv_register_title = (TextView) findViewById(R.id.tv_register_title);
        mRl_username = (RelativeLayout) findViewById(R.id.rl_username);
        mRl_username_title = (RelativeLayout) findViewById(R.id.rl_username_title);
        mTv_username_title = (TextView) findViewById(R.id.tv_username_title);
        mRl_set_username = (RelativeLayout) findViewById(R.id.rl_set_username);
        mEt_username = (EditText) findViewById(R.id.et_username);
        mRl_password = (RelativeLayout) findViewById(R.id.rl_password);
        mRl_password_title = (RelativeLayout) findViewById(R.id.rl_password_title);
        mTv_set_password_title = (TextView) findViewById(R.id.tv_set_password_title);
        mRl_set_password = (RelativeLayout) findViewById(R.id.rl_set_password);
        mEt_password = (EditText) findViewById(R.id.et_password);
        mRl_password_again = (RelativeLayout) findViewById(R.id.rl_password_again);
        mRl_password_again_title = (RelativeLayout) findViewById(R.id.rl_password_again_title);
        mTv_set_password_again_title = (TextView) findViewById(R.id.tv_set_password_again_title);
        mRl_set_password_again = (RelativeLayout) findViewById(R.id.rl_set_password_again);
        mEt_password_again = (EditText) findViewById(R.id.et_password_again);
        mRl_register = (RelativeLayout) findViewById(R.id.rl_register);
        mBtn_register = (Button) findViewById(R.id.btn_register);
        mRl_foot = (RelativeLayout) findViewById(R.id.rl_foot);
        mTv_hasaccount = (TextView) findViewById(R.id.tv_hasaccount);
        mTv_nowlogin = (TextView) findViewById(R.id.tv_nowlogin);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "悦圆.ttf");
        Typeface typeface3 = Typeface.createFromAsset(getAssets(), "悦黑.ttf");
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "PingFang_Regular.ttf");
        mTv_register_title.setTypeface(typeface);
        mTv_username_title.setTypeface(typeface);

        mTv_set_password_title.setTypeface(typeface3);
        mTv_set_password_again_title.setTypeface(typeface3);

        mTv_hasaccount.setTypeface(typeface);
        mTv_nowlogin.setTypeface(typeface);
        mBtn_register.setTypeface(typeface);

        mEt_username.setTypeface(typeface2);
        mEt_password.setTypeface(typeface2);
        mEt_password_again.setTypeface(typeface2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_nowlogin:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                RegisterActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
            break;
            case R.id.btn_register:
                userName = mEt_username.getText().toString();
                psw = mEt_password.getText().toString();
                pswAgain = mEt_password_again.getText().toString();
                new Thread(() -> {
                    //判断是否为空
                    if (TextUtils.isEmpty(mEt_username.getText())) {
                        new CircleDialog.Builder()
                                .setTitle("提示")
                                .setWidth(0.7f)
                                .setText("用户名不能为空！")
                                .setPositive("确定", null)
                                .setOnCancelListener(dialog -> {
                                    //重新获取焦点
                                    mEt_username.setFocusable(true);
                                    mEt_username.setFocusableInTouchMode(true);
                                    mEt_username.requestFocus();
                                    mEt_username.findFocus();
                                })
                                .show(getSupportFragmentManager());
                        return;
                    }
                    else if (TextUtils.isEmpty(mEt_password.getText())) {
                        new CircleDialog.Builder()
                                .setTitle("提示")
                                .setWidth(0.7f)
                                .setText("密码不能为空！")
                                .setPositive("确定", null)
                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        //重新获取焦点
                                        mEt_password.setFocusable(true);
                                        mEt_password.setFocusableInTouchMode(true);
                                        mEt_password.requestFocus();
                                        mEt_password.findFocus();
                                    }
                                })
                                .show(getSupportFragmentManager());
                        return;
                    }  else if (TextUtils.isEmpty(mEt_password_again.getText())) {
                        new CircleDialog.Builder()
                                .setTitle("提示")
                                .setWidth(0.7f)
                                .setText("请再次输入密码！")
                                .setPositive("确定", null)
                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        //重新获取焦点
                                        mEt_password_again.setFocusable(true);
                                        mEt_password_again.setFocusableInTouchMode(true);
                                        mEt_password_again.requestFocus();
                                        mEt_password_again.findFocus();
                                    }
                                })
                                .show(getSupportFragmentManager());
                        return;
                    }
                    else if (!psw.equals(pswAgain)){
                        new CircleDialog.Builder()
                                .setTitle("提示")
                                .setWidth(0.7f)
                                .setText("密码与确认密码不一致！")
                                .setPositive("确定", null)
                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        //重新获取焦点
                                        mEt_password_again.setText("");
                                        mEt_password_again.setFocusable(true);
                                        mEt_password_again.setFocusableInTouchMode(true);
                                        mEt_password_again.requestFocus();
                                        mEt_password_again.findFocus();
                                    }
                                })
                                .show(getSupportFragmentManager());
                        return;
                    }
                    else if (CheckUserName(mEt_username.getText().toString())) {
                        new CircleDialog.Builder()
                                .setTitle("提示")
                                .setWidth(0.7f)
                                .setText("该用户名已被注册！")
                                .setPositive("确定", null)
                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        //重新获取焦点
                                        mEt_username.setText("");
                                        mEt_password_again.setText("");
                                        mEt_password.setText("");
                                        mEt_username.setFocusable(true);
                                        mEt_username.setFocusableInTouchMode(true);
                                        mEt_username.requestFocus();
                                        mEt_username.findFocus();
                                    }
                                })
                                .show(getSupportFragmentManager());
                        return;
                    }
                    else {
                        register(mEt_username.getText().toString(),mEt_password.getText().toString());
                        new CircleDialog.Builder()
                                .setTitle("提示")
                                .setWidth(0.7f)
                                .setText("恭喜你，注册成功！")
                                .setPositive("确定", null)
                                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        //重新获取焦点
                                        mEt_username.setText("");
                                        mEt_password_again.setText("");
                                        mEt_password.setText("");
                                        mEt_username.setFocusable(true);
                                        mEt_username.setFocusableInTouchMode(true);
                                        mEt_username.requestFocus();
                                        mEt_username.findFocus();
                                    }
                                })
                                .show(getSupportFragmentManager());
                    }
                }).start();
                break;
        }
    }

    //重写方法，对Toolbar左箭头添加监听事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){ ;
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            RegisterActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 利用sql创建嵌入式数据库进行注册访问
     */
    private void register(String username, String password) {
       //创建一个可写入的数据库
         SQLiteDatabase db = dbHelper.getWritableDatabase();
        //存储键值对
         ContentValues values = new ContentValues();
         values.put("username",username);
         values.put("password",password);
        //insert（）方法中第一个参数是表名，第二个参数是表示给表中未指定数据的自动赋值为NULL。第三个参数是一个ContentValues对象
        db.insert("usertable",null,values);
        db.close();
    }
    /**
     * 检验用户名是否已经注册
     */
    public boolean CheckUserName(String value) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Query = "Select * from usertable where username =?";
        Cursor cursor = db.rawQuery(Query, new String[]{value});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
}
