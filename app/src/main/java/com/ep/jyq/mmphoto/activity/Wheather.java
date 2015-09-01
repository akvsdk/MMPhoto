package com.ep.jyq.mmphoto.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ep.jyq.mmphoto.R;
import com.ep.jyq.mmphoto.bean.CityW;
import com.ep.jyq.mmphoto.view.CityPicker;
import com.google.gson.Gson;

/**
 * Created by Joy on 2015/8/28.
 */
public class Wheather extends AppCompatActivity {

    private CityPicker cityPicker;
    private TextView tv, tv_weather;
    private FloatingActionButton fabBtn;
    private String url;
    private Context mContext;
    private RequestQueue mRequestQueue;
    private Toolbar toolbar;
    private CoordinatorLayout rootlayout;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        mRequestQueue = Volley.newRequestQueue(this);
        rootlayout = (CoordinatorLayout) findViewById(R.id.coord_rootlayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (toolbar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        cityPicker = (CityPicker) findViewById(R.id.citypicker);
        tv = (TextView) findViewById(R.id.tv_show);
        tv_weather = (TextView) findViewById(R.id.wether_show);
        fabBtn = (FloatingActionButton) findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = url;
                StringRequest stringRequest = new StringRequest(Request.Method.POST, temp, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.e("Joy", s);
                        Gson gson = new Gson();
                        CityW info = gson.fromJson(s.toString(), CityW.class);
                        tv_weather.setText(" 城市  " + info.getWeatherinfo().getCity() + "\n 当日最高温度  "
                                + info.getWeatherinfo().getTemp1() + "\n 当日最低温度  "
                                + info.getWeatherinfo().getTemp2() + "\n 当日天气  " + info.getWeatherinfo().getWeather()
                                + "\n 发布时间  " + info.getWeatherinfo().getPtime());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Snackbar.make(rootlayout, "还没有选好城市哦~", Snackbar.LENGTH_SHORT).show();
                    }
                });
                mRequestQueue.add(stringRequest);
            }


        });

        cityPicker.setOnSelectingListener(new CityPicker.OnSelectingListener() {
            @Override
            public void selected(boolean selected, String province_name, String city_name, String couny_name, String city_code) {
                String res = province_name + "省(市)  " + city_name + "  " + couny_name;
                tv.setText(res);
                if (city_code != null && !city_code.equals("")) {
                    url = "http://www.weather.com.cn/data/cityinfo/" + city_code + ".html";
                }

            }


        });

        inittitle();
    }
    private void inittitle() {     // 沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}