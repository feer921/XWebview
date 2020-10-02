package com.fee.xwebview

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * <P>DESC:
 * </p>
 * ******************(^_^)***********************
 */
object XWebViewHelper {
    @JvmStatic
    val BROADCAST_ACTION_OF_INIT_X5_RESULT: String = "action.com.common.x5.init"

    @JvmStatic
    val INTENT_KEY_NEED_BROADCAST_INIT_RESULT: String = "intent.key.need_broadcast"

    @JvmStatic
    val INTENT_KEY_CAN_DOWNLOAD_X5_WITHOUT_WIFI: String = "intent.key.download_without.wifi"

    @JvmStatic
    val INTENT_KEY_INIT_X5_RESLUT: String = "intent.key.init.x5.reslut"

    private var isX5InitOk = false


    internal fun setX5InitOk(isX5InitOk: Boolean) {
        this.isX5InitOk = isX5InitOk
    }












}