package com.liang.commons.inner.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.liang.dbhelper.cachedata.SharedPreferenceUtil;
import com.liang.dbhelper.dbdata.DbManager;

/**
 * Created by liang on 16/3/10.
 */
public class BaseActivity extends Activity {

    public SharedPreferenceUtil spu = null;
    public DbManager dbManager = null;

    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(null);

        spu = new SharedPreferenceUtil(this);//缓存本地数据对象
        dbManager = new DbManager(this);

    };
}
