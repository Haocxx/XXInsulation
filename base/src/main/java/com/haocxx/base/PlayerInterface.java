package com.haocxx.base;

import com.haocxx.xxinsulation.interfaces.IInsulator;

/**
 * Created by Haocxx
 * on 2020-05-27
 */
public interface PlayerInterface extends IInsulator {
    void start();
    void resume();
    void pause();
    void stop();
}
