package com.haocxx.xxinsulation.test;

import android.util.Log;

import com.haocxx.base.PlayerInterface;
import com.haocxx.xxinsulation.annotation.Insulator;

/**
 * Created by Haocxx
 * on 2020-05-27
 */
@Insulator
public class PlayImpl implements PlayerInterface {
    @Override
    public void start() {
        Log.d("Haocxx_PlayImpl", "start");
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }
}
