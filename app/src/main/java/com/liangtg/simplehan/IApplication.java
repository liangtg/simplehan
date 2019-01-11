package com.liangtg.simplehan;

import com.github.liangtg.base.BaseApplication;
import com.pgyersdk.crash.PgyCrashManager;

/**
 * @ProjectName: simplehan
 * @ClassName: IApplication
 * @Description: java类作用描述
 * @Author: liangtg
 * @CreateDate: 19-1-10 上午11:46
 * @UpdateUser: 更新者
 * @UpdateDate: 19-1-10 上午11:46
 * @UpdateRemark: 更新说明
 */
public class IApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        PgyCrashManager.register();
    }
}
