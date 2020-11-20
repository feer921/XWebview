package com.fee.xwebview.demo

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fee.xwebview.XWebViewHelper
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
class BrowseActivity : AppCompatActivity(),View.OnClickListener {
    //    private lateinit var webView: CommonWebView
    private lateinit var dataBinding: ActivityBrowseBinding

    private lateinit var tvHeaderTitle: TextView
    private lateinit var ivHeaderRefresh: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_browse)
        dataBinding.llHeaderView.let {
            findViewById<View>(R.id.ivBtnHeaderBack).setOnClickListener(this)
            ivHeaderRefresh = findViewById(R.id.ivBtnHeaderRefresh);
            ivHeaderRefresh.setOnClickListener(this)
            findViewById<View>(R.id.ivBtnHeaderClose).setOnClickListener(this)
            tvHeaderTitle = findViewById(R.id.tvHeaderTitle)
            tvHeaderTitle.text = ""
        }

        dataBinding.commonWebview.isEnableBackBrowse = true
        dataBinding.commonWebview.run {
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
                loadUrl(
//            "http://www.doubiekan.net/"
                    "https://www.baidu.com"
                ){
                    XWebViewHelper.commonConfigWebViewSettings(this)
                }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dataBinding.commonWebview.release(true, isWillDestroy = true)
    }

    override fun onBackPressed() {
        val isCanGobackBrowse = dataBinding.commonWebview.onBackBrowse()
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
            R.id.ivBtnHeaderClose ->{
                finish()
            }
            R.id.ivBtnHeaderRefresh ->{
                ivHeaderRefresh.animate().rotationBy(360f).setDuration(1000L)/*.withEndAction {

                }*/.start()
                dataBinding.commonWebview.reload()
            }
            else -> {
            }
        }
    }
}