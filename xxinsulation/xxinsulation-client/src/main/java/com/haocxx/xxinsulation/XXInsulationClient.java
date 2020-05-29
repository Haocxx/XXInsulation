package com.haocxx.xxinsulation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.ServiceLoader;

import com.haocxx.xxinsulation.interfaces.IInsulator;
import com.haocxx.xxinsulation.interfaces.IServer;
import com.haocxx.xxinsulation.interfaces.InsulatorList;

/**
 * Created by Haocxx
 * on 2020-04-26
 */
public class XXInsulationClient {
    private static XXInsulationClient sXXDefaultClient;

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
        ServiceLoader<IServer> loader = ServiceLoader.load(IServer.class);
        Iterator<IServer> iterator = loader.iterator();
        while (iterator.hasNext()) {
            IServer server = iterator.next();
            try {
                InsulatorList insulatorList = new InsulatorList();
                server.getInsulatorClasses(insulatorList);
                addInsulators(insulatorList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized <T extends IInsulator> T getInsulator(Class<T> clazz) {
        if (!clazz.isInterface()) {
            throw new RuntimeException(clazz.getName() + " is not interface.");
        }
        IInsulator cachedInsulator = mInsulatorCachedMap.get(clazz);
        if (cachedInsulator != null) {
            return (T) cachedInsulator;
        }
        Class<? extends IInsulator> insulatorClazz = mInsulatorMap.get(clazz);
        if (insulatorClazz != null) {
            try {
                cachedInsulator = insulatorClazz.newInstance();
                mInsulatorCachedMap.put(clazz, cachedInsulator);
                return (T) cachedInsulator;
            } catch (Throwable e) {
                return null;
            }
        }
        return null;
    }

    public void addInsulators(InsulatorList insulatorList) {
        for (int i = 0; i < insulatorList.size(); i++) {
            addInsulator(insulatorList.get(i));
        }
    }

    public synchronized void addInsulator(Class<? extends IInsulator> implClazz) {
        Class<?>[] interfaces = implClazz.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class<?> interfaceClazz : interfaces) {
                if (interfaceClazz == IInsulator.class) {
                    continue;
                }
                try {
                    mInsulatorMap.put((Class<? extends IInsulator>) interfaceClazz, implClazz);
                } catch (Throwable e) {}
            }
        }
    }
}
