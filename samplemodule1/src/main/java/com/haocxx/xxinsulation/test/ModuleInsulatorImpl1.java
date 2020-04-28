package com.haocxx.xxinsulation.test;

import android.util.Log;

import com.haocxx.base.ModuleInsulatorInterface1;
import com.haocxx.xxinsulation.annotation.Insulator;

/**
 * Created by Haocxx
 * on 2020-04-28
 */
@Insulator
public class ModuleInsulatorImpl1 implements ModuleInsulatorInterface1 {
    public static final String TAG = "ModuleInsulatorImpl1";

    @Override
    public void testImpl() {
        Log.d(TAG, "Module1 ready.");
    }
}
