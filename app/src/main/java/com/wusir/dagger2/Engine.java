package com.wusir.dagger2;

import javax.inject.Inject;

/**
 * Created by zy on 2018/2/7.
 */

public class Engine {
    private String gear;
//    @Inject
//    public Engine(){}
    public Engine(String gear) {
        this.gear = gear;
    }
    public void run(){
        System.out.println("引擎转起来了~~~");
    }
}
