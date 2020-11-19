package com.fee.xwebview.core.src

import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Picture
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.webkit.WebView
import com.fee.xwebview.L
import com.fee.xwebview.core.CommonValueCallback
import com.fee.xwebview.core.WebSettingsWrapper
import com.fee.xwebview.core.WebViewClientAndChromeClientSelector
import com.fee.xwebview.views.IWebview

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 20:31<br>
 * <P>DESC:
 * 原生 WebView
 * </p>
 * ******************(^_^)***********************
 */
class ASrcWebView(context: Context, attrs: AttributeSet?) : WebView(
    if (Build.VERSION.SDK_INT < 23) {
        context.createConfigurationContext(Configuration())//安卓5.1系统上原生WebView 初始化异常的问题
    } else context, attrs //可以解决原生webview无法弹出软键盘的问题
),IWebview {

    private val TAG = "AsrcWebView"
    constructor(context: Context) : this(context, null)

    /**
     * 异步执行JS脚本
     * Asynchronously evaluates JavaScript in the context of the currently displayed page.
     * If non-null, |resultCallback| will be invoked with any result returned from that
     * execution. This method must be called on the UI thread and the callback will
     * be made on the UI thread.
     *
     *
     * Compatibility note. Applications targeting [Build.VERSION_CODES.N] or
     * later, JavaScript state from an empty WebView is no longer persisted across navigations like
     * [.loadUrl]. For example, global variables and functions defined before calling
     * [.loadUrl] will not exist in the loaded page. Applications should use
     * [.addJavascriptInterface] instead to persist JavaScript objects across navigations.
     *
     * @param script         the JavaScript to execute.
     * @param resultCallback A callback to be invoked when the script execution
     * completes with the result of the execution (if any).
     */
    override fun evaluateJavascript(script: String?, resultCallback: CommonValueCallback<String?>?) {
        super.evaluateJavascript(script, resultCallback)
    }

    /**
     * Gets the width of the HTML content.
     *
     * @return the width of the HTML content
     */
    @Deprecated("hide by super ")
    override fun getContentWidth(): Int {
        //由于原生的WebView，该方法被@hide了
        return 0
    }

    //    @Override
    //    public void release(boolean clearCache) {
    //        clearCache(clearCache);
    //        ViewParent viewParent = getParent();
    //        if (viewParent != null) {
    //            ViewGroup parentView = (ViewGroup) viewParent;
    //            parentView.removeView(this);
    //        }
    //
    //        try {
    ////            setWebChromeClient(null);
    ////            setWebViewClient(null);
    //            stopLoading();
    //            getSettings().setJavaScriptEnabled(false);
    //            clearHistory();
    //            loadUrl("about:blank");
    //            removeAllViews();
    //            Log.e(TAG, "--->destroy()-----------");
    //            super.destroy();
    //        } catch (Exception ignore) {
    //            ignore.printStackTrace();
    //        }
    //    }
    override fun release(clearCache: Boolean, destroy: Boolean) {
        clearCache(clearCache)
        val viewParent: ViewParent? = parent
        if (viewParent is ViewGroup) {
            viewParent.removeView(this)
        }

        try {
//            setWebChromeClient(null);
//            setWebViewClient(null);
            stopLoading()
            settings?.javaScriptEnabled = false
            clearHistory()
            loadUrl("about:blank")
            removeAllViews()
            L.e(TAG, "--> release()----------- destroy = $destroy")
            if (destroy) {
                super.destroy()
            }
        } catch (ignore: Exception) {
            Log.e(TAG, "--> release() occur: $ignore")
        }
    }

    /**
     * 设置webViewClientSelector,用来给各WebView设置WebViewClient以及WebChromeClient
     *
     * @param webViewClientSelector WebViewClientHolder
     */
    override fun setWebClientSelector(webViewClientSelector: WebViewClientAndChromeClientSelector?) {
        if (webViewClientSelector != null) {
            //changed by fee :Android WebView页面中点击H5页面没有响应问题。
            //之前还一直以为是WebSettings设置不正确导致的，后面查了一下setWebChromeClient方法必须放在setWebViewClient的前面。
            webChromeClient = webViewClientSelector.getSrcWebChromeClient()
            webViewClient = webViewClientSelector.getSrcWebViewClient()
        }
    }


    private var theWebSettingsWrapper: WebSettingsWrapper? = null

    /**
     * 由于要写成通用的获取WebSettings，所以获取一个AbsWebSettings，并由该AbsWebSettings来进行相应的设置
     *
     * @param touchTheWebSettingAgain 是否重新获取一遍当前WebView内的WebSettings
     * @return WebSettingsWrapper
     */
    override fun getWebSettings(touchTheWebSettingAgain: Boolean): WebSettingsWrapper? {
        if (theWebSettingsWrapper == null) {
            theWebSettingsWrapper = WebSettingsWrapper()
            theWebSettingsWrapper?.setWebSettingsDelegate(settings)
        }
        if (touchTheWebSettingAgain) {
            theWebSettingsWrapper?.setWebSettingsDelegate(settings)
        }
        return theWebSettingsWrapper
    }

    override fun getViewSelf(): View {
        return this
    }

    /**
     * 获取真正的Webview控件
     *
     * @return
     */
    override fun getRealWebview(): View {
        return this
    }

    override fun captureSelf(): Bitmap? {
        val snapShot = capturePicture()
        val bmp = Bitmap.createBitmap(
            snapShot.width,
            snapShot.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bmp)
        snapShot.draw(canvas)
        return bmp
    }

    override fun getWebviewNameOrType(): String? {
        return "Src-Webview"
    }

    /**
     * @param width        要截取的宽
     * @param height       要截取的高
     * @param canvas       绘制到的目标画布
     */
    override fun captureWithConfigs(width: Int, height: Int, canvas: Canvas?) {
        val snapShot = capturePicture()
        snapShot.beginRecording(width, height)
        snapShot.draw(canvas)
    }

    override fun pictureOfWebview(): Picture? {
        return capturePicture()
    }

    override fun isSrcWebView(): Boolean {
        return true
    }


}