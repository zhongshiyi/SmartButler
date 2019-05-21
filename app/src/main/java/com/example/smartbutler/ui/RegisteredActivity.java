package com.example.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    RegisteredActivity
 * 作者：      钟士宜
 * 创建时间    2019/5/21 10:48
 * 描述：      创建账号
 */
public class RegisteredActivity extends BaseActivity implements View.OnClickListener {


    private EditText et_mail;
    private EditText et_user;
    private EditText et_password;
    private EditText et_pass_verify;
    private EditText et_age;
    private EditText et_desc;
    private RadioGroup mRadioGroup;
    private Button btn_register;
    //性别
    private boolean isGender = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        et_mail = findViewById(R.id.edit_mail);
        et_user = findViewById(R.id.edit_user_register);
        et_password = findViewById(R.id.edit_password_register);
        btn_register = findViewById(R.id.btn_register_accomplish);
        et_age = findViewById(R.id.edit_age);
        mRadioGroup = findViewById(R.id.mRadioGroup);
        et_desc = findViewById(R.id.et_desc);
        et_pass_verify = findViewById(R.id.et_pass_verify);

        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register_accomplish:
                //获取到输入框的值
                String name = et_user.getText().toString().trim();
                String email = et_mail.getText().toString().trim();
                String password = et_password.getText().toString().trim();
                String pass_verify = et_pass_verify.getText().toString().trim();
                String age = et_age.getText().toString().trim();
                String desc = et_desc.getText().toString().trim();

                //判断是否为空
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)
                        && !TextUtils.isEmpty(age) && !TextUtils.isEmpty(pass_verify)) {

                    //判断两次密码是否一样
                    if (pass_verify.equals(password)) {
                        //先判断性别
                        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (checkedId == R.id.rb_male) {
                                    isGender = true;
                                } else {
                                    isGender = false;
                                }
                            }
                        });

                        //判断简介是否为空
                        if (TextUtils.isEmpty(desc)) {
                            desc = "这个人很懒，什么都没有留下";
                        }

                        //注册
                        MyUser user = new MyUser();
                        user.setUsername(name);
                        user.setPassword(password);
                        user.setEmail(email);
                        user.setAge(Integer.parseInt(age));
                        user.setSex(isGender);
                        user.setDesc(desc);

                        user.signUp(new SaveListener<MyUser>() {
                            @Override
                            public void done(MyUser myUser, BmobException e) {
                                if (e == null) {
                                    Toast.makeText(RegisteredActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(RegisteredActivity.this, "注册失败:" + e.toString(), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    Toast.makeText(this, "输入框不能为空！", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
