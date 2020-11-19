package com.fee.xwebview.views

import android.view.View

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/18<br>
 * Time: 16:06<br>
 * <P>DESC:
 * 数据、WebView 的加载处理接口
 * </p>
 * ******************(^_^)***********************
 */
interface ILoadingHandle {
    /**
     * 实现类来提供 一个用来 显示正在加载 webview或者其他 的View
     * eg.: ProgressBar
     */
    fun provideShowLoadingView(): View?

    /**
     * 数据、WebView加载完成
     * @param theFinishData 加载完成时可能返回的 数据 .: Webview加载完成时的 url
     */
    fun onLoadingFinish(theFinishData: Any?)

    /**
     * 正在加载的 进度
     * @param isLoadingStart 是否是 开始加载. def= false(正在加载中); 如果该参数为 true，则不要关注 [newProgress]
     * @param newProgress 0~100
     */
    fun onLoadingProgress(isLoadingStart: Boolean = false, newProgress: Int)


}