package com.example.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.smartbutler.MainActivity;
import com.example.smartbutler.R;
import com.example.smartbutler.utils.L;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名：    SmartButler
 * 包名：      com.example.smartbutler.ui
 * 文件名：    GuideActivity
 * 作者：      钟士宜
 * 创建时间    2019/5/18 21:19
 * 描述：      TODO
 */
public class GuideActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mViewPager;
    //容器
    private List<View> mlist = new ArrayList<>();
    private View view1,view2,view3;
    //小圆点
    private ImageView point1,point2,point3;
    //跳过
    private ImageView iv_jump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //初始化
        initView();
    }
    //初始化View
    private void initView() {

        iv_jump = findViewById(R.id.iv_jump);
        iv_jump.setOnClickListener(this);

        point1 = findViewById(R.id.point_1);
        point2 = findViewById(R.id.point_2);
        point3 = findViewById(R.id.point_3);


        //默认point是on的状态
        setPointImg(true,false,false);

        mViewPager = findViewById(R.id.guide_mViewPager);

        view1 = View.inflate(this,R.layout.pager_item_one,null);
        view2 = View.inflate(this,R.layout.pager_item_tow,null);
        view3 = View.inflate(this,R.layout.pager_item_three,null);

        view3.findViewById(R.id.btn_start).setOnClickListener(this);

        mlist.add(view1);
        mlist.add(view2);
        mlist.add(view3);

        //设置适配器
        mViewPager.setAdapter(new GuideAdapter());

        //监听Viewpager的滑动，设置小圆点的变化
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            //切换
            @Override
            public void onPageSelected(int position) {
                L.i("position:" + position);
                switch(position){
                    case 0:
                        //设置默认的图片
                        setPointImg(true,false,false);
                        iv_jump.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        //设置默认的图片
                        setPointImg(false,true,false);
                        iv_jump.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        //设置默认的图片
                        setPointImg(false,false,true);
                        iv_jump.setVisibility(View.GONE);
                        break;
                        default:
                            break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
    //点击事件
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_start:
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
                break;
            case R.id.iv_jump:
                startActivity(new Intent(GuideActivity.this,MainActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    class GuideAdapter extends PagerAdapter{
        //获得数量
        @Override
        public int getCount() {
            return mlist.size();
        }
        //比较
        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }
        //添加下一个页面
        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            container.addView(mlist.get(position));
            return mlist.get(position);
        }
        //删除当前页面
        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(mlist.get(position));
            //super.destroyItem(container, position, object);
        }
    }

    /**
     * 设置小圆点的选中效果
     * @param isCheck1
     * @param isCheck2
     * @param isCheck3
     */
    private void setPointImg(boolean isCheck1,boolean isCheck2,boolean isCheck3){
        if(isCheck1){
            point1.setBackgroundResource(R.drawable.point_on);
        }else{
            point1.setBackgroundResource(R.drawable.point_off);
        }
        if(isCheck2){
            point2.setBackgroundResource(R.drawable.point_on);
        }else{
            point2.setBackgroundResource(R.drawable.point_off);
        }
        if(isCheck3){
            point3.setBackgroundResource(R.drawable.point_on);
        }else{
            point3.setBackgroundResource(R.drawable.point_off);
        }
    }
}
