package com.example.smartbutler.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.smartbutler.R;
import com.example.smartbutler.adapter.GridAdapter;
import com.example.smartbutler.entity.GirlData;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.PicassoUtils;
import com.example.smartbutler.utils.StaticClass;
import com.example.smartbutler.view.CustomDialog;
import com.google.gson.JsonArray;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.fragment
 * 文件名：    GirlFragment
 * 作者：      钟士宜
 * 创建时间    2019/5/18 10:30
 * 描述：      女孩社区
 */
public class GirlFragment extends Fragment  {

    //列表
    private GridView mGridView;
    //数据集
    private List<GirlData>mList = new ArrayList<>();
    //适配器
    private GridAdapter adapter;
    //提示框
    private Dialog dialog;
    //预览图片
    private ImageView iv_dialog_img;
    //图片地址的数据
    private List<String>mListUrl = new ArrayList<>();



    /**
     * 1.监听点击事件
     * 2.提示框
     * 3.加载图片
     * 4.PhotoView
     */

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl,container,false);

        findView(view);
        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void findView(View view) {
        mGridView = view.findViewById(R.id.mGridView);
        //初始化提示框
        dialog = new CustomDialog(getActivity(),LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,
                R.layout.dialog_girl,R.style.pop_anim_style,Gravity.CENTER);

        iv_dialog_img = dialog.findViewById(R.id.iv_dialog_img);

        //解析
        RxVolley.get("http://api.tianapi.com/meinv/?&key=" + StaticClass.GIRL_KEY + "&num=50",
                new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.i("Json：" + t);
                        parsingJson(t);
                    }
                });

        //监听点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //解析图片
                PicassoUtils.loadImageView(getActivity(),mListUrl.get(position),iv_dialog_img);

                //显示图片
                dialog.show();
            }
        });



    }

    //解析Json
    private void parsingJson(String t){
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONArray jsonArray = jsonObject.getJSONArray("newslist");
            for(int i = 0;i < jsonArray.length();i++){
                JSONObject json = (JSONObject) jsonArray.get(i);
                String url = json.getString("picUrl");

                mListUrl.add(url);

                GirlData data = new GirlData();
                data.setImgUrl(url);
                mList.add(data);

            }
            adapter = new GridAdapter(getActivity(),mList);
            //设置适配器
            mGridView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
