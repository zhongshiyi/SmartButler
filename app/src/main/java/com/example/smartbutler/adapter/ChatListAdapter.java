package com.example.smartbutler.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.ChatListData;

import java.util.List;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.adapter
 * 文件名：    ChatListAdapter
 * 作者：      钟士宜
 * 创建时间    2019/7/8 17:34
 * 描述：      对话adapter
 */
public class ChatListAdapter extends BaseAdapter {

    //左边的type
    public static final int VALUE_LEFT_TEXT = 1;
    //左边的type
    public static final int VALUE_RIGHT_TEXT = 2;

    private Context context;
    private LayoutInflater inflater;
    //实体类
    private ChatListAdapter data;
    private List<ChatListData> mList;

    public ChatListAdapter(Context context,List<ChatListData> mList){
        this.context = context;
        this.mList = mList;
        //获取系统服务
        inflater  = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        ViewHolderLeftText viewHolderLeftText = null;
        ViewHolderRightText viewHolderRightText = null;
        //获取当前要显示的type，根据这个type来区分数据的加载
        int type = getItemViewType(position);
        if(convertView == null){
            switch (type){
                case VALUE_LEFT_TEXT:
                    //加载左边的Item
                    viewHolderLeftText = new ViewHolderLeftText();
                    convertView = inflater.inflate(R.layout.left_item,null);
                    viewHolderLeftText.tv_left_text = convertView.findViewById(R.id.tv_left_text);
                    convertView.setTag(viewHolderLeftText);
                    break;
                case VALUE_RIGHT_TEXT:
                    //加载右边的Item
                    viewHolderRightText = new ViewHolderRightText();
                    convertView = inflater.inflate(R.layout.right_item,null);
                    viewHolderRightText.tv_right_text = convertView.findViewById(R.id.tv_right_text);
                    convertView.setTag(viewHolderRightText);
                    break;
            }
        }else{
            switch (type){
                case VALUE_LEFT_TEXT:
                    viewHolderLeftText = (ViewHolderLeftText) convertView.getTag();
                    break;
                case VALUE_RIGHT_TEXT:
                    viewHolderRightText = (ViewHolderRightText) convertView.getTag();
                    break;
            }
        }

        //赋值
        ChatListData data = mList.get(position);
        switch(type){
            case VALUE_LEFT_TEXT:
                viewHolderLeftText.tv_left_text.setText(data.getText());
                break;
            case VALUE_RIGHT_TEXT:
                viewHolderRightText.tv_right_text.setText(data.getText());
                break;
        }

        return convertView;
    }


    //根据数据源的position来返回要显示的item
    @Override
    public int getItemViewType(int position) {
        ChatListData data = mList.get(position);
        int type = data.getType();
        return type;
    }

    //返回所有的layout数据
    @Override
    public int getViewTypeCount() {
        return 3;//mList.size() + 1
    }

    //左边的文本
    class ViewHolderLeftText{
        private TextView tv_left_text;
    }

    //右边的文本
    class ViewHolderRightText{
        private TextView tv_right_text;
    }
}
