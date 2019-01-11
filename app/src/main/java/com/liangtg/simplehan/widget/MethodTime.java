package com.liangtg.simplehan.widget;

import android.util.Log;

import java.util.HashMap;

import androidx.collection.ArraySet;
import androidx.core.util.Pools;

/**
 * @ProjectName: simplehan
 * @ClassName: MethodTime
 * @Description: java类作用描述
 * @Author: liangtg
 * @CreateDate: 19-1-11 下午2:25
 * @UpdateUser: 更新者
 * @UpdateDate: 19-1-11 下午2:25
 * @UpdateRemark: 更新说明
 */
public class MethodTime {
    private static final Pools.SimplePool<MethodTime> sPool = new Pools.SimplePool<MethodTime>(50);
    private static final HashMap<String, ArraySet<Long>> METHOD_TIME = new HashMap<>();
    private long start;
    private String tag;
    private long time;

    private MethodTime() {
    }

    public static MethodTime obtain() {
        MethodTime instance = sPool.acquire();
        return (instance != null) ? instance : new MethodTime();
    }

    public MethodTime tag(String tag) {
        start = System.currentTimeMillis();
        this.tag = tag;
        return this;
    }

    public void end() {
        time = System.currentTimeMillis() - start;
        ArraySet<Long> set = METHOD_TIME.get(tag);
        if (null == set) {
            logTime();
            ArraySet<Long> value = new ArraySet<>();
            value.add(time);
            METHOD_TIME.put(tag, value);
        } else if (!set.contains(time)) {
            set.add(time);
            logTime();
        }
        recycle();
    }

    private void logTime() {
        Log.d("tt", String.format("%s\t%dms", tag, time));
    }

    public void recycle() {
        sPool.release(this);
    }
}
