package com.example.smartbutler.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartbutler.R;
import com.example.smartbutler.entity.MyUser;
import com.example.smartbutler.ui.CourierActivity;
import com.example.smartbutler.ui.LoginActivity;
import com.example.smartbutler.ui.PhoneActivity;
import com.example.smartbutler.utils.L;
import com.example.smartbutler.utils.ShareUtils;
import com.example.smartbutler.utils.UtilTools;
import com.example.smartbutler.view.CustomDialog;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

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

    //退出按钮
    private Button btn_exit_user;
    //编辑按钮
    private Button btn_edit_user;
    private EditText et_username;
    private EditText et_sex;
    private EditText et_age;
    private EditText et_intro;
    //确认更新
    private Button btn_confirm_info;
    //圆形头像
    private CircleImageView profile_image;
    //提示框
    private CustomDialog dialog;

    private Button btn_camera;
    private Button btn_picture;
    private Button btn_cancel;

    private TextView tb_courier;

    private TextView tv_phone;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user,container,false);
        findView(view);
        return view;
    }

    //初始化view
    private void findView(View view){
        btn_exit_user = view.findViewById(R.id.btn_exit_user);
        btn_exit_user.setOnClickListener(this);
        btn_edit_user = view.findViewById(R.id.edit_user);
        btn_edit_user.setOnClickListener(this);
        btn_confirm_info = view.findViewById(R.id.confirm_user_information);
        btn_confirm_info.setOnClickListener(this);

        et_username = view.findViewById(R.id.et_username);
        et_sex = view.findViewById(R.id.et_sex);
        et_age = view.findViewById(R.id.et_age);
        et_intro = view.findViewById(R.id.et_intro);

        profile_image = view.findViewById(R.id.profile_image);
        profile_image.setOnClickListener(this);
        //物流
        tb_courier = view.findViewById(R.id.tb_courier);
        tb_courier.setOnClickListener(this);
        //号码归属地
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_phone.setOnClickListener(this);
        //读取图片
        UtilTools.getImageView(getActivity(),profile_image);

        //初始化dialog
        dialog = new CustomDialog(getActivity(),
                R.layout.dialog_photo,Gravity.BOTTOM);

        //屏幕外点击无效
        dialog.setCancelable(false);

        //初始化dialog中的按钮
        btn_camera = dialog.findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(this);
        btn_picture = dialog.findViewById(R.id.btn_picture);
        btn_picture.setOnClickListener(this);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_cancel.setOnClickListener(this);

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
                 .setTitle(" ").setMessage("确定推出登录吗？")
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
                //选择头像
            case R.id.profile_image:
                dialog.show();
                break;
                //拍照
            case R.id.btn_camera:
                dialog.dismiss();
                toCamera();
                break;
                //相册
            case R.id.btn_picture:
                toPicture();
                break;
                //取消
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
                //快递查询
            case R.id.tb_courier:
                startActivity(new Intent(getActivity(),CourierActivity.class));
                break;
                //归属地查询
            case R.id.tv_phone:
                startActivity(new Intent(getActivity(),PhoneActivity.class));
                break;
        }
    }

    public static final String PHOTO_IMAGE_FILE_NAME = "fileIma.jpg";
    public static final int CAMERA_REQUEST_CODE = 100;
    public static final int IMAGE_REQUEST_CODE = 101;
    public static final int RESULT_REQUEST_CODE = 102;
    private File tempFile = null;

    //跳转相机
    private void toCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断内存卡是否可用，可用的话就进行储存
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(new File(Environment.getExternalStorageDirectory(), PHOTO_IMAGE_FILE_NAME)));
        startActivityForResult(intent,CAMERA_REQUEST_CODE);//跳转至相机界面
        dialog.dismiss();
    }
    //跳转相册
    private void toPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_REQUEST_CODE);//跳转至相册界面
        dialog.dismiss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != getActivity().RESULT_CANCELED){//"0"
            switch(requestCode){
                //相册数据
                case IMAGE_REQUEST_CODE:
                    startPhotoZoom(data.getData());
                    break;
                    //相机数据
                case CAMERA_REQUEST_CODE:
                    tempFile = new File(Environment.getExternalStorageDirectory(),PHOTO_IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //有可能点击舍去，得判断
                    if(data != null){
                        //拿到图片设置
                        setImageToView(data);
                        //既然已经设置了图片，我们原先的就应该删除
                        if(tempFile != null){
                            tempFile.delete();
                        }
                    }
                    break;
            }
        }
    }

    //相片裁剪
    private void startPhotoZoom(Uri uri){
        if(uri == null){
            L.e("uri == null");
            return;
        }
        Intent intent = new Intent("com.android.camera.action.CROP");
        //intent.setAction(Intent.ACTION_PICK);
        intent.setDataAndType(uri,"image/*");
        //设置裁剪
        intent.putExtra("crop","true");
        //裁剪狂傲比例
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        //裁剪图片的质量
        intent.putExtra("outputX",600);
        intent.putExtra("outputY",600);
        //发送数据
        intent.putExtra("return-data",true);

        intent.putExtra("scale",true);//支持缩放
        startActivityForResult(intent,RESULT_REQUEST_CODE);
    }

    //设置图片
    private void setImageToView(Intent data){
        Bundle bundle = data.getExtras();
        if(bundle != null){
            Bitmap bitmap = bundle.getParcelable("data");
            profile_image.setImageBitmap(bitmap);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //保存
        UtilTools.putImageToShare(getActivity(),profile_image);
    }
}
