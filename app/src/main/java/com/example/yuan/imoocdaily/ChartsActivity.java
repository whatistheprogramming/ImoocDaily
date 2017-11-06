package com.example.yuan.imoocdaily;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import lecho.lib.hellocharts.view.Chart;

/**
 * Created by yuan on 2017/11/6.
 */

public class ChartsActivity extends AppCompatActivity
{
    private Chart mChart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);
        //获取Chart实例
        mChart = (Chart) findViewById(R.id.chart);
        //得到MainActivity传入的dataList
        List<CostBean> dataList = (List<CostBean>) getIntent().getSerializableExtra("cost_list");
        


    }
}
