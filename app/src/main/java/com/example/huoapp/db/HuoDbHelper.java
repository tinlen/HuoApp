package com.example.huoapp.db;

/**
 * Created by tinle on 2017/11/6.
 */

public class HuoDbHelper {

    private HuoDbHelper(){}

    private static final class Holder{
        private static final HuoDbHelper INSTANCE = new HuoDbHelper();
    }

    public static HuoDbHelper getInstance(){
        return Holder.INSTANCE;
    }
}
