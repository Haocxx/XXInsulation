package com.haocxx.xxinsulation.test;

import android.app.Application;

import com.haocxx.xxinsulation.XXInsulationClient;

/**
 * Created by Haocxx
 * on 2020-04-29
 */
public class MainApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    XXInsulationClient.getDefault().init();
  }
}
