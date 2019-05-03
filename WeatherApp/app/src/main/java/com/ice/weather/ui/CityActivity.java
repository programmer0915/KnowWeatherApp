package com.ice.weather.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ice.weather.MainActivity;
import com.ice.weather.R;
import com.mylhyl.circledialog.CircleDialog;


public class CityActivity extends AppCompatActivity {
    private LinearLayout mTool_bar;
    private ImageView mIv_return;
    private EditText mEt_query;
    private ImageView mIv_search;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initView();
    }

    private void initView() {
        mTool_bar =  findViewById(R.id.tool_bar);
        mIv_return = (ImageView) findViewById(R.id.iv_return);
        mEt_query = (EditText) findViewById(R.id.et_query);
        mIv_search = (ImageView) findViewById(R.id.iv_search);

        mIv_return.setOnClickListener(view -> {
            Intent intent = new Intent(CityActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            CityActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
        });

        mIv_search.setOnClickListener(view -> {
           String city = mEt_query.getText().toString();
           if(TextUtils.isEmpty(city)){
               new CircleDialog.Builder()
                       .setTitle("提示")
                       .setWidth(0.6f)
                       .setText("城市名不能为空！")
                       .setPositive("确定", null)
                       .show(getSupportFragmentManager());
        }else{
               Intent intent = new Intent(CityActivity.this, MainActivity.class);
               intent.putExtra("city",city);
               intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
               finish();
               CityActivity.this.overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
           }
    });
    }

}
