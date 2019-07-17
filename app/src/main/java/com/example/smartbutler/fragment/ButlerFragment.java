package com.example.smartbutler.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smartbutler.R;
import com.example.smartbutler.adapter.ChatListAdapter;
import com.example.smartbutler.entity.ChatListData;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.fragment
 * 文件名：    ButlerFragment
 * 作者：      钟士宜
 * 创建时间    2019/5/18 10:29
 * 描述：      管家服务
 */
public class ButlerFragment extends Fragment implements View.OnClickListener {

    private ListView mChatListView;
    private EditText et_chat_text;
    private Button btn_sent;
    //数据源
    private List<ChatListData> mList = new ArrayList<>();
    private ChatListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler, container, false);

        findView(view);
        return view;
    }

    private void findView(View view) {
        mChatListView = view.findViewById(R.id.mChatListView);
        //输入框
        et_chat_text = view.findViewById(R.id.et_chat_text);
        btn_sent = view.findViewById(R.id.btn_sent);
        btn_sent.setOnClickListener(this);

        //设置设配器
        adapter = new ChatListAdapter(getActivity(), mList);
        mChatListView.setAdapter(adapter);

        addLeftItem("您好，我是小优！😄");
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sent:
                /**
                 * 逻辑
                 * 1.获取输入框的内容
                 * 2.判断是否为空
                 * 3.输入字节在1-128字符之间
                 * 4.清空输入框
                 * 5.添加你输入的内容到right item
                 * 6.发送给机器人请求返回内容
                 * 7.拿到机器人的返回值后添加到left item
                 */
                //1.获取输入框内容
                String text = et_chat_text.getText().toString();
                //2.判断是否为空
                if(!TextUtils.isEmpty(text)){
                    //3.判断是否在1-128个字节之间
                    if(text.length() > 128 || text.length() < 1 ){
                        Toast.makeText(getActivity(),"输入字符长度应在1-128之间！",Toast.LENGTH_SHORT).show();
                    }else{
                        //4.清空输入框
                        et_chat_text.setText("");
                        //5.添加你输入的内容到right item
                        addRightItem(text);
                        //6.发送给机器人请求返回内容
                        String url = "http://www.tuling123.com/openapi/api?key="+StaticClass.CHAT_LIST_KEY + "&info=" + text;
                        RxVolley.get(url, new HttpCallback() {
                            @Override
                            public void onSuccess(String t) {
                                //Toast.makeText(getActivity(),"Json："+t,Toast.LENGTH_SHORT).show();
                                L.i("Json:" + t);
                                pasingJson(t);
                            }
                        });
                    }
                }else{
                    Toast.makeText(getActivity(),"输入框不能为空！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
    //解析Json数据
    private void pasingJson(String t){
        try{
            JSONObject jsonObject = new JSONObject(t);
            String text = jsonObject.getString("text");
            //7.拿到机器人的返回值后添加到left item
            addRightItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //添加左边的文本
    private void addLeftItem(String text) {
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_LEFT_TEXT);
        data.setText(text);
        mList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }

    //添加右边的文本
    private void addRightItem(String text) {
        ChatListData data = new ChatListData();
        data.setType(ChatListAdapter.VALUE_RIGHT_TEXT);
        data.setText(text);
        mList.add(data);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //滚动到底部
        mChatListView.setSelection(mChatListView.getBottom());
    }
}
