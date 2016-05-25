package android.rock.com.mylibrary.async;

import android.os.Process;

import java.util.concurrent.ThreadFactory;

/**
 * Created by xhhe on 2016/5/23.
 */

public class AsyncTaskThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(final Runnable runnable) {
        Runnable wrapper = new Runnable() {
            @Override
            public void run() {
                try {
                    Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
                } catch (Throwable t) {
                    t.printStackTrace();
                }
                runnable.run();
            }
        };
        Thread thread = new Thread(wrapper, "JsBridge AsyncTaskExecutor");
        if (thread.isDaemon())
            thread.setDaemon(false);
        return thread;
    }
}
