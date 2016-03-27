package com.liang.commons.inner.base;

import android.util.Log;

/**
 * Created by liang on 16/3/22.
 * 这个类对 log 的做了统一的封装，为了统一关闭log的显示，在开发的时候log 的 开关isClose为 false;
 * 如果上线了 isCLose 设置为 true ，就统一关闭了所有的log
 *
 * Verbose,
 * Debug,
 * Info,
 * Warning,
 * Error.
 */
public class LogUtil {

    //开发期间次开发关闭
    public static boolean isClose = false;

    public static void verbose(String tag , String msg){
        if(!isClose){
            Log.i(tag,msg);
        }
    }

    public static void debug(String tag , String msg){
        if(!isClose){
            Log.d(tag,msg);
        }
    }

    public static void info(String tag , String msg){
        if(!isClose){
            Log.i(tag,msg);
        }
    }

    public static void warning(String tag , String msg){
        if(!isClose){
            Log.w(tag,msg);
        }
    }

    public static void error(String tag , String msg){
        if(!isClose){
            Log.e(tag,msg);
        }
    }


}
