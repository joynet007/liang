package com.liang;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.liang.business.table.TbStudent;
import com.liang.commons.inner.secret.AES;
import com.liang.commons.inner.activity.BaseActivity;
import com.liang.commons.inner.service.TestService;

import java.util.List;

import liang.com.liang.R;

public class MainActivity extends BaseActivity {

    private TextView mTextView;
    private Button bt_invoke_broadcast ;
    private Button bt_invoke_service ;
    private Button bt_stop_service ;
    private Button bt_aes_service ;
    private Button bt_dabase ;
    private Button bt_login ;
    private String tag = "MainActivity";
    public  Bundle bundle;

    public Intent intent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(null);
        setContentView(R.layout.activity_main);
        bundle = new Bundle();
        mTextView = (TextView)findViewById(R.id.mTextView);

        bt_invoke_broadcast = (Button)findViewById(R.id.bt_invoke_broadcast);
        bt_invoke_broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("abc");
                intent.putExtra("msg", "我在发送广播！这只是一个普通的广播，" +
                        "你们无法通过abortBroadcast()的方式停止广播的传播，" +
                        "也无法往Broadcast中存入数据因为它是异步的");
                sendBroadcast(intent);

            }
        });

        bt_invoke_service = (Button)findViewById(R.id.bt_invoke_service);
        bt_invoke_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(MainActivity.this, TestService.class);
                startService(intent);

//                stopService(intent);

            }
        });

        bt_stop_service = (Button)findViewById(R.id.bt_stop_service);
        bt_stop_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                stopService(intent);

//                stopService(intent);

            }
        });

        bt_aes_service = (Button)findViewById(R.id.bt_aes_service);
        bt_aes_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ID = "jin";

                String idEncrypt = AES.encrypt(ID);
                System.out.println("Jiami:"+idEncrypt);
                String idDecrypt = AES.decrypt(idEncrypt);
                System.out.println("jiemi:" + idDecrypt);


            }
        });

        bt_dabase = (Button)findViewById(R.id.bt_dabase);
        bt_dabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dbManager.createTable(TbStudent.class);

                ContentValues vc = new ContentValues();
                vc.put("name","yuanliang");
                vc.put("age", 32);
                dbManager.getDb().insert("TbStudent", null, vc);

                Cursor cs = dbManager.getDb().rawQuery("select * from TbStudent",null);
                if(cs != null)
                {
                    System.out.println("cs" + cs.getCount());
                }

                List<Object> list=dbManager.findObjects(TbStudent.class,null,null);
                System.out.println(list.size()+"---listisze");
                for(Object c : list){
                    TbStudent ts = (TbStudent)c;
                    System.out.println(ts.getId()+"--"+ts.getAge()+"--"+ts.getName());
                }

//                if(list!=null && !list.isEmpty()){
//                    System.out.println(list.size()+"-----list.size()");
//                }

            }
        });

        bt_login = (Button)findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        show(MainActivity.this);

//      Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        String url ="http://www.baidu.com";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                mTextView.setText("Response is: "+ response.substring(0,500));
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
                mTextView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);


            }
        });







    }//end oncreate


    public void show(Context context){
        Toast.makeText(this,"--"+context.getCacheDir()+"---"+context.getPackageName(),Toast.LENGTH_LONG).show();

    }


}
