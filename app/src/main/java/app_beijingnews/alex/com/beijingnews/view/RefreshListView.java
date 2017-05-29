package app_beijingnews.alex.com.beijingnews.view;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import app_beijingnews.alex.com.beijingnews.R;

/**
 * 自定义下拉刷新ListView
 * Created by Jhin on 2017/5/28 0028.
 */

public class RefreshListView extends ListView{
    /**
     * 下拉刷新和顶部轮播图（）
     */
    private LinearLayout  headerView;
    /**
     * 下拉刷新控件
     */
    private View ll_pull_down_refresh;
    private ImageView iv_arrow;
    private ProgressBar pb_status;
    private TextView tv_status;
    private TextView tv_time;
    /**
     * 下拉刷新的高
     */
    private int pullDownRefreshHeight;

    /**
     * 下拉刷新
     */
    private static final int PULL_DOWN_REFRESH = 0;

    /**
     * 手松刷新
     */
    private static final int RELEASE_REFRESH = 1;

    /**
     * 正在刷新
     */
    private static final int REFRESHING = 2;

    /**
     * 当前状态
     */
    private int currentStatus = PULL_DOWN_REFRESH;

    private Animation upAnimation;
    private Animation downAnimation;

    /**
     * 底部加载更多
     */
    private View footerView;
    /**
     * 加载更多 的高
     */
    private int footerViewHeight;
    /**
     * 是否已经加载更多
     */
    private boolean isLoadMore = false;

    public RefreshListView(Context context) {
        this(context,null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView(context);
        initAnimation();
        initFooterView(context);
    }

    private void initFooterView(Context context) {
        footerView = View.inflate(context,R.layout.refresh_footer,null);
        footerView.measure(0,0);
        footerViewHeight = footerView.getMeasuredHeight();

        footerView.setPadding(0,-footerViewHeight,0,0);

        //添加到ListView footer
        addFooterView(footerView);

        //监听ListView的滚动监听，最后一条
        setOnScrollListener(new MyOnScrollListener());
    }

    class MyOnScrollListener implements OnScrollListener{

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //当静止或惯性滚动的时候
            if(scrollState == OnScrollListener.SCROLL_STATE_IDLE||scrollState == OnScrollListener.SCROLL_STATE_FLING){
                if(getLastVisiblePosition() >= getCount()-1){
                    //显示加载更多 布局
                    footerView.setPadding(8,8,8,8);
                    //状态改变
                    isLoadMore = true;
                    //回调接口
                    if(mOnRefreshListener !=null){
                        mOnRefreshListener.onLoadMore();
                        
                    }
                }
            }
            //并且是最后一条可见的
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        }
    }

    private void initAnimation() {
        upAnimation = new RotateAnimation(0,-180,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180,0,RotateAnimation.RELATIVE_TO_SELF,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true);
    }

    private void initHeaderView(Context context) {
        headerView = (LinearLayout) View.inflate(context, R.layout.reflesh_header,null);
        //下拉刷新控件
        ll_pull_down_refresh = headerView.findViewById(R.id.ll_pull_down_refresh);
        iv_arrow = (ImageView) headerView.findViewById(R.id.iv_arrow);
        pb_status = (ProgressBar) headerView.findViewById(R.id.pb_status);
        tv_status = (TextView) headerView.findViewById(R.id.tv_status);
        tv_time = (TextView) headerView.findViewById(R.id.tv_time);

        //测量
        ll_pull_down_refresh.measure(0,0);
        pullDownRefreshHeight = ll_pull_down_refresh.getMeasuredHeight();

        //默认隐藏下拉刷新控件
        ll_pull_down_refresh.setPadding(0,-pullDownRefreshHeight,0,0);

        //添加ListView头
        addHeaderView(headerView);

    }
    private float startY = -1;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                //记录起始坐标
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(startY == -1){
                    startY = ev.getY();
                }

                //正在刷新则不刷新了
                if(currentStatus == REFRESHING){
                    break;
                }
                //来到新坐标
                float endY = ev.getY();
                //记录滑动的距离
                float distanceY = endY-startY;
                if(distanceY > 0){ //下拉
                    int paddingTop = (int) (-pullDownRefreshHeight + distanceY);

                    if(paddingTop < 0 && currentStatus != PULL_DOWN_REFRESH){
                        //下拉刷新状态
                        currentStatus = PULL_DOWN_REFRESH;
                        //更新状态
                        refreshViewState();
                    }else if(paddingTop < 0 && currentStatus != RELEASE_REFRESH){
                        //手松刷新状态
                        currentStatus = RELEASE_REFRESH;
                        //更新状态
                        refreshViewState();

                    }

                    ll_pull_down_refresh.setPadding(0,paddingTop,0,0);

                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if(currentStatus == PULL_DOWN_REFRESH){
                    ll_pull_down_refresh.setPadding(0,-pullDownRefreshHeight,0,0); //完全隐藏
                }else if(currentStatus == RELEASE_REFRESH){

                    //设置状态为正在刷新
                    currentStatus = REFRESHING;
                    refreshViewState();
                    ll_pull_down_refresh.setPadding(0,0,0,0); //完全显示

                    //回调接口
                    if(mOnRefreshListener!=null){
                        mOnRefreshListener.onPullDownRefresh();
                    }


                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void refreshViewState() {
        switch (currentStatus){
            case PULL_DOWN_REFRESH: //下拉刷新状态
                iv_arrow.startAnimation(downAnimation);
                tv_status.setText("下拉刷新...");
                break;

            case RELEASE_REFRESH: //手松刷新
                iv_arrow.startAnimation(upAnimation);
                tv_status.setText("松手刷新...");
                break;

            case REFRESHING: //正在刷新
                tv_status.setText("正在刷新...");
                pb_status.setVisibility(VISIBLE);
                iv_arrow.clearAnimation();
                iv_arrow.setVisibility(GONE);
                break;

        }
    }

    /**
     * 当联网刷新完成，失败时回调
     * 用于用户刷新状态的还原
     * @param status
     */
    public void onRefreshFinsh(boolean status) {

        tv_status.setText("下拉刷新...");
        currentStatus = PULL_DOWN_REFRESH;
        iv_arrow.clearAnimation();;
        pb_status.setVisibility(GONE);
        iv_arrow.setVisibility(VISIBLE);

        //隐藏下拉刷新控件
        ll_pull_down_refresh.setPadding(0,-pullDownRefreshHeight,0,0);
        if(status){
            tv_time.setText("上次更新时间："+getSystemTime());
        }else{
            Toast.makeText(getContext(),"网络连接异常",Toast.LENGTH_SHORT).show();
        }


    }

    /**
     * 得到当前系统时间
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getSystemTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    /**
     * 监听控件的刷新
     */
    public interface OnRefreshListener{

        /**
         * 当下拉刷新时回调
         */
        public void onPullDownRefresh();

        public void onLoadMore();
    }

    private OnRefreshListener mOnRefreshListener;

    /**
     * 设置监听刷新,由外界设置
     * @param l
     */
    public void setOnRefreshListener(OnRefreshListener l){
        this.mOnRefreshListener = l;
    }

}
