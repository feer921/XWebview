package com.fee.xwebview.views

import android.content.Context
import android.view.View
import android.widget.ProgressBar

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * <P>DESC:
 * 接口[ILoadingHandle] 的一个实现类
 * </p>
 * ******************(^_^)***********************
 */
open class DefLoadingHandle(protected val context: Context) : ILoadingHandle {

    protected var progressBar: ProgressBar? = null

    init {
        if (progressBar == null) {
//            progressBar = ProgressBar(context,null,0, R.style.Widget_AppCompat_ProgressBar_Horizontal) API 21
            // https://blog.csdn.net/wzy_1988/article/details/49619773 defStyleAttr 参数
            //通过构造方法的第三个参数来指定 ProgressBar 的样式(但该样式依赖于当前View所处的Activity的 theme > App整体的theme)
            progressBar = ProgressBar(context,null,android.R.attr.progressBarStyleHorizontal)
        }
        progressBar?.max = 100
        progressBar?.isIndeterminate = false
    }

    fun configProgressBar(configBlock: ProgressBar.() -> Unit) {
        progressBar?.let {
            it.max = 100
            it.configBlock()// custom your configs
        }
    }

    fun configOutSideProgressBar(progressBar: ProgressBar) {
        this.progressBar = progressBar
    }
    /**
     * 实现类来提供 一个用来 显示正在加载 webview或者其他 的View
     * eg.: ProgressBar
     */
    override fun provideShowLoadingView(): View? {
        return progressBar
    }

    /**
     * 数据、WebView加载完成
     * @param theFinishData 加载完成时可能返回的 数据 .: Webview加载完成时的 url
     */
    override fun onLoadingFinish(theFinishData: Any?) {
        progressBar?.visibility = View.GONE
    }

    /**
     * 正在加载的 进度
     * @param isLoadingStart 是否是 开始加载. def= false(正在加载中); 如果该参数为 true，则不要关注 [newProgress]
     * @param newProgress 0~100
     */
    override fun onLoadingProgress(isLoadingStart: Boolean, newProgress: Int) {
        if (isLoadingStart) {
            progressBar?.progress = 0
            progressBar?.visibility = View.VISIBLE
        }
        else{
            progressBar?.progress = newProgress
        }
    }

}