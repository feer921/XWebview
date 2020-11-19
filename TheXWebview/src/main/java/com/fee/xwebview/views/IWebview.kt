package com.fee.xwebview.views

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Picture
import android.view.View
import androidx.annotation.NonNull
import com.fee.xwebview.core.CommonValueCallback
import com.fee.xwebview.core.WebSettingsWrapper
import com.fee.xwebview.core.WebViewClientAndChromeClientSelector

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 15:30<br>
 * <P>DESC:
 * 把 WebView 抽象为接口
 * </p>
 * ******************(^_^)***********************
 */
interface IWebview {

    fun loadUrl(webviewUrl: String)

    /**
     * Loads the given URL with the specified additional HTTP headers.
     * Also see compatibility note on [.evaluateJavascript].
     *
     * @param url the URL of the resource to load
     * @param additionalHttpHeaders the additional headers to be used in the
     * HTTP request for this URL, specified as a map from name to
     * value. Note that if this map contains any of the headers
     * that are set by default by this WebView, such as those
     * controlling caching, accept types or the User-Agent, their
     * values may be overridden by this WebView's defaults.
     */
    fun loadUrl(url: String, additionalHttpHeaders: Map<String?, String?>?)

    /**
     * Loads the URL with postData using "POST" method into this WebView. If url
     * is not a network URL, it will be loaded with [.loadUrl]
     * instead, ignoring the postData param.
     *
     * @param url the URL of the resource to load
     * @param postData the data will be passed to "POST" request, which must be
     * be "application/x-www-form-urlencoded" encoded.
     */
    fun postUrl(url: String, postData: ByteArray?)

    fun loadData(data: String?, mimeType: String?, encoding: String?)

    fun loadDataWithBaseURL(
        baseUrl: String?,
        data: String?,
        mimeType: String?,
        encoding: String?,
        historyUrl: String?
    )


    /**
     * 异步执行JS脚本
     * Asynchronously evaluates JavaScript in the context of the currently displayed page.
     * If non-null, |resultCallback| will be invoked with any result returned from that
     * execution. This method must be called on the UI thread and the callback will
     * be made on the UI thread.
     *
     *
     * Compatibility note. Applications targeting [android.os.Build.VERSION_CODES.N] or
     * later, JavaScript state from an empty WebView is no longer persisted across navigations like
     * [.loadUrl]. For example, global variables and functions defined before calling
     * [.loadUrl] will not exist in the loaded page. Applications should use
     * [.addJavascriptInterface] instead to persist JavaScript objects across navigations.
     *
     * @param script the JavaScript to execute.
     * @param resultCallback A callback to be invoked when the script execution
     * completes with the result of the execution (if any).
     * May be null if no notification of the result is required.
     */
    fun evaluateJavascript(script: String?, resultCallback: CommonValueCallback<String?>?)

    /**
     * Gets the URL for the current page. This is not always the same as the URL
     * passed to WebViewClient.onPageStarted because although the load for
     * that URL has begun, the current page may not have changed.
     *
     * @return the URL for the current page
     */
    fun getUrl(): String

    /**
     * Gets the height of the HTML content.
     *
     * @return the height of the HTML content
     */
    fun getContentHeight(): Int

    /**
     * Gets the width of the HTML content.
     *
     * @return the width of the HTML content
     */
    fun getContentWidth(): Int

    /**
     * Saves the current view as a web archive.
     *
     * @param filename the filename where the archive should be placed
     */
    fun saveWebArchive(filename: String?)

    /**
     * 停止加载
     */
    fun stopLoading()

    /**
     * 重新加载
     */
    fun reload()

    /**
     * 当前WebView是否可回退
     * @return true:有历史记录，可回退到前一页; false:不可回退
     */
    fun canGoBack(): Boolean

    /**
     * 回退(如果可回退)
     */
    fun goBack()

    /**
     * 是否可向前浏览（一般是之前浏览过A、B、C，现在又浏览到B，调用该方法判断是否可以向前到C）
     * @return true:当前页面不是最后一次的记录；false:没有历史记录，当前网页可能是最后一个
     */
    fun canGoForward(): Boolean

    /**
     * 向前浏览
     */
    fun goForward()

    /**
     * 是否能回退或者前进到第几页
     * @param gobackOrForwardIndex 可回退/前进的第几页
     * @return true:可以；false:不可以
     */
    fun canGoBackOrForward(gobackOrForwardIndex: Int): Boolean

    /**
     * 回退/前进 到目标页
     * @param gobackOrForwardIndex 回退/前进的目标页
     */
    fun goBackOrForward(gobackOrForwardIndex: Int)

    @Deprecated("")
    fun clearView()

    /**
     * 设置渲染优先级策略
     * @param rendererRequestedPriority
     * @param waivedWhenNotVisible
     */
    fun setRendererPriorityPolicy(rendererRequestedPriority: Int, waivedWhenNotVisible: Boolean)

    /**
     * 获取当前的渲染优先级
     * @return
     */
    fun getRendererRequestedPriority(): Int

    /**
     * 该方法面向全局整个应用程序的webview，它会暂停所有webview的layout，parsing，JavaScript Timer
     * 当程序进入后台时，该方法的调用可以降低CPU功耗
     */
    fun pauseTimers()

    /**
     * 恢复pauseTimers时的所有操作。
     * 注：pauseTimers和resumeTimers 方法必须一起使用，否则再使用其它场景下的 WebView 会有问题
     */
    fun resumeTimers()

    /**
     * 尽力尝试暂停可以暂停的任何处理，如动画和地理位置。
     * 不会暂停JavaScript。 要全局暂停JavaScript，可使用pauseTimers
     */
    fun onPause()

    /**
     * 恢复onPause() 停掉的操作
     */
    fun onResume()

    /**
     * 清空网页访问留下的缓存数据。需要注意的时，由于缓存是全局的，所以只要是WebView用到的缓存都会被清空，即便其他地方也会使用到。
     * 该方法接受一个参数，从命名即可看出作用。若设为false，则只清空内存里的资源缓存，而不清空磁盘里的。
     * @param includeDiskFiles 是否包含磁盘内的缓存
     */
    fun clearCache(includeDiskFiles: Boolean)

    /**
     * 清除自动完成填充的表单数据。需要注意的是，该方法仅仅清除当前表单域自动完成填充的表单数据，并不会清除WebView存储到本地的数据
     */
    fun clearFormData()

    /**
     * 清除当前 WebView 访问的历史记录
     */
    fun clearHistory()

    /**
     * 清除ssl信息
     */
    fun clearSslPreferences()

    /**
     * 清除网页查找的高亮匹配字符
     */
    fun clearMatches()

    fun destroy()

    //    void release(boolean clearCache);
    /**
     * @param clearCache 是否清除缓存数据
     * @param destroy 是否 销毁当前 WebView
     */
    fun release(clearCache: Boolean, destroy: Boolean)

    /**
     * 设置webViewClientSelector,用来给各WebView设置WebViewClient以及WebChromeClient
     * @param webViewClientSelector WebViewClientHolder
     */
    fun setWebClientSelector(webViewClientSelector: WebViewClientAndChromeClientSelector?)

    /**
     * 注入 java 对象
     * @param object Java对象
     * @param jsInterfaceName 与前端约定的js调用的名称
     */
    fun addJavascriptInterface(jsBridgeObj: Any, jsInterfaceName: String?)


    /**
     * 删除 [jsInterfaceName] 对应的注入对象
     * @param jsInterfaceName
     */
    fun removeJavascriptInterface(@NonNull jsInterfaceName: String)

    /**
     * 由于要写成通用的获取WebSettings，所以获取一个AbsWebSettings，并由该AbsWebSettings来进行相应的设置
     * @param touchTheWebSettingAgain 是否重新获取一遍当前WebView内的WebSettings
     * @return WebSettingsWrapper
     */
    fun getWebSettings(touchTheWebSettingAgain: Boolean): WebSettingsWrapper?


    fun setLayerType(layerType: Int, paint: Paint?)

    /**
     * Specifies whether the horizontal scrollbar has overlay style.
     *
     * @param overlay true if horizontal scrollbar should have overlay style
     */
    @Deprecated("This method has no effect. 无效")
    fun setHorizontalScrollBarEnabled(overlay: Boolean)

    /**
     * Specifies whether the vertical scrollbar has overlay style.
     *
     * @param overlay true if vertical scrollbar should have overlay style
     */
    @Deprecated("")
    fun setVerticalScrollBarEnabled(overlay: Boolean)

//    void evaluateJavascript(String script, ValueCallback<String> resultCallback);


    /**
     * 获取 WebView 视图本身
     */
    fun getViewSelf(): View?

    /**
     * 获取真正的 Webview 控件
     * @return WebView
     */
    fun getRealWebview(): View?

    /**
     * Informs WebView of the network state. This is used to set
     * the JavaScript property window.navigator.isOnline and
     * generates the online/offline event as specified in HTML5, sec. 5.7.7
     *
     * @param networkUp a boolean indicating if network is available
     */
    fun setNetworkAvailable(networkUp: Boolean)

    /**
     * 利用WebView Api截自己范围的图
     * @return
     */
    fun captureSelf(): Bitmap?

    /**
     * 获取当前webview的类型或者名称
     * @return
     */
    fun getWebviewNameOrType(): String?

    /**
     *
     * @param width 要截取的宽
     * @param height 要截取的高
     * @param canvas 绘制到的目标画布
     */
    fun captureWithConfigs(width: Int, height: Int, canvas: Canvas?)

    fun pictureOfWebview(): Picture?

    fun getProgress(): Int
    fun isSrcWebView(): Boolean
}