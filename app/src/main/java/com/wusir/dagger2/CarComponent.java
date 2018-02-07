package com.wusir.dagger2;

import dagger.Component;

/**
 * Created by zy on 2018/2/7.
 */
@Component(modules = {MarkCarModule.class})
public interface CarComponent {
    void inject(Car car);
}
