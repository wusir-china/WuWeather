package com.wusir.dagger2;

import javax.inject.Inject;

/**
 * Created by zy on 2018/2/7.
 */

public class Car {
    @Inject
    Engine engine;
    public Car(){
        DaggerCarComponent.builder().markCarModule(new MarkCarModule()).build().inject(this);
    }
    public Engine getEngine(){
        return this.engine;
    }
    public static void main(String[] args){
        Car car = new Car();
        car.getEngine().run();
    }
}
