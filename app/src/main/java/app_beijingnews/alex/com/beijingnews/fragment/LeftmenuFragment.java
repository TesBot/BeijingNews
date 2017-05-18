package app_beijingnews.alex.com.beijingnews.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import app_beijingnews.alex.com.beijingnews.R;
import app_beijingnews.alex.com.beijingnews.activity.MainActivity;
import app_beijingnews.alex.com.beijingnews.base.BaseFragment;
//import app_beijingnews.alex.com.beijingnews.domain.NewsCenterPagerBean;
import app_beijingnews.alex.com.beijingnews.domain.NewsCenterPagerBean2;
import app_beijingnews.alex.com.beijingnews.pager.NewsCenterPager;
import app_beijingnews.alex.com.beijingnews.utils.DensityUtil;
import app_beijingnews.alex.com.beijingnews.utils.LogUtil;

/**左侧菜单的Fragment
 * Created by Jhin on 2017/5/4 0004.
 */

public class LeftmenuFragment extends BaseFragment {

//    private List<NewsCenterPagerBean.DataBean> data;
    private List<NewsCenterPagerBean2.DetailPagerData> data;

    private ListView listView;

    private LeftmenuFragmentAdapter adapter;

    //点击的位置
    private int prePosition;

    @Override
    public View initView() {
        LogUtil.e("左侧菜单视图被初始化");
        listView = new ListView(context);
        listView.setPadding(0, DensityUtil.dip2px(context,40),0,0);
        listView.setDividerHeight(0); //设置分割线高度0
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setSelector(android.R.color.transparent); //设置按下item不变色

        //设置item点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //记录点击位置
                prePosition = position;
                adapter.notifyDataSetChanged(); //getCount（） --》getView()
                //把左侧菜单关闭
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle(); //关-》开；开-》关
                //切换到对应的详情页面：
                switchPager(prePosition);

            }
        });
        return listView;
    }

    /**
     * 根据位置切换不同的详情页面
     * @param position
     */
    private void switchPager(int position) {
        MainActivity mainActivity = (MainActivity) context;

        ContentFragment contentFragment = mainActivity.getContentFragment();
        NewsCenterPager newsCenterPager = contentFragment.getNewsCenterPager();
        newsCenterPager.switchPager(position);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("左侧菜单数据被初始化");

    }

    //接收数据
//    public void setData(List<NewsCenterPagerBean.DataBean> data) {
    public void setData(List<NewsCenterPagerBean2.DetailPagerData> data) {
        this.data = data;
        for(int i=0; i<data.size();i++){

            LogUtil.e("title== "+data.get(i).getTitle());
        }

        //设置适配器（在这里设置肯定有数据）
        adapter = new LeftmenuFragmentAdapter();
        listView.setAdapter(adapter);

        //设置默认的页面
        switchPager(prePosition);
    }

    class LeftmenuFragmentAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = (TextView) View.inflate(context,R.layout.item_leftmenu,null);
            textView.setText(data.get(position).getTitle());
//            LogUtil.e(data.get(position).getTitle());
            if (position == prePosition){
                //红色
                textView.setEnabled(true);
            }
            else{
                textView.setEnabled(false);
            }
            //或者写成：
//            textView.setEnabled(position == prePosition);
            return textView;
        }
    }
}
