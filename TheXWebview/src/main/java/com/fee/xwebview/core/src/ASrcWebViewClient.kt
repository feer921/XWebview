package com.fee.xwebview.core.src

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Build
import android.webkit.*
import com.fee.xwebview.Api
import com.fee.xwebview.L
import com.fee.xwebview.core.CommonWebResourceRequest
import com.fee.xwebview.core.CommonWebResourceRsp
import com.fee.xwebview.core.IWebViewClient

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 16:36<br>
 * <P>DESC:
 * 对 原生[WebViewClient]的扩展
 * 目的为通过接口转回调出去,达到 不管是 原生WebView还是 X5 WebView,关于 WebViewClient的各方法回调的接收者一致
 * </p>
 * ******************(^_^)***********************
 */
open class ASrcWebViewClient : WebViewClient() {
    protected var mWebViewClientListener: IWebViewClient? = null

    fun setWebViewClientListener(l: IWebViewClient?) {
        this.mWebViewClientListener = l
    }


    /**
     * Give the host application a chance to take over the control when a new
     * url is about to be loaded in the current WebView. If WebViewClient is not
     * provided, by default WebView will ask Activity Manager to choose the
     * proper handler for the url. If WebViewClient is provided, return true
     * means the host application handles the url, while return false means the
     * current WebView handles the url.
     * This method is not called for requests using the POST "method".
     *
     * @param view The WebView that is initiating the callback.
     * @param url  The url to be loaded.
     * @return True if the host application wants to leave the current WebView
     * and handle the url itself, otherwise return false.
     */
    @Deprecated(
        "Use {@link #shouldOverrideUrlLoading(WebView, WebResourceRequest)shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead."
    )
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        return if (mWebViewClientListener != null) {
            mWebViewClientListener!!.shouldOverrideUrlLoading(url)
        } else super.shouldOverrideUrlLoading(view, url)
    }

    @Deprecated("")
    override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
        mWebViewClientListener?.let {
            val commonWebResourceRsp: CommonWebResourceRsp? =
                it.shouldInterceptRequest(CommonWebResourceRequest(url))
            if (commonWebResourceRsp != null) {
                val srcWebResourceResponse: WebResourceResponse? =
                    commonWebResourceRsp.srcWebResourceResponse
                if (srcWebResourceResponse != null) {
                    return srcWebResourceResponse
                }
            }
        }
        return super.shouldInterceptRequest(view, url)
    }

    override fun shouldInterceptRequest(
        view: WebView?,
        request: WebResourceRequest?
    ): WebResourceResponse? {
        mWebViewClientListener?.let {
            val commonWebResourceRsp: CommonWebResourceRsp? =
                it.shouldInterceptRequest(CommonWebResourceRequest(request))
            if (commonWebResourceRsp != null) {
                val srcWebResourceResponse = commonWebResourceRsp.srcWebResourceResponse
                if (srcWebResourceResponse != null) {
                    return srcWebResourceResponse
                }
            }
        }
        return super.shouldInterceptRequest(view, request)
    }

    /**
     * Give the host application a chance to take over the control when a new
     * url is about to be loaded in the current WebView. If WebViewClient is not
     * provided, by default WebView will ask Activity Manager to choose the
     * proper handler for the url. If WebViewClient is provided, return true
     * means the host application handles the url, while return false means the
     * current WebView handles the url.
     * Notes:
     *  * This method is not called for requests using the POST &quot;method&quot;.
     *  * This method is also called for subframes with non-http schemes, thus it is
     * strongly disadvised to unconditionally call [WebView.loadUrl]
     * with the request's url from inside the method and then return true,
     * as this will make WebView to attempt loading a non-http url, and thus fail.
     *
     * @param view    The WebView that is initiating the callback.
     * @param request Object containing the details of the request.
     * @return True if the host application wants to leave the current WebView
     * and handle the url itself, otherwise return false.
     */
    @SuppressLint("NewApi")
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        if (mWebViewClientListener != null) {
            var resourceRequest: CommonWebResourceRequest? = null
            if (request != null) {
                resourceRequest = CommonWebResourceRequest(request)
            }
            return mWebViewClientListener!!.shouldOverrideUrlLoading(resourceRequest)
        }
        return super.shouldOverrideUrlLoading(view, request)
    }

    /**
     * Notify the host application that a page has started loading. This method
     * is called once for each main frame load so a page with iframes or
     * framesets will call onPageStarted one time for the main frame. This also
     * means that onPageStarted will not be called when the contents of an
     * embedded frame changes, i.e. clicking a link whose target is an iframe,
     * it will also not be called for fragment navigations (navigations to
     * #fragment_id).
     *
     * @param view    The WebView that is initiating the callback.
     * @param url     The url to be loaded.
     * @param favicon The favicon for this page if it already exists in the
     */
    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        mWebViewClientListener?.onPageStarted(url, favicon)
    }


    /**
     * Notify the host application that a page has finished loading. This method
     * is called only for main frame. When onPageFinished() is called, the
     * rendering picture may not be updated yet. To get the notification for the
     * new Picture, use [WebView.PictureListener.onNewPicture].
     *
     * @param view The WebView that is initiating the callback.
     * @param url  The url of the page.
     */
    override fun onPageFinished(view: WebView?, url: String?) {
        mWebViewClientListener?.onPageFinished(url)
    }

    /**
     * Report web resource loading error to the host application. These errors usually indicate
     * inability to connect to the server. Note that unlike the deprecated version of the callback,
     * the new version will be called for any resource (iframe, image, etc), not just for the main
     * page. Thus, it is recommended to perform minimum required work in this callback.
     *
     * @param view    The WebView that is initiating the callback.
     * @param request The originating request.
     * @param error   Information about the error occured.
     */
    @SuppressLint("NewApi")
    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        mWebViewClientListener?.let {
            var resourceRequest: CommonWebResourceRequest? = null
            if (request != null) {
                resourceRequest = CommonWebResourceRequest(request)
            }
            var errCode = 0
            var errorDesc = ""
            if (error != null) {
                if (Api.isApiCompatible(23)) {
                    errCode = error.errorCode //api 23
                    errorDesc = error.description.toString() //api 23
                }
            }
            it.onReceivedError(resourceRequest, errCode, errorDesc)
        }
    }

    /**
     * Notify the host application that an HTTP error has been received from the server while
     * loading a resource.  HTTP errors have status codes &gt;= 400.  This callback will be called
     * for any resource (iframe, image, etc), not just for the main page. Thus, it is recommended to
     * perform minimum required work in this callback. Note that the content of the server
     * response may not be provided within the **errorResponse** parameter.
     *
     * @param view          The WebView that is initiating the callback.
     * @param request       The originating request.
     * @param errorResponse Information about the error occured.
     */
    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        if (mWebViewClientListener != null) {
            val commonRequest = CommonWebResourceRequest(request)
            val commonWebResourceRsp = CommonWebResourceRsp(errorResponse)
            mWebViewClientListener!!.onReceivedHttpError(commonRequest, commonWebResourceRsp)
        }
    }

    /**
     * Notify the host application that an SSL error occurred while loading a
     * resource. The host application must call either handler.cancel() or
     * handler.proceed(). Note that the decision may be retained for use in
     * response to future SSL errors. The default behavior is to cancel the
     * load.
     *
     * @param view    The WebView that is initiating the callback.
     * @param handler An SslErrorHandler object that will handle the user's
     * response.
     * @param error   The SSL error object.
     */
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, error: SslError?) {
        mWebViewClientListener?.let {
            it.onReceivedSslError()
            if (it.isProceedWhenReceivedSslError()) {
                handler.proceed()
                return
            }
        }
        super.onReceivedSslError(view, handler, error)
    }

    /**
     * Notify the host application that the scale applied to the WebView has
     * changed.
     *
     * @param view     The WebView that is initiating the callback.
     * @param oldScale The old scale factor
     * @param newScale The new scale factor
     */
    override fun onScaleChanged(view: WebView?, oldScale: Float, newScale: Float) {
        mWebViewClientListener?.onScaleChanged(oldScale, newScale)
    }

    override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
        var didCrash = false
        var curRenderPriorityAtExit = 0
        if (Build.VERSION.SDK_INT >= 26 && detail != null) {
            didCrash = detail.didCrash()
            curRenderPriorityAtExit = detail.rendererPriorityAtExit()
        }

        L.e("ASrcWebViewClient", "-->onRenderProcessGone() detail =  $detail didCrash = $didCrash")
        var handled = false
        if (mWebViewClientListener != null) {
            handled = mWebViewClientListener!!.onRenderProcessGone(didCrash, curRenderPriorityAtExit)
        }
        return if (handled) {
            true
        } else super.onRenderProcessGone(view, detail)
    }
}