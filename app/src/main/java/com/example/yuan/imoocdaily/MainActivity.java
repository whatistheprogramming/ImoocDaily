package com.example.yuan.imoocdaily;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private ListView costList;

    private List<CostBean> mCostBeanList = new ArrayList<>();

    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //获取ListView实例
        costList = (ListView) findViewById(R.id.list_view);

        mDatabaseHelper = new DatabaseHelper(this);


        //提供数据
        initCostData();
        //ListView设置适配器
        costList.setAdapter(new CostListAdapter(this, mCostBeanList));




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initCostData()
    {
        //清空数据
        mDatabaseHelper.deleteAllData();
        for (int i = 0; i < 20; i++)
        {
            CostBean costBean = new CostBean();
            costBean.setCostDate(i * 39 + "");
            costBean.setCostMoney(i * 234 + "");
            costBean.setCostTitle("item" + i);
            //插入到数据库中
            mDatabaseHelper.insertCost(costBean);
        }
        Cursor cursor = mDatabaseHelper.getAllCostData();
        if (cursor != null)
        {
            while (cursor.moveToNext())
            {
                CostBean costBean = new CostBean();
                costBean.setCostTitle(cursor.getString(cursor.getColumnIndex("cost_title")));
                costBean.setCostDate(cursor.getString(cursor.getColumnIndex("cost_date")));
                costBean.setCostMoney(cursor.getString(cursor.getColumnIndex("cost_money")));
                //把从数据库查出来的数据添加到dataList中去
                mCostBeanList.add(costBean);

            }
            //不要忘记close
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
