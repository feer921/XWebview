# 使用
    具体使用可以参考：[BrowseActivity],本框架内的注释全

# 依赖组件

```groovy
implementation 'io.github.feer921:XWebview:1.0'
```
# 如果不需要X5的浏览器内核，则可以依赖下面的版本,用法不用改变
```groovy
implementation 'io.github.feer921:XWebview:1.1-srcWebview'
```


## 初始化组件,使用框架内的　**XWebViewHelper**

```kotlin
/**
     * 启动 初始化 X5 WebView 内核的 独立进程 Service
     * 目的为 让 X5 内核的初始化 在独立进程中进行不影响 主进程
     */
    fun initWebViewService(
        context: Context,
        needBroadcastInitResult: Boolean = false,
        canInitX5WithoutWifi: Boolean = true
    ) {
        val intent = Intent(context, X5LoadService::class.java)
        intent.putExtra(INTENT_KEY_CAN_DOWNLOAD_X5_WITHOUT_WIFI, canInitX5WithoutWifi)
            .putExtra(INTENT_KEY_NEED_BROADCAST_INIT_RESULT, needBroadcastInitResult)
        context.startService(intent)
    }
```



## 使用封装好的控件

```xml

    <com.fee.xwebview.views.CommonWebView
        android:id="@+id/commonWebview"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintTop_toBottomOf="@id/llHeaderView" />

```

## 加载网页

```kotlin
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
```

## 配置Webview

可以使用框架内的　**XWebViewHelper**

```kotlin
/**
     * 较通用的配置 WebView
     * @param theWebView 当前的具体的 类型 的 WebView 实例
     */
    fun commonConfigWebViewSettings(theWebView: IWebview) {
        theWebView.getWebSettings(false)?.apply {
            setAppCacheEnabled(true)
            allowContentAccess = true
            // Using setJavaScriptEnabled can introduce XSS vulnerabilities into your application,
            // review carefully
            javaScriptEnabled = true
            setSupportZoom(true)
            useWideViewPort = true//设置此属性，可任意比例缩放 设置webview推荐使用的窗口
            domStorageEnabled = true
            loadWithOverviewMode = true
            builtInZoomControls = true
            javaScriptCanOpenWindowsAutomatically = true
            allowFileAccess = true
            setAllowFileAccessFromFileURLs(true)
            setSupportMultipleWindows(true)
            setPluginsEnabled(true)
            savePassword = true
            textZoom = 100
            mediaPlaybackRequiresUserGesture = false
            if (Api.isApiCompatible(21)) {
                mixedContentMode = 0//允许加载H5网页时，https/http混合使用
            }
            displayZoomControls = false//一般不需要 网页上显示 缩放按钮
        }
        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true)//在 debug输出包时，把 Chrome 浏览器可调试功能开启
        }
    }
```



# 图示

  . 支持视频播放的 全屏播放
  ![image](https://user-images.githubusercontent.com/6622321/187073894-10526c46-8cf0-4279-9833-208187a2dd60.png)

  . 浏览网页
  ![image](https://user-images.githubusercontent.com/6622321/187073903-c5cdf646-ce0c-49fb-8a42-c4abba3e8c63.png)

