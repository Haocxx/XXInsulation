package com.haocxx.xxinsulation.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Haocxx
 * on 2020-05-27
 */
public class InsulatorList {
    private List<Class<? extends IInsulator>> mList;

    public InsulatorList() {
        mList = new ArrayList<>();
    }

    public int size() {
        return mList.size();
    }

    public Class<? extends IInsulator> get(int index) {
        return mList.get(index);
    }

    public void add(Class<? extends IInsulator> insulatorClazz) {
        mList.add(insulatorClazz);
    }
}
