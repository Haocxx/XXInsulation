package com.haocxx.xxinsulation.test;

import android.util.Log;

import com.haocxx.base.LoginInterface;
import com.haocxx.xxinsulation.annotation.Insulator;

/**
 * Created by Haocxx
 * on 2020-05-27
 */
@Insulator
public class LoginImpl implements LoginInterface {
    @Override
    public void login() {
        Log.d("Haocxx_LoginImpl", "login");
    }
}
