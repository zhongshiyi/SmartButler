package com.example.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.smartbutler.R;
import com.example.smartbutler.adapter.GridAdapter;
import com.example.smartbutler.entity.GirlData;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.StaticClass;
import com.google.gson.JsonArray;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

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
public class GirlFragment extends Fragment {

    private GridView mGridView;
    //数据集
    private List<GirlData>mList = new ArrayList<>();
    private GridAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_girl,container,false);

        findView(view);
        return view;
    }

    private void findView(View view) {
        mGridView = view.findViewById(R.id.mGridView);

        //解析
        RxVolley.get("http://api.tianapi.com/meinv/?key=" + StaticClass.GIRL_KEY,
                new HttpCallback() {
                    @Override
                    public void onSuccess(String t) {
                        L.i("Json：" + t);
                        parsingJson(t);
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
