package com.example.smartbutler.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.smartbutler.R;
import com.example.smartbutler.application.BaseApplication;
import com.example.smartbutler.utils.L;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import static com.example.smartbutler.utils.StaticClass.COURIER_KEY;
import static com.example.smartbutler.utils.StaticClass.EDIT_CANNOT_EMPTY;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    CourierActivity
 * 作者：      钟士宜
 * 创建时间    2019/7/6 8:40
 * 描述：      快递查询
 */
public class CourierActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_name;
    private EditText et_number;
    private Button btn_refer;
    private ListView lv_listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);

        initView();
    }

    private void initView() {
        et_name = findViewById(R.id.et_name);
        et_number = findViewById(R.id.et_number);
        btn_refer = findViewById(R.id.btn_refer);
        btn_refer.setOnClickListener(this);
        lv_listView = findViewById(R.id.lv_listView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_refer:
                /**
                 * 1.获取输入框的内容
                 * 2.判断输入框是否为空
                 * 3.拿到数据去请求数据（Json）
                 * 4.解析Json
                 * 5.ListView 适配器
                 * 6.ListView需要实体类（item的布局）
                 * 7.设置数据/显示效果
                 */
                //1.获取输入框的内容
                String name = et_name.getText().toString().trim();
                String number = et_number.getText().toString().trim();

                //拼接我们的url
                String url = "http://v.juhe.cn/exp/index?key="+ COURIER_KEY +"&com="+ name +"&no=" + number;
                //2.判断输入框是否为空
                if(!TextUtils.isEmpty(name) & !TextUtils.isEmpty(number)){
                    //3.拿到数据去请求数据（Json）
                    RxVolley.get(url, new HttpCallback() {
                        @Override
                        public void onSuccess(String t) {
                            Toast.makeText(CourierActivity.this,t,Toast.LENGTH_SHORT).show();
                            L.i("Json:" + t);
                        }
                    });
                }else{
                    Toast.makeText(this,EDIT_CANNOT_EMPTY,Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
