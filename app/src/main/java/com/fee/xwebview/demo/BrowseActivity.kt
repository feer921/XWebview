package com.fee.xwebview.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fee.xwebview.XWebViewHelper
import com.fee.xwebview.demo.databinding.ActivityBrowseBinding

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
class BrowseActivity : AppCompatActivity() {
    //    private lateinit var webView: CommonWebView
    private lateinit var dataBinding: ActivityBrowseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_browse)
        dataBinding.commonWebview
        .loadUrl("www.baidu.com"){
            XWebViewHelper.commonConfigWebViewSettings(this)
        }
    }
}