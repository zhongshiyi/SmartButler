package com.example.smartbutler.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smartbutler.R;


/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.fragment
 * 文件名：    ButlerFragment
 * 作者：      钟士宜
 * 创建时间    2019/5/18 10:29
 * 描述：      管家服务
 */
public class ButlerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_butler,container,false);
        return view;
    }
}
