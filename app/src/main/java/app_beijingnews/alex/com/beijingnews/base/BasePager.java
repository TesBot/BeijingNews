package app_beijingnews.alex.com.beijingnews.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import app_beijingnews.alex.com.beijingnews.R;
import app_beijingnews.alex.com.beijingnews.activity.MainActivity;

/**
 * Created by Jhin on 2017/5/10 0010.
 * 公共类，五个页面都继承于它
 */

public class BasePager {

    /**
     * 上下文
     */
    public final Context context; //MainActivity
    /**
     * 代表个个不同的页面
     */
    public View rootview;
    public TextView tv_title;
    public ImageButton ib_menu;

    /**
     * 帧布局
     * 加载个个子页面
     */
    public FrameLayout fl_content;

    public BasePager(Context context) {
        this.context = context;
        //构造方法一执行，试图就被初始化
        rootview = initView();
    }

    /**
     * 初始化公共部分，加载子视图的FragmentLayout
     * @return
     */
    private View initView() {
        View view = View.inflate(context, R.layout.base_pager,null);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        ib_menu = (ImageButton) view.findViewById(R.id.ib_menu);
        fl_content = (FrameLayout) view.findViewById(R.id.fl_content);
        ib_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.getSlidingMenu().toggle(); //关-》开；开-》关
            }
        });

        return view;

    }

    /**
     * 初始化数据，重写该方法
     */
    public void initData(){

    }

}
