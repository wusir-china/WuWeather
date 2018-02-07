package com.wusir.dagger2;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zy on 2018/2/7.
 */
@Module
public class MarkCarModule {
    public MarkCarModule(){}
    @Provides
    Engine provideEngine(){
        return new Engine("gear");
    }
}
