package app_beijingnews.alex.com.beijingnews.menudetailpager.tabledetailpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import app_beijingnews.alex.com.beijingnews.base.MenuDetailBasePager;
import app_beijingnews.alex.com.beijingnews.domain.NewsCenterPagerBean2;

/**
 * 页签详情页面
 * Created by Jhin on 2017/5/19 0019.
 */

public class TableDetailPager extends MenuDetailBasePager {

    private final NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData;
    private TextView textView;

    public TableDetailPager(Context context, NewsCenterPagerBean2.DetailPagerData.ChildrenData childrenData) {
        super(context);
        this.childrenData = childrenData;
    }



    @Override
    public View initView() {
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        textView.setTextSize(25);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        textView.setText(childrenData.getTitle());
    }
}
