package com.fee.xwebview.core.x5

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Picture
import android.os.Build
import android.os.Process
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import com.fee.xwebview.L
import com.fee.xwebview.core.CommonValueCallback
import com.fee.xwebview.core.WebSettingsWrapper
import com.fee.xwebview.core.WebViewClientAndChromeClientSelector
import com.fee.xwebview.views.IWebview
import com.tencent.smtt.sdk.BuildConfig
import com.tencent.smtt.sdk.QbSdk
import com.tencent.smtt.sdk.WebView

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 21:02<br>
 * <P>DESC:
 * 一个 X5 WebView
 * </p>
 * ******************(^_^)***********************
 */
class AX5WebView(context: Context, attributeSet: AttributeSet?, defStyle: Int) :
    WebView(context, attributeSet, defStyle) ,IWebview{

    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)

    constructor(context: Context) : this(context, null)

    private val TAG = "AX5Webview"

    private var theWebSettingsWrapper: WebSettingsWrapper? = null

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
    override fun evaluateJavascript(
        script: String?,
        resultCallback: CommonValueCallback<String?>?
    ) {
        super.evaluateJavascript(script, resultCallback)
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
            L.e(TAG, "--->destroy()----------- destroy = $destroy")
            if (destroy) {
                super.destroy()
            }
        } catch (ignore: Exception) {
            L.e(TAG, "-->release() occur: $ignore")
        }
    }

    /**
     * 设置webViewClientSelector,用来给各WebView设置WebViewClient以及WebChromeClient
     *
     * @param webViewClientSelector WebViewClientHolder
     */
    override fun setWebClientSelector(webViewClientSelector: WebViewClientAndChromeClientSelector?) {
        if (webViewClientSelector != null) {
            //changed by fee: Android WebView页面中点击H5页面没有响应问题。
            //之前还一直以为是WebSettings设置不正确导致的，后面查了一下setWebChromeClient方法必须放在setWebViewClient的前面。
            webChromeClient = webViewClientSelector.getX5WebChromeClient()
            webViewClient = webViewClientSelector.getX5WebViewClient()
        }
    }

    /**
     * 由于要写成通用的获取WebSettings，所以获取一个AbsWebSettings，并由该AbsWebSettings来进行相应的设置
     * @param getNewAgain 是否重新获取一遍
     * @return WebSettingsWrapper
     */
    override fun getWebSettings(touchTheWebSettingAgain: Boolean): WebSettingsWrapper? {
//        WebView.setWebContentsDebuggingEnabled(true);
        if (theWebSettingsWrapper == null) {
            theWebSettingsWrapper = WebSettingsWrapper()
            theWebSettingsWrapper?.setX5WebSettingsDelegate(settings)
        }
        if (touchTheWebSettingAgain) {
            theWebSettingsWrapper?.setX5WebSettingsDelegate(settings)
        }
        return theWebSettingsWrapper
    }

    private var mPaintX5Info: Paint? = null

    private fun initPaint() {
        if (mPaintX5Info == null) {
            mPaintX5Info = Paint()
            mPaintX5Info?.color = 0x7fff0000
            mPaintX5Info?.textSize = 24f
            mPaintX5Info?.isAntiAlias = true
        }
    }

    override fun drawChild(canvas: Canvas, child: View?, drawingTime: Long): Boolean {
        if (!BuildConfig.DEBUG) {
            return super.drawChild(canvas, child, drawingTime)
        }
        val ret = super.drawChild(canvas, child, drawingTime)
        canvas.save()
        initPaint()
        if (x5WebViewExtension != null) {
            canvas.drawText(
                this.context.packageName + "-pid:"
                        + Process.myPid(), 10f, 50f, (mPaintX5Info)!!
            )
            //			getX5WebViewExtension();//非空为加载X5内核成功
            canvas.drawText(
                "X5  Core:" + QbSdk.getTbsVersion(this.context), 10f, 100f, (mPaintX5Info)!!
            )
        } else {
            canvas.drawText(
                (this.context.packageName + "-pid:"
                        + Process.myPid()), 10f, 50f, (mPaintX5Info)!!
            )
            canvas.drawText("Sys Core", 10f, 100f, (mPaintX5Info)!!)
        }
        canvas.drawText(Build.MANUFACTURER, 10f, 150f, (mPaintX5Info)!!)
        canvas.drawText(Build.MODEL, 10f, 200f, (mPaintX5Info)!!)
        canvas.restore()
        return ret
    }

    override fun getViewSelf(): View {
        return this
    }

    /**
     * 获取真正的Webview控件
     *
     * @return
     */
    override fun getRealWebview(): View{
        return view
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
        return "X5-Webview"
    }

    /**
     * @param width  要截取的宽
     * @param height 要截取的高
     * @param canvas 绘制到的目标画布
     */
    override fun captureWithConfigs(width: Int, height: Int, canvas: Canvas?) {
        val snapShot = capturePicture()
        snapShot.beginRecording(width, height)
        snapShot.draw(canvas)
    }

    override fun pictureOfWebview(): Picture? {
        return capturePicture()
    }

}