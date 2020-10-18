package com.fee.xwebview.views

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import com.fee.xwebview.XWebViewHelper

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/18<br>
 * Time: 15:40<br>
 * <P>DESC:
 * SDK 所提供的一个通用的 封装了 [WebView] 的 自定义View
 * </p>
 * ******************(^_^)***********************
 */
open class CommonWebView(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {

    constructor(context: Context) : this(context, null)

    /**
     * 当前的 具体类型 WebView，本类会根据SDK初始化情况，优先使用 X5 WebView，可切换
     */
    protected var theWebView: IWebview? = null


    /**
     * WebView 初始加载的 H5的 url地址，该地址目的是为了在系统回调 加载结束时来比较的
     */
    protected var theHostUrl: String? = ""


    init {
//        XWebViewHelper
        //自定义属性？？

    }
    fun loadUrl(webViewUrl: String?) {
        if (!webViewUrl.isNullOrBlank()) {
            theWebView?.loadUrl(webViewUrl)
        }
    }

    /**
     * 在加载 相关的 [webViewUrl]前，可以先在[configBlock]进行 设置 WebView相关参数
     */
    fun loadUrl(webViewUrl: String?, configBlock: IWebview.() -> Unit) {
        if (!webViewUrl.isNullOrBlank()) {
            theWebView?.let {
                configBlock(it)
                it.loadUrl(webViewUrl)
            }
        }
    }

    fun reload() {
        theWebView?.reload()
    }

}