package com.fee.xwebview.core;


import android.annotation.TargetApi;
import android.webkit.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 18:20<br>
 * <P>DESC:
 * 原生  WebSettings 与 X5 WebSettings的包裹类
 * 并且写成联级调用，方便设置
 * </p>
 * ******************(^_^)***********************
 */
public class WebSettingsWrapper {

    protected WebSettings x5WebSettings;
    protected android.webkit.WebSettings srcWebSettings;


    public void setWebSettingsDelegate(android.webkit.WebSettings srcWebSettings) {
        this.srcWebSettings = srcWebSettings;
    }

    public void setX5WebSettingsDelegate(WebSettings x5WebSettings) {
        this.x5WebSettings = x5WebSettings;
    }

    /**
     * Sets whether the WebView should support zooming using its on-screen zoom
     * controls and gestures. The particular zoom mechanisms that should be used
     * can be set with setBuiltInZoomControls}. This setting does not
     * affect zooming performed using the WebView#zoomIn()} and
     * WebView#zoomOut()} methods. The default is true.
     *
     * @param support whether the WebView should support zoom
     */
    public WebSettingsWrapper setSupportZoom(boolean support) {
        if (srcWebSettings != null) {
            srcWebSettings.setSupportZoom(support);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setSupportZoom(support);
        }
        return this;
    }

    /**
     * Gets the current behavior of the WebView with regard to loading insecure content from a
     * secure origin.
     *
     * @return The current setting, one of { #MIXED_CONTENT_NEVER_ALLOW},
     * {#MIXED_CONTENT_ALWAYS_ALLOW} or { #MIXED_CONTENT_COMPATIBILITY_MODE}.
     */
    @TargetApi(21)
    public synchronized int getMixedContentMode() {
        if (srcWebSettings != null) {
            return srcWebSettings.getMixedContentMode();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getMixedContentMode();
        }
        return -1;
    }

    /**
     * Gets whether the WebView supports zoom.
     *
     * @return true if the WebView supports zoom
     * @see #setSupportZoom
     */
    public boolean supportZoom() {
        if (srcWebSettings != null) {
            return srcWebSettings.supportZoom();
        }
        if (x5WebSettings != null) {
            x5WebSettings.supportZoom();
        }
        return false;
    }

    /**
     * Sets whether the WebView should use its built-in zoom mechanisms. The
     * built-in zoom mechanisms comprise on-screen zoom controls, which are
     * displayed over the WebView's content, and the use of a pinch gesture to
     * control zooming. Whether or not these on-screen controls are displayed
     * can be set with {@link #setDisplayZoomControls}. The default is false.
     * <p>
     * The built-in mechanisms are the only currently supported zoom
     * mechanisms, so it is recommended that this setting is always enabled.
     *
     * @param enabled whether the WebView should use its built-in zoom mechanisms
     */
    public WebSettingsWrapper setBuiltInZoomControls(boolean enabled) {
        if (srcWebSettings != null) {
            srcWebSettings.setDisplayZoomControls(enabled);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setBuiltInZoomControls(enabled);
        }
        return this;
    }

    /**
     * Gets whether the zoom mechanisms built into WebView are being used.
     *
     * @return true if the zoom mechanisms built into WebView are being used
     * @see #setBuiltInZoomControls
     */
    public boolean getBuiltInZoomControls() {
        if (srcWebSettings != null) {
            return srcWebSettings.getBuiltInZoomControls();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getBuiltInZoomControls();
        }
        return false;
    }

    /**
     * Sets whether the WebView should display on-screen zoom controls when
     * using the built-in zoom mechanisms. See {@link #setBuiltInZoomControls}.
     * The default is true.
     *
     * @param enabled whether the WebView should display on-screen zoom controls
     */
    public WebSettingsWrapper setDisplayZoomControls(boolean enabled) {
        if (srcWebSettings != null) {
            srcWebSettings.setDisplayZoomControls(enabled);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setDisplayZoomControls(enabled);
        }
        return this;
    }

    /**
     * Gets whether the WebView displays on-screen zoom controls when using
     * the built-in zoom mechanisms.
     *
     * @return true if the WebView displays on-screen zoom controls when using
     * the built-in zoom mechanisms
     * @see #setDisplayZoomControls
     */
    public boolean getDisplayZoomControls() {
        if (srcWebSettings != null) {
            return srcWebSettings.getDisplayZoomControls();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getDisplayZoomControls();
        }
        return false;
    }

    /**
     * Enables or disables file access within WebView. File access is enabled by
     * default.  Note that this enables or disables file system access only.
     * Assets and resources are still accessible using file:///android_asset and
     * file:///android_res.
     * <p>
     * 是否允许加载本地 html 文件
     * def: false
     */
    public WebSettingsWrapper setAllowFileAccess(boolean allow) {
        if (srcWebSettings != null) {
            srcWebSettings.setAllowFileAccess(allow);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setAllowFileAccess(allow);
        }
        return this;
    }

    /**
     * Gets whether this WebView supports file access.
     *
     * @see #setAllowFileAccess
     */
    public boolean getAllowFileAccess() {
        if (srcWebSettings != null) {
            return srcWebSettings.getAllowFileAccess();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getAllowFileAccess();
        }
        return false;
    }

    /**
     * Enables or disables content URL access within WebView.  Content URL
     * access allows WebView to load content from a content provider installed
     * in the system. The default is enabled.
     * def: true
     */
    public WebSettingsWrapper setAllowContentAccess(boolean allow) {
        if (srcWebSettings != null) {
            srcWebSettings.setAllowContentAccess(allow);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setAllowContentAccess(allow);
        }
        return this;
    }

    /**
     * Gets whether this WebView supports content URL access.
     *
     * @see #setAllowContentAccess
     */
    public boolean getAllowContentAccess() {
        if (srcWebSettings != null) {
            return srcWebSettings.getAllowContentAccess();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getAllowContentAccess();
        }
        return false;
    }

    /**
     * Configures the WebView's behavior when a secure origin attempts to load a resource from an
     * insecure origin.
     * <p>
     * By default, apps that target {@link android.os.Build.VERSION_CODES#KITKAT} or below default
     * to { #MIXED_CONTENT_ALWAYS_ALLOW}. Apps targeting
     * {@link android.os.Build.VERSION_CODES#LOLLIPOP} default to {#MIXED_CONTENT_NEVER_ALLOW}.
     * <p>
     * The preferred and most secure mode of operation for the WebView is
     * {#MIXED_CONTENT_NEVER_ALLOW} and use of { #MIXED_CONTENT_ALWAYS_ALLOW} is
     * strongly discouraged.
     *
     * @param mode The mixed content mode to use. One of { #MIXED_CONTENT_NEVER_ALLOW},
     *             { #MIXED_CONTENT_ALWAYS_ALLOW} or { #MIXED_CONTENT_COMPATIBILITY_MODE}.
     */
    @TargetApi(21)
    public WebSettingsWrapper setMixedContentMode(int mode) {
        if (srcWebSettings != null) {
            srcWebSettings.setMixedContentMode(mode);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setMixedContentMode(mode);
        }
        return this;
    }

    /**
     * Sets whether the WebView loads pages in overview mode, that is,
     * zooms out the content to fit on screen by width. This setting is
     * taken into account when the content width is greater than the width
     * of the WebView control, for example, when {#getUseWideViewPort}
     * is enabled. The default is false.
     */
    public WebSettingsWrapper setLoadWithOverviewMode(boolean overview) {
        if (srcWebSettings != null) {
            srcWebSettings.setLoadWithOverviewMode(overview);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setLoadWithOverviewMode(overview);
        }
        return this;
    }

    /**
     * Gets whether this WebView loads pages in overview mode.
     *
     * @return whether this WebView loads pages in overview mode
     * @see #setLoadWithOverviewMode
     */
    public boolean getLoadWithOverviewMode() {
        if (srcWebSettings != null) {
            return srcWebSettings.getLoadWithOverviewMode();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getLoadWithOverviewMode();
        }
        return false;
    }

    /**
     * Sets whether the WebView will enable smooth transition while panning or
     * zooming or while the window hosting the WebView does not have focus.
     * If it is true, WebView will choose a solution to maximize the performance.
     * e.g. the WebView's content may not be updated during the transition.
     * If it is false, WebView will keep its fidelity. The default value is false.
     *
     * @deprecated This method is now obsolete, and will become a no-op in future.
     */
    @Deprecated
    public WebSettingsWrapper setEnableSmoothTransition(boolean enable) {
        if (srcWebSettings != null) {
            srcWebSettings.setEnableSmoothTransition(enable);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setEnableSmoothTransition(enable);
        }
        return this;
    }

    /**
     * Gets whether the WebView enables smooth transition while panning or
     * zooming.
     *
     * @see #setEnableSmoothTransition
     * @deprecated This method is now obsolete, and will become a no-op in future.
     */
    @Deprecated
    public boolean enableSmoothTransition() {
        if (srcWebSettings != null) {
            return srcWebSettings.enableSmoothTransition();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.enableSmoothTransition();
        }
        return false;
    }

    /**
     * Sets whether the WebView should save form data. In Android O, the
     * platform has implemented a fully functional Autofill feature to store
     * form data. Therefore, the Webview form data save feature is disabled.
     * <p>
     * Note that the feature will continue to be supported on older versions of
     * Android as before.
     * <p>
     * This function does not have any effect.
     */
    public WebSettingsWrapper setSaveFormData(boolean save) {
        if (srcWebSettings != null) {
            srcWebSettings.setSaveFormData(save);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setSaveFormData(save);
        }
        return this;
    }

    /**
     * Gets whether the WebView saves form data.
     *
     * @return whether the WebView saves form data
     * @see #setSaveFormData
     */
    public boolean getSaveFormData() {
        if (srcWebSettings != null) {
            return srcWebSettings.getSaveFormData();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getSaveFormData();
        }
        return false;
    }

    /**
     * Sets whether the WebView should save passwords. The default is true.
     * def: true
     *
     * @deprecated Saving passwords in WebView will not be supported in future versions.
     * 建议：关闭密码保存提醒功能，防止明文密码存在本地被盗用
     */
    public WebSettingsWrapper setSavePassword(boolean save) {
        if (srcWebSettings != null) {
            srcWebSettings.setSavePassword(save);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setSavePassword(save);
        }
        return this;
    }

    /**
     * Gets whether the WebView saves passwords.
     *
     * @return whether the WebView saves passwords
     * @see #setSavePassword
     * @deprecated Saving passwords in WebView will not be supported in future versions.
     */
    public boolean getSavePassword() {
        if (srcWebSettings != null) {
            return srcWebSettings.getSavePassword();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getSavePassword();
        }
        return false;
    }

    /**
     * Sets the text zoom of the page in percent. The default is 100
     *
     * @param textZoom the text zoom in percent
     * @return
     */
    public synchronized WebSettingsWrapper setTextZoom(int textZoom) {
        if (srcWebSettings != null) {
            srcWebSettings.setTextZoom(textZoom);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setTextZoom(textZoom);
        }
        return this;
    }

    /**
     * Gets the text zoom of the page in percent.
     *
     * @return the text zoom of the page in percent
     * @see #setTextZoom
     */
    public synchronized int getTextZoom() {
        if (srcWebSettings != null) {
            return srcWebSettings.getTextZoom();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getTextZoom();
        }
        return 100;
    }

    /**
     * Enables using light touches to make a selection and activate mouseovers.
     *
     * @deprecated From {@link android.os.Build.VERSION_CODES#JELLY_BEAN} this
     * setting is obsolete and has no effect.
     */
    public WebSettingsWrapper setLightTouchEnabled(boolean enabled) {
        if (srcWebSettings != null) {
            srcWebSettings.setLightTouchEnabled(enabled);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setLightTouchEnabled(enabled);
        }
        return this;
    }

    /**
     * Gets whether light touches are enabled.
     *
     * @see #setLightTouchEnabled
     * @deprecated This setting is obsolete.
     */
    public boolean getLightTouchEnabled() {
        if (srcWebSettings != null) {
            return srcWebSettings.getLightTouchEnabled();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getLightTouchEnabled();
        }
        return false;
    }

    /**
     * Sets the WebView's user-agent string. If the string is null or empty,
     * the system default value will be used.
     * <p>
     * Note that starting from {@link android.os.Build.VERSION_CODES#KITKAT} Android
     * version, changing the user-agent while loading a web page causes WebView
     * to initiate loading once again.
     *
     * @param ua new user-agent string
     */
    public WebSettingsWrapper setUserAgent(String ua) {
        if (srcWebSettings != null) {
            srcWebSettings.setUserAgentString(ua);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setUserAgent(ua);
        }
        return this;
    }

    /**
     * Gets the WebView's user-agent string.
     *
     * @return the WebView's user-agent string
     * @see #setUserAgent(String)
     */
    public String getUserAgentString() {
        if (srcWebSettings != null) {
            return srcWebSettings.getUserAgentString();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getUserAgentString();
        }
        return "";
    }

    /**
     * 设置webview推荐使用的窗口
     * Sets whether the WebView should enable support for the &quot;viewport&quot;
     * HTML meta tag or should use a wide viewport.
     * When the value of the setting is false, the layout width is always set to the
     * width of the WebView control in device-independent (CSS) pixels.
     * When the value is true and the page contains the viewport meta tag, the value
     * of the width specified in the tag is used. If the page does not contain the tag or
     * does not provide a width, then a wide viewport will be used.
     *
     * @param use whether to enable support for the viewport meta tag
     */
    public WebSettingsWrapper setUseWideViewPort(boolean use) {
        if (srcWebSettings != null) {
            srcWebSettings.setUseWideViewPort(use);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setUseWideViewPort(use);
        }
        return this;
    }

    /**
     * Gets whether the WebView supports the &quot;viewport&quot;
     * HTML meta tag or will use a wide viewport.
     *
     * @return true if the WebView supports the viewport meta tag
     * @see #setUseWideViewPort
     */
    public boolean getUseWideViewPort() {
        if (srcWebSettings != null) {
            return srcWebSettings.getUseWideViewPort();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getUseWideViewPort();
        }
        return false;
    }

    /**
     * Sets whether the WebView whether supports multiple windows. If set to
     * true, {@link WebChromeClient#onCreateWindow} must be implemented by the
     * host application. The default is false.
     *
     * @param support whether to suport multiple windows
     */
    public WebSettingsWrapper setSupportMultipleWindows(boolean support) {
        if (srcWebSettings != null) {
            srcWebSettings.setSupportMultipleWindows(support);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setSupportMultipleWindows(support);
        }
        return this;
    }

    /**
     * Gets whether the WebView supports multiple windows.
     *
     * @return true if the WebView supports multiple windows
     * @see #setSupportMultipleWindows
     */
    public synchronized boolean supportMultipleWindows() {
        if (srcWebSettings != null) {
            return srcWebSettings.supportMultipleWindows();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.supportMultipleWindows();
        }
        return false;
    }

    /**
     * 设置原生WebView的布局算法
     *
     * @param l
     * @return
     */
    public WebSettingsWrapper setLayoutAlgorithm(android.webkit.WebSettings.LayoutAlgorithm l) {
        if (srcWebSettings != null) {
            srcWebSettings.setLayoutAlgorithm(l);
        }

        return this;
    }

    /**
     * 给X5设置布局算法
     * algorithm: n. [计][数] 算法，运算法则
     *
     * @param var1
     * @return
     */
    public WebSettingsWrapper setX5LayoutAlgorithm(WebSettings.LayoutAlgorithm var1) {
        if (x5WebSettings != null) {
            x5WebSettings.setLayoutAlgorithm(var1);
        }
        return this;
    }

    public WebSettingsWrapper setStandardFontFamily(String font) {
        if (srcWebSettings != null) {
            srcWebSettings.setStandardFontFamily(font);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setStandardFontFamily(font);
        }
        return this;
    }

    /**
     * Gets the standard font family name.
     *
     * @return the standard font family name as a string
     * @see #setStandardFontFamily
     */
    public synchronized String getStandardFontFamily() {
        if (srcWebSettings != null) {
            return srcWebSettings.getStandardFontFamily();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getStandardFontFamily();
        }
        return "";
    }

    public WebSettingsWrapper setFixedFontFamily(String font) {
        if (srcWebSettings != null) {
            srcWebSettings.setFixedFontFamily(font);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setFixedFontFamily(font);
        }
        return this;
    }

    /**
     * Gets the standard font family name.
     *
     * @return the standard font family name as a string
     * @see #setStandardFontFamily
     */
    public synchronized String getFixedFontFamily() {
        if (srcWebSettings != null) {
            return srcWebSettings.getFixedFontFamily();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getFixedFontFamily();
        }
        return "";
    }

    /**
     * Sets the minimum font size. The default is 8.
     *
     * @param size a non-negative integer between 1 and 72. Any number outside
     *             the specified range will be pinned.
     */
    public WebSettingsWrapper setMinimumFontSize(int size) {
        if (srcWebSettings != null) {
            srcWebSettings.setMinimumFontSize(size);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setMinimumFontSize(size);
        }
        return this;
    }

    /**
     * Gets the minimum font size.
     *
     * @return a non-negative integer between 1 and 72
     * @see #setMinimumFontSize
     */
    public synchronized int getMinimumFontSize() {
        if (srcWebSettings != null) {
            return srcWebSettings.getMinimumFontSize();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getMinimumFontSize();
        }
        return 0;
    }

    /**
     * Sets whether the WebView should not load image resources from the
     * network (resources accessed via http and https URI schemes).  Note
     * that this method has no effect unless
     * {#getLoadsImagesAutomatically} returns true. Also note that
     * disabling all network loads using { #setBlockNetworkLoads}
     * will also prevent network images from loading, even if this flag is set
     * to false. When the value of this setting is changed from true to false,
     * network images resources referenced by content currently displayed by
     * the WebView are fetched automatically. The default is false.
     *
     * @param flag whether the WebView should not load image resources from the
     *             network
     * @see #setBlockNetworkLoads
     */
    public WebSettingsWrapper setBlockNetworkImage(boolean flag) {
        if (srcWebSettings != null) {
            srcWebSettings.setBlockNetworkImage(flag);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setBlockNetworkImage(flag);
        }
        return this;
    }

    /**
     * Gets whether the WebView does not load image resources from the network.
     *
     * @return true if the WebView does not load image resources from the network
     * @see #setBlockNetworkImage
     */
    public boolean getBlockNetworkImage() {
        if (srcWebSettings != null) {
            return srcWebSettings.getBlockNetworkImage();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getBlockNetworkImage();
        }
        return false;
    }

    /**
     * Sets whether the WebView should not load resources from the network.
     * Use {@link #setBlockNetworkImage} to only avoid loading
     * image resources. Note that if the value of this setting is
     * changed from true to false, network resources referenced by content
     * currently displayed by the WebView are not fetched until
     * {@link android.webkit.WebView#reload} is called.
     * If the application does not have the
     * {@link android.Manifest.permission#INTERNET} permission, attempts to set
     * a value of false will cause a {@link SecurityException}
     * to be thrown. The default value is false if the application has the
     * {@link android.Manifest.permission#INTERNET} permission, otherwise it is
     * true.
     *
     * @param flag true means block network loads by the WebView
     * @see android.webkit.WebView#reload
     */
    public synchronized WebSettingsWrapper setBlockNetworkLoads(boolean flag) {
        if (srcWebSettings != null) {
            srcWebSettings.setBlockNetworkLoads(flag);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setBlockNetworkLoads(flag);
        }
        return this;
    }

    /**
     * Gets whether the WebView does not load any resources from the network.
     *
     * @return true if the WebView does not load any resources from the network
     * @see #setBlockNetworkLoads
     */
    public boolean getBlockNetworkLoads() {
        if (srcWebSettings != null) {
            return srcWebSettings.getBlockNetworkLoads();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getBlockNetworkLoads();
        }
        return false;
    }

    /**
     * Tells the WebView to enable JavaScript execution.
     * <b>The default is false.</b>
     * 注：设置是否要支持 Js 使用
     *
     * @param flag true if the WebView should execute JavaScript
     */
    public WebSettingsWrapper setJavaScriptEnabled(boolean flag) {
        if (srcWebSettings != null) {
            srcWebSettings.setJavaScriptEnabled(flag);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setJavaScriptEnabled(flag);
        }
        return this;
    }

    /**
     * 如果此设置是允许，则 setAllowFileAccessFromFileURLs 不起做用
     * Sets whether JavaScript running in the context of a file scheme URL
     * should be allowed to access content from any origin. This includes
     * access to content from other file scheme URLs. See
     * {#setAllowFileAccessFromFileURLs}. To enable the most restrictive,
     * and therefore secure policy, this setting should be disabled.
     * Note that this setting affects only JavaScript access to file scheme
     * resources. Other access to such resources, for example, from image HTML
     * elements, is unaffected. To prevent possible violation of same domain policy
     * on {@link android.os.Build.VERSION_CODES#ICE_CREAM_SANDWICH} and earlier
     * devices, you should explicitly set this value to {@code false}.
     * <p>
     * The default value is true for API level
     * {@link android.os.Build.VERSION_CODES#ICE_CREAM_SANDWICH_MR1} and below,
     * and false for API level {@link android.os.Build.VERSION_CODES#JELLY_BEAN}
     * and above.
     *
     * @param flag whether JavaScript running in the context of a file scheme
     *             URL should be allowed to access content from any origin
     */
    public WebSettingsWrapper setAllowUniversalAccessFromFileURLs(boolean flag) {
        if (srcWebSettings != null) {
            srcWebSettings.setAllowUniversalAccessFromFileURLs(flag);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setAllowUniversalAccessFromFileURLs(flag);
        }
        return this;
    }

    /**
     * 允许通过 file url 加载的 Javascript 读取其他的本地文件,
     * Android 4.1 之前默认是true，在 Android 4.1 及以后默认是false,也就是禁止
     *
     * @param flag
     * @return
     */
    public WebSettingsWrapper setAllowFileAccessFromFileURLs(boolean flag) {
        if (srcWebSettings != null) {
            srcWebSettings.setAllowFileAccessFromFileURLs(flag);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setAllowFileAccessFromFileURLs(flag);
        }
        return this;
    }

    public WebSettingsWrapper setPluginsEnabled(boolean flag) {
        if (srcWebSettings != null) {
            if (flag) {
                srcWebSettings.setPluginState(android.webkit.WebSettings.PluginState.ON);
            } else  {
                srcWebSettings.setPluginState(android.webkit.WebSettings.PluginState.OFF);
            }
        }
        if (x5WebSettings != null) {
            x5WebSettings.setPluginsEnabled(flag);
        }
        return this;
    }



    /**
     * 设置数据库路径
     * Sets the path to where database storage API databases should be saved.
     * In order for the database storage API to function correctly, this method
     * must be called with a path to which the application can write. This
     * method should only be called once: repeated calls are ignored.
     *
     * @param databasePath a path to the directory where databases should be
     *                     saved.
     * @deprecated Database paths are managed by the implementation and calling this method
     * will have no effect.
     */
    public WebSettingsWrapper setDatabasePath(String databasePath) {
        if (srcWebSettings != null) {
            srcWebSettings.setDatabasePath(databasePath);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setDatabasePath(databasePath);
        }
        return this;
    }

    /**
     * 是否启用缓存模式
     * Sets whether the Application Caches API should be enabled. The default
     * is false. Note that in order for the Application Caches API to be
     * enabled, a valid database path must also be supplied to
     * {@link #setAppCachePath}.
     *
     * @param flag true if the WebView should enable Application Caches
     */
    public WebSettingsWrapper setAppCacheEnabled(boolean flag) {
        if (srcWebSettings != null) {
            srcWebSettings.setAppCacheEnabled(flag);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setAppCacheEnabled(flag);
        }
        return this;
    }

    /**
     * Android 私有缓存存储，如果你不调用setAppCachePath方法，WebView将不会产生这个目录
     * Sets the path to the Application Caches files. In order for the
     * Application Caches API to be enabled, this method must be called with a
     * path to which the application can write. This method should only be
     * called once: repeated calls are ignored.
     *
     * @param appCachePath a String path to the directory containing
     *                     Application Caches files.
     * @see #setAppCacheEnabled
     */
    public WebSettingsWrapper setAppCachePath(String appCachePath) {
        if (srcWebSettings != null) {
            srcWebSettings.setAppCachePath(appCachePath);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setAppCachePath(appCachePath);
        }
        return this;
    }

    /**
     * 设置 AppCache 最大缓存值(现在官方已经不提倡使用，已废弃)
     * Sets the maximum size for the Application Cache content. The passed size
     * will be rounded to the nearest value that the database can support, so
     * this should be viewed as a guide, not a hard limit. Setting the
     * size to a value less than current database size does not cause the
     * database to be trimmed. The default size is {@link Long#MAX_VALUE}.
     * It is recommended to leave the maximum size set to the default value.
     *
     * @param appCacheMaxSize the maximum size in bytes
     * @deprecated In future quota will be managed automatically.
     */
    @Deprecated
    public WebSettingsWrapper setAppCacheMaxSize(long appCacheMaxSize) {
        if (srcWebSettings != null) {
            srcWebSettings.setAppCacheMaxSize(appCacheMaxSize);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setAppCacheMaxSize(appCacheMaxSize);
        }
        return this;
    }

    /**
     * 是否开启数据库缓存
     * Sets whether the database storage API is enabled. The default value is
     * false. See also {@link #setDatabasePath} for how to correctly set up the
     * database storage API.
     * <p>
     * This setting is global in effect, across all WebView instances in a process.
     * Note you should only modify this setting prior to making <b>any</b> WebView
     * page load within a given process, as the WebView implementation may ignore
     * changes to this setting after that point.
     *
     * @param flag true if the WebView should use the database storage API
     */
    public WebSettingsWrapper setDatabaseEnabled(boolean flag) {
        if (srcWebSettings != null) {
            srcWebSettings.setDatabaseEnabled(flag);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setDatabaseEnabled(flag);
        }
        return this;
    }

    /**
     * 开启DOM缓存
     * Sets whether the DOM storage API is enabled. The default value is false.
     *
     * @param flag true if the WebView should use the DOM storage API
     */
    public WebSettingsWrapper setDomStorageEnabled(boolean flag) {
        if (srcWebSettings != null) {
            srcWebSettings.setDomStorageEnabled(flag);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setDomStorageEnabled(flag);
        }
        return this;
    }

    public synchronized boolean getDomStorageEnabled() {
        if (srcWebSettings != null) {
            return srcWebSettings.getDomStorageEnabled();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getDomStorageEnabled();
        }
        return false;
    }

    public synchronized String getDatabasePath() {
        if (srcWebSettings != null) {
            return srcWebSettings.getDatabasePath();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getDatabasePath();
        }
        return "";
    }

    public synchronized boolean getDatabaseEnabled() {
        if (srcWebSettings != null) {
            return srcWebSettings.getDatabaseEnabled();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getDatabaseEnabled();
        }
        return false;
    }

    public synchronized boolean getJavaScriptEnabled() {
        if (srcWebSettings != null) {
            return srcWebSettings.getJavaScriptEnabled();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getJavaScriptEnabled();
        }
        return false;
    }

    /**
     * Tells JavaScript to open windows automatically. This applies to the
     * JavaScript function window.open(). The default is false.
     *
     * @param flag true if JavaScript can open windows automatically
     */
    public WebSettingsWrapper setJavaScriptCanOpenWindowsAutomatically(boolean flag) {
        if (srcWebSettings != null) {
            srcWebSettings.setJavaScriptCanOpenWindowsAutomatically(flag);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setJavaScriptCanOpenWindowsAutomatically(flag);
        }
        return this;
    }

    public synchronized boolean getJavaScriptCanOpenWindowsAutomatically() {
        if (srcWebSettings != null) {
            return srcWebSettings.getJavaScriptCanOpenWindowsAutomatically();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getJavaScriptCanOpenWindowsAutomatically();
        }
        return false;
    }

    public WebSettingsWrapper setDefaultTextEncodingName(String encoding) {
        if (srcWebSettings != null) {
            srcWebSettings.setDefaultTextEncodingName(encoding);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setDefaultTextEncodingName(encoding);
        }
        return this;
    }

    public boolean getMediaPlaybackRequiresUserGesture() {
        if (srcWebSettings != null) {
            return srcWebSettings.getMediaPlaybackRequiresUserGesture();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getMediaPlaybackRequiresUserGesture();
        }
        return false;
    }

    public WebSettingsWrapper setMediaPlaybackRequiresUserGesture(boolean flag) {
        if (srcWebSettings != null) {
            srcWebSettings.setMediaPlaybackRequiresUserGesture(flag);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setMediaPlaybackRequiresUserGesture(flag);
        }
        return this;
    }

    public WebSettingsWrapper setNeedInitialFocus(boolean flag) {
        if (srcWebSettings != null) {
            srcWebSettings.setNeedInitialFocus(flag);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setNeedInitialFocus(flag);
        }
        return this;
    }

    public WebSettingsWrapper setCacheMode(int mode) {
        if (srcWebSettings != null) {
            srcWebSettings.setCacheMode(mode);
        }
        if (x5WebSettings != null) {
            x5WebSettings.setCacheMode(mode);
        }
        return this;
    }

    public int getCacheMode() {
        if (srcWebSettings != null) {
            return srcWebSettings.getCacheMode();
        }
        if (x5WebSettings != null) {
            return x5WebSettings.getCacheMode();
        }
        return 0;
    }

    public WebSettingsWrapper setRenderPriority(android.webkit.WebSettings.RenderPriority priority) {
        if (srcWebSettings != null) {
            srcWebSettings.setRenderPriority(priority);
        }
        return this;
    }
}

