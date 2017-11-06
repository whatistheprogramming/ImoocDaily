package com.example.yuan.imoocdaily;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yuan on 2017/11/6.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{

    public static final String COST_MONEY = "cost_money";
    public static final String COST_TITLE = "cost_title";
    public static final String COST_DATE = "cost_date";
    public static final String IMOOC_COST = "imooc_cost";

    public DatabaseHelper(Context context)
    {
        //第2个参数是数据库名，第3个是工厂模式，第4个是版本
        super(context, "imooc_daily", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        //注意sql语句中间不要漏掉括号和逗号
        String sql = "create table if not exists imooc_cost(" +
                "id integer primary key," +
                "cost_title varchar," +
                "cost_date varchar," +
                "cost_money varchar)";
        db.execSQL(sql);

    }

    //插入一条数据
    public void insertCost(CostBean costBean)
    {
        //获取数据对象
        SQLiteDatabase database = getWritableDatabase();
        //用ContentValues存储数据
        ContentValues values = new ContentValues();
        values.put(COST_TITLE, costBean.getCostTitle());
        values.put(COST_DATE, costBean.getCostDate());
        values.put(COST_MONEY, costBean.getCostMoney());
        //插入到数据库中
        //第一个参数是数据库名，第二个是填null，第三个是ContentValues
        database.insert(IMOOC_COST, null, values);
    }

    /**
     * 查询所有结果，返回Cursor对象
     */
    public Cursor getAllCostData()
    {
        SQLiteDatabase database = getWritableDatabase();
        return database.query(IMOOC_COST, null, null, null, null, null, COST_DATE + " ASC"); //按照时间排序

    }

    /**
     * 清空数据库
     */
    public void deleteAllData()
    {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(IMOOC_COST, null, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }

}
