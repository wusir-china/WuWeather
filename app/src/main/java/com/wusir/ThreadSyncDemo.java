package com.wusir;

import com.wusir.util.ToastUtil;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zy on 2018/1/9.
 */

public class ThreadSyncDemo {
    public void lookMoney(){
        System.out.println("账户余额："+count);
    }
    //1.将synchronized加在需要互斥的方法上,通常没有必要同步整个方法,因为开销比较大
    private int count =0;//账户余额
    public synchronized void addMoney(int money){
        count+=money;
        System.out.println(System.currentTimeMillis()+"存进："+money);
    }
    public synchronized void outMoney(int money){
        if(count-money<0){
            System.out.print("余额不足");
            return;
        }
        count-=money;
        System.out.println(+System.currentTimeMillis() + "取出：" + money);
    }
    //2.将synchronized加在需要互斥方法里面互斥的代码块上
    private int count2 =0;//账户余额
    public void addMoney2(int money){
        synchronized (ThreadSyncDemo.this){
            count2+=money;
        }
        System.out.println(System.currentTimeMillis()+"存进："+money);
    }
    public void outMoney2(int money){
        synchronized (this){
            if(count2-money<0){
                System.out.print("余额不足");
                return;
            }
            count2-=money;
        }
        System.out.println(+System.currentTimeMillis() + "取出：" + money);
    }
    //3.共享变量前加volatile关键字
    private volatile int count3 =0;//账户余额
    public void addMoney3(int money){
        count3+=money;
        System.out.println(System.currentTimeMillis()+"存进："+money);
    }
    public void outMoney3(int money){
        if(count3-money<0){
            System.out.print("余额不足");
            return;
        }
        count3-=money;
        System.out.println(+System.currentTimeMillis() + "取出：" + money);
    }
    //4.在互斥方法中使用重入锁
    private int count4=0;
    private Lock lock=new ReentrantLock();
    public void addMoney4(int money){
        lock.lock();
        try{
            count4+=money;
            System.out.println(System.currentTimeMillis()+"存进："+money);
        }finally {
            lock.unlock();
        }
    }
    public void outMoney4(int money){
        lock.lock();
        try{
            if(count4-money<0){
                System.out.print("余额不足");
                return;
            }
            count4-=money;
            System.out.println(+System.currentTimeMillis() + "取出：" + money);
        }finally {
            lock.unlock();
        }
    }
    //5.将共享变量设置为ThreadLocal带泛型类的局部变量
    private static ThreadLocal<Integer> count5=new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {
            return 0;//count5初始值
        }
    };
    public void addMoney5(int money){
        count5.set(count5.get()+money);
        System.out.println(System.currentTimeMillis()+"存进："+money);
    }
    public void outMoney5(int money){
        if(count5.get()-money<0){
            System.out.print("余额不足");
            return;
        }
        count5.set(count5.get()-money);
        System.out.println(+System.currentTimeMillis() + "取出：" + money);
    }
    //测试
    public static void main(String args[]){
        final ThreadSyncDemo demo=new ThreadSyncDemo();
        //入账线程
        Thread tAdd=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    demo.addMoney(100);
                    demo.lookMoney();
                }
            }
        });
        //出账线程
        Thread tOut=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    demo.outMoney(100);
                    demo.lookMoney();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        tOut.start();
        tAdd.start();
    }
}
