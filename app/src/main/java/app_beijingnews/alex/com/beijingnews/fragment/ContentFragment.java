package app_beijingnews.alex.com.beijingnews.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import app_beijingnews.alex.com.beijingnews.R;
import app_beijingnews.alex.com.beijingnews.activity.MainActivity;
import app_beijingnews.alex.com.beijingnews.adapter.ContentFragmentAdapter;
import app_beijingnews.alex.com.beijingnews.base.BaseFragment;
import app_beijingnews.alex.com.beijingnews.base.BasePager;
import app_beijingnews.alex.com.beijingnews.pager.GovaffairPager;
import app_beijingnews.alex.com.beijingnews.pager.HomePager;
import app_beijingnews.alex.com.beijingnews.pager.NewsCenterPager;
import app_beijingnews.alex.com.beijingnews.pager.SettingPager;
import app_beijingnews.alex.com.beijingnews.pager.SmartServicePager;
import app_beijingnews.alex.com.beijingnews.utils.LogUtil;
import app_beijingnews.alex.com.beijingnews.view.NoScrollViewPager;

/**正文Fragment
 * Created by Jhin on 2017/5/4 0004.
 */

public class ContentFragment extends BaseFragment {

    //初始化控件
    @ViewInject(R.id.viewpager)
    private NoScrollViewPager viewpager;

    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;
    /**
     * 装五个页面的集合
     */
    private ArrayList<BasePager> basePagers;


    @Override
    public View initView() {
        LogUtil.e("正文Fragment视图被初始化");
        View view = View.inflate(context, R.layout.content_fragment, null);

        //把视图注入到框架中，让此类和View关联起来
        x.view().inject(ContentFragment.this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文数据被初始化");

        //初始化五个页面，并放入集合中
        basePagers = new ArrayList<>();
        basePagers.add(new HomePager(context));
        basePagers.add(new NewsCenterPager(context));
        basePagers.add(new SmartServicePager(context));
        basePagers.add(new GovaffairPager(context));
        basePagers.add(new SettingPager(context));


        //设置ViewPager适配器
        viewpager.setAdapter(new ContentFragmentAdapter(basePagers));

        //监听radiogroup
        rg_main.setOnCheckedChangeListener(new MyOnCheckedChangeListener());

        //监听某个页面被选中，初始化对应的页面数据
        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());

        //设置默认选中
        rg_main.check(R.id.rb_home);
        basePagers.get(0).initData();
        //设置SlidingMenu默认不可以滑动
        isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);


    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        /**
         * 当某个页面被选中时候回调这个方法
         *
         * @param position 被选中页面的位置
         */
        @Override
        public void onPageSelected(int position) {
            //调用被选中页面的initDate方法
            basePagers.get(position).initData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener {

        /**
         * @param group
         * @param checkedId 选中的button的id
         */
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId) {
                case R.id.rb_home:
                    viewpager.setCurrentItem(0);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_newscenter:
                    viewpager.setCurrentItem(1);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
                    break;
                case R.id.rb_smartservice:
                    viewpager.setCurrentItem(2);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_govaffair:
                    viewpager.setCurrentItem(3);
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
                case R.id.rb_setting:
                    viewpager.setCurrentItem(4); //参数加false则无动画
                    isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
                    break;
            }
        }
    }

    /**
     * 根据传入的参数设置是否左滑SlidingMenu
     *
     * @param touchmodeFullscreen
     */
    private void isEnableSlidingMenu(int touchmodeFullscreen) {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.getSlidingMenu().setTouchModeAbove(touchmodeFullscreen);
    }
}
