package com.fee.xwebview.core

import android.webkit.JavascriptInterface
import java.lang.ref.WeakReference

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 11:15<br>
 * <P>DESC:
 * H5与原生 交互的接口 映射对象
 * </p>
 * ******************(^_^)***********************
 */
open class H5Bridge(theH5ThinkImp: IH5Think?) {

    protected var weakRefH5ThinkImp: WeakReference<IH5Think>? = null

    init {
        weakRefH5ThinkImp = WeakReference(theH5ThinkImp)
    }

    /**
     * JS 调用原生的 桥梁方法，JS调用参考：
     * window.android(接口映射名,该名称为使用者给JS注入Java的接口映射时所指定).jsReqNativeDo("{'key1':'value1'}")
     */
    @JavascriptInterface
    open fun jsReqNativeDo(reqJsonData: String) {
        try {
            weakRefH5ThinkImp?.get()?.onJsReqNativeDo(reqJsonData)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    /**
     * 该接口为 JS通过调用 原生的接口 映射对象[H5Bridge],再由[H5Bridge]通过接口方式回调出去
     */
    interface IH5Think{
        /**
         * @param reqJsonData JS调用 原生 传递的 JSon字符器 数据
         */
        fun onJsReqNativeDo(reqJsonData: String)
    }
}
