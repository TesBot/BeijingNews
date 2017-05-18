package app_beijingnews.alex.com.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import app_beijingnews.alex.com.beijingnews.activity.MainActivity;
import app_beijingnews.alex.com.beijingnews.base.BasePager;
import app_beijingnews.alex.com.beijingnews.base.MenuDetailBasePager;
import app_beijingnews.alex.com.beijingnews.domain.NewsCenterPagerBean;
import app_beijingnews.alex.com.beijingnews.fragment.LeftmenuFragment;
import app_beijingnews.alex.com.beijingnews.menudetailpager.InteracMenuDetailPager;
import app_beijingnews.alex.com.beijingnews.menudetailpager.NewsMenuDetailPager;
import app_beijingnews.alex.com.beijingnews.menudetailpager.PhotosMenuDetailPager;
import app_beijingnews.alex.com.beijingnews.menudetailpager.TopicMenuDetailPager;
import app_beijingnews.alex.com.beijingnews.utils.Constants;
import app_beijingnews.alex.com.beijingnews.utils.LogUtil;

/**
 * Created by Jhin on 2017/5/10 0010.
 */

public class NewsCenterPager extends BasePager {
    //左侧菜单对应的数据集合
    List<NewsCenterPagerBean.DataBean> data;
    /**
     * 详情页面的集合
     */
    private ArrayList<MenuDetailBasePager> detailBasePagers;

    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("新闻中心数据被初始化");
        ib_menu.setVisibility(View.VISIBLE);

        //设置标题
        tv_title.setText("新闻中心");
        //联网请求数据，创建视图
        TextView textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);

        //把子视图添加到BasePager的FragmentLayout中
        fl_content.addView(textView);
        //绑定数据
        textView.setText("新闻中心内容");

        //联网请求数据
        getDataFromNet();

    }

    /**
     * 使用xUtils3联网请求数据
     */
    private void getDataFromNet() {
        RequestParams params = new RequestParams(Constants.NEWCSCENTER_PAGER_URL);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                LogUtil.e("使用Xutils3联网请求成功 == "+result);
                processData(result);

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                LogUtil.e("使用Xutils3联网请求失败 == "+ex.getMessage());

            }

            @Override
            public void onCancelled(CancelledException cex) {
                LogUtil.e("使用Xutils3联网请求 取消  == "+cex.getMessage());
            }

            @Override
            public void onFinished() {
                LogUtil.e("使用Xutils3联网请求完成 ");

            }
        });

    }

    /**
     * 解析json数据和显示数据
     * @param json
     */
    private void processData(String json) {
        NewsCenterPagerBean bean = parsedJson(json);
        String title = bean.getData().get(0).getChildren().get(1).getTitle();
        LogUtil.e("使用Gson解析Json数据成功 title == "+title);

        //给左侧菜单传递数据
        data = bean.getData();

        //
        MainActivity mainActivity = (MainActivity) context;
        LeftmenuFragment leftmenuFragment = mainActivity.getLeftmenuFragment();

        //添加详情页面
        detailBasePagers = new ArrayList<>();
        detailBasePagers.add(new NewsMenuDetailPager(context)); //新闻详情页面
        detailBasePagers.add(new TopicMenuDetailPager(context)); //专题详情页面
        detailBasePagers.add(new PhotosMenuDetailPager(context)); //组图详情页面
        detailBasePagers.add(new InteracMenuDetailPager(context)); //互动详情页面

        //把数据传递给左侧菜单；
        leftmenuFragment.setData(data);

    }

    /**
     * 解析json数据：1，使用系统的API解析 2，使用第三方框架解析json数据，如Gson，fastjson
     * @param json
     * @return
     */
    private NewsCenterPagerBean parsedJson(String json) {
        Gson gson = new Gson();
        NewsCenterPagerBean bean = gson.fromJson(json,NewsCenterPagerBean.class);
        return bean;
    }

    /**
     * 根据位置切换详情页面
     * @param position
     */
    public void switchPager(int position) {
        //设置标题
        tv_title.setText(data.get(position).getTitle());

        //移除内容
        fl_content.removeAllViews(); //移除之前所有试图

        //添加新内容
        MenuDetailBasePager detailBasePager = detailBasePagers.get(position);
        View rootView = detailBasePager.rootView;
        detailBasePager.initData(); //初始化数据
        fl_content.addView(rootView);
    }
}

