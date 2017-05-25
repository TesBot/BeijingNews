package app_beijingnews.alex.com.beijingnews.menudetailpager.tabledetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import app_beijingnews.alex.com.beijingnews.R;
import app_beijingnews.alex.com.beijingnews.base.MenuDetailBasePager;
import app_beijingnews.alex.com.beijingnews.domain.NewsCenterPagerBean2;
import app_beijingnews.alex.com.beijingnews.utils.Constants;
import app_beijingnews.alex.com.beijingnews.utils.LogUtil;

/**
 * 页签详情页面
 * Created by Jhin on 2017/5/19 0019.
 */

public class TableDetailPager extends MenuDetailBasePager {

    private final NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData;
    private String url;

    public TableDetailPager(Context context, NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData) {
        super(context);
        this.childrenData = childrenData;
    }



    @Override
    public View initView() {
        View view = View.inflate(context, R.layout.tabdetail_pager,null);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        url = Constants.BASE_URL + childrenData.getUrl();
        LogUtil.e(childrenData.getTitle()+" 的联网地址： "+url);
    }
}
