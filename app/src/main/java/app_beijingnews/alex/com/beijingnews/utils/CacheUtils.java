package app_beijingnews.alex.com.beijingnews.utils;

import android.content.Context;
import android.content.SharedPreferences;

import app_beijingnews.alex.com.beijingnews.SplashActivity;

/**
 * Created by Jhin on 2017/5/2 0002.
 */

public class CacheUtils {
    public static boolean getBoolean (Context context, String key){
        SharedPreferences sp = context.getSharedPreferences("atguigu",Context.MODE_PRIVATE);


        return sp.getBoolean(key,false);
    }

}
