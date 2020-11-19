package com.fee.xwebview.core

import com.fee.xwebview.core.src.ASrcWebChromeClient
import com.fee.xwebview.core.src.ASrcWebViewClient
import com.fee.xwebview.core.x5.AX5WebChromeClient
import com.fee.xwebview.core.x5.AX5WebViewClient

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 19:40<br>
 * <P>DESC:
 * WebView 所需要配置的 WebViewClient 和 WebViewChromeClient，根据当前配置的 WebView类型(X5、原生)
 * 而构建对应的 WebViewClient 和 WebViewChromeClient
 * </p>
 * ******************(^_^)***********************
 */
open class WebViewClientAndChromeClientSelector(
    private val webViewClientType: Int, private val webviewClientImp: IWebViewClient?,
   private val webviewChromeClientImp: IWebChromeClient?
) {
    private var ax5WebViewClient: AX5WebViewClient? = null

    private var ax5WebChromeClient: AX5WebChromeClient? = null

    private var srcWebViewClient: ASrcWebViewClient? = null

    private var srcWebChromeClient: ASrcWebChromeClient? = null

    /**
     * 是否设置成: 本类 可兼容 即提供 X5的 [AX5WebViewClient] 和 [AX5WebChromeClient] 也可以提供 原生WebView的 [ASrcWebViewClient]和[ASrcWebChromeClient]
     * 而不用去重新创建本类的实例
     * def:false
     */
    var isFullCompatAllClients: Boolean = false

    init {
        when (webViewClientType) {
            CLIENT_TYPE_SRC -> {
                srcWebViewClient = ASrcWebViewClient()
                srcWebViewClient?.setWebViewClientListener(webviewClientImp)
                srcWebChromeClient = ASrcWebChromeClient()
                srcWebChromeClient?.setChromeClientListener(webviewChromeClientImp)
            }
            CLIENT_TYPE_X5 -> {
                ax5WebViewClient = AX5WebViewClient()
                ax5WebViewClient?.setWebViewClientListener(webviewClientImp)
                ax5WebChromeClient = AX5WebChromeClient()
                ax5WebChromeClient?.setWebChromeClientListener(webviewChromeClientImp)
            }
            else -> {
            }
        }
    }

    companion object ClientSelector{
        const val CLIENT_TYPE_X5 = 10
        const val CLIENT_TYPE_SRC = CLIENT_TYPE_X5 + 1

        fun aSrcWebViewClientsSelector(
            webviewClientImp: IWebViewClient?,
            webviewChromeClientImp: IWebChromeClient?
        ):WebViewClientAndChromeClientSelector = WebViewClientAndChromeClientSelector(
            CLIENT_TYPE_SRC, webviewClientImp, webviewChromeClientImp
        )


        fun aX5WebViewClientsSelector(
            webviewClientImp: IWebViewClient?,
            webviewChromeClientImp: IWebChromeClient?
        ):WebViewClientAndChromeClientSelector = WebViewClientAndChromeClientSelector(
            CLIENT_TYPE_X5, webviewClientImp, webviewChromeClientImp
        )

    }


    fun free() {
        ax5WebViewClient?.setWebViewClientListener(null)
        ax5WebChromeClient?.setWebChromeClientListener(null)

        srcWebViewClient?.setWebViewClientListener(null)
        srcWebChromeClient?.setChromeClientListener(null)
    }

    open fun getX5WebViewClient(): AX5WebViewClient? {
        if (ax5WebViewClient == null && isFullCompatAllClients) {
            ax5WebViewClient = AX5WebViewClient()
            ax5WebViewClient?.setWebViewClientListener(webviewClientImp)
        }
        return ax5WebViewClient
    }

    open fun getX5WebChromeClient(): AX5WebChromeClient? {
        if (ax5WebChromeClient == null && isFullCompatAllClients) {
            ax5WebChromeClient = AX5WebChromeClient()
            ax5WebChromeClient?.setWebChromeClientListener(webviewChromeClientImp)
        }
        return ax5WebChromeClient
    }

    open fun getSrcWebViewClient(): ASrcWebViewClient? {
        if (srcWebViewClient == null && isFullCompatAllClients) {
            srcWebViewClient = ASrcWebViewClient()
            srcWebViewClient?.setWebViewClientListener(webviewClientImp)
        }
        return srcWebViewClient
    }

    open fun getSrcWebChromeClient(): ASrcWebChromeClient? {
        if (srcWebChromeClient == null && isFullCompatAllClients) {
            srcWebChromeClient = ASrcWebChromeClient()
            srcWebChromeClient?.setChromeClientListener(webviewChromeClientImp)
        }
        return srcWebChromeClient
    }

}