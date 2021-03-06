package com.goldensoft.goldenlibrary;

import android.content.Context;

import com.goldensoft.goldenlibrary.exception.CrashTool;

/**
 * Created by Administrator on 2018/3/22.
 */

public class golden {
    private static Context context;
    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context){
        golden.context=context.getApplicationContext();
        //CrashTool.init(context);
    }
    private golden(){
    }
    public static boolean isDebug(){
        return Builder.debug;
    }
    public static class Builder{
        private static boolean debug;
        private Builder(){}
        public static void setDebug(boolean debug){
            Builder.debug=debug;
        }
    }
    /**
     * 在某种获取不到 Context 的情况下，即可以使用才方法获取 Context
     * <p>
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) {
            return context;
        }
        throw new NullPointerException("请先调用init()方法");
    }
}
