package com.haocxx.xxinsulation.test;

import android.util.Log;

import com.haocxx.base.ModuleInsulatorInterface2;
import com.haocxx.xxinsulation.annotation.Insulator;

/**
 * Created by Haocxx
 * on 2020-04-28
 */
@Insulator
public class ModuleInsulatorImpl2 implements ModuleInsulatorInterface2 {
    public static final String TAG = "ModuleInsulatorImpl2";

    @Override
    public void testImpl() {
        Log.d(TAG, "Module2 ready.");
    }
}
