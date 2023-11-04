package com.fee.xwebview.core

import android.webkit.ValueCallback


/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 15:52<br>
 * <P>DESC:
 * 这里主要统一 [X5] 的[ValueCallback]和原生的，由于X5的ValueCallback继承的是原生的ValueCallback
 * 所以继承X5的即可
 * 该接口为 WebView执行 JS方法时的返回回调接口
 * </p>
 * ******************(^_^)***********************
 */
interface CommonValueCallback<V>: ValueCallback<V> {
    override fun onReceiveValue(value: V) {

    }
}