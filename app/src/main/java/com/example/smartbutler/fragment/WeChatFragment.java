package com.example.smartbutler.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smartbutler.R;
import com.example.smartbutler.adapter.WeChatAdapter;
import com.example.smartbutler.entity.WeChatData;
import com.example.smartbutler.utils.StaticClass;
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
 * 文件名：    WeChatFragment
 * 作者：      钟士宜
 * 创建时间    2019/5/18 10:31
 * 描述：      微信精选
 */
public class WeChatFragment extends Fragment {

    private ListView weChat_mListView;

    private List<WeChatData>mList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wechat, container, false);

        findView(view);
        return view;
    }

    //初始化view
    private void findView(View view) {
        weChat_mListView = view.findViewById(R.id.weChat_mListView);

        //解析接口 http://v.juhe.cn/weixin/query?key=您申请的KEY
        String url = "http://v.juhe.cn/weixin/query?key=" + StaticClass.WECHAT_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                Toast.makeText(getActivity(), t, Toast.LENGTH_SHORT);
                pasingJson(t);
            }
        });
    }

    private void pasingJson(String t) {
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            JSONArray jsonList = jsonResult.getJSONArray("list");
            for(int i = 0;i < jsonList.length();i++){
                JSONObject json = (JSONObject) jsonList.get(i);
                WeChatData data = new WeChatData();
                data.setTitle(json.getString("title"));
                data.setSource(json.getString("source"));
                //暂不支持封面图片
                //data.setImgurl(json.getString("firstImg"));
                mList.add(data);
            }
            WeChatAdapter adapter = new WeChatAdapter(getActivity(),  mList);
            weChat_mListView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
