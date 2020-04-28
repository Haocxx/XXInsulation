package com.haocxx.xxinsulation.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.haocxx.base.ModuleInsulatorInterface1;
import com.haocxx.base.ModuleInsulatorInterface2;
import com.haocxx.xxinsulation.XXInsulationClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XXInsulationClient.getDefault().getInsulator(ModuleInsulatorInterface1.class).testImpl();
        XXInsulationClient.getDefault().getInsulator(ModuleInsulatorInterface2.class).testImpl();
    }
}
