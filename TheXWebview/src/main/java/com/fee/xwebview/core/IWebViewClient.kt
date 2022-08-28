package com.fee.xwebview.core

import android.graphics.Bitmap
import android.webkit.WebViewClient

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 16:06<br>
 * <P>DESC:
 * 该接口的目的为统一监听 不管是 原生 [WebView] 还是 X5 的 Webview的
 * WebViewClient的各方法回调
 * </p>
 * ******************(^_^)***********************
 */
interface IWebViewClient {

    /** Generic error  */
    val ERROR_UNKNOWN: Int
        get() = WebViewClient.ERROR_UNKNOWN

    /** Server or proxy hostname lookup failed  */
    val ERROR_HOST_LOOKUP: Int
        get() = WebViewClient.ERROR_HOST_LOOKUP

    /** Unsupported authentication scheme (not basic or digest)  */
    val ERROR_UNSUPPORTED_AUTH_SCHEME: Int
        get() = WebViewClient.ERROR_UNSUPPORTED_AUTH_SCHEME

    /** User authentication failed on server  */
    val ERROR_AUTHENTICATION: Int
        get() = WebViewClient.ERROR_AUTHENTICATION

    /** User authentication failed on proxy  */
    val ERROR_PROXY_AUTHENTICATION: Int
        get() = WebViewClient.ERROR_PROXY_AUTHENTICATION

    /** Failed to connect to the server  */
    val ERROR_CONNECT: Int
        get() = WebViewClient.ERROR_CONNECT

    /** Failed to read or write to the server  */
    val ERROR_IO: Int
        get() = WebViewClient.ERROR_IO

    /** Connection timed out  */
    val ERROR_TIMEOUT: Int
        get() = WebViewClient.ERROR_TIMEOUT

    /** Too many redirects  */
    val ERROR_REDIRECT_LOOP: Int
        get() = WebViewClient.ERROR_REDIRECT_LOOP

    /** Unsupported URI scheme  */
    val ERROR_UNSUPPORTED_SCHEME: Int
        get() = WebViewClient.ERROR_UNSUPPORTED_SCHEME

    /** Failed to perform SSL handshake  */
    val ERROR_FAILED_SSL_HANDSHAKE: Int
        get() = WebViewClient.ERROR_FAILED_SSL_HANDSHAKE

    /** Malformed URL  */
    val ERROR_BAD_URL: Int
        get() = WebViewClient.ERROR_BAD_URL

    /** Generic file error  */
    val ERROR_FILE: Int
        get() = WebViewClient.ERROR_FILE

    /** File not found  */
    val ERROR_FILE_NOT_FOUND: Int
        get() = WebViewClient.ERROR_FILE_NOT_FOUND

    /** Too many requests during this load  */
    val ERROR_TOO_MANY_REQUESTS: Int
        get() = WebViewClient.ERROR_TOO_MANY_REQUESTS

    /** Resource load was cancelled by Safe Browsing
     * WebViewClient.ERROR_UNSAFE_RESOURCE need api 26
     */
    val ERROR_UNSAFE_RESOURCE: Int
        get() = -16

    @Deprecated("Android原生标记为过时", replaceWith = ReplaceWith("shouldOverrideUrlLoading()","CommonWebResourceRequest"))
    fun shouldOverrideUrlLoading(url: String?): Boolean

    /**
     * 用来代替 标志为过时的[shouldOverrideUrlLoading]
     * @param request
     * @return
     */
    fun shouldOverrideUrlLoading(request: CommonWebResourceRequest?): Boolean

    fun onPageStarted(url: String?, favicon: Bitmap?)

    fun onPageFinished(url: String?)

    fun onLoadResource(url: String?)

    /**
     * 拦截 WebView的请求
     * 并且返回 请求的响应数据
     * @param request
     * @return [CommonWebResourceRsp]
     */
    fun shouldInterceptRequest(request: CommonWebResourceRequest?): CommonWebResourceRsp?

    /**
     * Callback from X5 webviwe
     * @param errorCode
     * @param description
     * @param failingUrl
     */
    fun onReceivedError(errorCode: Int, description: String?, failingUrl: String?)

    /**
     * Callback from Src webview
     * @param request
     * @param errorCode
     * @param errorDesc
     */
    fun onReceivedError(request: CommonWebResourceRequest?, errorCode: Int, errorDesc: String?)


    fun onReceivedHttpError(
        request: CommonWebResourceRequest?,
        errorResponse: CommonWebResourceRsp?
    )

    /**
     * 当加载网页时，如果收到SslError,是否需要继续进行
     * @return true:继续进行；false:取消
     * def: 默认为返回 true,让网页继续进行
     */
    fun isProceedWhenReceivedSslError(): Boolean = true

    fun onReceivedSslError()

    //    void onReceivedClientCertRequest(ICommonWebView view, ClientCertRequest request);
    //    void onReceivedHttpAuthRequest(ICommonWebView view, HttpAuthHandler handler, String host, String realm);
    fun onScaleChanged(oldScale: Float, newScale: Float)

    fun onReceivedLoginRequest(realm: String?, account: String?, args: String?)

    /**
     * 检测到白屏
     * Callback from X5 webview
     * @param url
     * @param code
     */
    fun onDetectedBlankScreen(url: String?, code: Int)

    /**
     * 对象的渲染器进程消失（可能是因为系统为收回急需的内存终止了渲染器，或是因为渲染器进程本身已崩溃）的情况。
     * 通过使用此 API，即使渲染器进程已经消失，您的应用也可以继续执行
     * @param didCrash 是否 crash了
     * @param rendererPriorityAtExit 返回当前渲染的优先级的值
     * @return true if the host application handled the situation that process has
     * exited, otherwise, application will crash if render process crashed,
     * or be killed if render process was killed by the system.
     */
    fun onRenderProcessGone(didCrash: Boolean, rendererPriorityAtExit: Int): Boolean = true
}