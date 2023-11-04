package com.fee.xwebview.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.IntRange
import androidx.annotation.RequiresApi
import com.fee.TheXWebview.R
import com.fee.xwebview.L
import com.fee.xwebview.XWebViewHelper
import com.fee.xwebview.core.ACompatWebViewClient
import com.fee.xwebview.core.CommonValueCallback
import com.fee.xwebview.core.WebViewClientAndChromeClientSelector
import com.fee.xwebview.core.src.ASrcWebView

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/18<br>
 * Time: 15:40<br>
 * <P>DESC:
 * SDK 所提供的一个通用的 封装了 [WebView] 的 自定义View
 * </p>
 * ******************(^_^)***********************
 */
open class CommonWebView : LinearLayout {

    protected val TAG: String = "CommonWebView"

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        //执行到这里时，已经初始化了 实例 init{} 已经执行
        initViews(context, attrs)
    }

    init {
        L.d(TAG, "---> init()")
    }

    /**
     * 是否使能 WebView的回退浏览
     * def = false
     */
    var isEnableBackBrowse = false

    /**
     * 是否 需要让本类 在 构建 具体类型的 WebView 时 并且使用 [XWebViewHelper]中的默认对 WebView 进行相关的配置
     * def = true
     */
    var isNeedDefConfigWebView = true

    /**
     * 当前的 具体类型 WebView，本类会根据SDK初始化情况，优先使用 X5 WebView，可切换
     */
    protected var theWebView: IWebview? = null


    /**
     * WebView 初始加载的 H5的 url地址，该地址目的是为了在系统回调
     * 加载结束时来比较的(因为有些网页会有重定向或者加载多个url问题)
     */
    protected var theHostUrl: String? = ""

    /**
     * 当前WebView 与 JS交互 所配置的接口映射名称
     * JS中调用为 webview.[javaScriptInterfaceName].method
     * 注：本类默认持有一个，但实际上可以注册多个
     */
    private var javaScriptInterfaceName: String = ""

    protected var mLoadingHandle: ILoadingHandle? = null

    private var aSrcWebview: ASrcWebView? = null

//    private var aX5WebView: AX5WebView? = null

    private var aCompatWebViewClients: ACompatWebViewClient? = null
        get() {
            field = mDefCompatWebViewClient
            return field
        }

    private var webViewClientAndChromClientSelector: WebViewClientAndChromeClientSelector? = null

    private val typeX5 = WebViewClientAndChromeClientSelector.CLIENT_TYPE_X5

    private val typeSrcwebview = WebViewClientAndChromeClientSelector.CLIENT_TYPE_SRC

    /**
     * 当前 WebView 的类型
     * def: 0
     */
    private var curWebviewType = 0

    /**
     * 在初始化 XML中配置的 中所期望使用的 WebView 类型
     * def = 9
     */
    private var theInitExpectedWebViewType = 0;

    /**
     * 延迟初始化 WebView 的 Runnable 任务
     */
    private var delayInitWebViewTask: Runnable? = null

    /**
     * 外部的 对 WebView 相关事件的 进行监听的 Client
     */
    var mOutSideACompatWebViewClient: ACompatWebViewClient? = null
        set(value) {
            field = value
            aCompatWebViewClients?.mOutSideCompatWebViewClient = value
        }
    /**
     * 本类默认的 WebView 事件监听 Client
     */
    private val mDefCompatWebViewClient by lazy(LazyThreadSafetyMode.NONE) {
        object : ACompatWebViewClient() {
            @Deprecated(
                "Android原生标记为过时",
                replaceWith = ReplaceWith("shouldOverrideUrlLoading()", "CommonWebResourceRequest")
            )
            override fun shouldOverrideUrlLoading(url: String?): Boolean {
                theWebView?.loadUrl(url ?: "")
                L.d(TAG, "--> shouldOverrideUrlLoading() url = $url")
                return true
            }

            override fun onReceivedError(
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
                L.e(
                    TAG,
                    "--> onReceivedError() errorCode = $errorCode, description = $description,failingUrl = $failingUrl"
                )
                mOutSideACompatWebViewClient?.onReceivedError(errorCode, description, failingUrl)
            }

            override fun onPageStarted(url: String?, favicon: Bitmap?) {
                mLoadingHandle?.onLoadingProgress(true, 0)
                L.d(TAG, "--> onPageStarted() url = $url")
                mOutSideACompatWebViewClient?.onPageStarted(url,favicon)
            }

            override fun onPageFinished(url: String?) {
                mLoadingHandle?.onLoadingFinish(url)//todo 重定向问题??
                L.e(TAG, "--> onPageFinished() url = $url")
                mOutSideACompatWebViewClient?.onPageFinished(url)
            }

            override fun onProgressChanged(newProgress: Int) {
                mLoadingHandle?.onLoadingProgress(newProgress = newProgress)
                L.d(TAG, "--> onProgressChanged() newProgress = $newProgress")
                mOutSideACompatWebViewClient?.onProgressChanged(newProgress)
            }

            override fun onReceivedTitle(title: String?) {
                L.d(TAG, "--> onReceivedTitle() title = $title")
                mOutSideACompatWebViewClient?.onReceivedTitle(title)
            }

            /**
             * @param view eg.: android.widget.FrameLayout
             */
            override fun handleOnShowCustomView(view: View?): ViewGroup? {
                L.d(TAG, "--> handleOnShowCustomView() view = $view")
                val viewGroup = mOutSideACompatWebViewClient?.handleOnShowCustomView(view)
                return viewGroup?: super.handleOnShowCustomView(view)
            }

            override fun onHideCustomView() {
                L.d(TAG, "--> onHideCustomView()")
                mOutSideACompatWebViewClient?.onHideCustomView()
            }

            override fun onLoadResource(url: String?) {
                super.onLoadResource(url)
                L.d(TAG, "--> onLoadResource() url = $url")
            }
        } //end
    }

    private fun initViews(context: Context, attrs: AttributeSet?) {
        if (attrs == null) {
            //to do??
        }
        var assignedDelaySeconds = 0
        val typeArray: TypedArray? =
            context.obtainStyledAttributes(attrs, R.styleable.CommonWebView)
        typeArray?.let {
            theHostUrl = it.getString(R.styleable.CommonWebView_webViewUrl)
            val webviewTypeAttrIndex = R.styleable.CommonWebView_webViewType
            theInitExpectedWebViewType = it.getInt(
                webviewTypeAttrIndex,
                typeSrcwebview // xml 中未配置，会使用 原生 WebView
            )//10 value值
            //延迟初始化 WebView 的时间： 单位秒
            assignedDelaySeconds = it.getInt(R.styleable.CommonWebView_webViewDelayInitTime, 0)
        }
        typeArray?.recycle()
        orientation = VERTICAL

        if (assignedDelaySeconds > 0) { //延迟初始化
            delayInitWebViewTask = Runnable { switchWebViewType(theInitExpectedWebViewType) }
            postDelayed(delayInitWebViewTask, assignedDelaySeconds * 1000L)
        } else {
            L.d(
                TAG,
                "--> initViews(), theInitExpectedWebViewType = $theInitExpectedWebViewType"
            )
//            switchWebViewType(theInitExpectedWebViewType)
        }
    }


    fun loadUrl(webViewUrl: String?) {
        if (!webViewUrl.isNullOrBlank()) {
            this.theHostUrl = webViewUrl
            if (theWebView == null) {
                switchWebViewType(theInitExpectedWebViewType)
            }
            theWebView?.loadUrl(webViewUrl)
        }
    }

    /**
     * 在加载 相关的 [webViewUrl]前，可以先在[configBlock]进行 设置 WebView相关参数
     */
    fun loadUrl(webViewUrl: String?, configBlock: IWebview.() -> Unit) {
        L.d(TAG, "--> loadUrl() theWebView = $theWebView , webViewUrl = $webViewUrl")
        if (!webViewUrl.isNullOrBlank()) {
            this.theHostUrl = webViewUrl
            if (theWebView == null) {
                switchWebViewType(theInitExpectedWebViewType)
            }
            theWebView?.let {
                configBlock(it)
                it.loadUrl(webViewUrl)
            }
        }
    }

    fun reload() {
        theWebView?.reload()
    }

    /**
     * 在 [loadUrl] 加载网页之前，可以 配置 自定义的 loading 状态场景 视图
     */
    fun setLoadingHandle(theLoadingHandle: ILoadingHandle?) {
        this.mLoadingHandle = theLoadingHandle
        theLoadingHandle?.let {
            val provideShowLoadingView = it.provideShowLoadingView()
            provideShowLoadingView?.let { loadingView ->
                if (loadingView.parent == null) {
                    var layoutParams = loadingView.layoutParams
                    if (layoutParams == null) {
                        layoutParams =
                            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
                    }
                    addView(loadingView, 0, layoutParams)
                }
            }
        }
    }

    /**
     * 后退浏览，如果本类 [isEnableBackBrowse] = true,并且[theWebView]有历史记录后退
     * @return true:开始了回退浏览；false:未进行回退浏览
     */
    fun onBackBrowse(isNeedGoBack: Boolean = true): Boolean {
        if (isEnableBackBrowse && theWebView?.canGoBack() == true) {
            if (isNeedGoBack) {
                theWebView?.goBack()
            }
            return true
        }
        return false
    }


    /**
     * 给当前 WebView 注册 JS 与原生交互的 接口映射对象
     * 注：一般情况下，注册一个就行
     * @param interfaceMapObj [H5Bridge]
     * @param javaScriptInterfaceName 映射的名称，该名称为 H5中JS定义的调用原生的 名称
     */
    fun registerJavascriptInterface(interfaceMapObj: Any, javaScriptInterfaceName: String) {
        this.javaScriptInterfaceName = javaScriptInterfaceName
        theWebView?.addJavascriptInterface(interfaceMapObj, javaScriptInterfaceName)
    }

    fun removeJavascriptInterface(javaScriptInterfaceName: String) {
        theWebView?.removeJavascriptInterface(javaScriptInterfaceName)
    }

    /**
     * 获取当前 类型的WebView的名称
     */
    fun getWebviewTypeOrName(): String {
        return theWebView?.getWebviewNameOrType() ?: "no-webview"
    }

    /**
     * <html>
     * <head>
     * <meta http-equiv ="Content-Type" charset="UTF-8"/>
     * <title>测试Java与JS的交互</title>
     * <script type = "text/javascript">
     * var s = "我来自JS方法";
     * function javaToJsCallback(param){
     * document.getElementById("textshow").innerHTML=(param);
     * //Js调用android的方法
     * //window.android.JsToJavaInterface(s)
     * }
     * </script>
     * </head>
     * <body>
     * <h3>JS Method</h3>
     * <h3 id="textshow">这里将会显示来自JAVA的信息</h3>
     * </body>
     * </html>
     * 使用WebView的loadUrl()方式来调用网页内Js的方法(方法内可传入java层的参数(依Js的方法参数))
     * 注：1、该种方式，如果JS的方法有返回值，也会直接显示在WebView上，这样的话就不合适
     * 2、由于loadUrl()不能从Js返回数据，可以让Js回调android的方法回传参数，如：window.android.JsToJavaInterface(s)
     * 3、原生语法为webView.loadUrl("javascript:"+"js方法('参数'));但本基类把"javascript:"已经写了，调用方只传js内的方法和参数
     *
     * 注：该 API 为同步执行，性能方面有影响,并且需要保证在主线程内(其实安卓原生API里会校验是否和创建时的在同一线程)
     * 并且经使用X5时，可以不在同一线程: 233333
     * @param jsMethodInfo eg:JS内有方法： javaToJsCallback('param')
     */
    fun loadJsMethod(jsMethodInfo: String) {
        if (jsMethodInfo.isNotBlank()) {
            theWebView?.loadUrl("javascript:$jsMethodInfo")
        }
    }

    /**
     * 一次性执行多条 JS 内的方法
     */
    fun loadJsMethods(vararg jsMethodInfos: String) {
        jsMethodInfos.forEach {
            loadJsMethod(it)
        }
    }

    /**
     * 根据传入 Js内定义的方法名，以及传入该方法所需要的 所有 参数，让WebView执行
     * @param jsMethodName JS内定义的 方法、函数名称; .: javaToJsCallback; 注：不要带"()"
     * @param methodParams [jsMethodName]该方法所需要传入的方法参数
     */
    fun runJsMethod(jsMethodName: String, vararg methodParams: Any?) {
//        jsMethodName.isBlank()//这个方法 判断了 "" 及 "    "
        if (jsMethodName.isNotBlank()) {
            loadJsMethod(XWebViewHelper.assembleJsMethodInfos(jsMethodName, methodParams))
        }
    }

    /**
     * WebView的异步执行 JS方法,并且有异步返回结果值
     * 注：让WebView 执行JS方法最好使用该API
     */
    @RequiresApi(19)
    fun evaluateJsMethod(jsMethodInfo: String, callback: CommonValueCallback<String?>?) {
        if (jsMethodInfo.isNotBlank()) {
            theWebView?.evaluateJavascript(jsMethodInfo, callback)
        }
    }

    fun release(cleanWebviewCache: Boolean, isWillDestroy: Boolean) {
        // TO DO: 2020/11/18 others release
        if (delayInitWebViewTask != null) {
            removeCallbacks(delayInitWebViewTask)
            delayInitWebViewTask = null
        }
        webViewClientAndChromClientSelector?.free()
        theWebView?.let {
            it.removeJavascriptInterface(javaScriptInterfaceName)
            it.release(cleanWebviewCache, isWillDestroy)
        }
        theWebView = null
    }

//    /**
//     * 切换到 原生WebView
//     */
//    fun switchToSrcWebView() {
//        switchWebViewType(typeSrcwebview)
//    }
//
//    /**
//     * 切换到 X5 WebView
//     */
//    fun switchToX5WebView() {
//        switchWebViewType(typeX5)
//    }


    /**
     * 切换当前的 WebView
     */
    private fun switchWebViewType(
        @IntRange(
            from = WebViewClientAndChromeClientSelector.CLIENT_TYPE_X5.toLong(),
            to = WebViewClientAndChromeClientSelector.CLIENT_TYPE_SRC.toLong()
        ) targetWebViewType: Int
    ) {
        L.i(TAG, "--> switchWebViewType() targetWebViewType = $targetWebViewType")
        if (delayInitWebViewTask != null) {
            removeCallbacks(delayInitWebViewTask)
            delayInitWebViewTask = null
        }
        val isTargetUseX5 = targetWebViewType == typeX5
        var isTargetUseSrcWebView = targetWebViewType == typeSrcwebview
        var isNeedInitLoad = false
        if (isTargetUseX5 || isTargetUseSrcWebView) {
            if (theWebView == null) {// 还没有 初始化 WebView 的场景
                isNeedInitLoad = true
                if (isTargetUseX5) {
                    val isX5Ok = XWebViewHelper.isX5InitOk()
                    if (isX5Ok) {
//                        if (aX5WebView == null) {
//                            aX5WebView = AX5WebView(context)
//                        }
//                        theWebView = aX5WebView
                        curWebviewType = typeX5
                    } else {
                        isTargetUseSrcWebView = true
                    }
                }
                if (isTargetUseSrcWebView) {
                    if (aSrcWebview == null) {
                        aSrcWebview = ASrcWebView(context)
                    }
                    theWebView = aSrcWebview
                    curWebviewType = typeSrcwebview
                }
                if (webViewClientAndChromClientSelector == null) {
                    webViewClientAndChromClientSelector = WebViewClientAndChromeClientSelector(
                        curWebviewType,
                        aCompatWebViewClients,
                        aCompatWebViewClients
                    )
                    webViewClientAndChromClientSelector!!.isFullCompatAllClients = true
                }
                theWebView!!.setWebClientSelector(webViewClientAndChromClientSelector)
                addedTheWebView()
            }//theWebView == null end
            else {//theWebView 已经初始化过了
                val isDifCurType = curWebviewType != targetWebViewType
                if (isDifCurType) {
                    if (isTargetUseX5) {
//                        if (aX5WebView == null) {
//                            val isX5Ok = XWebViewHelper.isX5InitOk()
//                            if (!isX5Ok) {
//                                return // 切换失败,保留使用 原生 WebView
//                            }
//                            aX5WebView = AX5WebView(context)
//                        }
                        isNeedInitLoad = true
//                        theWebView = aX5WebView
                        theWebView?.setWebClientSelector(webViewClientAndChromClientSelector)
                        addedTheWebView()
                        curWebviewType = typeX5
                        aSrcWebview?.let {
                            removeView(it)
                        }
                    } else {//要切换到 原生WebView
                        if (aSrcWebview == null) {
                            aSrcWebview = ASrcWebView(context)
                        }
                        isNeedInitLoad = true
//                        theWebView = aX5WebView
                        theWebView?.setWebClientSelector(webViewClientAndChromClientSelector)
                        addedTheWebView()
                        curWebviewType = typeSrcwebview
//                        aX5WebView?.let {
//                            removeView(it)
//                        }
                    }
                }
            }
            if (isNeedInitLoad) {
                if (isNeedDefConfigWebView) {
                    if (theWebView != null) {
                        XWebViewHelper.commonConfigWebViewSettings(theWebView!!)
                    }
                }
                loadUrl(theHostUrl)
            }
        }
    }

    private fun addedTheWebView() {
        theWebView?.getViewSelf()?.let { webView: View ->
            if (webView.parent == null) {//当前 [webView]未添加进本容器 View
                layoutWebView(webView)
            }
        }
    }

    protected open fun layoutWebView(theWebView: View) {
        var mayExistSrcLayoutParam: ViewGroup.LayoutParams? = theWebView.layoutParams
        if (mayExistSrcLayoutParam == null) {
            mayExistSrcLayoutParam =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            theWebView.layoutParams = mayExistSrcLayoutParam
        }
        addView(theWebView)
    }
}