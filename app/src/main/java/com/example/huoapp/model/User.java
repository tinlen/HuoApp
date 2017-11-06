package com.example.huoapp.model;


import org.litepal.crud.DataSupport;

/**
 * Created by tinle on 2017/11/6.
 */

public class User extends DataSupport{
    String name;
    int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
