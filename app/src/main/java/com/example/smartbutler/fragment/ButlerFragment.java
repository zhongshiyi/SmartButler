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
import com.example.smartbutler.utils.ShareUtils;
import com.example.smartbutler.utils.StaticClass;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
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

    //TTS
    private SpeechSynthesizer mTts;

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



        //1.创建SpeechSynthesizer对象
        //mTts= SpeechSynthesizer.createSynthesizer(getActivity(),null);
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        //mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        //mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        //mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        //设置合成音频保存位置（可自定义保存位置），保存在“./tts_test.pcm”
        //如果不需要保存合成音频，注释该行代码
        //mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./tts_test.pcm");
        //3.开始合成
        //mTts.startSpeaking("语音合成测试程序", mSynListener);

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
            addLeftItem(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //添加左边的文本
    private void addLeftItem(String text) {

        boolean isSpeak = ShareUtils.getBoolean(getActivity(),"isSpeak",false);
        if(isSpeak){
            startSpeak(text);//说话
        }

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

    //3.开始合成
    private void startSpeak(String text){
        mTts.startSpeaking(text, mSynListener);
    }

    //合成监听器
    private SynthesizerListener mSynListener = new SynthesizerListener(){

        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {}

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }
        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {}
        //开始播放
        public void onSpeakBegin() {}
        //暂停播放
        public void onSpeakPaused() {}
        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {}
        //恢复播放回调接口
        public void onSpeakResumed() {}
    };
}
