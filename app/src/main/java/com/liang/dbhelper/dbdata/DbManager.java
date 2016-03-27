package com.liang.dbhelper.dbdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.liang.commons.inner.base.LogUtil;
import com.liang.dbhelper.dbdata.annotation.AutoIncrement;
import com.liang.dbhelper.dbdata.annotation.ColLength;
import com.liang.dbhelper.dbdata.annotation.Id;

/**
 * Created by liang on 16/3/22.
 *
 * 这个类主要操作数据库的增 、 删 、改 、 查
 */
public class DbManager {

    private Context context;
    private SQLiteDatabase db;
    private DbHelper dbHelper;

    private String tag = this.getClass().getSimpleName();

    public DbManager(Context context) {
        this.context = context;
        dbHelper = new DbHelper(context);
        db = dbHelper.getWritableDatabase();

    }

    /**
     * @param sql
     * 执行sql
     * 可以执行 删除的sql  、 更新的sql 、新增的sql
     */
    public void execSQL(String sql){
        db.execSQL(sql);
    }

    /**
     * 创建表，这个方法会根据 java class 自动create table ，
     * 表的字段和java class 对象一一对应 ，其中 有三个 annotation ，ColLength 标识字段长度，
     * Id 标识 字段是否是主键 ，AutoIncrement 标识是否自增长。
     *
     * 目前仅仅做简单的封装，仅支持 Integer 、 String 两种类型
     * @param c
     */

    public void createTable(Class c){
        String tableName = c.getSimpleName();
        Field [] fields = c.getDeclaredFields();
        StringBuffer str = new StringBuffer("CREATE TABLE IF NOT EXISTS " +tableName+" (");
        if(fields != null && fields.length>0) {

            for (int i = 0; i < fields.length; i++) {
                Field f = (Field) fields[i];
                str.append(f.getName() + " ");
                String colType = f.getType().toString();

                if (colType.endsWith("String")) {
                    str.append(" VARCHAR");
                } else if (colType.endsWith("Integer") || colType.endsWith("int")) {
                    str.append(" INTEGER");
                }

                ColLength cl = f.getAnnotation(ColLength.class);
                if(cl != null){
                    str.append("("+cl.getLength()+")");
                }

                if (f.getAnnotation(Id.class) != null) {
                    str.append(" PRIMARY KEY");
                }

                if (f.getAnnotation(AutoIncrement.class) != null) {
                    str.append(" AUTOINCREMENT ");
                }

                if(i < (fields.length -1)){
                    str.append(" , ");
                }

            }// end for
            str.append(" ) ");

            LogUtil.info(tag,"建表语句： "+str.toString());
            db.execSQL(str.toString());
        }//end if


    }

    /**
     * 查询数据库的记录
     * @param c
     * @return
     */


    /**
     *
     * @param c  这个是要返回的对象
     * @param sql : select * from person where name like ? and age=?" (注意：sql 不能用;结束)
     * @param selectionArgs ：new String[]{"%传智%", "4"}
     * @return
     */
    public List<Object> findObjects(Class c ,String sql , String [] selectionArgs){

        List<Object> list = new ArrayList<Object>();
        Cursor cursor = null;
        if(null != sql){
            if(null != selectionArgs){
                //selectionArgs 这个是where 的条件
                cursor =  db.rawQuery(sql,selectionArgs);
            }else{
                cursor =  db.rawQuery(sql ,null);
            }
        }else{
            cursor =  db.rawQuery("select * from " + c.getSimpleName() ,null);
        }


        if(cursor != null){
            Field[] fields = c.getDeclaredFields();//获取所有声明的字段包括 private 和 public

            for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext())
            {

                Object obj = null;
                try {
                    obj = c.newInstance();
                } catch (InstantiationException e1) {
                    e1.printStackTrace();
                } catch (IllegalAccessException e1) {
                    e1.printStackTrace();
                }


                for(Field f : fields){
                    f.setAccessible(true);//设置字段可以访问
                    String f_type = f.getType().toString();//获取属性的类型 目前仅支持  String 、int、 Integer 三种类型
                    String f_name = f.getName();//获取字段名称
                    int column = cursor.getColumnIndex(f_name);//根据字段名称获取游标的 位置
                    Object value = null;
                    if (f_type.endsWith("String")) {
                        //String 类型
                        value = cursor.getString(column);

                    }else if(f_type.endsWith("int") || f_type.endsWith("Integer")){

                        value = cursor.getInt(column);

                    }else{
                        LogUtil.info(tag,f.getName()+" 字段的类型 "+f.getType());
                    }

                    try {
                        f.set(obj,value);

                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                }//end for(Field f : fields){

                list.add(obj);

            }

        }

        return list;
    }


    /**
     * 创建单条记录
     * @param obj
     */
    public void insert(Object obj ){
        ContentValues cvs = new ContentValues();
        Class c = obj.getClass();
        Field[] fields = c.getDeclaredFields();//获取所有声明的字段包括 private 和 public
        if(null != fields && fields.length > 0)
        {
            for (int i = 0; i < fields.length; i++) {
                Field f = (Field) fields[i];
                Object value = null;
                try {
                    value = f.get(obj);//获取字段的值
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                if (f.getAnnotation(Id.class) != null  && f.getAnnotation(AutoIncrement.class) != null) {
                    //如果这个字段是主键，并且是自增长的 就不要插入这个字段了（不建议使用数据库自己的主键 自增长）
                }else{
                    if(null == value){
                        cvs.put(f.getName(),""); //这里强制转成String
                    }else{
                        cvs.put(f.getName(),(String)value); //这里强制转成String
                    }
                }
            }//end for
        }// end if

        db.insert(c.getSimpleName(),null,cvs);
    }

    /**
     * 修改单条记录，根据此条记录的主键 修改其他的值
     * @param obj
     */
    public void update(Object obj ){
        ContentValues cvs = new ContentValues();
        Class c = obj.getClass();
        Field[] fields = c.getDeclaredFields();//获取所有声明的字段包括 private 和 public

        String whereClause = "";//条件
        String[] whereArgs = new String[1];//条件值

        if(null != fields && fields.length > 0)
        {
            for (int i = 0; i < fields.length; i++) {
                Field f = (Field) fields[i];
                Object value = null;
                try {
                    value = f.get(obj);//获取字段的值
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                //根据主键来修改
                if (f.getAnnotation(Id.class) != null) {
                    whereClause = f.getName()+"=?";
                    whereArgs[0] = (String)value;
                }else{
                    if(null == value){
                        cvs.put(f.getName(),""); //这里强制转成String
                    }else{
                        cvs.put(f.getName(),(String)value); //这里强制转成String
                    }
                }
            }//end for
        }// end if
        db.update(c.getSimpleName(),cvs,whereClause,whereArgs);
    }

    public SQLiteDatabase getDb() {
        return db;
    }
}
