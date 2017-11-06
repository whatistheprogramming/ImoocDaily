package com.example.yuan.imoocdaily;

import java.io.Serializable;

/**
 * Created by yuan on 2017/11/5.
 */

public class CostBean implements Serializable //要通过Intent传递，就要实现序列化
{
    private String costTitle;
    private String costDate;
    private String costMoney;

    public String getCostTitle()
    {
        return costTitle;
    }

    public void setCostTitle(String costTitle)
    {
        this.costTitle = costTitle;
    }

    public String getCostDate()
    {
        return costDate;
    }

    public void setCostDate(String costDate)
    {
        this.costDate = costDate;
    }

    public String getCostMoney()
    {
        return costMoney;
    }

    public void setCostMoney(String costMoney)
    {
        this.costMoney = costMoney;
    }
}
