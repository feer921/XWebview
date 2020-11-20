package com.fee.xwebview.core

import android.graphics.Bitmap
import android.net.Uri
import android.webkit.ValueCallback

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/18<br>
 * Time: 17:29<br>
 * <P>DESC:
 * 该类即实现[IWebViewClient] 也实现[IWebChromeClient],即统一 WebView的事件回调
 * </p>
 * ******************(^_^)***********************
 */
abstract class ACompatWebViewClient : IWebViewClient,IWebChromeClient{
    private var outSideCompatWebViewClient: ACompatWebViewClient? = null

    //-------------- @[IWebViewClient] interface methods @start---------------------
//    override fun shouldOverrideUrlLoading(url: String?): Boolean {
//    }
    /**
     * 用来代替 标志为过时的[shouldOverrideUrlLoading]
     * @param request
     * @return
     */
    override fun shouldOverrideUrlLoading(request: CommonWebResourceRequest?): Boolean {
        request?.let {
            return shouldOverrideUrlLoading(it.getCurUrlInfo())
        }
        return true
    }

    override fun onPageStarted(url: String?, favicon: Bitmap?) {

    }

//    override fun onPageFinished(url: String?) {
//    }

    override fun onLoadResource(url: String?) {
    }

    /**
     * 拦截 WebView的请求
     * 并且返回 请求的响应数据
     * 如果 项目 有需要需要 拦截 H5的请求，可重写之
     * @param request
     * @return [CommonWebResourceRsp]
     */
    override fun shouldInterceptRequest(request: CommonWebResourceRequest?): CommonWebResourceRsp? {
        return null
    }

//    /**
//     * Callback from X5 webviwe
//     * @param errorCode
//     * @param description
//     * @param failingUrl
//     */
//    override fun onReceivedError(errorCode: Int, description: String?, failingUrl: String?) {
//        //外部实现
//    }

    /**
     * Callback from Src webview
     * @param request
     * @param errorCode
     * @param errorDesc
     */
    override fun onReceivedError(
        request: CommonWebResourceRequest?,
        errorCode: Int,
        errorDesc: String?
    ) {
        var failingUrl: String? = ""
        if (request != null) {
            failingUrl = request.getCurUrlInfo()
        }
        onReceivedError(errorCode,errorDesc,failingUrl)
    }

    override fun onReceivedHttpError(
        request: CommonWebResourceRequest?,
        errorResponse: CommonWebResourceRsp?
    ) {
    }

    override fun onReceivedSslError() {
    }

    override fun onScaleChanged(oldScale: Float, newScale: Float) {
    }

    override fun onReceivedLoginRequest(realm: String?, account: String?, args: String?) {

    }

    /**
     * 检测到白屏
     * Callback from X5 webview
     * @param url
     * @param code
     */
    override fun onDetectedBlankScreen(url: String?, code: Int) {
    }

    //-------------- @[IWebViewClient] interface methods @end---------------------



    //-------------- @[IWebChromeClient] interface methods @start---------------------

//    /**
//     * Tell the host application the current progress of loading a page.
//     *
//     *
//     * //     * @param view        The WebView that initiated the callback.
//     *
//     * @param newProgress Current page loading progress, represented by
//     */
//    override fun onProgressChanged(newProgress: Int) {
//    }

//    /**
//     * Notify the host application of a change in the document title.
//     *
//     * @param title A String containing the new title of the document.
//     */
//    override fun onReceivedTitle(title: String?) {
//    }

    /**
     * Notify the host application of a new favicon for the current page.
     *
     *
     * //     * @param view The WebView that initiated the callback.
     *
     * @param icon A Bitmap containing the favicon for the current page.
     */
    override fun onReceivedIcon(icon: Bitmap?) {
    }

    /**
     * WebView在加载网页过程中Js的行为
     *
     * @param jsShowCase
     *  * {[.TYPE_ON_JS_ALERT]}
     *  * {[.TYPE_ON_JS_CONFIRM]}
     *  * {[.TYPE_ON_JS_PROMPT]}
     *
     * @param url
     * @param message
     * @param defaultValue Alert时没有该值
     * @return boolean Whether the client will handle the alert/confirm/prompt dialog.
     */
    override fun onJsCase(
        jsShowCase: Int,
        url: String?,
        message: String?,
        defaultValue: String?
    ): Boolean {
        return false
    }

    /**
     * Report a JavaScript console message to the host application. The ChromeClient
     * should override this to process the log message as they see fit.
     *
     * @param message Object containing details of the console message.
     * @return true if the message is handled by the client.
     */
    override fun onConsoleMessage(message: String?): Boolean {
        return false
    }

    /**
     * Tell the client to open a file chooser.
     *
     * @param uploadFile A ValueCallback to set the URI of the file to upload.
     * onReceiveValue must be called to wake up the thread.a
     * @param acceptType The value of the 'accept' attribute of the input tag
     * associated with this file picker.
     * @param capture    The value of the 'capture' attribute of the input tag
     * associated with this file picker.
     */
    override fun openFileChooser(
        uploadFile: ValueCallback<Uri>?,
        acceptType: String?,
        capture: String?
    ) {
    }

    /**
     * Tell the client to open a file chooser.
     *
     * @param uploadFile A ValueCallback to set the URI of the file to upload.
     * onReceiveValue must be called to wake up the thread.a
     * @param acceptType The value of the 'accept' attribute of the input tag
     * associated with this file picker.
     * @param capture    The value of the 'capture' attribute of the input tag
     * associated with this file picker.
     */
    override fun onShowOpenFileChooser(
        uploadFile: ValueCallback<Array<Uri>>?,
        acceptType: Array<out String>?,
        capture: String?
    ): Boolean {
        return false
    }

    //-------------- @[IWebChromeClient] interface methods @end---------------------
}