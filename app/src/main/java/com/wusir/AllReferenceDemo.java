package com.wusir;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * Created by zy on 2017/12/21.
 */

public class AllReferenceDemo {
    //1.强引用(两种都是)
    Object object = new Object();
    String str = "hello";
    //2.软引用
    SoftReference<String> srr=new SoftReference<String>(str);//关联强引用
    SoftReference<String> sr=new SoftReference<String>(new String("hello"));//关联弱引用
    private void print(){
        System.out.print(sr.get());
        System.gc();//通知JVM(dvm)的gc进行垃圾回收
        System.out.println(sr.get());
    }
    //3.弱引用
    WeakReference<String> sr2=new WeakReference<String>(new String("hello"));
    private void print2(){
        System.out.print(sr2.get());
        sr2.isEnqueued();//返回是否被垃圾回收器标记为即将回收的垃圾
        System.gc();//通知JVM(dvm)的gc进行垃圾回收
        System.out.println(sr2.get());
    }
    //4.虚引用
    ReferenceQueue<String> queue=new ReferenceQueue<>();
    PhantomReference<String> sr3=new PhantomReference<String>(new String("hello"),queue);
    private void print3(){
        System.out.print(sr3.get());
        sr3.isEnqueued();//返回是否被垃圾回收器标记为即将回收的垃圾
        System.gc();//通知JVM(dvm)的gc进行垃圾回收
        System.out.println(sr3.get());
    }
}
