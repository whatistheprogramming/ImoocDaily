package com.example.yuan.imoocdaily;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private ListView costList;

    private List<CostBean> mCostBeanList = new ArrayList<>();

    private DatabaseHelper mDatabaseHelper;
    private CostListAdapter adapter;

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
        adapter = new CostListAdapter(this, mCostBeanList);
        costList.setAdapter(adapter);




        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //创建对话框
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                //创建LayoutInflater
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View viewDialog = inflater.inflate(R.layout.dialog_cost_data, null);
                final EditText title = (EditText) viewDialog.findViewById(R.id.edit_cost_title);
                final EditText money = (EditText) viewDialog.findViewById(R.id.edit_cost_money);
                final DatePicker date = (DatePicker) viewDialog.findViewById(R.id.date_picker);

                //给对话框设置view对象
                dialog.setView(viewDialog);

                //给对话框添加标题
                dialog.setTitle("New Cost");

                /**
                 * 给对话框加入监听事件，当点击确定时，取出数据
                 */
                //设置OK按钮
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        CostBean costBean = new CostBean();
                        costBean.setCostTitle(title.getText().toString());
                        costBean.setCostMoney(money.getText().toString());
                        //取出时间
                        String dateTime = date.getYear() + "-" + (date.getMonth() + 1) + "-" + date.getDayOfMonth();
                        costBean.setCostDate(dateTime);

                        //最后插入到数据库中
                        mDatabaseHelper.insertCost(costBean);

                        //插入到dataList中
                        mCostBeanList.add(costBean);

                        //通知listview更新数据源,基于dataList的，所以dataList先要有变化,否则不能刷新
                        adapter.notifyDataSetChanged();


                    }
                });

                //设置取消按钮
                dialog.setNegativeButton("Cancel", null);

                //千万记住要调用dialog的show()方法让它显示,先创建
                dialog.create().show();
    }
});
        }

private void initCostData()
{
//        mDatabaseHelper.deleteAllData();
//        for (int i = 0; i < 6; i++)
//        {
//            CostBean costBean = new CostBean();
//            costBean.setCostDate("11-11");
//            costBean.setCostMoney(i * 234 + "");
//            costBean.setCostTitle("item" + i);
//            插入到数据库中
//            mDatabaseHelper.insertCost(costBean);
//        }
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
        if (id == R.id.action_chart)
        {
            //当点击菜单项，弹出图表
            Intent intent = new Intent(MainActivity.this, ChartsActivity.class);
            //将mCostBeanList序列化并通过intent传递到ChartsActivity中
            intent.putExtra("cost_list", (Serializable) mCostBeanList);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
