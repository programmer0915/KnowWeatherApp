package com.ice.weather.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ice.weather.MainActivity;
import com.ice.weather.R;
import com.ice.weather.db.SqLiteOpenHelper;
import com.ice.weather.utils.CodeUtil;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.params.ProgressParams;

import java.util.Date;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView mTv_userlogin,mTv_welcome,mTv_register;
    private EditText mEt_username;
    private EditText mEt_password;
    private Button mBtn_sign;
    private TextView mTv_unregister,mTv_change;
    private ImageView mIv_username_clear;
    private ImageView mIv_password_clear;
    private ImageButton openpwd;
    private EditText mEt_code;
    private ImageView mIv_codeimage;
    private Bitmap bitmap;
    private String code;
    private boolean flag = false;
    private SqLiteOpenHelper dbHelper;
    private DialogFragment dialogFragment;
    //用户名，密码，再次输入的密码的控件的获取值
    private String userName,psw;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        getDate(mTv_welcome);
        dbHelper = new SqLiteOpenHelper(this, "Data", null, 1);
    }

    private void initView() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "PingFang_Light.ttf");
        Typeface typeface2 = Typeface.createFromAsset(getAssets(), "悦圆.ttf");
        mTv_userlogin = (TextView) findViewById(R.id.tv_userlogin);
        mTv_register = (TextView) findViewById(R.id.tv_register);
        mTv_welcome = (TextView) findViewById(R.id.tv_welcome);
        mTv_change = (TextView) findViewById(R.id.tv_change);
        mEt_username = (EditText) findViewById(R.id.et_username);
        mEt_password = (EditText) findViewById(R.id.et_password);
        mBtn_sign = (Button) findViewById(R.id.btn_sign);
        mTv_unregister = (TextView) findViewById(R.id.tv_unregister);
        mIv_username_clear = findViewById(R.id.iv_user_clear);
        mIv_password_clear = findViewById(R.id.iv_password_clear);
        openpwd = (ImageButton) findViewById(R.id.btn_openpwd);
        mIv_codeimage = (ImageView) findViewById(R.id.iv_codeimage);
        mEt_code = (EditText) findViewById(R.id.et_code);
        mTv_userlogin.setTypeface(typeface2);
        mTv_welcome.setTypeface(typeface2);
        mTv_unregister.setTypeface(typeface);
        mBtn_sign.setTypeface(typeface);
        mTv_unregister.setTypeface(typeface);
        // 为输入框绑定一个监听文字变化的监听器
        mEt_username.addTextChangedListener(Username_Watcher);
        mEt_password.addTextChangedListener(Password_Watcher);
        //获取工具类生成的图片验证码对象
        bitmap = CodeUtil.getInstance().createBitmap();
        //获取当前图片验证码的对应内容用于校验
        code = CodeUtil.getInstance().getCode();
        mIv_codeimage.setImageBitmap(bitmap);
    }
    // 当输入框状态改变时，会调用相应的方法
    private TextWatcher Username_Watcher = new TextWatcher() {
        //Text改变之前被调用
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        //Text改变过程中触发调用
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        // Text改变后调用
        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                Username_hideBtn();// 隐藏按钮
            } else {
                Username_showBtn();// 显示按钮
            }
        }
    };

    private TextWatcher Password_Watcher = new TextWatcher() {
        //Text改变之前被调用
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        //Text改变过程中触发调用
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        // Text改变后调用
        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                Password_hideBtn();// 隐藏按钮
            } else {
                Password_showBtn();// 显示按钮
            }
        }
    };

    private void getDate(TextView tv) {
        //得到小时,进行判断
        Date d = new Date();
        if (d.getHours() < 11) {
            tv.setText("上午好，欢迎回来！");
        } else if (d.getHours() < 13) {
            tv.setText("中午好，欢迎回来！");
        } else if (d.getHours() < 18) {
            tv.setText("一下午好，欢迎回来！一");
        } else if (d.getHours() < 24) {
            tv.setText("晚上好，欢迎回来！一");
        }
    }

    private void Username_hideBtn() {
        // 设置按钮不可见
        if (mIv_username_clear.isShown()){
            mIv_username_clear.setVisibility(View.INVISIBLE);
        }
    }

    private void Username_showBtn() {
        // 设置按钮可见
        if (!mIv_username_clear.isShown()){
            mIv_username_clear.setVisibility(View.VISIBLE);
        }
    }

    private void Password_hideBtn() {
        // 设置按钮不可见
        if (mIv_password_clear.isShown()){
            mIv_password_clear.setVisibility(View.INVISIBLE);
        }
    }

    private void Password_showBtn() {
        // 设置按钮可见
        if (!mIv_password_clear.isShown()){
            mIv_password_clear.setVisibility(View.VISIBLE);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sign:
                //  开启一个子线程
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //判断是否为空
                        if (TextUtils.isEmpty(mEt_username.getText())) {
                            new CircleDialog.Builder()
                                    .setTitle("提示")
                                    .setWidth(0.6f)
                                    .setText("用户名不能为空！")
                                    .setPositive("确定", null)
                                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialog) {
                                            //重新获取焦点
                                            mEt_username.setFocusable(true);
                                            mEt_username.setFocusableInTouchMode(true);
                                            mEt_username.requestFocus();
                                            mEt_username.findFocus();
                                        }
                                    })
                                    .show(getSupportFragmentManager());
                            return;
                        }
                        else if (TextUtils.isEmpty(mEt_password.getText())){
                            new CircleDialog.Builder()
                                    .setTitle("提示")
                                    .setWidth(0.6f)
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
                        }
                        else if (!code.equals(mEt_code.getText().toString())) {
                            new CircleDialog.Builder()
                                    .setTitle("提示")
                                    .setWidth(0.6f)
                                    .setText("验证码输入有误！")
                                    .setPositive("确定", null)
                                    .setOnCancelListener(new DialogInterface.OnCancelListener() {
                                        @Override
                                        public void onCancel(DialogInterface dialog) {
                                            mEt_code.setText("");
                                            //重新获取焦点
                                            mEt_code.setFocusable(true);
                                            mEt_code.setFocusableInTouchMode(true);
                                            mEt_code.requestFocus();
                                            mEt_code.findFocus();
                                        }
                                    })
                                    .show(getSupportFragmentManager());
                            return;
                        }
                        else {
                            readUserInfo();
                        }
                    }
                });
                thread.start();
                break;
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                LoginActivity.this.overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);
                break;
            case R.id.iv_user_clear:
                // 清空
                mEt_username.setText("");
                break;
            case R.id.iv_password_clear:
                // 清空
                mEt_password.setText("");
                break;
            case R.id.btn_openpwd:
                        if (flag == true) {
                            //不可见
                            mEt_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            flag = false;
                            openpwd.setBackgroundResource(R.mipmap.invisible);
                        } else {
                            //可见
                            mEt_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            flag = true;
                            openpwd.setBackgroundResource(R.mipmap.visible);
                        }
                break;
            case R.id.tv_change:
                bitmap = CodeUtil.getInstance().createBitmap();
                code = CodeUtil.getInstance().getCode();
                mIv_codeimage.setImageBitmap(bitmap);
        }
    }
    /**
     * 读取Data.db中的用户信息
     * */
    protected void readUserInfo() {
        userName = mEt_username.getText().toString();
        psw = mEt_password.getText().toString();
        if (login(userName,psw)) {
            //插入到一个队列
            Looper.prepare();
            dialogFragment = new CircleDialog.Builder()
                    .setProgressText("登录中...")
                    .setProgressStyle(ProgressParams.STYLE_SPINNER)
                    .show(getSupportFragmentManager());
            new Handler().postDelayed(() -> {
                dialogFragment.dismiss();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.putExtra("Username",userName);
                //1、打开Preferences，username，如果存在则打开它，否则创建新的Preferences MODE_PRIVATE → 0 创建的文件只能被本应用访问 会覆盖
                //APPEND追加
                SharedPreferences sharedPreferences = getSharedPreferences("sp_username",0);
                //2、让setting处于编辑状态
                SharedPreferences.Editor editor = sharedPreferences.edit();
                //3、存放数据
                editor.putString("name",userName);
                //4、完成提交
                editor.commit();

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                LoginActivity.this.overridePendingTransition(R.anim.alpha_in,R.anim.alpha_out);

            }, 1000);
            Looper.loop();
        } else {
            new CircleDialog.Builder()
                    .setTitle("提示")
                    .setWidth(0.6f)
                    .setText("用户名或密码错误！")
                    .setPositive("确定", null)
                    .show(getSupportFragmentManager());
        }
    }
    /**
     * 验证登录信息
     * */
    public boolean login(String username, String password) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            String sql = "Select * from usertable where username=? and password=?";
            Cursor cursor = db.rawQuery(sql, new String[]{username, password});
            //指向查询结果的第一个位置
            if (cursor.moveToFirst()) {
                cursor.close();
                return true;
            }
            return false;
        }
    }
