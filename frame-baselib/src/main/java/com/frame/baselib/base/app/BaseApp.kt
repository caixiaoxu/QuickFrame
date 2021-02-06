package com.frame.baselib.base.app

import android.app.Application
import com.frame.baselib.common.GlobalParamter

/**
 * Title : Application基类
 * Author: Lsy
 * Date: 2/6/21 10:00 AM
 * Version:
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalParamter.globalContext = this
    }
}