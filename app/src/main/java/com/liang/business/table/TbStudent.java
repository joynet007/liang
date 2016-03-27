package com.liang.business.table;

import com.liang.dbhelper.dbdata.annotation.AutoIncrement;
import com.liang.dbhelper.dbdata.annotation.ColLength;
import com.liang.dbhelper.dbdata.annotation.Id;

/**
 * Created by liang on 16/3/25.
 * 这是数据库表的对象
 */
public class TbStudent {
    @Id  //表示主键
    @AutoIncrement //表示自动增长
    public Integer id;
    @ColLength(getLength = 64) //表示设置数据库的长度
    public String name;
    @ColLength (getLength = 4) //表示设置数据库字段的长度
    public String age;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
