package app_beijingnews.alex.com.beijingnews.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import app_beijingnews.alex.com.beijingnews.R;
import app_beijingnews.alex.com.beijingnews.base.BaseFragment;
import app_beijingnews.alex.com.beijingnews.base.BasePager;
import app_beijingnews.alex.com.beijingnews.pager.GovaffairPager;
import app_beijingnews.alex.com.beijingnews.pager.HomePager;
import app_beijingnews.alex.com.beijingnews.pager.NewsCenterPager;
import app_beijingnews.alex.com.beijingnews.pager.SettingPager;
import app_beijingnews.alex.com.beijingnews.pager.SmartServicePager;
import app_beijingnews.alex.com.beijingnews.utils.LogUtil;

/**正文Fragment
 * Created by Jhin on 2017/5/4 0004.
 */

public class ContentFragment extends BaseFragment {

    //初始化控件
    @ViewInject(R.id.viewpager)
    private ViewPager viewpager;

    @ViewInject(R.id.rg_main)
    private RadioGroup rg_main;
    /**
     * 装五个页面的集合
     */
    private ArrayList<BasePager> basePagers;


    @Override
    public View initView() {
        LogUtil.e("正文Fragment视图被初始化");
        View view = View.inflate(context, R.layout.content_fragment,null);

        //把视图注入到框架中，让此类和View关联起来
        x.view().inject(ContentFragment.this,view);
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

        //设置默认选中

        //设置ViewPager适配器
        viewpager.setAdapter(new ContentFragmentAdapter());

    }

    class ContentFragmentAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return basePagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = basePagers.get(position);//个个页面的实例
            View rootView = basePager.rootview; //个个子页面
            //调用个个页面的initData（）方法
            basePager.initData();
            container.addView(rootView);

            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
