package app_beijingnews.alex.com.beijingnews.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Jhin on 2017/5/27 0027.
 * 水平方向滑动的ViewPager
 */

public class HorizontalScrollViewPager extends ViewPager{
    public HorizontalScrollViewPager(Context context) {
        super(context);
    }

    public HorizontalScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 起始坐标
     */
    private float startX;
    private float startY;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //请求父层视图不拦截当前控件事件
        getParent().requestDisallowInterceptTouchEvent(false);
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true); //都把事件传给当前控件
                //记录起始坐标
                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //来到新坐标
                float endX = ev.getX();
                float endY = ev.getY();
                //计算偏移量
                float distanceX = endX-startX;
                float distanceY = endY-startY;
                //判断方向
                if(Math.abs(distanceX)>Math.abs(distanceY)){
//                    //1 当滑动ViewPager到 0 个页面时，并且是←滑动
                      if(getCurrentItem()==0 && distanceX >0){
                             getParent().requestDisallowInterceptTouchEvent(false);
                      }
                    else if(getCurrentItem() == (getAdapter().getCount()-1) && distanceX<0){

//                    //2 滑动到ViewPager最后一个页面，并且是→滑动
                             getParent().requestDisallowInterceptTouchEvent(false);
                    }
                      else {
//                    //3 其他
                               getParent().requestDisallowInterceptTouchEvent(true);
                      }

                }else{
                    //竖直方向滑动
                    getParent().requestDisallowInterceptTouchEvent(false);

                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;

        }
        return super.dispatchTouchEvent(ev);
    }
}
