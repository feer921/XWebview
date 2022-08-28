package com.fee.xwebview.core

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.view.ViewGroup
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
    var mOutSideCompatWebViewClient: ACompatWebViewClient? = null

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
        mOutSideCompatWebViewClient?.onPageStarted(url,favicon)
    }

//    override fun onPageFinished(url: String?) {
//    }

    override fun onLoadResource(url: String?) {
        mOutSideCompatWebViewClient?.onLoadResource(url)
    }

    /**
     * 拦截 WebView的请求
     * 并且返回 请求的响应数据
     * 如果 项目 有需要需要 拦截 H5的请求，可重写之
     * @param request
     * @return [CommonWebResourceRsp]
     */
    override fun shouldInterceptRequest(request: CommonWebResourceRequest?): CommonWebResourceRsp? {
        return mOutSideCompatWebViewClient?.shouldInterceptRequest(request)
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
        mOutSideCompatWebViewClient?.onReceivedHttpError(request,errorResponse)
    }

    override fun onReceivedSslError() {
        mOutSideCompatWebViewClient?.onReceivedSslError()
    }

    override fun onScaleChanged(oldScale: Float, newScale: Float) {
        mOutSideCompatWebViewClient?.onScaleChanged(oldScale,newScale)
    }

    override fun onReceivedLoginRequest(realm: String?, account: String?, args: String?) {
        mOutSideCompatWebViewClient?.onReceivedLoginRequest(realm,account,args)
    }

    /**
     * 检测到白屏
     * Callback from X5 webview
     * @param url
     * @param code
     */
    override fun onDetectedBlankScreen(url: String?, code: Int) {
        mOutSideCompatWebViewClient?.onDetectedBlankScreen(url,code)
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
        mOutSideCompatWebViewClient?.onReceivedIcon(icon)
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
        return mOutSideCompatWebViewClient?.onJsCase(jsShowCase, url, message, defaultValue) ?: false
    }

    /**
     * Report a JavaScript console message to the host application. The ChromeClient
     * should override this to process the log message as they see fit.
     *
     * @param message Object containing details of the console message.
     * @return true if the message is handled by the client.
     */
    override fun onConsoleMessage(message: String?): Boolean {
        return mOutSideCompatWebViewClient?.onConsoleMessage(message) ?: false
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
        mOutSideCompatWebViewClient?.openFileChooser(uploadFile,acceptType,capture)
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
        return mOutSideCompatWebViewClient?.onShowOpenFileChooser(uploadFile, acceptType, capture)
            ?: false
    }

    /**
     * 对应   WebChromeClient#onShowCustomView(View, WebChromeClient.CustomViewCallback)
     * 回调的处理
     * H5 中请求宿主需要进行全屏显示
     * @param view eg.: 而全屏的视频会在其参数View view中进行渲染
     * @return 处理者返回一个可以 添加 @param view 的容器View
     */
    override fun handleOnShowCustomView(view: View?): ViewGroup? {
        return mOutSideCompatWebViewClient?.handleOnShowCustomView(view)
    }

    override fun onHideCustomView() {
        mOutSideCompatWebViewClient?.onHideCustomView()
    }

    //-------------- @[IWebChromeClient] interface methods @end---------------------
}