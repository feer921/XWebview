package com.fee.xwebview

import android.util.Log
import com.fee.TheXWebview.BuildConfig

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 11:30<br>
 * <P>DESC:
 * 控制 Log 输出的 对象类
 * </p>
 * ******************(^_^)***********************
 */

object L {
    private var isDebug = BuildConfig.DEBUG
    fun enableDebug(canDebug: Boolean) {
        isDebug = canDebug
    }
    internal fun d(logTag: String, logContent: String) {
        if (isDebug) {
            Log.d(logTag, logContent)
        }
    }

    internal fun e(logTag: String, logContent: String?, throwable: Throwable? = null) {
        if (isDebug) {
            Log.e(logTag, logContent, throwable)
        }
    }

    internal fun i(logTag: String,logContent: String){
        if (isDebug) {
            Log.i(logTag, logContent)
        }
    }
}