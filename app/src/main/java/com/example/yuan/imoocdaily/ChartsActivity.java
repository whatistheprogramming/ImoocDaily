package com.example.yuan.imoocdaily;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by yuan on 2017/11/6.
 */

public class ChartsActivity extends AppCompatActivity
{
    private LineChartView mLineChartView;

    //存储时间和money
    private Map<String, Integer> table = new TreeMap<>(); //TreeMap是有序的

    private LineChartData mLineChartData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);
        //获取Chart实例
        mLineChartView = (LineChartView) findViewById(R.id.chart);
        //得到MainActivity传入的dataList
        List<CostBean> dataList = (List<CostBean>) getIntent().getSerializableExtra("cost_list");
        //对日期重复的进行合并
        generateValues(dataList);

        //处理charts的数据
        generateData();

    }

    private void generateData()
    {
        //线
        List<Line> lines = new ArrayList<>();
        //点
        List<PointValue> values = new ArrayList<>();
        int indexX=0;
        /**
         * 处理点
         */
        for (Integer value : table.values())
        {

            values.add(new PointValue(indexX, value)); //PointValue有x横坐标和y纵坐标，这里x日期，y为money
            indexX++;
        }

        /**
         * 处理线
         */
        Line line = new Line(values);
        //设置线颜色
        line.setColor(ChartUtils.COLOR_BLUE);
        //设置点为圆点
        line.setShape(ValueShape.CIRCLE);
        //设置点的颜色
        line.setPointColor(ChartUtils.COLOR_RED);
        lines.add(line);

        mLineChartData = new LineChartData();
        mLineChartData.setLines(lines);
        mLineChartView.setLineChartData(mLineChartData);




    }

    private void generateValues(List<CostBean> dataList)
    {
        if (dataList != null)
        {
            for (int i = 0; i < dataList.size(); i++)
            {
                CostBean costBean = dataList.get(i);
                String costDate = costBean.getCostDate();
                int money = Integer.parseInt(costBean.getCostMoney());
                //同一个日期还没有重复
                if (!table.containsKey(costDate))
                {
                    table.put(costDate, money);
                }
                //有重复的日期,money就累加
                else
                {
                    //原来的money
                    int oldMoney = table.get(costDate);
                    //money累加
                    table.put(costDate, oldMoney + money);
                }
            }
        }
    }
}
