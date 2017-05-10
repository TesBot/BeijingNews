package app_beijingnews.alex.com.beijingnews;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Jhin on 2017/5/10 0010.
 */

public class BeijingNewsApplication extends Application{
    /**
     * 所有组件被创建之前执行
     */
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.setDebug(true);
        x.Ext.init(this);
    }
}
