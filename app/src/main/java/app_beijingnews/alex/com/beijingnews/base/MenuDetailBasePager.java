package app_beijingnews.alex.com.beijingnews.base;

import android.content.Context;
import android.view.View;

/**
 * Created by Jhin on 2017/5/17 0017.
 * 详情及页面的基类
 */

public abstract class MenuDetailBasePager  {
    /**
     * 上下文
     */
    public final Context context;

    /**
     * 代表个个详情页面试图
     */
    public View rootView;

    public MenuDetailBasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    /**
     * 抽象方法，强制孩子实现该方法，每个页面实现不同的效果
     *
     * @return
     */
    public abstract View initView();

    /**
     * 子页面需要绑定数据，联网请求数据，重写该方法
     */
    public void initData(){

    }

}
