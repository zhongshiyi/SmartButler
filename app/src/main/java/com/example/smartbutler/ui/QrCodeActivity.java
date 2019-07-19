package com.example.smartbutler.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.smartbutler.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    QrCodeActivity
 * 作者：      钟士宜
 * 创建时间    2019/7/19 10:48
 * 描述：      生成二维码
 */
public class QrCodeActivity extends BaseActivity {

    //我的二维码
    private ImageView iv_qr_code;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        initView();
    }

    //初始化
    private void initView() {

        iv_qr_code = findViewById(R.id.iv_qr_code);

        //获取屏幕的宽
        int width = getResources().getDisplayMetrics().widthPixels;

        //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
        Bitmap qrCodeBitmap = EncodingUtils.createQRCode("我是智能管家", width, width,
                        //图片显示在二维码中央
                        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        iv_qr_code.setImageBitmap(qrCodeBitmap);
    }
}
