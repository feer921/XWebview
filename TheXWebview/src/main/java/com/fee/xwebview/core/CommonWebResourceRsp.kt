package com.fee.xwebview.core

import android.webkit.WebResourceResponse

//import com.tencent.smtt.export.external.interfaces.WebResourceResponse

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 16:51<br>
 * <P>DESC:
 * 目的为包裹 原生WebView的 资源响应对象 和 X5 WebView的资源响应对象
 * </p>
 * ******************(^_^)***********************
 */
class CommonWebResourceRsp {
//    var x5WebResourceResponse: WebResourceResponse? = null
    var srcWebResourceResponse: android.webkit.WebResourceResponse? = null

//    constructor(x5WebResourceResponse: WebResourceResponse?){
//        this.x5WebResourceResponse = x5WebResourceResponse
//    }
    constructor(srcWebResourceResponse: android.webkit.WebResourceResponse?){
        this.srcWebResourceResponse = srcWebResourceResponse
    }
}