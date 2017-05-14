package app_beijingnews.alex.com.beijingnews.pager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import app_beijingnews.alex.com.beijingnews.base.BasePager;
import app_beijingnews.alex.com.beijingnews.domain.NewsCenterPagerBean;
import app_beijingnews.alex.com.beijingnews.utils.Constants;
import app_beijingnews.alex.com.beijingnews.utils.LogUtil;

/**
 * Created by Jhin on 2017/5/10 0010.
 */

public class NewsCenterPager extends BasePager {
    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public void initData() {
        super.initData();
        LogUtil.e("新闻中心数据被初始化");

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
}

