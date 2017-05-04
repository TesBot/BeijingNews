package app_beijingnews.alex.com.beijingnews.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* 基本的Fragment，left、content都继承
 * Created by Jhin on 2017/5/4 0004.
 */

public abstract class BaseFragment extends Fragment {

    public Activity context; //MainActivity

    /**
     * 当Fragment创建时，回调
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    /**
     * 当创建View回调
     * @param inflater
     * @param container
     * @param savedInstanceState
     * 创建了视图
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return initView();

    }

    /**
     * 让孩子实现自己的视图，达到自己特有的效果
     * @return
     */
    public abstract View initView() ;

    /**
     * 当Activity被创建后，回调
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 子页面没有数据，联网请求数据，并且绑定到initView初始化的视图
     */
    public void initData(){

    }
}
