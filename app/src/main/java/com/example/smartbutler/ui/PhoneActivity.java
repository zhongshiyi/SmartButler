package com.example.smartbutler.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbutler.R;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.StaticClass;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    PhoneActivity
 * 作者：      钟士宜
 * 创建时间    2019/7/6 17:00
 * 描述：      归属地查询
 */
public class PhoneActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_phone_number;//输入框
    private ImageView iv_company;//通信公司logo
    private TextView tv_result;//结果

    private Button btn_1, btn_2, btn_3, btn_4, btn_5, btn_6, btn_7, btn_8, btn_9, btn_0, btn_del, btn_query;

    //标记位
    private boolean flag = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        initView();
    }

    private void initView() {
        et_phone_number = findViewById(R.id.et_phone_number);
        iv_company = findViewById(R.id.iv_company);
        tv_result = findViewById(R.id.tv_result);
        btn_0 = findViewById(R.id.btn_0);
        btn_0.setOnClickListener(this);
        btn_1 = findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2 = findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        btn_3 = findViewById(R.id.btn_3);
        btn_3.setOnClickListener(this);
        btn_4 = findViewById(R.id.btn_4);
        btn_4.setOnClickListener(this);
        btn_5 = findViewById(R.id.btn_5);
        btn_5.setOnClickListener(this);
        btn_6 = findViewById(R.id.btn_6);
        btn_6.setOnClickListener(this);
        btn_7 = findViewById(R.id.btn_7);
        btn_7.setOnClickListener(this);
        btn_8 = findViewById(R.id.btn_8);
        btn_8.setOnClickListener(this);
        btn_9 = findViewById(R.id.btn_9);
        btn_9.setOnClickListener(this);
        btn_del = findViewById(R.id.btn_del);
        btn_del.setOnClickListener(this);
        btn_query = findViewById(R.id.btn_query);
        btn_query.setOnClickListener(this);

        //长按事件清空输入框
        btn_del.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                et_phone_number.setText("");
                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        /**
         * 1.获取输入框内容（电话number）
         * 2.判断是否为空
         * 3.网络请求
         * 4.解析Json
         * 5.结果显示
         * ....
         * 键盘逻辑
         */

        //获取输入框的内容
        String str = et_phone_number.getText().toString();

        switch (v.getId()) {
            case R.id.btn_0:
            case R.id.btn_1:
            case R.id.btn_2:
            case R.id.btn_3:
            case R.id.btn_4:
            case R.id.btn_5:
            case R.id.btn_6:
            case R.id.btn_7:
            case R.id.btn_8:
            case R.id.btn_9:
                if(flag){
                    flag = false;
                    et_phone_number.setText("");
                    str = "";
                }
                et_phone_number.setText(str + ((Button) v).getText());
                //移动光标
                et_phone_number.setSelection(str.length() + 1);
                break;
            case R.id.btn_del:
                if (!TextUtils.isEmpty(str) && str.length() > 0) {
                    //每次结尾减去1
                    et_phone_number.setText(str.substring(0, str.length() - 1));
                    //想做移动光标
                    et_phone_number.setSelection(str.length() - 1);
                }
                break;
            //查询
            case R.id.btn_query:
                getPhoneAddress(str);
                break;
        }
    }

    //获取归属地
    private void getPhoneAddress(String str) {
        String url = "http://apis.juhe.cn/mobile/get?phone=" + str + "&key=" + StaticClass.PHONE_KEY;
        RxVolley.get(url, new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                L.i("Phone：" + t);
                if(!TextUtils.isEmpty(t)){
                    parsingJson(t);
                }else{

                    Toast.makeText(PhoneActivity.this, StaticClass.EDIT_CANNOT_EMPTY, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    /**
     * 解析JSON数据
     * "resultcode":"200",
     * "reason":"Return Successd!",
     * "result":{
     * "province":"浙江",
     * "city":"杭州",
     * "areacode":"0571",
     * "zip":"310000",
     * "company":"中国移动",
     * "card":""
     */
    @SuppressLint("SetTextI18n")
    private void parsingJson(String t){
        try {
            JSONObject jsonObject = new JSONObject(t);
            JSONObject jsonResult = jsonObject.getJSONObject("result");
            String province = jsonResult.getString("province");
            String city = jsonResult.getString("city");
            String areacode = jsonResult.getString("areacode");
            String zip = jsonResult.getString("zip");
            String company = jsonResult.getString("company");

//            这种添加方法会使得再次查询时，数据叠加的问题，即新数据显示，旧数据却不消失
//            tv_result.append("归属地：" + province + city + "\n");
//            tv_result.append("区号：" + areacode + "\n");
//            tv_result.append("邮政编码：" + zip + "\n");
//            tv_result.append("运营商：" + company + "\n");
            //解决了上述的bug
            tv_result.setText("归属地：" + province + city + "\n"
            + "区号：" + areacode + "\n"
            + "邮政编码：" + zip + "\n"
            +"运营商：" + company);

            //图片显示
            switch (company){
                case "移动":
                    iv_company.setBackgroundResource(R.drawable.yidong);
                    break;
                case "联通":
                    iv_company.setBackgroundResource(R.drawable.liantong);
                    break;
                case "电信":
                    iv_company.setBackgroundResource(R.drawable.dianxin);
                    break;
            }
            flag = true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
