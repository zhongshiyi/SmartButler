package com.example.smartbutler.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.MyUser;
import com.example.smartbutler.ui.LoginActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.example.smartbutler.utils.StaticClass.EDIT_CANNOT_EMPTY;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.fragment
 * 文件名：    UserFragment
 * 作者：      钟士宜
 * 创建时间    2019/5/18 10:30
 * 描述：      个人中心
 */
public class UserFragment extends Fragment implements View.OnClickListener {

    private Button btn_exit_user;
    private Button btn_edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_intro;
    private Button btn_comfirm_info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,null);
        findView(view);
        return view;
    }

    //初始化view
    private void findView(View view){
        btn_exit_user = view.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        btn_edit_user = view.findViewById(R.id.edit_user);
        btn_edit_user.setOnClickListener(this);
        btn_comfirm_info = view.findViewById(R.id.confirm_user_information);
        btn_comfirm_info.setOnClickListener(this);

        et_username = view.findViewById(R.id.et_username);
        et_sex = view.findViewById(R.id.et_sex);
        et_age = view.findViewById(R.id.et_age);
        et_intro = view.findViewById(R.id.et_intro);

        //默认是不可点击的/不可输入的
        setEnabled(false);

        //设置具体的值
        MyUser userinfo = BmobUser.getCurrentUser(MyUser.class);
        et_username.setText(userinfo.getUsername());
        et_age.setText(userinfo.getAge() + "");//getText是String类型的
        et_sex.setText(userinfo.isSex()?"男":"女");
        et_intro.setText(userinfo.getDesc());
    }

    //隐藏还是显示个人信息输入框
    private void setEnabled(boolean is){
        et_username.setEnabled(is);
        et_sex.setEnabled(is);
        et_age.setEnabled(is);
        et_intro.setEnabled(is);
    }

    //带"是"和"否"的消息提示框
    private void showExitDialog(){
         new AlertDialog.Builder(getContext())
                 .setTitle("带确定的提示框").setMessage("确定推出登录吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //消息框消失
                        dialog.dismiss();
                        //退出登录
                        MyUser.logOut();
                        startActivity(new Intent(getActivity(),LoginActivity.class));
                        getActivity().finish();
                    }
                })
                .setNegativeButton("取消", null)
                .create().show();
    }

    //点击事件
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_exit_user:
                //消息框
                showExitDialog();
                break;
                //编辑资料
            case R.id.edit_user:
                setEnabled(true);
                break;
                //确认修改
            case R.id.confirm_user_information:
                //1.拿到输入框的值
                String username = et_username.getText().toString();
                String age = et_age.getText().toString();
                String sex = et_sex.getText().toString();
                String intro = et_intro.getText().toString();

                //2.判断是否为空
                if (!TextUtils.isEmpty(username) & !TextUtils.isEmpty(age) & !TextUtils.isEmpty(sex)){
                    //判断属性
                    MyUser user = new MyUser();
                    user.setUsername(username);
                    user.setAge(Integer.parseInt(age));//强制转换
                    //性别
                    if(sex.equals("男")){
                        user.setSex(true);
                    }else{
                        user.setSex(false);
                    }
                    //简介
                    if(!TextUtils.isEmpty(intro)){
                        user.setDesc(intro);
                    }else{
                        user.setDesc("这个人很懒，什么都没有留下！");
                    }
                    //更新用户信息
                    BmobUser bmobUser = BmobUser.getCurrentUser();
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                //修改成功
                                setEnabled(false);
                                Toast.makeText(getActivity(),"修改成功！",Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(),"修改失败！",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(getActivity(),EDIT_CANNOT_EMPTY,Toast.LENGTH_SHORT).show();//输入框不能为空！
                }
                break;
        }
    }
}
