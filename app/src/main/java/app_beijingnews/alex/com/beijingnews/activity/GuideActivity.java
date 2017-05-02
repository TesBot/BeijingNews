package app_beijingnews.alex.com.beijingnews.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import app_beijingnews.alex.com.beijingnews.R;
import app_beijingnews.alex.com.beijingnews.SplashActivity;
import app_beijingnews.alex.com.beijingnews.utils.CacheUtils;
import app_beijingnews.alex.com.beijingnews.utils.DensityUtil;

public class GuideActivity extends Activity {

    private ViewPager viewpager;
    private Button btn_start_main;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageViews;
    private ImageView iv_red_point;

    private int widthdpi;

    private int leftmax; //两点间距

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        viewpager = (ViewPager) findViewById(R.id.viewpager);
        btn_start_main = (Button) findViewById(R.id.btn_start_main);
        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
        iv_red_point = (ImageView) findViewById(R.id.iv_red_point);

        int[] ids = new int[]{
            R.drawable.guide_1,
            R.drawable.guide_2,
            R.drawable.guide_3
        };
        widthdpi = DensityUtil.dip2px(this,10);

        imageViews = new ArrayList<>();
        for (int i=0;i<ids.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(ids[i]);

            //添加到集合
            imageViews.add(imageView);

            //创建点，添加到线性布局
            ImageView point = new ImageView(this);
            point.setBackgroundResource(R.drawable.point_normal);
            //单位 像素,把单位当成dp，转成相对应的像素
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(widthdpi,widthdpi);
            if(i !=0){
                //不包括第0个，所有点距离左边10像素
                params.leftMargin = widthdpi;
            }
            point.setLayoutParams(params);
            ll_point_group.addView(point);

        }

        //设置ViewPager的适配器
        viewpager.setAdapter(new MyPagerAdapter());

        //根据View生命周期，当视图执行到onLayout或onDraw，视图高宽，边距都有
        iv_red_point.getViewTreeObserver().addOnGlobalLayoutListener(new MyOnGlobalLayoutListener());

        //得到屏幕滑动的百分比
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

        //设置按钮的点击事件
        btn_start_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存曾经进入过主页面
                CacheUtils.putBoolean(GuideActivity.this, SplashActivity.START_MAIN,true);

                //跳转到主页面
                Intent intent = new Intent(GuideActivity.this,MainActivity.class);
                startActivity(intent);
                //关闭当前引导
                finish();
            }
        });
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        /**
         * 页面滚动回调
         * @param position   当前滑动页面的位置
         * @param positionOffset   页面滑动的百分比
         * @param positionOffsetPixels  滑动的像素
         */
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            //两点间移动的距离 = 屏幕滑动百分比 * 间距
//            int leftmargin = (int) (positionOffset * leftmax);

            //两点间滑动距离对应的左边 = 原来的起始位置 + 两点间移动的距离
            int leftmargin = (int) (position * leftmax + (positionOffset * leftmax));
            //params.leftMargin = 两点间滑动距离对应的坐标
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) iv_red_point.getLayoutParams();
            params.leftMargin = leftmargin;
            iv_red_point.setLayoutParams(params);

        }

        /**
         * 页面被选中回调
         * @param position  被选中的页面的位置
         */
        @Override
        public void onPageSelected(int position) {
            if(position == imageViews.size() -1 ){
                //最后一个页面
                btn_start_main.setVisibility(View.VISIBLE);
            }else{
                btn_start_main.setVisibility(View.GONE);
            }
        }

        /**
         * 当ViewPager滑动状态发生变化的时候
         * @param state
         */
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {

        @Override
        public void onGlobalLayout() {
            //执行不止一次
            iv_red_point.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            //间距
            leftmax = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();
        }
    }

    class MyPagerAdapter extends PagerAdapter{
        //返回数据总个数
        @Override
        public int getCount() {
            return imageViews.size();
        }

        /**
         * 作用，getView
         * @param container  ViewPager
         * @param position   要创建页面的位置
         * @return  返回和创建当前页面关系的值
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = imageViews.get(position);
            //添加到容器
            container.addView(imageView);
            return imageView;
//            return super.instantiateItem(container, position);
        }

        /**
         * 销毁
         * @param container  ViewPager
         * @param position   要销毁的页面位置
         * @param object   要销毁的页面
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);

            container.removeView((View) object);
        }

        /**
         * 判断
         * @param view 当前视图
         * @param object
         * @return
         */
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
