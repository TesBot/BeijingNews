package app_beijingnews.alex.com.beijingnews.utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;

import app_beijingnews.alex.com.beijingnews.SplashActivity;
import app_beijingnews.alex.com.beijingnews.activity.GuideActivity;

/**
 * Created by Jhin on 2017/5/2 0002.
 */

public class CacheUtils {
    public static boolean getBoolean (Context context, String key){
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);


        return sp.getBoolean(key,false);
    }

    /**
     * 保存软件参数
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    /**
     * 缓存文本数据
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    /**
     * 获取缓存的文本信息
     * @param context
     * @param key
     * @return
     */
    public static String getString(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);


        return sp.getString(key,"");
    }
}
