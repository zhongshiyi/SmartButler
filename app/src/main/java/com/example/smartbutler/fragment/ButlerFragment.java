package com.example.smartbutler.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.smartbutler.R;
import com.example.smartbutler.adapter.ChatListAdapter;
import com.example.smartbutler.entity.ChatListData;

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
    private Button btn_left, btn_right;
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

        btn_left = view.findViewById(R.id.btn_left);
        btn_left.setOnClickListener(this);
        btn_right = view.findViewById(R.id.btn_right);
        btn_right.setOnClickListener(this);

        //设置设配器
        adapter = new ChatListAdapter(getActivity(), mList);
        mChatListView.setAdapter(adapter);

        addLeftItem("您好，我是小管家！😄");
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                addLeftItem("左边");
                break;
            case R.id.btn_right:
                addRightItem("右边");
                break;
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
