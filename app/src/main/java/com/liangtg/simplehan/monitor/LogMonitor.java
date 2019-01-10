package com.liangtg.simplehan.monitor;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

/**
 * @ProjectName: simplehan
 * @ClassName: LogMonitor
 * @Description: java类作用描述
 * @Author: liangtg
 * @CreateDate: 19-1-10 上午11:42
 * @UpdateUser: 更新者
 * @UpdateDate: 19-1-10 上午11:42
 * @UpdateRemark: 更新说明
 */
public class LogMonitor {

    private static final long TIME_BLOCK = 16L;
    private static LogMonitor sInstance = new LogMonitor();
    private static Runnable mLogRunnable = new Runnable() {
        @Override
        public void run() {
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString() + "\n");
            }
            Log.e("TAG", sb.toString());
            Log.e("TAG", "------");
        }
    };
    private HandlerThread mLogThread = new HandlerThread("log");
    private Handler mIoHandler;

    private LogMonitor() {
        mLogThread.start();
        mIoHandler = new Handler(mLogThread.getLooper());
    }

    public static LogMonitor getInstance() {
        return sInstance;
    }

    public boolean isMonitor() {
//        return mIoHandler.hasCallbacks(mLogRunnable);
        return false;
    }

    public void startMonitor() {
        mIoHandler.postDelayed(mLogRunnable, TIME_BLOCK);
    }

    public void removeMonitor() {
        mIoHandler.removeCallbacks(mLogRunnable);
    }
}
