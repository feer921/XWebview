package com.fee.xwebview.core.src

import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.fee.xwebview.core.IWebChromeClient

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 18:07<br>
 * <P>DESC:
 * 对 原生[WebChromeClient]的扩展,让本 [WebChromeClient]的响应行为通过
 * 接口[IWebChromeClient]转回调出去
 * </p>
 * ******************(^_^)***********************
 */
open class ASrcWebChromeClient : WebChromeClient() {
    protected var chromeClient: IWebChromeClient? = null

    open fun setChromeClientListener(l: IWebChromeClient?) {
        chromeClient = l
    }

    /**
     * Tell the host application the current progress of loading a page.
     *
     * @param view        The WebView that initiated the callback.
     * @param newProgress Current page loading progress, represented by
     */
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        chromeClient?.onProgressChanged(newProgress)
    }

    /**
     * Notify the host application of a change in the document title.
     *
     * @param view  The WebView that initiated the callback.
     * @param title A String containing the new title of the document.
     */
    override fun onReceivedTitle(view: WebView?, title: String?) {
        chromeClient?.onReceivedTitle(title)
    }

    /**
     * Notify the host application of a new favicon for the current page.
     *
     * @param view The WebView that initiated the callback.
     * @param icon A Bitmap containing the favicon for the current page.
     */
    override fun onReceivedIcon(view: WebView?, icon: Bitmap?) {
        chromeClient?.onReceivedIcon(icon)
    }

    /**
     * Tell the client to display a javascript alert dialog.  If the client
     * returns true, WebView will assume that the client will handle the
     * dialog.  If the client returns false, it will continue execution.
     *
     * @param view    The WebView that initiated the callback.
     * @param url     The url of the page requesting the dialog.
     * @param message Message to be displayed in the window.
     * @param result  A JsResult to confirm that the user hit enter.
     * @return boolean Whether the client will handle the alert dialog.
     */
    override fun onJsAlert(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean {
        chromeClient?.let {
           return it.onJsCase(IWebChromeClient.TYPE_ON_JS_ALERT, url, message, "")
        }
        return super.onJsAlert(view, url, message, result)
    }

    /**
     * Tell the client to display a confirm dialog to the user. If the client
     * returns true, WebView will assume that the client will handle the
     * confirm dialog and call the appropriate JsResult method. If the
     * client returns false, a default value of false will be returned to
     * javascript. The default behavior is to return false.
     *
     * @param view    The WebView that initiated the callback.
     * @param url     The url of the page requesting the dialog.
     * @param message Message to be displayed in the window.
     * @param result  A JsResult used to send the user's response to
     * javascript.
     * @return boolean Whether the client will handle the confirm dialog.
     */
    override fun onJsConfirm(
        view: WebView?,
        url: String?,
        message: String?,
        result: JsResult?
    ): Boolean {
        return if (chromeClient != null) {
            chromeClient!!.onJsCase(IWebChromeClient.TYPE_ON_JS_CONFIRM, url, message, "")
        } else super.onJsConfirm(view, url, message, result)
    }

    /**
     * Tell the client to display a prompt dialog to the user. If the client
     * returns true, WebView will assume that the client will handle the
     * prompt dialog and call the appropriate JsPromptResult method. If the
     * client returns false, a default value of false will be returned to to
     * javascript. The default behavior is to return false.
     *
     * @param view         The WebView that initiated the callback.
     * @param url          The url of the page requesting the dialog.
     * @param message      Message to be displayed in the window.
     * @param defaultValue The default value displayed in the prompt dialog.
     * @param result       A JsPromptResult used to send the user's reponse to
     * javascript.
     * @return boolean Whether the client will handle the prompt dialog.
     */
    override fun onJsPrompt(
        view: WebView?,
        url: String?,
        message: String?,
        defaultValue: String?,
        result: JsPromptResult?
    ): Boolean {
        return if (chromeClient != null) {
            chromeClient!!.onJsCase(IWebChromeClient.TYPE_ON_JS_PROMPT, url, message, defaultValue)
        } else super.onJsPrompt(view, url, message, defaultValue, result)
    }

    /**
     * Notify the host application that web content is requesting permission to
     * access the specified resources and the permission currently isn't granted
     * or denied. The host application must invoke [PermissionRequest.grant]
     * or [PermissionRequest.deny].
     *
     *
     * If this method isn't overridden, the permission is denied.
     *
     * @param request the PermissionRequest from current web content.
     */
    override fun onPermissionRequest(request: PermissionRequest?) {
        super.onPermissionRequest(request)
    }

    /**
     * Report a JavaScript console message to the host application. The ChromeClient
     * should override this to process the log message as they see fit.
     *
     * @param consoleMessage Object containing details of the console message.
     * @return true if the message is handled by the client.
     */
    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        if (chromeClient != null) {
            val consoleMsg = if (consoleMessage != null) consoleMessage.message() else ""
            return chromeClient!!.onConsoleMessage(consoleMsg)
        }
        return super.onConsoleMessage(consoleMessage)
    }

    /**
     * Tell the client to show a file chooser.
     *
     *
     * This is called to handle HTML forms with 'file' input type, in response to the
     * user pressing the "Select File" button.
     * To cancel the request, call `filePathCallback.onReceiveValue(null)` and
     * return true.
     *
     * @param webView           The WebView instance that is initiating the request.
     * @param filePathCallback  Invoke this callback to supply the list of paths to files to upload,
     * or NULL to cancel. Must only be called if the
     * `showFileChooser` implementations returns true.
     * @param fileChooserParams Describes the mode of file chooser to be opened, and options to be
     * used with it.
     * @return true if filePathCallback will be invoked, false to use default handling.
     * @see FileChooserParams
     */
    override fun onShowFileChooser(//ValueCallback<Uri[]>
        webView: WebView?,
        filePathCallback: ValueCallback<Array<Uri?>>?,
        fileChooserParams: FileChooserParams?
    ): Boolean {
        if (chromeClient != null) {
            val acceptTypes: Array<String>? = null
            if (fileChooserParams != null) {
//                acceptTypes = fileChooserParams.getAcceptTypes();// API 21
            }
            return chromeClient!!.onShowOpenFileChooser(filePathCallback, acceptTypes, "")
        }
        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
    }

    //因为 原生WebView 如果有视频播放时点击需要 全屏播放，则需要实现该方法(如果不重写，H5的video标签的控制器的按钮为灰色)
    private var mCustomViewFromH5: View? = null
    private var callback: CustomViewCallback? = null
    private var customViewContainer: ViewGroup? = null

    /**
     *  H5内 <video>标签 的控制 点击时回调
     */
    override fun onShowCustomView(view: View?, callback: CustomViewCallback?) {
        super.onShowCustomView(view, callback)
        customViewContainer = chromeClient?.handleOnShowCustomView(view)
        if (customViewContainer == null) {
            //外部不处理的情况下，就都不处理了
            return
        }
        if (mCustomViewFromH5 != null) {
            callback?.onCustomViewHidden()
            return
        }
        mCustomViewFromH5 = view
        mCustomViewFromH5?.visibility = View.VISIBLE
        this.callback = callback
        customViewContainer?.let {
            if (mCustomViewFromH5?.parent == null) {
                it.addView(mCustomViewFromH5)
            }
            it.visibility = View.VISIBLE
            it.bringToFront()
        }
    }

    override fun onHideCustomView() {
        super.onHideCustomView()
        if (mCustomViewFromH5 == null) {
            return
        }
        mCustomViewFromH5?.visibility = View.GONE
        customViewContainer?.let {
            it.removeView(mCustomViewFromH5)
            it.visibility = View.GONE
        }
        mCustomViewFromH5 = null
        try {
            callback?.onCustomViewHidden()
        } catch (ex: Exception) {
        }
        chromeClient?.onHideCustomView()//一般需要外部 恢复 屏幕方向
    }
}