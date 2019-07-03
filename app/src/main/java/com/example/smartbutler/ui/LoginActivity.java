package com.example.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbutler.MainActivity;
import com.example.smartbutler.R;
import com.example.smartbutler.entity.MyUser;
import com.example.smartbutler.utils.ShareUtils;
import com.example.smartbutler.view.CustomDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.example.smartbutler.utils.StaticClass.EDIT_CANNOT_EMPTY;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    LoginActivity
 * 作者：      钟士宜
 * 创建时间    2019/5/21 9:54
 * 描述：      登录
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //注册按钮
    private TextView tv_register;
    //登录按钮
    private Button btn_login;
    private EditText et_name;
    private EditText et_password;
    private CheckBox cb_remember_pass;
    private TextView tv_forget;

    private CustomDialog dialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
    }

    private void initView() {
        tv_register = findViewById(R.id.btn_register);
        tv_register.setOnClickListener(this);

        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);

        et_name = findViewById(R.id.et_name);
        et_password = findViewById(R.id.et_password);

        cb_remember_pass = findViewById(R.id.cb_remember_password);

        tv_forget = findViewById(R.id.tv_forget);
        tv_forget.setOnClickListener(this);

        //初始化dialog
        dialog = new CustomDialog(this,
                R.layout.dialog_loding,Gravity.CENTER);
        //屏幕外点击无效
        dialog.setCancelable(false);

        //设置选中的状态
        boolean isCheck = ShareUtils.getBoolean(this,"remember password",true);
        cb_remember_pass.setChecked(isCheck);
        if(isCheck){
            //设置密码
            et_name.setText(ShareUtils.getString(this,"name",""));
            et_password.setText(ShareUtils.getString(this,"password",""));
        }else{
            et_name.setText(ShareUtils.getString(this,"name",""));
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_register:
                startActivity(new Intent(this,RegisteredActivity.class));
                break;
            case R.id.btn_login:
                //1、获取输入框的值
                String name = et_name.getText().toString().trim();//trim()去空格
                String password = et_password.getText().toString().trim();//trim()去空格
                //2、判断是否为空
                if(!TextUtils.isEmpty(name) &  !TextUtils.isEmpty(password)){
                    //dialog
                    dialog.show();
                    //登录
                    final MyUser user = new MyUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>() {
                        @Override
                        public void done(MyUser myUser, BmobException e) {
                            //判断结果
                            if(e == null){
                                //dialog
                                dialog.dismiss();
                                //判断邮箱是否验证
                                if(user.getEmailVerified()){
                                    //跳转
                                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this,"请前往邮箱验证",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                //dialog
                                dialog.dismiss();
                                Toast.makeText(LoginActivity.this,"登录失败:" + e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(this,EDIT_CANNOT_EMPTY,Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_forget:
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                break;
        }
    }
    //假设我现在输入用户名和密码，但是我不点击登录，而是直接退出了
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //保存状态
        ShareUtils.putBoolean(this,"remember_pass",cb_remember_pass.isChecked());//不管初始是true还是false

        //是否记住密码
        if(cb_remember_pass.isChecked()){
            //记住用户名和密码
            ShareUtils.putString(this,"name",et_name.getText().toString().trim());
            ShareUtils.putString(this,"password",et_password.getText().toString().trim());
        }else{
            ShareUtils.deleShare(this,"password");
        }
    }
}
