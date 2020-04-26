package com.haocxx.xxinsulation;

import java.util.HashMap;

/**
 * Created by Haocxx
 * on 2020-04-26
 */
public class XXInsulationClient {
    public static XXInsulationClient sXXDefaultClient;

    private HashMap<Class<? extends IInsulator>, Class<? extends IInsulator>> mInsulatorMap = new HashMap<>();
    private HashMap<Class<? extends IInsulator>, IInsulator> mInsulatorCachedMap = new HashMap<>();

    public static XXInsulationClient getDefault() {
        if (sXXDefaultClient == null) {
            synchronized (XXInsulationClient.class) {
                if (sXXDefaultClient == null) {
                    sXXDefaultClient = new XXInsulationClient();
                }
            }
        }
        return sXXDefaultClient;
    }

    public void init() {

    }

    public synchronized  <T extends IInsulator> T getInsulator(Class<T> clazz) {
        IInsulator cachedInsulator = mInsulatorCachedMap.get(clazz);
        if (cachedInsulator != null) {
            return (T) cachedInsulator;
        }
        return null;
    }
}
