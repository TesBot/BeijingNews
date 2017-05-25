package app_beijingnews.alex.com.beijingnews.menudetailpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.viewpagerindicator.TabPageIndicator;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import app_beijingnews.alex.com.beijingnews.R;
import app_beijingnews.alex.com.beijingnews.activity.MainActivity;
import app_beijingnews.alex.com.beijingnews.base.MenuDetailBasePager;
import app_beijingnews.alex.com.beijingnews.domain.NewsCenterPagerBean2;
import app_beijingnews.alex.com.beijingnews.menudetailpager.tabledetailpager.TableDetailPager;
import app_beijingnews.alex.com.beijingnews.utils.LogUtil;

/**
 * Created by Jhin on 2017/5/17 0017.
 * 新闻详情页面
 */

public class NewsMenuDetailPager extends MenuDetailBasePager{

    @ViewInject(R.id.tabPageIndicator)
    private TabPageIndicator tabPageIndicator;

    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;

    @ViewInject(R.id.ib_tab_next)
    private ImageButton ib_tab_next;

    //对应的页签页面数据集合
    private List<NewsCenterPagerBean2.DetailPagerData.ChildrenData> children;
    //页签页面集合
    private ArrayList<TableDetailPager> tableDetailPagers;

    public NewsMenuDetailPager(Context context, NewsCenterPagerBean2.DetailPagerData detailPagerData) {
        super(context);
        children = detailPagerData.getChildren();
    }

    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.newsmenu_detail_pager,null);
        x.view().inject(this,view);

        //设置点击事件
        ib_tab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            }
        });
        return view;
    }

    @Override
    public void initData() {
        super.initData();

        LogUtil.e("新闻详情页面数据被初始化。。。");

        //准备新闻详情页面的数据
        tableDetailPagers = new ArrayList<>();
        for(int i=0; i<children.size(); i++){
            tableDetailPagers.add(new TableDetailPager(context,children.get(i)));
        }

        //设置ViewPager适配器
        viewPager.setAdapter(new MyNewsMenuDetailPagerAdapter());
        //ViewPager和TabPageIndicator关联
        tabPageIndicator.setViewPager(viewPager);

        //注意监听页面变化，用TabPageIndicator来监听页面
        tabPageIndicator.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0){
                //当前是北京，菜单可以全屏滑动
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_FULLSCREEN);
            }else{
                isEnableSlidingMenu(SlidingMenu.TOUCHMODE_NONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

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

    class MyNewsMenuDetailPagerAdapter extends PagerAdapter{

        @Override
        public CharSequence getPageTitle(int position) {
            return children.get(position).getTitle();
        }

        @Override
        public int getCount() {
            return tableDetailPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TableDetailPager tabDetailPager = tableDetailPagers.get(position);
            View rootView = tabDetailPager.rootView;
            tabDetailPager.initData(); //初始化数据
            container.addView(rootView);
            return rootView;
        }
    }
}
