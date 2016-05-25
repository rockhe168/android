package android.rock.com.mylibrary;

import android.rock.com.mylibrary.core.BaseHybrid;
import android.rock.com.mylibrary.core.NativeMethodInjectHelper;

/**
 * Created by xhhe on 2016/5/24.
 */

public class RackBridge {
    private volatile static RackBridge sInstance;

    private RackBridge() {
    }

    public static RackBridge getInstance() {
        RackBridge instance = sInstance;
        if (instance == null) {
            synchronized (RackBridge.class) {
                instance = sInstance;
                if (instance == null) {
                    instance = new RackBridge();
                    sInstance = instance;
                }
            }
        }
        return instance;
    }

    public NativeMethodInjectHelper loadingClass(Class<? extends BaseHybrid> clazz) {
        return NativeMethodInjectHelper.getInstance().loadingClass(clazz);
    }
}
