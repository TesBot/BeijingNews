package app_beijingnews.alex.com.beijingnews.menudetailpager.tabledetailpager;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import app_beijingnews.alex.com.beijingnews.R;
import app_beijingnews.alex.com.beijingnews.base.MenuDetailBasePager;
import app_beijingnews.alex.com.beijingnews.domain.NewsCenterPagerBean2;
import app_beijingnews.alex.com.beijingnews.domain.TabDetailPagerBean;
import app_beijingnews.alex.com.beijingnews.utils.CacheUtils;
import app_beijingnews.alex.com.beijingnews.utils.Constants;
import app_beijingnews.alex.com.beijingnews.utils.LogUtil;
import app_beijingnews.alex.com.beijingnews.view.HorizontalScrollViewPager;
import app_beijingnews.alex.com.beijingnews.view.RefreshListView;

/**
 * 页签详情页面
 * Created by Jhin on 2017/5/19 0019.
 */

public class TableDetailPager extends MenuDetailBasePager {

    private HorizontalScrollViewPager  viewpager;
    private TextView tv_title;
    private LinearLayout ll_point_group;
    private RefreshListView listview;
    private TabDetailPagerListAdapter adapter;
    private ImageOptions imageOptions;

    private final NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData;
    private String url;
    /**
     * 顶部轮播图部分数据
     */
    private List<TabDetailPagerBean.DataBean.TopnewsBean> topnews;
    /**
     * x新闻列表数据集合
     */
    private List<TabDetailPagerBean.DataBean.NewsBean> news;

    public TableDetailPager(Context context, NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData) {
        super(context);
        this.childrenData = childrenData;
        imageOptions = new ImageOptions.Builder()
                .setSize(DensityUtil.dip2px(100), DensityUtil.dip2px(100))
                .setRadius(DensityUtil.dip2px(5))
                // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.drawable.news_pic_default)
                .setFailureDrawableId(R.drawable.news_pic_default)
                .build();
    }



    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.tabdetail_pager,null);
        listview = (RefreshListView)view.findViewById(R.id.listview);

        View topNewsView = View.inflate(context,R.layout.topnews,null);
        viewpager = (HorizontalScrollViewPager ) topNewsView.findViewById(R.id.viewpager);
        tv_title = (TextView) topNewsView.findViewById(R.id.tv_title);
        ll_point_group = (LinearLayout) topNewsView.findViewById(R.id.ll_point_group);

        //把顶部轮播图视图，以头的方式添加到ListView中
        listview.addHeaderView(topNewsView);

        //设置监听下拉刷新
        listview.setOnRefreshListener(new MyOnRefreshListener());

        return view;
    }

    class MyOnRefreshListener implements RefreshListView.OnRefreshListener{

        @Override
        public void onPullDownRefresh() {
            getDataFromNet();
        }

        @Override
        public void onLoadMore() {

        }
    }

    @Override
    public void initData() {
        super.initData();
        url = Constants.BASE_URL + childrenData.getUrl();

        //把缓存的数据取出来
        String saveJson = CacheUtils.getString(context,url);
        if(!TextUtils.isEmpty(saveJson)){
            //解析数据
            processData(saveJson);
        }

//        LogUtil.e(childrenData.getTitle()+" 的联网地址： "+url);

        //联网请求数据
        getDataFromNet();
    }

    private void getDataFromNet() {
        RequestParams params = new RequestParams(url);
        //设置连接超时时间 10s
        params.setConnectTimeout(10000);

        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e(childrenData.getTitle()+"- 页面数据请求成功 == "+result);

                //缓存数据
                CacheUtils.putString(context,url,result);

                //解析和处理数据
                processData(result);

                //隐藏下拉刷新控件
                listview.onRefreshFinsh(true);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e(childrenData.getTitle()+"- 页面数据请求失败 == "+ex);
                //隐藏下拉刷新控件，不更新时间，只隐藏
                listview.onRefreshFinsh(false);


            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e(childrenData.getTitle()+"- 页面数据请求onCancelled == "+cex.getMessage());

            }

            @Override
            public void onFinished() {
                LogUtil.e(childrenData.getTitle()+"- 页面数据请求onFinished == ");


            }
        });
    }

    /**
     * 之前点 高亮的位置
     */
    private int prePosition;

    private void processData(String json) {
        TabDetailPagerBean bean = parsedJson(json);
        LogUtil.e(childrenData.getTitle()+"- 解析成功 == "+bean.getData().getNews().get(0).getTitle());

        //顶部轮播图
        topnews = bean.getData().getTopnews();

        //设置viewpager适配器
        viewpager.setAdapter(new TabDetailPagerTopNewsAdapter());

        ll_point_group.removeAllViews();//移除所有的红点

        //按照多少数据去添加点
        for(int i=0;i<topnews.size();i++){
            ImageView imageView = new ImageView(context);
            //点 选择器
            imageView.setBackgroundResource(R.drawable.point_selector);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtil.dip2px(8),DensityUtil.dip2px(8));

            if(i==0){
                imageView.setEnabled(true);
            }else{
                imageView.setEnabled(false);
                params.leftMargin = DensityUtil.dip2px(8);
            }
            imageView.setLayoutParams(params);
            ll_point_group.addView(imageView);

        }

        //监听页面的改变，设置红点变化，文本变化

        viewpager.addOnPageChangeListener(new MyOnPageChangeListener());
        tv_title.setText(topnews.get(prePosition).getTitle());

        //准备iListView对应的集合数据
        news = bean.getData().getNews();

        //设置ListView的适配器
        adapter = new TabDetailPagerListAdapter();
        listview.setAdapter(adapter);


    }

    class TabDetailPagerListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return news.size();
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
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(context,R.layout.item_tabdetail_pager,null);
                viewHolder = new ViewHolder();
                viewHolder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                viewHolder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                viewHolder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);

                convertView.setTag(viewHolder);

            }else{
                viewHolder = (ViewHolder) convertView.getTag();

            }

            //根据位置得到数据

            TabDetailPagerBean.DataBean.NewsBean newsData = news.get(position);
            String imageUrl = Constants.BASE_URL+newsData.getListimage();
            //请求图片
            x.image().bind(viewHolder.iv_icon,imageUrl,imageOptions);
            //设置标题
            viewHolder.tv_title.setText(newsData.getTitle());
            //设置时间
            viewHolder.tv_time.setText(newsData.getPubdate());

            return convertView;
        }
    }

    static class ViewHolder{
        ImageView iv_icon;
        TextView tv_title;
        TextView tv_time;
    }

    class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //1. 设置文本
            tv_title.setText(topnews.get(position).getTitle());
            //2. 对应页面红点高亮
                //把之前的变成灰色
            ll_point_group.getChildAt(prePosition).setEnabled(false);
                //把当前的红色
            ll_point_group.getChildAt(position).setEnabled(true);

            prePosition = position;


        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class TabDetailPagerTopNewsAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return topnews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            //设置默认图片
            ImageView imageView = new ImageView(context);
            imageView.setImageResource(R.drawable.home_scroll_default);
            //XY拉伸
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //图片加到容器中
            container.addView(imageView);

            TabDetailPagerBean.DataBean.TopnewsBean topnewsData = topnews.get(position);
            String imageUrl = Constants.BASE_URL + topnewsData.getTopimage();

            //联网请求图片
            x.image().bind(imageView,imageUrl);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }

    }

    private TabDetailPagerBean parsedJson(String json) {
        return new Gson().fromJson(json,TabDetailPagerBean.class);
    }
}
