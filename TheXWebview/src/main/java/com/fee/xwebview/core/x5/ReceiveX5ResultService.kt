package com.fee.xwebview.core.x5

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.fee.xwebview.L
import com.fee.xwebview.XWebViewHelper

/**
 * ******************(^_^)***********************<br>
 * User: fee(QQ/WeiXin:1176610771)<br>
 * Date: 2020/10/02<br>
 * Time: 14:08<br>
 * <P>DESC:
 * 转到主进程上接收 X5的初始化结果的 Service
 * </p>
 * ******************(^_^)***********************
 */
internal class ReceiveX5ResultService : Service() {

    override fun onCreate() {
        super.onCreate()
        L.e("ReceiveX5ResultService", "--> onCreate()")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            if (it.hasExtra(XWebViewHelper.INTENT_KEY_INIT_X5_RESLUT)) {
                val isX5InitOk = it.getBooleanExtra(XWebViewHelper.INTENT_KEY_INIT_X5_RESLUT, false)
                L.e("ReceiveX5ResultService", "--> onStartCommand() isX5InitOk = $isX5InitOk")
                XWebViewHelper.setX5InitOk(isX5InitOk)
            }
        }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}