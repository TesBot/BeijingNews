package app_beijingnews.alex.com.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/** 自定义不可以滑动的ViewPager
 * Created by Jhin on 2017/5/11 0011.
 */

public class NoScrollViewPager extends ViewPager {
    /**
     * 通常在代码中实例化时候用
     * @param context
     */
    public NoScrollViewPager(Context context) {
        super(context);
    }

    /**
     * 在布局文件中使用该类时候，该方法不能少，会崩溃
     * @param context
     * @param attrs
     */
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 重写触摸时间。消费点击
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }
}
