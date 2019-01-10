package com.liangtg.simplehan.monitor;

import android.os.Looper;
import android.util.Printer;

/**
 * @ProjectName: simplehan
 * @ClassName: BlockDetectByPrinter
 * @Description: java类作用描述
 * @Author: liangtg
 * @CreateDate: 19-1-10 上午11:45
 * @UpdateUser: 更新者
 * @UpdateDate: 19-1-10 上午11:45
 * @UpdateRemark: 更新说明
 */
public class BlockDetectByPrinter {

    public static void start() {

        Looper.getMainLooper().setMessageLogging(new Printer() {

            private static final String START = ">>>>> Dispatching";
            private static final String END = "<<<<< Finished";

            @Override
            public void println(String x) {
                if (x.startsWith(START)) {
                    LogMonitor.getInstance().startMonitor();
                }
                if (x.startsWith(END)) {
                    LogMonitor.getInstance().removeMonitor();
                }
            }
        });
    }
}
