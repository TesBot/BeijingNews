package app_beijingnews.alex.com.beijingnews.fragment;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import app_beijingnews.alex.com.beijingnews.base.BaseFragment;
import app_beijingnews.alex.com.beijingnews.utils.LogUtil;

/**正文Fragment
 * Created by Jhin on 2017/5/4 0004.
 */

public class ContentFragment extends BaseFragment {
    private TextView textView;

    @Override
    public View initView() {
        LogUtil.e("正文Fragment视图被初始化");
        textView = new TextView(context);
        textView.setTextSize(23);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("正文数据被初始化");
        textView.setText("正文Fragment的页面");

    }

}
