package com.fee.xwebview

import android.content.Context
import android.content.Intent
import android.webkit.WebView
import com.fee.TheXWebview.BuildConfig
//import com.fee.xwebview.core.x5.X5LoadService
import com.fee.xwebview.views.IWebview

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * <P>DESC:
 * WebView 初始化，辅助类
 * </p>
 * ******************(^_^)***********************
 */
object XWebViewHelper {
    @JvmStatic
    val BROADCAST_ACTION_OF_INIT_X5_RESULT: String = "action.com.common.x5.init"

    @JvmStatic
    val INTENT_KEY_NEED_BROADCAST_INIT_RESULT: String = "intent.key.need_broadcast"

    @JvmStatic
    val INTENT_KEY_CAN_DOWNLOAD_X5_WITHOUT_WIFI: String = "intent.key.download_without.wifi"

    @JvmStatic
    val INTENT_KEY_INIT_X5_RESLUT: String = "intent.key.init.x5.reslut"

    private var isX5InitOk = false


    internal fun setX5InitOk(isX5InitOk: Boolean) {
        this.isX5InitOk = isX5InitOk
    }

    fun isX5InitOk():Boolean {
        return this.isX5InitOk
    }

    /**
     * 启动 初始化 X5 WebView 内核的 独立进程 Service
     * 目的为 让 X5 内核的初始化 在独立进程中进行不影响 主进程
     */
//    fun initWebViewService(
//        context: Context,
//        needBroadcastInitResult: Boolean = false,
//        canInitX5WithoutWifi: Boolean = true
//    ) {
//        val intent = Intent(context, X5LoadService::class.java)
//        intent.putExtra(INTENT_KEY_CAN_DOWNLOAD_X5_WITHOUT_WIFI, canInitX5WithoutWifi)
//            .putExtra(INTENT_KEY_NEED_BROADCAST_INIT_RESULT, needBroadcastInitResult)
//        context.startService(intent)
//    }


    /**
     * 较通用的配置 WebView
     * @param theWebView 当前的具体的 类型 的 WebView 实例
     */
    fun commonConfigWebViewSettings(theWebView: IWebview) {
        theWebView.getWebSettings(false)?.apply {
            setAppCacheEnabled(true)
            allowContentAccess = true
            // Using setJavaScriptEnabled can introduce XSS vulnerabilities into your application,
            // review carefully
            javaScriptEnabled = true
            setSupportZoom(true)
            useWideViewPort = true//设置此属性，可任意比例缩放 设置webview推荐使用的窗口
            domStorageEnabled = true
            loadWithOverviewMode = true
            builtInZoomControls = true
            javaScriptCanOpenWindowsAutomatically = true
            allowFileAccess = true
            setAllowFileAccessFromFileURLs(true)
            setSupportMultipleWindows(true)
            setPluginsEnabled(true)
            savePassword = true
            textZoom = 100
            mediaPlaybackRequiresUserGesture = false
            if (Api.isApiCompatible(21)) {
                mixedContentMode = 0//允许加载H5网页时，https/http混合使用
            }
            displayZoomControls = false//一般不需要 网页上显示 缩放按钮
        }
        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true)//在 debug输出包时，把 Chrome 浏览器可调试功能开启
        }
    }

    /**
     * 根据 JS的方法名称以及所传的 方法参数，组装一条 WebView执行的 JS的方法信息
     * @param justJsMethodName .: "callJsMethod"
     * @param jsMethodParams JS内方法所需要传入的参数; .: '10',''
     * @return .: "callJsMethod('10',true,'man')"
     */
    fun assembleJsMethodInfos(justJsMethodName: String, vararg jsMethodParams: Any?): String {
        if (justJsMethodName.isNotBlank()) {
            val sb: StringBuilder = StringBuilder("$justJsMethodName(")
            val paramLen = jsMethodParams.size
            jsMethodParams.forEachIndexed { index, aParam ->
                var paramValue: Any = ""
                if (aParam != null) {
                    paramValue = aParam
                }
                sb.append("'$paramValue'")
                if (index != paramLen - 1) {
                    sb.append(",")
                }
            }
            sb.append(")")
            return sb.toString()
        }
        return ""
    }







}