package com.fee.xwebview.demo

import android.app.Application
import com.fee.xwebview.XWebViewHelper

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/9<br>
 * Time: 17:59<br>
 * <P>DESC:
 * </p>
 * ******************(^_^)***********************
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        XWebViewHelper.initWebViewService(this)
    }
}