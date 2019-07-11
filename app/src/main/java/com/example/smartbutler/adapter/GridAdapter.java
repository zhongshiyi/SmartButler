package com.example.smartbutler.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.GirlData;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.smartbutler.R.layout.girl_item;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.adapter
 * 文件名：    GridAdapter
 * 作者：      钟士宜
 * 创建时间    2019/7/11 21:09
 * 描述：      女孩适配器
 */
public class GridAdapter extends BaseAdapter {

    private Context context;
    private List<GirlData>mList;
    private LayoutInflater inflater;
    private GirlData data;

    private WindowManager windowManager;
    //屏幕宽
    private int width;

    @SuppressLint("ServiceCast")
    public GridAdapter(Context context, List<GirlData> mList){
        this.context =context;
        this.mList = mList;
        //获取系统服务
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        width = windowManager.getDefaultDisplay().getWidth();
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
            convertView = inflater.inflate(R.layout.girl_item,null);
            viewHolder.imageView = convertView.findViewById(R.id.image_view);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        data = mList.get(position);
        //解析图片
        String url = data.getImgUrl();
        //加载图片
        //PicassoUtils.loadImageViewSize(context,url,width/2,250,viewHolder.imageView);
        Picasso.with(context).load(url).into(viewHolder.imageView);
        return convertView;
    }

    class ViewHolder{
        private ImageView imageView;
    }
}
