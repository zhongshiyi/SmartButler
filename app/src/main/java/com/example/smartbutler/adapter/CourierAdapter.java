package com.example.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.CourierData;
import com.example.smartbutler.ui.BaseActivity;

import java.util.List;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.adapter
 * 文件名：    CourierAdapter
 * 作者：      钟士宜
 * 创建时间    2019/7/6 11:13
 * 描述：      快递查询的适配器
 */
public class CourierAdapter extends BaseAdapter {

    private Context context;
    private List<CourierData> mList;
    //布局加载器
    private LayoutInflater inflater;
    private CourierData data;

    public CourierAdapter(Context context,List<CourierData> mList){
        this.context = context;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public CourierAdapter(){

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder  viewHolder = null;
        //第一次加载
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.layout_courier_item,null);
            viewHolder.tv_remark = convertView.findViewById(R.id.tv_remark);
            viewHolder.tv_datetime = convertView.findViewById(R.id.tv_datetime);
            viewHolder.tv_zone = convertView.findViewById(R.id.tv_zone);

            //设置缓存
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //设置数据
        data = mList.get(position);

        viewHolder.tv_remark.setText(data.getRemark());
        viewHolder.tv_zone.setText(data.getZone());
        viewHolder.tv_datetime.setText(data.getDatetime());
        return convertView;
    }

    class ViewHolder{
        private TextView tv_zone;
        private TextView tv_datetime;
        private TextView tv_remark;
    }
}
