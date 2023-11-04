package com.fee.xwebview.core

import android.annotation.SuppressLint
import android.net.Uri
import android.webkit.WebResourceRequest
import com.fee.xwebview.Api
//import com.tencent.smtt.export.external.interfaces.WebResourceRequest

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 17:08<br>
 * <P>DESC:
 * 通用的 WebView 资源 请求
 * </p>
 * ******************(^_^)***********************
 */
open class CommonWebResourceRequest {
    protected var isX5WebResourceReq = false
    protected var curUri: Uri? = null


    protected var isForMainFrame = false

    protected var isRedirect = false

    protected var isHasGesture = false

    protected var curMethod: String? = null

    protected var justReqUrl:String? = ""

    protected var curRequestHeaders: Map<String, String>? = null

    constructor(justUrl: String?) {
        justReqUrl = justUrl
    }

//    constructor(x5WebResourceRequest: WebResourceRequest?) {
//        if (x5WebResourceRequest != null) {
//            isX5WebResourceReq = true
//            curUri = x5WebResourceRequest.url
//            curMethod = x5WebResourceRequest.method
//            isRedirect = x5WebResourceRequest.isRedirect
//            isForMainFrame = x5WebResourceRequest.isForMainFrame
//            isHasGesture = x5WebResourceRequest.hasGesture()
//            curRequestHeaders = x5WebResourceRequest.requestHeaders
//        }
//    }

    @SuppressLint("NewApi")
    constructor(srcWebResourceRequest: android.webkit.WebResourceRequest?) {
        if (srcWebResourceRequest != null) {
            if (Api.isApiCompatible(21)) {
                curUri = srcWebResourceRequest.url
                curMethod = srcWebResourceRequest.method
                isForMainFrame = srcWebResourceRequest.isForMainFrame
                isHasGesture = srcWebResourceRequest.hasGesture()
                curRequestHeaders = srcWebResourceRequest.requestHeaders
            }
            //            if (ApiUtil.isApiCompatible(24)) {
//                try {
//                    this.isRedirect = srcWebResourceRequest.isRedirect();
//                }catch (Exception ignore){}//java.lang.AbstractMethodError: abstract method
//            }
        }
    }



    fun getCurUrlInfo(): String? {
        return if (curUri == null) justReqUrl else curUri.toString()
    }

    override fun toString(): String {
        return "CommonWebResourceRequest(isX5WebResourceReq=$isX5WebResourceReq, curUri=$curUri, isForMainFrame=$isForMainFrame, isRedirect=$isRedirect, isHasGesture=$isHasGesture, curMethod=$curMethod, justReqUrl=$justReqUrl, curRequestHeaders=$curRequestHeaders)"
    }


}