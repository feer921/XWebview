package com.fee.xwebview.core.x5

import android.graphics.Bitmap
import android.net.Uri
import com.fee.xwebview.core.IWebChromeClient
import com.tencent.smtt.export.external.interfaces.ConsoleMessage
import com.tencent.smtt.export.external.interfaces.JsPromptResult
import com.tencent.smtt.export.external.interfaces.JsResult
import com.tencent.smtt.sdk.ValueCallback
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebView

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/9/28<br>
 * Time: 19:23<br>
 * <P>DESC:
 * 对 X5[WebChromeClient]的扩展,让本 [WebChromeClient]的响应行为通过
 * 接口[IWebChromeClient]转回调出去
 * </p>
 * ******************(^_^)***********************
 */
open class AX5WebChromeClient : WebChromeClient() {
    protected var chromeClient: IWebChromeClient? = null
    open fun setWebChromeClientListener(l: IWebChromeClient?) {
        chromeClient = l
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        if (chromeClient != null) {
            val msg = if (consoleMessage != null) consoleMessage.message() else ""
            return chromeClient!!.onConsoleMessage(msg)
        }
        return super.onConsoleMessage(consoleMessage)
    }

    override fun onJsAlert(
        webView: WebView?,
        s: String?,
        s1: String?,
        jsResult: JsResult?
    ): Boolean {
        return if (chromeClient != null) {
            chromeClient!!.onJsCase(IWebChromeClient.TYPE_ON_JS_ALERT, s, s1, "")
        } else super.onJsAlert(webView, s, s1, jsResult)
    }

    override fun onJsConfirm(
        webView: WebView?,
        s: String?,
        s1: String?,
        jsResult: JsResult?
    ): Boolean {
        return if (chromeClient != null) {
            chromeClient!!.onJsCase(IWebChromeClient.TYPE_ON_JS_CONFIRM, s, s1, "")
        } else super.onJsConfirm(webView, s, s1, jsResult)
    }

    override fun onJsPrompt(
        webView: WebView?,
        s: String?,
        s1: String?,
        s2: String?,
        jsPromptResult: JsPromptResult?
    ): Boolean {
        return if (chromeClient != null) {
            chromeClient!!.onJsCase(IWebChromeClient.TYPE_ON_JS_PROMPT, s, s1, s2)
        } else super.onJsPrompt(webView, s, s1, s2, jsPromptResult)
    }

    override fun onProgressChanged(webView: WebView?, i: Int) {
        chromeClient?.onProgressChanged(i)
    }

    override fun onReceivedIcon(webView: WebView?, bitmap: Bitmap?) {
        chromeClient?.onReceivedIcon(bitmap)
    }

    override fun onReceivedTitle(webView: WebView?, s: String?) {
        chromeClient?.onReceivedTitle(s)
    }

    //H5选择文件的功能// For Android  > 4.1.1
    override fun openFileChooser(
        valueCallback: ValueCallback<Uri?>?,
        acceptType: String?,
        capture: String?
    ) {
        chromeClient?.openFileChooser(valueCallback, acceptType, capture)
    }

    override fun onShowFileChooser(
        webView: WebView?,
        valueCallback: ValueCallback<Array<Uri?>?>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        if (chromeClient != null) {
            var acceptTypes: Array<String?>? = null
            if (fileChooserParams != null) {
                acceptTypes = fileChooserParams.acceptTypes
            }
            return chromeClient!!.onShowOpenFileChooser(valueCallback, acceptTypes, "")
        }
        return super.onShowFileChooser(webView, valueCallback, fileChooserParams)
    }
}