package com.haocxx.xxinsulation;

/**
 * Created by Haocxx
 * on 2020-04-26
 */
public class XXInsulationClient {
    public static XXInsulationClient sXXDefaultClient;

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
}
