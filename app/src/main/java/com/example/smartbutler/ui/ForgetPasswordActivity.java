package com.example.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.smartbutler.utils.StaticClass.EDIT_CANNOT_EMPTY;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    ForgetPasswordActivity
 * 作者：      钟士宜
 * 创建时间    2019/5/23 19:25
 * 描述：      忘记/重置密码
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    private Button btn_forget_password;
    private EditText et_email;
    private EditText et_old_password;
    private EditText et_new_password;
    private EditText et_new_password_again;
    private Button btn_change_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget);

        initView();
    }

    private void initView() {
        btn_forget_password = findViewById(R.id.btn_forget_password);
        btn_forget_password.setOnClickListener(this);

        et_email = findViewById(R.id.et_email);
        et_old_password = findViewById(R.id.et_old_password);
        et_new_password = findViewById(R.id.et_new_password);
        et_new_password_again = findViewById(R.id.et_new_password_again);


        btn_change_password = findViewById(R.id.btn_change_password);
        btn_change_password.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_password:
                //1、获取新旧密码的值
                final String old_password = et_old_password.getText().toString().trim();
                final String new_password = et_new_password.getText().toString().trim();
                final String new_password_again = et_new_password_again.getText().toString().trim();
                //2、判断是否为空
                if (!TextUtils.isEmpty(old_password) & !TextUtils.isEmpty(new_password) & !TextUtils.isEmpty(new_password_again)){
                    //3、判断密码是否输入一致
                    if(new_password.equals(new_password_again)){
                        //重置密码
                        MyUser.updateCurrentUserPassword(old_password, new_password, new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(ForgetPasswordActivity.this,"更改密码成功！",Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ForgetPasswordActivity.this,"更改密码失败:"+ e.toString(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else{
                        Toast.makeText(ForgetPasswordActivity.this,"两次密码不同！",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ForgetPasswordActivity.this,EDIT_CANNOT_EMPTY,Toast.LENGTH_SHORT).show();
                }
                    break;
            case R.id.btn_forget_password:
                //1、获取输入框的邮箱
                final String email = et_email.getText().toString().trim();
                //2、判断是否为空
                if (!TextUtils.isEmpty(email)) {
                    //3、发送邮箱
                    MyUser.resetPasswordByEmail(email, new UpdateListener() {

                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                Toast.makeText(ForgetPasswordActivity.this, "邮件已经发送至："
                                        + email, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ForgetPasswordActivity.this, "邮箱发送失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(this, "邮箱不能为空！", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
