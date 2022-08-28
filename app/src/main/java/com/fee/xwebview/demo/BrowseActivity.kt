package com.fee.xwebview.demo

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fee.xwebview.core.ACompatWebViewClient
import com.fee.xwebview.demo.databinding.ActivityBrowseBinding
import com.fee.xwebview.views.DefLoadingHandle

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/18<br>
 * Time: 16:23<br>
 * <P>DESC:
 * 网页 浏览 Activity
 * </p>
 * ******************(^_^)***********************
 */
class BrowseActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mViewBinding: ActivityBrowseBinding

    private lateinit var tvHeaderTitle: TextView
    private lateinit var ivHeaderRefresh: ImageView

    private var mLastScreenOrientation = 0

    private val mCompatWebViewClient by lazy(LazyThreadSafetyMode.NONE) {
        object : ACompatWebViewClient(){
            override fun shouldOverrideUrlLoading(url: String?): Boolean {
                mViewBinding.commonWebview.loadUrl(url)
                return true
            }

            /**
             * Callback from X5 webviwe
             * @param errorCode
             * @param description
             * @param failingUrl
             */
            override fun onReceivedError(
                errorCode: Int,
                description: String?,
                failingUrl: String?
            ) {
            }

            override fun onPageFinished(url: String?) {
            }

            /**
             * Tell the host application the current progress of loading a page.
             *
             *
             * //     * @param view        The WebView that initiated the callback.
             *
             * @param newProgress Current page loading progress, represented by
             */
            override fun onProgressChanged(newProgress: Int) {
            }

            /**
             * Notify the host application of a change in the document title.
             *
             * @param title A String containing the new title of the document.
             */
            override fun onReceivedTitle(title: String?) {
                tvHeaderTitle.text = title
            }

            override fun handleOnShowCustomView(view: View?): ViewGroup? {
                requestedOrientation  = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                //当 播放的视频 全屏(横屏时，如果按返回键，则会先恢复到竖屏)
                return mViewBinding.flContainer

            }

            override fun onHideCustomView() {
                super.onHideCustomView()
                requestedOrientation  = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
    }

    private val tag = "BrowseActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.let {
            it.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)

        }
        mLastScreenOrientation = requestedOrientation
        supportActionBar?.hide()
        Log.d(tag, "--> onCreate() ")
        mViewBinding = ActivityBrowseBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)
        mViewBinding.llHeaderView.let {
            it.ivBtnHeaderBack.setOnClickListener(this)
            ivHeaderRefresh = it.ivBtnHeaderRefresh
//            ivHeaderRefresh = findViewById(R.id.ivBtnHeaderRefresh);
            ivHeaderRefresh.setOnClickListener(this)
            it.ivBtnHeaderClose.setOnClickListener(this)
            tvHeaderTitle = it.tvHeaderTitle
            tvHeaderTitle.text = ""
            it.root.setPadding(0,60,0,0)
        }

        mViewBinding.commonWebview.run {
            isEnableBackBrowse = true
            val defLoadingHandle = DefLoadingHandle(context)
            val myProgressBar = View.inflate(
                context,
                R.layout.view_progressbar,
                null
            )
//            val outSideProgressBar = ProgressBar(context,null,R.attr.myHorizontalProgressBarAttrs)
//            defLoadingHandle.configOutSideProgressBar(outSideProgressBar)

            defLoadingHandle.configOutSideProgressBar(myProgressBar as ProgressBar)
            setLoadingHandle(defLoadingHandle)
            this.mOutSideACompatWebViewClient = mCompatWebViewClient
            loadUrl(
//            "http://www.doubiekan.net/"
                "https://www.xiaoli123.cc/"
            ) {
//                XWebViewHelper.commonConfigWebViewSettings(this)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding.commonWebview.release(true, isWillDestroy = true)
        Log.d(tag, "--> onDestroy() ")
    }

    override fun onBackPressed() {
        val isCanGobackBrowse = mViewBinding.commonWebview.onBackBrowse()
        if (isCanGobackBrowse) {
            return
        }
        super.onBackPressed()
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBtnHeaderBack -> {
                onBackPressed()
            }
            R.id.ivBtnHeaderClose -> {
                finish()
            }
            R.id.ivBtnHeaderRefresh -> {
                ivHeaderRefresh.animate().rotationBy(360f).setDuration(1000L)
                    /*.withEndAction {

                }*/
                    .start()
                mViewBinding.commonWebview.reload()
            }
            else -> {
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.i(tag, "--> onConfigurationChanged() newConfig = $newConfig")

    }
}