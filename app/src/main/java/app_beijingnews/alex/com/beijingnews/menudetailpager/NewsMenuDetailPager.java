package app_beijingnews.alex.com.beijingnews.menudetailpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import app_beijingnews.alex.com.beijingnews.R;
import app_beijingnews.alex.com.beijingnews.base.MenuDetailBasePager;
import app_beijingnews.alex.com.beijingnews.domain.NewsCenterPagerBean2;
import app_beijingnews.alex.com.beijingnews.menudetailpager.tabledetailpager.TableDetailPager;
import app_beijingnews.alex.com.beijingnews.utils.LogUtil;

/**
 * Created by Jhin on 2017/5/17 0017.
 * 新闻详情页面
 */

public class NewsMenuDetailPager extends MenuDetailBasePager{

    @ViewInject(R.id.viewpager)
    private ViewPager viewPager;

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
    }

    class MyNewsMenuDetailPagerAdapter extends PagerAdapter{

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
