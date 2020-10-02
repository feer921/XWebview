package com.fee.xwebview.core.x5

import android.graphics.Bitmap
import com.fee.xwebview.core.CommonWebResourceRequest
import com.fee.xwebview.core.CommonWebResourceRsp
import com.fee.xwebview.core.IWebViewClient
import com.tencent.smtt.export.external.interfaces.*
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 16:15<br>
 * <P>DESC:
 * 扩展 X5 的 [WebViewClient]，目的是使 X5 的 [WebViewClient]的回调转至
 * 接口[IWebViewClient]的回调出去
 * </p>
 * ******************(^_^)***********************
 */
open class AX5WebViewClient : WebViewClient() {
    protected var mWebViewClientListener: IWebViewClient? = null

    fun setWebViewClientListener(l: IWebViewClient?) {
        this.mWebViewClientListener = l
    }

    override fun shouldOverrideUrlLoading(webview: WebView?, url: String?): Boolean {
        if (mWebViewClientListener != null) {
            return mWebViewClientListener!!.shouldOverrideUrlLoading(url)
        }
        return super.shouldOverrideUrlLoading(webview, url)
    }

    override fun shouldInterceptRequest(
        webView: WebView?,
        webResourceRequest: WebResourceRequest?
    ): WebResourceResponse? {
        mWebViewClientListener?.let {
            val commonWebResourceRsp: CommonWebResourceRsp?  =
                it.shouldInterceptRequest(CommonWebResourceRequest(webResourceRequest))
            if (commonWebResourceRsp != null) {
                val x5WebResourceResponse: WebResourceResponse? =
                    commonWebResourceRsp.x5WebResourceResponse
                if (x5WebResourceResponse != null) {
                    return x5WebResourceResponse
                }
            }
        }
        return super.shouldInterceptRequest(webView, webResourceRequest)
    }

    override fun onPageStarted(webView: WebView?, s: String?, bitmap: Bitmap?) {
        super.onPageStarted(webView, s, bitmap)
        mWebViewClientListener?.onPageStarted(s, bitmap)
    }

    override fun onPageFinished(webView: WebView?, s: String?) {
        mWebViewClientListener?.onPageFinished(s)
    }

    override fun onReceivedError(webView: WebView?, i: Int, s: String?, s1: String?) {
        mWebViewClientListener?.onReceivedError(i, s, s1)
    }

    override fun onReceivedError(
        webView: WebView?,
        webResourceRequest: WebResourceRequest?,
        webResourceError: WebResourceError?
    ) {
        super.onReceivedError(webView, webResourceRequest, webResourceError)
    }

    override fun onReceivedHttpError(
        webView: WebView?,
        webResourceRequest: WebResourceRequest?,
        webResourceResponse: WebResourceResponse?
    ) {
        mWebViewClientListener?.let {
            val resourceRequest = CommonWebResourceRequest(webResourceRequest)
            val rsp = CommonWebResourceRsp(webResourceResponse)
            it.onReceivedHttpError(resourceRequest, rsp)
        }
    }

    override fun onReceivedSslError(
        webView: WebView?,
        sslErrorHandler: SslErrorHandler,
        sslError: SslError?
    ) {
//        super.onReceivedSslError(webView, sslErrorHandler, sslError);
        sslErrorHandler.proceed()
//        if (webViewClient != null) {
//            webViewClient.onReceivedSslError(null, sslErrorHandler, sslError);
//        }
    }

    override fun onScaleChanged(webView: WebView?, v: Float, v1: Float) {
        mWebViewClientListener?.onScaleChanged(v, v1)
    }

    /**
     * Callback from X5 webview
     * @param s
     * @param i
     */
    override fun onDetectedBlankScreen(s: String?, i: Int) {
        mWebViewClientListener?.onDetectedBlankScreen(s, i)
    }
}