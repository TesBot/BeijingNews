package app_beijingnews.alex.com.beijingnews.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app_beijingnews.alex.com.beijingnews.base.BaseFragment;
import app_beijingnews.alex.com.beijingnews.utils.LogUtil;

/**左侧菜单的Fragment
 * Created by Jhin on 2017/5/4 0004.
 */

public class LeftmenuFragment extends BaseFragment {

    private TextView textView;

    @Override
    public View initView() {
        LogUtil.e("左侧菜单视图被初始化");
        textView = new TextView(context);
        textView.setTextSize(23);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(Color.RED);
        return textView;
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("左侧菜单数据被初始化");
        textView.setText("左侧菜单的页面");

    }
}
