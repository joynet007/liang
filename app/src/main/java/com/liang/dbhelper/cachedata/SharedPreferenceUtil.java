package com.liang.dbhelper.cachedata;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by liang on 16/3/10.
 */
public class SharedPreferenceUtil {

    public static SharedPreferences preferences;
    public static SharedPreferences.Editor editor;
    public static Context context;
    public final static String shareKey = "dlb";

    public SharedPreferenceUtil(Context context)
    {
        this.context = context;
        preferences = context.getSharedPreferences(shareKey, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    /**
     * 获取值 对象String
     * @param key
     * @return
     */
    public String getValue(String key)
    {
        if (preferences == null)
        {
            preferences = context.getSharedPreferences(shareKey, Context.MODE_PRIVATE);
        }
        return preferences.getString(key,null);
    }

    /**
     * 设置值
     * @param key
     * @param value
     */
    public void setValue(String key , String value)
    {
        if (preferences == null)
        {
            preferences = context.getSharedPreferences(shareKey, Context.MODE_PRIVATE);
        }
        if(editor == null){
            editor = preferences.edit();
        }

        editor.putString(key,value);
    }



}
