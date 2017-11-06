package com.example.yuan.imoocdaily;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by yuan on 2017/11/5.
 */

public class CostListAdapter extends BaseAdapter
{
    private Context context;
    private List<CostBean> mList;

    public CostListAdapter(Context context, List<CostBean> mList)
    {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
            viewHolder.costTitle = (TextView) convertView.findViewById(R.id.text_view_title);
            viewHolder.costDate = (TextView) convertView.findViewById(R.id.text_view_date);
            viewHolder.costMoney = (TextView) convertView.findViewById(R.id.text_view_cost);
            //调用convertView的setTag()方法保存这个类对象
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CostBean bean = mList.get(position);
        viewHolder.costTitle.setText(bean.getCostTitle());
        viewHolder.costDate.setText(bean.getCostDate());
        viewHolder.costMoney.setText(bean.getCostMoney());

        return convertView;
    }

    static class ViewHolder
    {
        TextView costTitle;
        TextView costDate;
        TextView costMoney;
    }

}
