package com.example.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.WeChatData;

import org.w3c.dom.Text;

import java.util.List;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.adapter
 * 文件名：    WeChatAdapter
 * 作者：      钟士宜
 * 创建时间    2019/7/9 10:00
 * 描述：      微信精选adapter
 */
public class WeChatAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<WeChatData>mList;
    private WeChatData data;

    public WeChatAdapter(Context context,List<WeChatData>mList){
        this.context =context;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.wechat_item,null);
            viewHolder.iv_wechat_img = convertView.findViewById(R.id.iv_wechat_img);
            viewHolder.tv_title = convertView.findViewById(R.id.tv_title);
            viewHolder.tv_source = convertView.findViewById(R.id.tv_source);
            //设置Tag
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mList.get(position);
        viewHolder.tv_title.setText(data.getTitle());
        viewHolder.tv_source.setText(data.getSource());
        return convertView;
    }

    class ViewHolder{
        private ImageView iv_wechat_img;
        private TextView tv_title;
        private TextView tv_source;
    }
}
